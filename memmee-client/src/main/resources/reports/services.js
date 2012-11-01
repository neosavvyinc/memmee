var memmeeServices = angular.module('reporting.services', ['ngResource']);

memmeeServices.factory('reportingService', ["$http", "$rootScope", function ($http, $scope) {
    'use strict';
    return {
        showAllRegistered: function (resultHandler, faultHandler) {
            $http({method: 'GET', url: '/reporting/users' }).then(
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
        showAllUsersWithCompletedProfiles: function (resultHandler, faultHandler) {
            $http({method: 'GET', url: '/reporting/users/completed/profile' }).then(
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
        showAllUsersWithoutCompletedProfiles: function (resultHandler, faultHandler) {
            $http({method: 'GET', url: '/reporting/users/no/profile' }).then(
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
        showAllUsersWithMemmeeCount: function (resultHandler, faultHandler) {
            $http({method: 'GET', url: '/reporting/users/memmeecount' }).then(
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
        showAllUsersWhoHaveNeverMemmeed: function (resultHandler, faultHandler) {
            $http({method: 'GET', url: '/reporting/users/no/memmeecount' }).then(
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
        }

    };

}]);
