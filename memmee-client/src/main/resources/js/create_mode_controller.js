function CreateMemmeesController($scope, $http, broadCastService, $location) {

    $scope.master= {
        id: '',
        userId: broadCastService.user ? broadCastService.user.id : null,
        text: '',
        creationDate: Date.today(),
        lastUpdateDate: Date.today(),
        displayDate: Date.today()
    };

    $scope.memmee = {
        id: '',
        userId: broadCastService.user ? broadCastService.user.id : null,
        text: '',
        creationDate: Date.today(),
        lastUpdateDate: Date.today(),
        displayDate: Date.today()
    };

    $scope.$on('attachmentUploadSuccess', function() {
        $scope.memmee.attachment = broadCastService.attachment;
        console.log("attachment was uploaded");
    });

    $scope.update = function(memmee) {
        $scope.master= angular.copy(memmee);
    };

    $scope.reset = function() {
        $scope.memmee = angular.copy($scope.master);
    };

    $scope.isUnchanged = function(memmee) {
        return angular.equals(memmee, $scope.master);
    };

    $scope.cancel = function()
    {
        broadCastService.createModeCancelled();
    }

    $scope.getDisplayDate = function( )
    {
        return $scope.memmee.displayDate.toDateString();
    }

    $scope.createMemmee = function()
    {
        $http({method: 'POST', url: '/memmeerest/insertmemmee/?apiKey=' + broadCastService.user.apiKey, data: $scope.memmee}).
            success(function(data, status, headers, config) {
                console.log('you have saved a memmee');
                broadCastService.createModeCancelled();
            }).
            error(function(data, status, headers, config) {
                console.log('error while saving your user');
                console.log(data);
            });
    }
}

CreateMemmeesController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location'];


function AttachmentController($scope, broadCastService) {
    //============== DRAG & DROP =============
    // source for drag&drop: http://www.webappers.com/2011/09/28/drag-drop-file-upload-with-html5-javascript/
    var dropbox = document.getElementById("dropbox")
    $scope.dropText = 'Drop files here...'

    // init event handlers
    function dragEnterLeave(evt) {
        evt.stopPropagation()
        evt.preventDefault()
        $scope.$apply(function(){
            $scope.dropText = 'Drop files here...'
            $scope.dropClass = ''
        })
    }
    dropbox.addEventListener("dragenter", dragEnterLeave, false)
    dropbox.addEventListener("dragleave", dragEnterLeave, false)
    dropbox.addEventListener("dragover", function(evt) {
        evt.stopPropagation()
        evt.preventDefault()
        var clazz = 'not-available'
        var ok = evt.dataTransfer && evt.dataTransfer.types && evt.dataTransfer.types.indexOf('Files') >= 0
        $scope.$apply(function(){
            $scope.dropText = ok ? 'Drop files here...' : 'Only files are allowed!'
            $scope.dropClass = ok ? 'over' : 'not-available'
        })
    }, false)
    dropbox.addEventListener("drop", function(evt) {
        console.log('drop evt:', JSON.parse(JSON.stringify(evt.dataTransfer)))
        evt.stopPropagation()
        evt.preventDefault()
        $scope.$apply(function(){
            $scope.dropText = 'Drop files here...'
            $scope.dropClass = ''
        })
        var files = evt.dataTransfer.files
        if (files.length > 0) {
            $scope.$apply(function(){
                $scope.files = []
                for (var i = 0; i < files.length; i++) {
                    $scope.files.push(files[i])
                }
            })
        }
    }, false)
    //============== DRAG & DROP =============

//    $scope.fileToUpload = {}
//    $scope.$watch('fileToUpload', function(){
//        $scope.files = []
//        var files = document.getElementById('fileToUpload').files
//        console.log('files:', files)
//        for (var i = 0; i < files.length; i++) {
//            $scope.files.push(files[i])
//        }
//        $scope.progressVisible = false
//    })

    $scope.uploadFile = function() {
        var fd = new FormData()
        for (var i in $scope.files) {
            fd.append("file", $scope.files[i])
        }
        var xhr = new XMLHttpRequest()
        xhr.upload.addEventListener("progress", uploadProgress, false)
        xhr.addEventListener("load", uploadComplete, false)
        xhr.addEventListener("error", uploadFailed, false)
        xhr.addEventListener("abort", uploadCanceled, false)
        xhr.open("POST", "/memmeerest/uploadattachment")
        $scope.progressVisible = true
        xhr.send(fd)
    }

    function uploadProgress(evt) {
        $scope.$apply(function(){
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
        $scope.$apply(function(){
            $scope.progressVisible = false
        })
        alert("The upload has been canceled by the user or the browser dropped the connection.")
    }
}

AttachmentController.$inject = ['$scope', 'memmeeBroadCastService'];