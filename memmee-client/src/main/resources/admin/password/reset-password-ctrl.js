function ResetPasswordController($scope, adminService ) {

    $scope.user = {};

    var result = function( data ) {
        alert("User updated properly");
    }

    var fault = function( data ) {
        alert("User was not updated...");
    }

    $scope.reset = function() {

        adminService.resetPassword( $scope.user, result, fault )

    }




}