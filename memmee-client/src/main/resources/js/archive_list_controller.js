function ArchiveListController($scope, $http, broadCastService) {
    $scope.user = broadCastService.user;
    $scope.memmees = null;

    $http({method:'GET', url:'/memmeerest/getmemmees?apiKey=' + $scope.user.apiKey}).
        success(function (data, status, headers, config) {
            console.log('your memmee has been loaded');
            $scope.memmees = data;
        }).
        error(function (data, status, headers, config) {
              console.log('error loading memmees');
        });

    //Action Handlers
    $scope.onMemmeeSelect = function(memmee) {
        broadCastService.memmeeSelectedArchiveListController(memmee);
    };

    //UI
    $scope.innerScrollerWidth = function(memmees) {
        if( memmees !== null )
        {
            return {'width': (memmees.length * 450).toString() + "px"};
        }

        //TODO: This might be a bad value - added this to calm the error it was throwing -AP
        return {'width': "450px"};
    };

    $scope.imageStyle = function(memmee) {
        return {'background-image': 'url(' + memmee.attachment.thumbFilePath + ')'};
    };

    $scope.getDisplayDate = function(memmee) {
        if (memmee.displayDate != null) {
            var myDate = new Date.parse(memmee.displayDate);
            return MemmeeDateUtil.shortMonth(myDate.getMonth()) + " " + myDate.getDate().toString();
        }
        return null;
    };
}

ArchiveListController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];