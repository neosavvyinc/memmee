function CreateMemmeesController($scope, $http, broadCastService) {

    var DEFAULT_PROMPT = "start typing here...";

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.master = {
                id:'',
                userId:broadCastService.user ? broadCastService.user.id : null,
                text: DEFAULT_PROMPT,
                creationDate:Date.today(),
                lastUpdateDate:Date.today(),
                displayDate:Date.today()
            };

            $scope.memmee = {
                id:'',
                userId:broadCastService.user ? broadCastService.user.id : null,
                text: DEFAULT_PROMPT,
                creationDate:Date.today(),
                lastUpdateDate:Date.today(),
                displayDate:Date.today()
            };
        });

    $scope.memmeeStyleSelectorVisibilityStyle = "isHidden";
    $scope.toggleMemmeeThemeSelection = function () {
        console.log("mouse over happening.")
        if ($scope.memmeeStyleSelectorVisibilityStyle == "isHidden") {
            $scope.memmeeStyleSelectorVisibilityStyle = "isVisible";
        }
        else {
            $scope.memmeeStyleSelectorVisibilityStyle = "isHidden";
        }
    }

    $scope.calendarVisibleStyle = "isHidden";
    $scope.toggleCalendar = function () {
        console.log("mouse over happening.")
        if ($scope.calendarVisibleStyle == "isHidden") {
            $scope.calendarVisibleStyle = "isVisible";
        }
        else {
            $scope.calendarVisibleStyle = "isHidden";
        }
    }

    //Action Handlers
    $scope.update = function (memmee) {
        $scope.master = angular.copy(memmee);
    };

    $scope.reset = function () {
        $scope.memmee = angular.copy($scope.master);
    };

    $scope.cancel = function () {
        broadCastService.createModeCancelledCreateModeController();
    };

    $scope.removePrompt = function () {
        if ( $scope.memmee.text == DEFAULT_PROMPT )
        {
            $scope.memmee.text = "";
        }
    }

    $scope.addDefaultPrompt = function () {
        if( $scope.memmee.text == "" )
        {
            $scope.memmee.text = DEFAULT_PROMPT;
        }
    }

    $scope.createMemmee = function () {
        //@TODO, this logic and naming will eventually be changed
        if (!$scope.memmee.theme) {
            $scope.memmee.theme = {};
        }

        $scope.memmee.theme.name = $scope.selectedTheme;
        $scope.memmee.theme.listName = $scope.selectedListTheme;

        $http({method:'POST', url:'/memmeerest/insertmemmee/?apiKey=' + broadCastService.user.apiKey, data:$scope.memmee}).
            success(function (data, status, headers, config) {
                console.log('you have saved a memmee');

                broadCastService.selectedMemmee = data;

                //Broadcasts Create Event
                broadCastService.memmeeCreatedCreateModeController(broadCastService.selectedMemmee);

                //Legacy Event, references will be removed
                broadCastService.createModeCancelledCreateModeController();
            }).
            error(function (data, status, headers, config) {
                console.log('error while saving your user');
                console.log(data);
            });
    };

    $scope.selectMemmee = function (event, memmee) {
        broadCastService.selectedMemmee = memmee;
        broadCastService.createModeCancelledCreateModeController();
    };

    $scope.dateChanged = function (e) {
        $scope.memmee.displayDate = e.date;
        $(e.currentTarget).datepicker("hide");

        //Forces View Refresh
        $scope.$apply();
    };

    //Getters
    $scope.getTodaysDate = function () {
        var myDate = $scope.memmee.displayDate;

        return myDate.getMonth() + 1 + "-" + myDate.getDate() + "-" + myDate.getFullYear();
    }

    $scope.getDisplayDate = function () {
        if ($scope.memmee && $scope.memmee.displayDate && $scope.memmee.displayDate instanceof Date) {
            return $scope.memmee.displayDate.toDateString();
        }
        console.log("$scope.memmee.displayDate was not a Date object");
        return "";
    }

    $scope.isUnchanged = function (memmee) {
        return angular.equals(memmee, $scope.master);
    };

    $scope.selectedTheme = "memmee-card";
    $scope.selectedListTheme = "slidecard note crimson";


    //Setters
    $scope.setTheme = function (number) {
        switch (number) {
            case 0:
                $scope.selectedTheme = "memmee-card";
                $scope.selectedListTheme = "slidecard note crimson"
                break;
            case 1:
                $scope.selectedTheme = "memmee-card style-coffee";
                $scope.selectedListTheme = "slidecard art rokkitt"
                break;
            case 2:
                $scope.selectedTheme = "memmee-card style-travel";
                $scope.selectedListTheme = "slidecard luggage crimson";
                break;
            case 3:
                $scope.selectedTheme = "memmee-card style-inspiration";
                $scope.selectedListTheme = "slidecard pushpin josefinslab";
                break;
            case 4:
                $scope.selectedTheme = "memmee-card style-whimsy";
                $scope.selectedListTheme = "slidecard whimsy";
                break;
            case 5:
                $scope.selectedTheme = "memmee-card style-valentines";
                $scope.selectedListTheme = "slidecard whimsy";
                break;
            default:
                $scope.selectedTheme = "memmee-card";
                $scope.selectedListTheme = "slidecard note crimson"
                break;
        }
        $scope.toggleMemmeeThemeSelection();
    }


    $scope.showImage = function () {
        if ($scope.memmee && $scope.memmee.attachment && $scope.memmee.attachment.filePath) {
            return true;
        }
        return false;
    }


    //Broadcast and Event Handlers
    $scope.$on('attachmentUploadSuccess', function () {
        $scope.memmee.attachment = broadCastService.attachment;
        console.log("attachment url" + $scope.memmee.attachment.filePath);
        console.log("attachment was uploaded");
        $scope.$apply();
    });

    $scope.$on('deleteAttachment', function () {
        console.log("deleting attachment from backend...");
        $http({method:'DELETE', url:'/memmeerest/deleteattachment/?apiKey=' + broadCastService.user.apiKey + "&id=" + $scope.memmee.attachment.id}).
            success(function (data, status, headers, config) {
                console.log('you have deleted your current attachment');
                delete $scope.memmee.attachment;

                broadCastService.deleteAttachmentSuccess();
            }).
            error(function (data, status, headers, config) {
                console.log('an error occurred while deleting your current attachment');

            });
    });


    $scope.$on(ArchiveListControllerEvents.get('MEMMEE_SELECTED'), function (event, memmee) {
        console.log(">>>memmee selected event in the create-ctrl");
        if (memmee.id != $scope.memmee.id) {
            if ($scope.isUnchanged($scope.memmee)) {
                $scope.selectMemmee(event, memmee);
            } else {
                $scope.maybeSelectMemmee = memmee;
                broadCastService.confirmDiscardCreateModeController();
            }
        }
    });

    $scope.$on(AlertsControllerEvents.get('YES_SELECTED'), function (event, promptingEvent) {
        if (promptingEvent == CreateModeControllerEvents.get('CONFIRM_DISCARD')) {
            $scope.selectMemmee(event, $scope.maybeSelectMemmee);
        }
    });

    $scope.$on(AlertsControllerEvents.get('NO_SELECTED'), function (event, promptingEvent) {
        if (promptingEvent == CreateModeControllerEvents.get('CONFIRM_DISCARD')) {
            $scope.maybeSelectMemmee = null;
        }
    });

    //UI Initialization
    $scope.initializeDatePicker = function (clazz) {
        $(clazz).data("date", $scope.getTodaysDate());
        $(clazz).datepicker({format:'mm-dd-yyyy'}).on('changeDate', $scope.dateChanged);
    };

    //Show instructions in first two logins
    if (broadCastService.user && broadCastService.user.loginCount < 3) {
        broadCastService.createModeNewUserLogin();
    }
}

CreateMemmeesController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];
