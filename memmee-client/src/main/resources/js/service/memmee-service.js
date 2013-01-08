'use strict'

Memmee.Services.factory('memmeeService', function (configuration, $q, $rootScope, $http) {

    return {
        share: function(requestUrl, memmee) {
            var deferred = $q.defer();

            $http.put(requestUrl, memmee).
                success(function (data, status, headers, config) {
                    deferred.resolve(data);
                }).
                error(function (data, status, headers, config) {
                    deferred.reject(data);
                });

            return deferred.promise;
        }
    }

});
