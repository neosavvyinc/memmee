function CreateMemmeesController($scope, $http, broadCastService, $location) {

    //Properties
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

    //Action Handlers
    $scope.update = function (memmee) {
        $scope.master = angular.copy(memmee);
    };

    $scope.reset = function () {
        $scope.memmee = angular.copy($scope.master);
    };

    $scope.cancel = function () {
        broadCastService.createModeCancelled();
    };

    $scope.createMemmee = function () {
        $http({method:'POST', url:'/memmeerest/insertmemmee/?apiKey=' + broadCastService.user.apiKey, data:$scope.memmee}).
            success(function (data, status, headers, config) {
                console.log('you have saved a memmee');
                broadCastService.createModeCancelled();
            }).
            error(function (data, status, headers, config) {
                console.log('error while saving your user');
                console.log(data);
            });
    };

    $scope.selectMemmee = function (event, memmee) {
        $scope.master = memmee;

        //Makes a shallow copy of the object for editing.
        $scope.memmee = jQuery.extend({}, memmee);
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
        return $scope.memmee.displayDate.toDateString();
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
    }
}

CreateMemmeesController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location'];


function AttachmentController($scope, broadCastService) {
    var dropbox = document.getElementById("dropbox")

    function onDropboxChange(evt) {
        var input = document.getElementById("uploadInput")
        console.log("input contents change");

        var files = input.files;
        if (files.length > 0) {
            $scope.$apply(function () {
                $scope.files = []
                for (var i = 0; i < files.length; i++) {
                    $scope.files.push(files[i])
                }
            })
        }

        var fd = new FormData()
        for (var i in $scope.files) {
            fd.append("file", $scope.files[i])
        }
        var xhr = new XMLHttpRequest()
        xhr.upload.addEventListener("progress", uploadProgress, false)
        xhr.addEventListener("load", uploadComplete, false)
        xhr.addEventListener("error", uploadFailed, false)
        xhr.addEventListener("abort", uploadCanceled, false)
        xhr.open("POST", "/memmeerest/uploadattachment/?apiKey=" + broadCastService.user.apiKey)
        $scope.progressVisible = true
        xhr.send(fd)

    }

    dropbox.addEventListener("change", onDropboxChange, false)

    function uploadProgress(evt) {
        $scope.$apply(function () {
            if (evt.lengthComputable) {
                $scope.progress = Math.round(evt.loaded * 100 / evt.total)
            } else {
                $scope.progress = 'unable to compute'
            }
        })
    }

    function uploadComplete(evt) {
        broadCastService.attachmentSuccess(JSON.parse(evt.target.responseText));
    }

    function uploadFailed(evt) {
        alert("There was an error attempting to upload the file.")
    }

    function uploadCanceled(evt) {
        $scope.$apply(function () {
            $scope.progressVisible = false
        })
        alert("The upload has been canceled by the user or the browser dropped the connection.")
    }
}

AttachmentController.$inject = ['$scope', 'memmeeBroadCastService'];