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

    //UI
    $scope.innerScrollerWidth = function() {
        return {'width': (memmees.length * 5000).toString() + "px"};
    }

    $scope.imageStyle = function(memmee) {
        return {'background-image': 'url(/memmee/' + memmee.attachment.filePath + ')'};
    }

}

ArchiveListController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];