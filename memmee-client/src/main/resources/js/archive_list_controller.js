function ArchiveListController($scope, $http, broadCastService) {
    $scope.user = broadCastService.user;
    $scope.memmees = null;
    $scope.scrollableLeft = false;
    $scope.scrollableRight = true;
    $scope.amountScrolledRight = 0;

    //Action Handlers
    $scope.onMemmeeSelect = function (memmee) {
        broadCastService.memmeeSelectedArchiveListController(memmee);
    };

    //Broadcast Handlers
    $scope.$on(CreateModeControllerEvents.get('MEMMEE_CREATED'), function () {
        $scope.getMemmees();
    });

    $scope.$on(ViewModeControllerEvents.get('MEMMEE_DELETED'), function () {
        $scope.getMemmees();
    });

    //Service Calls
    $scope.getMemmees = function () {
        $http({method:'GET', url:'/memmeerest/getmemmees?apiKey=' + $scope.user.apiKey}).
            success(function (data, status, headers, config) {
                console.log('your memmee has been loaded');
                $scope.memmees = data;
            }).
            error(function (data, status, headers, config) {
                console.log('error loading memmees');
            });
    };

    //UI
    $scope.innerScrollerWidth = function (memmees) {
        if (memmees !== null) {
            return {'width':(memmees.length * 450).toString() + "px"};
        }

        //TODO: This might be a bad value - added this to calm the error it was throwing -AP
        return {'width':"450px"};
    };

    $scope.imageStyle = function (memmee) {
        return {'background-image':'url(' + memmee.attachment.thumbFilePath + ')'};
    };

    $scope.getDisplayDate = function (memmee) {
        if (memmee.displayDate != null) {
            var myDate = new Date.parse(memmee.displayDate);
            return MemmeeDateUtil.longMonth(myDate.getMonth()) + " " + myDate.getDate().toString() + ", " + myDate.getFullYear().toString();
        }
        return null;
    };

    $scope.addScrollListener = function(clazz) {
        $(clazz).scroll(function(e) {
            $scope.amountScrolledRight = $(e.currentTarget).scrollLeft();
        });
    };

    $scope.leftScroll = function (clazz) {
        var scrollable = $scope.amountScrolledRight >= 300;
        $scope.animateScroll($(clazz), "+=300px", scrollable, true)
    };

    $scope.rightScroll = function (clazz) {
        var scrollable = $scope.amountScrolledRight < (300 * $scope.memmees.length - 700);
        $scope.animateScroll($(clazz), "-=300px", scrollable, false);
    };

    $scope.animateScroll = function (obj, param, scrollable, left) {
        if (scrollable) {
            obj.animate({"margin-left":param}, 400, function () {
                if (left) {
                    $scope.amountScrolledRight -= 300;
                } else {
                    $scope.amountScrolledRight += 300;
                }
            });
        }
    };

    //Initialization
    $scope.getMemmees();
}

ArchiveListController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];