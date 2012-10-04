function ViewModeController($scope, $http, broadCastService, $timeout) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = broadCastService.user;
            $scope.memmee = null;
        });

    var isMemmeeValid = function () {
        if ("You have no memmees." == $scope.memmee.text) {
            console.log("isMemmeValid():returning false");
            return false
        }
        else {
            console.log("isMemmeValid():returning true");
            return true;
        }
    }

    var closedropdownTimer;
    $scope.shareVisibilityStyle = "isHidden";
    $scope.toggleShareDropdown = function () {
        console.log("mouse over happening.")
        if ($scope.shareVisibilityStyle == "isHidden") {
            $scope.shareVisibilityStyle = "isVisible";
        }
        else {
            $scope.shareVisibilityStyle = "isHidden";
        }

        if (closedropdownTimer) {
            closedropdownTimer = undefined;
        }
    }

    $scope.closeShareDropdown = function() {
        $scope.shareVisibilityStyle = "isHidden";

        if (closedropdownTimer) {
            closedropdownTimer = undefined;
        }
    }

    $scope.closeShareIfMouseOutside = function () {
        console.log("mouse down outside...");
        closedropdownTimer = $timeout($scope.closeShareDropdown, 100);
    }

    $scope.cancelShareTimer = function () {
        console.log("cancel share timer....");
        if (closedropdownTimer) {
            $timeout.cancel(closedropdownTimer);
        }
    }

    //Action Handlers
    $scope.onDeleteMemmee = function () {
        if (isMemmeeValid()) {
            broadCastService.confirmDeleteViewModeController();
        }
    };

    $scope.generateAndShowPublicLink = function () {
        $http({method:'PUT', url:'/memmeerest/sharememmee/?apiKey=' + $scope.user.apiKey, data:$scope.memmee}).
            success(function (data, status, headers, config) {
                console.log('you have generated a share link');
                $scope.memmee = data;
                broadCastService.showShareLinkViewModeController($scope.memmee);
                $scope.toggleShareDropdown();
            }).
            error(function (data, status, headers, config) {
                console.log('there was an error generating your share link');
            });
    }

    //Service Calls
    $scope.getDefaultMemmee = function () {
        if (broadCastService && broadCastService.selectedMemmee) {
            $scope.memmee = broadCastService.selectedMemmee;
        } else {
            $http({method:'GET', url:'/memmeerest/getmemmee?apiKey=' + $scope.user.apiKey}).
                success(function (data, status, headers, config) {
                    console.log('your memmee has been loaded');
                    console.log("memmee: " + JSON.stringify(data));
                    $scope.memmee = broadCastService.selectedMemmee = data;

                    if (!$scope.memmee.theme || !$scope.memmee.theme.name) {
                        $scope.memmee.theme = {name:"memmee-card" };
                    }
                    $scope.hideAttachmentDiv();
                }).error(function (data, status, headers, config) {
                    console.log('error loading your doggone memmee');
                });
        }
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
        console.log(">>>memmee selected event in the view-ctrl: " + memmee.text);
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

    $scope.$on(CreateModeControllerEvents.get('MEMMEE_CREATED'), function(event, memmee) {
        //@TODO, should populate automatically from selected, but worthwhile fallback
        $scope.memmee = memmee;
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

    $scope.hideAttachmentDiv = function () {

        console.log("ViewModeController.hideAttachmentDiv()");

        if (isMemmeeValid()) {
            console.log("ViewModeController.hideAttachmentDiv() -- still a memmee left");
            $scope.attachmentVisible = true;
        }
        else {
            console.log("ViewModeController.hideAttachmentDiv() -- no memmees left");
            $scope.attachmentVisible = false;
        }
    }

    $scope.getDisplayDate = function (memmee) {
        if (memmee && memmee.displayDate) {
            return MemmeeDateUtil.standardDate(new Date.parse(memmee.displayDate));
        }
        return null;
    };
}

ViewModeController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$timeout'];