var memmeeServices = angular.module('admin.services', ['ngResource']);

memmeeServices.factory('adminService', ["$http", "$rootScope", function ($http, $scope) {
    'use strict';
    return {
        showAllInspirations: function (resultHandler, faultHandler) {
            $http({method: 'GET', url: '/memmeeinspirationrest/inspirations/all' }).then(
                function (response) {
                    if (resultHandler) {
                        resultHandler(response.data);
                    }
                },
                function (response) {
                    if (faultHandler) {
                        faultHandler(response.data);
                    }
                }
            );
        },

        showAllCategories: function(resultHandler, faultHandler) {
            $http({method: 'GET', url: '/memmeeinspirationrest/getAllCategories'}).then(
                function(response) {
                    if(resultHandler) {
                        resultHandler(response.data);
                    }
                },
                function(response) {
                    if(faultHandler) {
                        faultHandler(response.data);
                    }
                }
            )
        },

        addCategory: function(categoryToAdd, resultHandler, faultHandler) {
            $http({method: 'POST', url: '/memmeeinspirationrest/addCategory', data:categoryToAdd}).then(
                function(response) {
                    if(resultHandler) {
                        resultHandler(response.data);
                    }
                },
                function(response) {
                    if(faultHandler) {
                        faultHandler(response.data);
                    }
                }
            )
        }


    };

}]);
