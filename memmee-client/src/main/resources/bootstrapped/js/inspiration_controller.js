function InspirationController($scope, $http, broadCastService) {
    //Constants
    var ViewStates = (function () {
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

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.viewState = ViewStates.get('VIEW_INSPIRATION_STATE');
            $scope.user = broadCastService.user;
            $scope.inspiration = null;
        });

    //State Handers
    $scope.hiddenInspiration = function() {
        return $scope.viewState == ViewStates.get('HIDDEN_INSPIRATION_STATE');
    };

    $scope.viewInspiration = function() {
        return $scope.viewState == ViewStates.get('VIEW_INSPIRATION_STATE');
    };

    //Action Handlers
    $scope.getRandomInspiration = function (excludeId) {
        var appendedParams = "";

        if (excludeId != undefined && excludeId != null) {
             appendedParams = "&excludeId=" + excludeId.toString();
        }

       $http({method: 'GET', url: '/memmeeinspirationrest/getrandominspiration?apiKey=' + $scope.user.apiKey + appendedParams}).
           success(function (data, status, headers, config) {
               console.log('The random inspiration has been loaded');
               $scope.inspiration = data;
           }).error(function (data, status, headers, config) {
               console.log('error loading your doggone inspiration');
           });
    };

    $scope.toggleViewState = function() {
        if ($scope.viewInspiration()) {
            $scope.viewState = ViewStates.get('HIDDEN_INSPIRATION_STATE');
            $scope.getRandomInspiration();
        } else {
            $scope.viewState = ViewStates.get('VIEW_INSPIRATION_STATE');
        }
    };

    //Initialization
    $scope.getRandomInspiration();
}

InspirationController.$inject = ['$scope', '$http', 'memmeeBroadCastService']