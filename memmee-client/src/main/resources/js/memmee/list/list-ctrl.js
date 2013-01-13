function ArchiveListController($scope, $http, broadCastService) {
    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = broadCastService.user;
            $scope.memmees = null;
            $scope.memmeeSets = null;
            $scope.scrollableLeft = false;
            $scope.scrollableRight = true;
            $scope.amountScrolledRight = 0;
        });

    //Action Handlers
    $scope.onMemmeeSelect = function (memmee) {
        //console.log("onMemmeeSelect() ---- memmee: " + memmee.text);
        broadCastService.memmeeSelectedArchiveListController(memmee);
    };

    $scope.onCreateMemmee = function () {
        broadCastService.createModeStartedCreateModeController();
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

        if( !$scope.user )
        {
            console.log("ERROR: No user set - probably should reroute out of here");
            return;
        }

        $http({method:'GET', url:'/memmeerest/getmemmees?apiKey=' + $scope.user.apiKey}).
            success(function (data, status, headers, config) {
                console.log('your memmee has been loaded');
                $scope.memmees = data;

                if ($scope.memmees) {
                    var memmeeSets = [
                        []
                    ];

                    for (var i = 0; i < $scope.memmees.length; i++) {
                        if (memmeeSets[memmeeSets.length - 1].length >= 4)
                            memmeeSets.push([]);

                        memmeeSets[memmeeSets.length - 1].push($scope.memmees[i]);
                    }

                    $scope.memmeeSets = memmeeSets;
                }
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
            return MemmeeDateUtil.standardDate(new Date.parse(memmee.displayDate));
        }
        return null;
    };

    $scope.getTruncatedText = function(text) {
       return StringUtil.truncate(text, 40, true);
    };

    $scope.initializeScrolling = function (clazz) {
        $(clazz).scrollable();
    }

    //Initialization
    $scope.getMemmees();
}

ArchiveListController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];