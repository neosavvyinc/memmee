function NavigationController($scope, broadCastService, $location) {

    $scope.loggedOutNavigationItems = [
        { displayName: "Home", navigationLink: "#home", selected: "active" }
    ];

    $scope.loggedInNavigationItems = [
            { displayName: "Home", navigationLink: "#home", selected: "active"},
            { displayName: "View", navigationLink: "#view", selected: ""},
            { displayName: "New", navigationLink: "#new", selected: ""}
        ];

    $scope.navigationItems = $scope.loggedOutNavigationItems;

    $scope.profileSelected = "";

    $scope.select = function( $selectedNavigationLink ) {

        $scope.toggleProfile(false)
        for ( navIndex in $scope.navigationItems )
        {
            if( $scope.navigationItems[navIndex].navigationLink == $selectedNavigationLink.navigationLink )
            {
                $scope.navigationItems[navIndex].selected = "active";
            }
            else
            {
                $scope.navigationItems[navIndex].selected = "";
            }
        }
    }

    $scope.toggleProfile = function( $selected ) {

        if( $selected )
        {
            $scope.profileSelected = "active";
            for ( navIndex in $scope.navigationItems )
            {
                $scope.navigationItems[navIndex].selected = "";
            }
        }
        else
        {
            $scope.profileSelected = "";
        }
    }

    $scope.$on('handleLogin', function() {
        $scope.updateNavigation(true);
    });
    $scope.$on('handleLogout', function() {
        $scope.updateNavigation(false);
    });


    $scope.updateNavigation = function( loggedIn ) {

        if( loggedIn )
        {
            $scope.navigationItems = $scope.loggedInNavigationItems;
            if( $location.url() == "/view" )
            {
                $scope.select($scope.loggedInNavigationItems[1]);
            }
            else if ( $location.url() == "/home" )
            {
                $scope.select($scope.loggedInNavigationItems[0]);
            }
            else if ( $location.url() == "/new" )
            {
                $scope.select($scope.loggedInNavigationItems[2]);
            }

        }
        else
        {
            $scope.navigationItems = $scope.loggedOutNavigationItems;
        }

    }
}


function RegistrationController($scope, $http, broadCastService) {

    $scope.user = {
        email: ''
    };

    $scope.register = function()
    {
        $http({method: 'POST', url: '/memmeeuserrest/user', data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('you were successfully registered');
                $scope.saveLoggedInUser(data);
                broadCastService.loginUser(data);
            }).
            error(function(data, status, headers, config) {
                console.log('error while saving a new user');
            });
    }


}

function LoginController($scope, $http, broadCastService) {

    $scope.user = {
        email: '',
        password: ''
    };

    $scope.login = function()
    {
        $http({method: 'POST', url: '/memmeeuserrest/user/login', data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('you were successfully registered');
                $scope.saveLoggedInUser(data);
                broadCastService.loginUser(data);
            }).
            error(function(data, status, headers, config) {
                console.log('error while saving a new user');
            });
    }

}

function ProfileController($scope, $http, broadCastService) {

    $scope.user = broadCastService.user;
    $scope.confirmedPass = '';

    $scope.update = function()
    {
        if( $scope.confirmedPass != $scope.user.password )
        {
            console.log("your password doesn't match your provided password");
            return;
        }

        console.log("scope.user.id" + $scope.user.id);

        $http({method: 'PUT', url: '/memmeeuserrest/user/' + $scope.user.id + '?apiKey=' + $scope.user.apiKey, data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('your user has been updated')
                broadCastService.loginUser(data);
                $scope.saveLoggedInUser(data);
            }).
            error(function(data, status, headers, config) {
                console.log('error while saving your user');
            });
    }

    if( broadCastService && broadCastService.apiKey )
    {
        $http({method: 'GET', url: '/memmeeuserrest/user/login?apiKey=' + broadCastService.apiKey}).
            success(function(data, status, headers, config) {
                console.log('your user was loaded via API key')
                broadCastService.loginUser(data);
                $scope.user = data;
                $scope.saveLoggedInUser(data);
            }).
            error(function(data, status, headers, config) {
                console.log('unable to load your user by API key');
            });
    }
}

function NewMemmeeController($scope, $http, broadCastService) {

    $scope.user = broadCastService.user;
    $scope.memmee = {
        id: '',
        userId: $scope.user ? $scope.user.id : null,
        title: '',
        text: ''
    };

    $scope.$on('attachmentUploadSuccess', function() {
        $scope.memmee.attachment = broadCastService.attachment;
        console.log("attachment was uploaded");
    });


    $scope.createMemmee = function()
    {
        $http({method: 'POST', url: '/memmeerest/insertmemmee/?apiKey=' + $scope.user.apiKey, data: $scope.memmee}).
            success(function(data, status, headers, config) {
                console.log('you have saved a memmee');
            }).
            error(function(data, status, headers, config) {
                console.log('error while saving your user');
                console.log(data);
            });
    }
}

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

    $scope.fileToUpload = {}
    $scope.$watch('fileToUpload', function(){
        //this seems to be a fake array, because for (i in files) will traverse on the "length" attribute as well,
        //and it doesn't have a forEach method
        // scope.files = document.getElementById('fileToUpload').files
        //...so we need to copy the files to a new array
        $scope.files = []
        var files = document.getElementById('fileToUpload').files
        console.log('files:', files)
        for (var i = 0; i < files.length; i++) {
            $scope.files.push(files[i])
        }
        $scope.progressVisible = false
    })

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
        /* This event is raised when the server send back a response */
//        alert(evt.target.responseText)

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

function ViewMemmeesController($scope, $http, broadCastService) {

    $scope.user = broadCastService.user;
    $scope.memmees = [];

    $http({method: 'GET', url: '/memmeerest/getmemmees/?apiKey=' + $scope.user.apiKey}).
        success(function(data, status, headers, config) {
            console.log('your memmees have been loaded')
            $scope.memmees = data;
        }).
        error(function(data, status, headers, config) {
            console.log('error loading your doggone memmees');
        });

}

function SecurityController($scope, broadCastService, $location, $timeout) {
    $scope.loggedInUser = null;
    $scope.visibleLoggedInStyle = { visibility: 'hidden' };

    $scope.saveLoggedInUser = function( $user ) {
        $scope.loggedInUser = $user;
        localStorage.setItem( "user", JSON.stringify($user) );
        $scope.visibleLoggedInStyle = { visibility: 'visible' };
    }

    $scope.logout = function() {
        $scope.visibleLoggedInStyle = { visibility: 'hidden' };
        broadCastService.logoutUser($scope.loggedInUser);
        localStorage.removeItem( "user");
        $location.path('/home');
    }

    if( localStorage.getItem("user") !== null && localStorage.getItem("user") !== "" )
    {
        var obj = localStorage.getItem( "user" );
        broadCastService.user = JSON.parse(obj);
        $scope.saveLoggedInUser(broadCastService.user);
        console.log("Loading a user from local storage: " + obj);
    }

    if( broadCastService.user !== null )
    {
        $timeout(function() {
            broadCastService.loginUser( broadCastService.user );
        }, 100);
    }
}

AttachmentController.$inject = ['$scope', 'memmeeBroadCastService'];

ViewMemmeesController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];

NewMemmeeController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];

LoginController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];

ProfileController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];

SecurityController.$inject = ['$scope', 'memmeeBroadCastService', '$location', '$timeout'];

NavigationController.$inject = ['$scope', 'memmeeBroadCastService', '$location'];

RegistrationController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];