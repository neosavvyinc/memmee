function InspirationController($scope, $http, broadCastService) {
    //Constants
    $scope.ViewStates = (function () {
        var private = {
            'VIEW_INSPIRATION_STATE':'viewInspirationState',
            'HIDDEN_INSPIRATION_STATE':'hiddenInspirationState'
        };

        return {
            get: function (name) {
                return private[name];
            }
        }
    })();

    //Properties
    $scope.viewState = ViewStates.get('VIEW_INSPIRATION_STATE');
    $scope.user = broadCastService.user;
    $scope.inspiration = null;

    //Action Handlers
    $scope.getRandomInspiration = function () {
       $http({method: 'GET', url: '/memmeeinspirationrest/getrandominspiration?apiKey=' + $scope.user.apiKey}).
           success(function (data, status, headers, config) {
               console.log('The random inspiration has been loaded');
               $scope.inspiration = data;
           }).error(function (data, status, headers, config) {
               console.log('error loading your doggone inspiration');
           });
    }
}

InspirationController.$inject = ['$scope', '$http', 'memmeeBroadCastService']