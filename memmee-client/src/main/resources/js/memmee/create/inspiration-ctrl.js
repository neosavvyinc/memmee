function InspirationController($scope, $http, broadCastService) {
    //Constants
    "use strict";
    var ViewStates = (function () {
        var private_states = {
            'VIEW_INSPIRATION_STATE': 'viewInspirationState',
            'HIDDEN_INSPIRATION_STATE': 'hiddenInspirationState'
        };

        return {
            get: function (name) {
                return private_states[name];
            }
        };
    }());

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.viewState = ViewStates.get('VIEW_INSPIRATION_STATE');
            $scope.user = broadCastService.user;
            $scope.inspiration = null;
            $scope.nextStartingInspiration = null;
            $scope.previousStartingInspiration = null;
        });

    //State Handers
    $scope.hiddenInspiration = function () {
        return $scope.viewState == ViewStates.get('HIDDEN_INSPIRATION_STATE');
    };

    $scope.viewInspiration = function () {
        return $scope.viewState == ViewStates.get('VIEW_INSPIRATION_STATE');
    };

    //Action Handlers
    $scope.getRandomInspiration = function () {
        $http({method:'GET', url:'/memmeeinspirationrest/getrandominspiration?apiKey=' + $scope.user.apiKey}).
            success(function (data, status, headers, config) {
                console.log('The random inspiration has been loaded');
                $scope.inspiration = data;
            }).error(function (data, status, headers, config) {
                console.log('error loading your doggone inspiration');
            });
    };

    $scope.getNextInspiration = function () {
        if ($scope.inspiration == undefined || $scope.inspiration == null)
            throw "You must have a selected inspiration to choose the next inspiration."

        if (!$scope.nextStartingInspiration) {
            $scope.previousStartingInspiration = null;
            $scope.nextStartingInspiration = $scope.inspiration;
        }

        $http({method:'GET', url:'/memmeeinspirationrest/getnextinspiration?apiKey=' + $scope.user.apiKey +
            "&startingId=" + $scope.nextStartingInspiration.id.toString() +
            "&currentId=" + $scope.inspiration.id.toString()}).
            success(function (data, status, headers, config) {
                console.log('inspiration index: ' + data.inspirationCategoryIndex.toString());
                console.log('category index: ' + data.inspirationCategory.index.toString());
                $scope.inspiration = data;
            }).error(function (data, status, headers, config) {
                console.log('error loading your doggone inspiration');
            });
    };

    $scope.getPreviousInspiration = function () {
        if ($scope.inspiration == undefined || $scope.inspiration == null)
            throw "You must have a selected inspiration to choose the next inspiration."

        if (!$scope.previousStartingInspiration) {
            $scope.nextStartingInspiration = null;
            $scope.previousStartingInspiration = $scope.inspiration;
        }

        $http({method:'GET', url:'/memmeeinspirationrest/getpreviousinspiration?apiKey=' + $scope.user.apiKey +
            "&startingId=" + $scope.previousStartingInspiration.id.toString() +
            "&currentId=" + $scope.inspiration.id.toString()}).
            success(function (data, status, headers, config) {
                console.log('inspiration index: ' + data.inspirationCategoryIndex.toString());
                console.log('category index: ' + data.inspirationCategory.index.toString());
                $scope.inspiration = data;
            }).error(function (data, status, headers, config) {
                console.log('error loading your doggone inspiration');
            });
    };

    $scope.toggleViewState = function () {
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

InspirationController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];