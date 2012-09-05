function AttachmentController($scope, broadCastService) {
    var dropbox = document.getElementById("dropbox")
    $scope.files = [];

    function onDropboxChange(evt) {
        var input = document.getElementById("uploadInput")
        console.log("input contents change");

        var files = input.files;
        if (files.length > 0) {
            $scope.$apply(function () {
                $scope.files = [];
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

    $scope.deleteCurrentAttachment = function() {
        broadCastService.deleteAttachment();
    }

    $scope.$on("deleteAttachmentSuccess", function() {
        $scope.files = [];
    })
}

AttachmentController.$inject = ['$scope', 'memmeeBroadCastService'];