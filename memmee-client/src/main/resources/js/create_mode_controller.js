function CreateMemmeesController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.master = {
                id:'',
                userId:broadCastService.user ? broadCastService.user.id : null,
                text:'',
                creationDate:Date.today(),
                lastUpdateDate:Date.today(),
                displayDate:Date.today()
            };

            $scope.memmee = {
                id:'',
                userId:broadCastService.user ? broadCastService.user.id : null,
                text:'',
                creationDate:Date.today(),
                lastUpdateDate:Date.today(),
                displayDate:Date.today()
            };
        });

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

    $scope.createMemmee = function () {
        $http({method:'POST', url:'/memmeerest/insertmemmee/?apiKey=' + broadCastService.user.apiKey, data:$scope.memmee}).
            success(function (data, status, headers, config) {
                console.log('you have saved a memmee');

                broadCastService.selectedMemmee = data;

                //Broadcasts Create Event
                broadCastService.memmeeCreatedCreateModeController();

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

    //Setters
    $scope.setTheme = function (number) {
        broadCastService.setTheme(number);
    }

    //Broadcast and Event Handlers
    $scope.$on('attachmentUploadSuccess', function () {
        $scope.memmee.attachment = broadCastService.attachment;
        console.log("attachment was uploaded");
    });

    $scope.$on(ArchiveListControllerEvents.get('MEMMEE_SELECTED'), function (event, memmee) {
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
}

CreateMemmeesController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];
