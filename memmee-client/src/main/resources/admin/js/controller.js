function UserController($scope, $http) {

    $scope.hello = 'hello';

    $scope.user = {
        firstName: '',
        lastName: '',
        pass: '',
        email: ''
    };

    $scope.users = {};


    $scope.loadUsers = function() {
        $http({method: 'GET', url: '/memmeeuserrest/user'}).
            success(function(data, status, headers, config) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.users = data;
            }).
            error(function(data, status, headers, config) {
                console.log('error while getting users');
            });

    }

    $scope.saveUser = function()
    {
        if( $scope.user.id == null )
        {
            $scope.user.id = 1;
            $http({method: 'POST', url: '/memmeeuserrest/user', data: $scope.user}).
                success(function(data, status, headers, config) {
                    $scope.loadUsers();
                    $scope.newUser();
                }).
                error(function(data, status, headers, config) {
                    console.log('error while saving a new user');
                });
        }
        else
        {
            $http({method: 'PUT', url: '/memmeeuserrest/user/' + $scope.user.id, data: $scope.user}).
                success(function(data, status, headers, config) {
                    $scope.loadUsers();
                    $scope.newUser();
                }).
                error(function(data, status, headers, config) {
                    console.log('error while updating an existing user');
                });


        }
    }

    $scope.editUser = function( user )
    {
        $scope.user = user;
        console.log("user.id=" + user.id);
        console.log("user=" + user);
    }

    $scope.deleteUser = function( )
    {
        $http({method: 'DELETE', url: '/memmeeuserrest/user/' + $scope.user.id, data: $scope.user}).
            success(function(data, status, headers, config) {
                $scope.loadUsers();
                $scope.newUser();
            }).
            error(function(data, status, headers, config) {
                console.log('error while updating an existing user');
            });
    }

    $scope.newUser = function()
    {
        $scope.user = {
            firstName: '',
            lastName: '',
            email: '',
            pass: ''
        }
    }

    $scope.loadUsers();
}

UserController.$inject = ['$scope', '$http'];