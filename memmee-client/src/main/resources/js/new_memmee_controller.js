function NewMemmeeController($scope, $http, broadCastService) {

    $scope.user = broadCastService.user;
    $scope.memmee = {
        id: '',
        userId: $scope.user ? $scope.user.id : null,
        text: ''
    };

    $scope.$on('attachmentUploadSuccess', function() {
        $scope.memmee.attachment = broadCastService.attachment;
        console.log("attachment was uploaded");
    });


    $scope.createMemmee = function()
    {
        $http({method: 'POST', url: '/memmeerest/insertmemmee/?apiKey=' + $scope.user.apiKey, data: $scope.memmee}).
            success(function(data, status, headers, config) {
                console.log('you have saved a memmee');
            }).
            error(function(data, status, headers, config) {
                console.log('error while saving your user');
                console.log(data);
            });
    }
}

NewMemmeeController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];