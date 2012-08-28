function ViewModeController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = broadCastService.user;
            $scope.memmee = null;
        });

    var isMemmeeValid = function()
    {
        if ( "You have no memmees." == $scope.memmee.text )
        {
            console.log("isMemmeValid():returning false");
            return false
        }
        else
        {
            console.log("isMemmeValid():returning true");
            return true;
        }
    }

    //Action Handlers
    $scope.onDeleteMemmee = function () {
        if( isMemmeeValid() )
        {
            broadCastService.confirmDeleteViewModeController();
        }
    };

    $scope.generateAndShowPublicLink = function () {
        $http({method:'PUT', url:'/memmeerest/sharememmee/?apiKey=' + $scope.user.apiKey, data:$scope.memmee}).
            success(function (data, status, headers, config) {
                console.log('you have generated a share link');
                $scope.memmee = data;
                broadCastService.showShareLinkViewModeController($scope.memmee);
            }).
            error(function (data, status, headers, config) {
                console.log('there was an error generating your share link');
            });
    }

    //Service Calls
    $scope.getDefaultMemmee = function () {
        $http({method:'GET', url:'/memmeerest/getmemmee?apiKey=' + $scope.user.apiKey}).
            success(function (data, status, headers, config) {
                console.log('your memmee has been loaded');
                console.log("memmee: " + JSON.stringify(data));
                $scope.memmee = broadCastService.selectedMemmee = data;
                $scope.hideAttachmentDiv();
            }).error(function (data, status, headers, config) {
                console.log('error loading your doggone memmee');
            });
    };

    $scope.deleteMemmee = function (memmee) {
        $http({method:'DELETE', url:'/memmeerest/deletememmee?apiKey=' + $scope.user.apiKey + "&id=" + memmee.id}).
            success(function (data, status, headers, config) {
                console.log('your memmee has been deleted');
                $scope.memmee = null;

                //Broadcasts event
                broadCastService.memmeeDeletedViewModeController();
            }).
            error(function (data, status, headers, config) {
                console.log('error deleting your doggone memmee');
            });
    };

    //Broadcast Handlers
    $scope.$on(ArchiveListControllerEvents.get('MEMMEE_SELECTED'), function (event, memmee) {
        console.log(">>>memmee selected event in the view-ctrl");
        $scope.memmee = memmee;
    });

    $scope.$on(ViewModeControllerEvents.get('MEMMEE_DELETED'), function (event) {
        console.log("Deleting the local memmee on ViewController!");
        //this is a bug
        $scope.memmee = broadCastService.selectedMemmee = { attachment:{ filePath:"/img/1x1.gif"}};
        $scope.getDefaultMemmee();
    });

    $scope.$on(AlertsControllerEvents.get('YES_SELECTED'), function (event, promptingEvent) {
        if (promptingEvent == ViewModeControllerEvents.get('CONFIRM_DELETE')) {
            $scope.deleteMemmee($scope.memmee);
        }
    });

    //UI
    $scope.getDisplayDate = function () {
        return $scope.memmee.displayDate.toDateString();
    }

    //Initializaton
    $scope.getDefaultMemmee();

    $scope.showImage = function () {
        if ($scope.memmee && $scope.memmee.attachment && $scope.memmee.attachment.filePath) {
            return true;
        }
        return false;
    }

    $scope.attachmentVisible = true;

    $scope.hideAttachmentDiv = function() {

        console.log("ViewModeController.hideAttachmentDiv()");

        if ( isMemmeeValid() )
        {
            console.log("ViewModeController.hideAttachmentDiv() -- still a memmee left");
            $scope.attachmentVisible = true;
        }
        else
        {
            console.log("ViewModeController.hideAttachmentDiv() -- no memmees left");
            $scope.attachmentVisible = false;
        }
    }
}

ViewModeController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];