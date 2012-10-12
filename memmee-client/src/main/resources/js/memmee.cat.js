var AlertsControllerEvents = (function () {
    "use strict";
    var controllerName = "AlertsController",
        priv_events = {
            'YES_SELECTED': 'yesSelected' + controllerName,
            'NO_SELECTED': 'noSelected' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };

}());

var ArchiveListControllerEvents = (function () {
    "use strict";
    var controllerName = "ArchiveListController",
        priv_events = {
            'MEMMEE_SELECTED': 'memmeeSelected' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };

}());

var CreateModeControllerEvents = (function () {
    "use strict";
    var controllerName = "CreateModeController",
        priv_events = {
            'CONFIRM_DISCARD': 'confirmDiscard' + controllerName,
            'DISCARD_CONFIRMED': 'discardConfirmed' + controllerName,
            'MEMMEE_CREATED': 'memmeeCreated' + controllerName,
            'CREATE_MODE_CANCELLED': 'createModeCancelled' + controllerName,
            'CREATE_MODE_STARTED': 'createModeStarted' + controllerName,
            'NEW_USER_LOGIN': 'newUserLogin' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };

}());

var LoginControllerEvents = (function () {
    "use strict";
    var controllerName = "LoginController",
        priv_events = {
            'INVALID_LOGIN': 'invalidLogin' + controllerName,
            'LOGIN': 'login' + controllerName,
            'LOGOUT': 'logout' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };
}());

var ProfileControllerEvents = (function () {
    "use strict";
    var controllerName = "ProfileController",
        priv_events = {
            'UPDATE_SUCCESS': 'updateSuccess' + controllerName,
            'UPDATE_FAILURE': 'updateFailure' + controllerName,
            'PROFILE_OPENED': 'profileOpened' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };
}());

var RegistrationControllerEvents = (function () {
    "use strict";
    var controllerName = "RegistrationController",
        priv_events = {
            'ERROR_SAVING': 'errorSaving' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };
}());

var ViewModeControllerEvents = (function () {
    "use strict";
    var controllerName = "ViewModeController",
        priv_events = {
            'CONFIRM_DELETE': 'confirmDelete' + controllerName,
            'DELETE_CONFIRMED': 'deleteConfirmed' + controllerName,
            'SHOW_SHARE_LINK': 'showShareLink' + controllerName,
            'MEMMEE_DELETED': 'memmeeDeleted' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };

}());var Errors = (function () {
    var private = {
        'INVALID_LOGIN_HEADER':'there was a problem logging in',
        'INVALID_LOGIN_MESSAGE':'you have entered an invalid username or password.',
        'PROFILE_UPDATE_HEADER':'there was a problem updating your profile',
        'PROFILE_UPDATE_MESSAGE':"thanks for updating your profile. let's memmee.",
        'REGISTRATION_HEADER': 'there was a problem signing up'
    };

    return {
        get:function (name) {
            return private[name];
        }
    }
})();var Notifications = (function () {
    var private = {
        'PROFILE_UPDATE_SUCCESS_HEADER':"thanks for updating your profile.",
        'PROFILE_UPDATE_SUCCESS_MESSAGE':"let's memmee.",
        'DISCARD_MEMMEE_HEADER': 'discard memmee?',
        'DISCARD_MEMMEE_MESSAGE': 'you have not saved your memmee. would you like to discard it?',
        'DELETE_MEMMEE_HEADER': 'delete memmee?',
        'DELETE_MEMMEE_MESSAGE': 'are you sure you want to delete this memmee?'
    };

    return {
        get:function (name) {
            return private[name];
        }
    }
})();var MemmeeDateUtil = (function () {

    var shortMonth = {
        '0':'Jan',
        '1':'Feb',
        '2':'Mar',
        '3':'Apr',
        '4':'May',
        '5':'Jun',
        '6':'Jul',
        '7':'Aug',
        '8':'Sep',
        '9':'Oct',
        '10':'Nov',
        '11':'Dec'
    };

    var longMonth = {
        '0':'January',
        '1':'February',
        '2':'March',
        '3':'April',
        '4':'May',
        '5':'June',
        '6':'July',
        '7':'August',
        '8':'September',
        '9':'October',
        '10':'November',
        '11':'December'
    };

    var method = {
        shortMonth:function (number) {
            if (number != null) {
                return shortMonth[number.toString()];
            }
            return null;
        },
        longMonth:function (number) {
            if (number != null) {
                return longMonth[number.toString()];
            }
            return null;
        },
        isToday:function (date) {
            if (date != null) {
                var today = Date.today()
                return date.getDate() == today.getDate() && date.getMonth() == today.getMonth() && date.getYear() == today.getYear();
            }
            return false;
        }
    };

    return {
        shortMonth:method['shortMonth'],
        longMonth:method['longMonth'],
        isToday:method['isToday'],
        standardDate:function (date) {
            if (date != null) {
                return method['longMonth'](date.getMonth()) +
                    " " + date.getDate().toString() +
                    ", " + date.getFullYear().toString();
            }
            return null;
        }
    };

})();window.onload = function() {
    var txts = document.getElementsByTagName('TEXTAREA')

    for(var i = 0, l = txts.length; i < l; i++) {
        if(/^[0-9]+$/.test(txts[i].getAttribute("maxlength"))) {
            var func = function() {
                var len = parseInt(this.getAttribute("maxlength"), 10);

                if(this.value.length > len) {
                    console.log('Maximum length exceeded: ' + len);
                    this.value = this.value.substr(0, len);
                    return false;
                }
            }

            txts[i].onkeyup = func;
            txts[i].onblur = func;
        }
    }
}

var keynum, lines = 1;

function limitLines(obj, e) {

    // IE
    if(window.event) {
        keynum = e.keyCode;
        // Netscape/Firefox/Opera
    } else if(e.which) {
        keynum = e.which;
    }

    if(keynum == 13) {
        if(lines == obj.rows - 1) {
            return false;
        }
    }

    //TODO FIX NPE
    lines = obj.value.match(/\n/g).length;
    if(lines == obj.rows) {
        return false;
    }
}angular.module('memmee-app', ['memmee-app.services']).
    config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/home', {templateUrl:'js/home/home-ptl.html'}).
        when('/create', {templateUrl:'js/memmee/create/create-ptl.html'}).
        when('/view', {templateUrl:'js/memmee/view/view-ptl.html'}).
        when('/requiredchangepassword', {templateUrl:'js/profile/change-password-ptl.html'}).
        when('/profile', {templateUrl:'js/profile/profile-ptl.html'}).
        when('/share', {templateUrl:'js/memmee/share/share-ptl.html'}).
        otherwise({redirectTo:'/home'});
}]).
    run(['$rootScope', '$location', function( $rootScope, $location ) {
    $rootScope.$on( "$routeChangeStart", function(event, next, current) {
        console.log("route is changing");
        $rootScope.$broadcast("closeAllDropdowns");
    });
}]).
    directive('fileButton', function() {
        return {
            link: function(scope, element, attributes) {

                var el = angular.element(element)
                var button = el.children()[0]

                el.css({
                    position: 'relative',
                    overflow: 'hidden',
                    width: button.offsetWidth,
                    height: button.offsetHeight
                })

                var fileInput = angular.element('<input id="uploadInput" type="file" multiple />')
                fileInput.css({
                    position: 'absolute',
                    top: 0,
                    left: 0,
                    'z-index': '2',
                    width: '100%',
                    height: '100%',
                    opacity: '0',
                    cursor: 'pointer'
                })

                el.append(fileInput)


            }
        }
    }).run();var memmeeServices = angular.module('memmee-app.services', ['ngResource']);
memmeeServices.factory("memmeeBroadCastService", function ($rootScope, $location) {

    var broadCastService = {};

    broadCastService.user = null;
    broadCastService.attachment = null;
    broadCastService.apiKey = $location.search().apiKey;
    broadCastService.selectedMemmee = null;

    broadCastService.loginUser = function ($user) {
        this.user = $user;
        $user.password.value = "";
        localStorage.setItem("user", JSON.stringify($user));
        $rootScope.$broadcast(LoginControllerEvents.get('LOGIN'));

        if ($location.url() == "/home" || $location.url() == "/requiredchangepassword") {
            if ($user.password.temp)
                $location.url("/requiredchangepassword");
            else
                $location.url("/create");
        }
    }

    broadCastService.logoutUser = function ($user) {
        this.user = null;
        localStorage.removeItem("user");
        $location.path('/home');
        $rootScope.$broadcast(LoginControllerEvents.get('LOGOUT'));
    }

    broadCastService.attachmentSuccess = function ($attachment) {
        this.attachment = $attachment;
        $rootScope.$broadcast('attachmentUploadSuccess');
    }

    broadCastService.deleteAttachment = function () {
        $rootScope.$broadcast('deleteAttachment');
    }

    broadCastService.deleteAttachmentSuccess = function () {
        $rootScope.$broadcast('deleteAttachmentSuccess');
    }

    var createMode = true;

    broadCastService.isCreateMode = function () {
        return createMode;
    }


    /**
     * Modal Popup Notifications:
     */
    broadCastService.showProfileUpdatedSuccess = function () {
        $rootScope.$broadcast('showProfileUpdatedSuccess');
    }

    broadCastService.showProfileUpdatedError = function () {
        $rootScope.$broadcast('showProfileUpdatedError');
    }

    /**
     * AlertsController
     */
    broadCastService.yesSelectedAlertsController = function (promptingEvent) {
        $rootScope.$broadcast(AlertsControllerEvents.get('YES_SELECTED'), promptingEvent);
    };

    broadCastService.noSelectedAlertsController = function (promptingEvent) {
        $rootScope.$broadcast(AlertsControllerEvents.get('NO_SELECTED'), promptingEvent);
    };

    /**
     * ArchiveListController
     */
    broadCastService.memmeeSelectedArchiveListController = function (memmee) {
        $rootScope.$broadcast(ArchiveListControllerEvents.get('MEMMEE_SELECTED'), memmee);
    };

    /**
     * CreateModeController
     */
    broadCastService.confirmDiscardCreateModeController = function () {
        $rootScope.$broadcast(CreateModeControllerEvents.get('CONFIRM_DISCARD'));
    };

    broadCastService.memmeeCreatedCreateModeController = function (memmee) {
        $rootScope.$broadcast(CreateModeControllerEvents.get('MEMMEE_CREATED'), memmee);
    };

    broadCastService.createModeCancelledCreateModeController = function () {
        createMode = false;
        $location.url("/view");
        $rootScope.$broadcast(CreateModeControllerEvents.get('CREATE_MODE_CANCELLED'));
    };

    broadCastService.createModeStartedCreateModeController = function () {
        createMode = true;
        $rootScope.$broadcast(CreateModeControllerEvents.get('CREATE_MODE_STARTED'));
    };

    broadCastService.createModeNewUserLogin = function() {
        $rootScope.$broadcast(CreateModeControllerEvents.get('NEW_USER_LOGIN'));
    };

    /**
     * LoginController
     */
    broadCastService.invalidLoginLoginController = function () {
        $rootScope.$broadcast(LoginControllerEvents.get('INVALID_LOGIN'));
    };

    /**
     * ProfileController
     */
    broadCastService.showProfileUpdatedSuccess = function () {
        $rootScope.$broadcast(ProfileControllerEvents.get('UPDATE_SUCCESS'));
    }

    broadCastService.showProfileUpdatedError = function () {
        $rootScope.$broadcast(ProfileControllerEvents.get('UPDATE_FAILURE'));
    }

    broadCastService.profileOpenedProfileController = function() {
        $rootScope.$broadcast(ProfileControllerEvents.get('PROFILE_OPENED'));
    }

    /**
     * RegistrationController
     */
    broadCastService.errorSavingUserRegistrationController = function (message) {
        $rootScope.$broadcast(RegistrationControllerEvents.get('ERROR_SAVING'), message);
    }

    /**
     * ViewModeController
     */
    broadCastService.confirmDeleteViewModeController = function () {
        $rootScope.$broadcast(ViewModeControllerEvents.get('CONFIRM_DELETE'));
    }

    broadCastService.memmeeDeletedViewModeController = function () {
        broadCastService.selectedMemmee = null;
        $rootScope.$broadcast(ViewModeControllerEvents.get('MEMMEE_DELETED'));
    }

    broadCastService.showShareLinkViewModeController = function (memmee) {
        $rootScope.$broadcast(ViewModeControllerEvents.get('SHOW_SHARE_LINK'), [memmee])
    }

    /**
     * Initialization
     */
    broadCastService.setTheme = function (number) {
        switch (number) {
            case 0:
                $rootScope.$broadcast('changeToDefaultTheme');
                break;
            case 1:
                $rootScope.$broadcast('changeToTheme1');
                break;
            case 2:
                $rootScope.$broadcast('changeToTheme2');
                break;
            case 3:
                $rootScope.$broadcast('changeToTheme3');
                break;
        }
    }

    return broadCastService;
});
//Use this as a place to put base/extended functionality in other controllers (search for use to see)
function DefaultController($scope, tearDown, runTearDownOnLoad) {
    if (runTearDownOnLoad == undefined) {
        runTearDownOnLoad = true;
    }

    //Event Listeners
    $scope.$on(LoginControllerEvents.get('LOGOUT'), tearDown);


    //Initialization
    if (runTearDownOnLoad) {
        tearDown();
    }
}function SecurityController($scope, broadCastService, $timeout, $location, $routeParams) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.defaultTheme = "../css/bootstrap.css";
            $scope.theme1 = "../css/bootstrap1.css";
            $scope.theme2 = "../css/bootstrap2.css";
            $scope.theme3 = "../css/bootstrap3.css";

            $scope.cssThemeUrl = $scope.defaultTheme;
        });

    $scope.$on('changeToTheme1', function () {
        $scope.cssThemeUrl = $scope.theme1;
        console.log("Theme is now set to 1");
    });
    $scope.$on('changeToTheme2', function () {
        $scope.cssThemeUrl = $scope.theme2;
        console.log("Theme is now set to 2");
    });
    $scope.$on('changeToTheme3', function () {
        $scope.cssThemeUrl = $scope.theme3;
        console.log("Theme is now set to 3");
    });
    $scope.$on('changeToDefaultTheme', function () {
        $scope.cssThemeUrl = $scope.defaultTheme;
        console.log("Theme is now set to default");
    });

    if( localStorage.getItem("user") !== null && localStorage.getItem("user") !== "" )
    {
        var obj = localStorage.getItem( "user" );
        broadCastService.user = JSON.parse(obj);
        broadCastService.loginUser(broadCastService.user);
        console.log("Loading a user from local storage: " + obj);
    }

    if( broadCastService.user !== null )
    {
        $timeout(function() {
            broadCastService.loginUser( broadCastService.user );
        }, 100);
    }
    else if( $location.path() == '/share' )
    {
        console.log("Loading a memmee from a share key");
    }
    else
    {
        $location.path('/home');
    }

    //UI Initialization
    $scope.initializeBodyUI = function() {
        $('body').iealert();
    };
}

SecurityController.$inject = ['$scope', 'memmeeBroadCastService', '$timeout', '$location', '$routeParams'];function AlertsController($scope, $http, broadCastService, $location) {

    $scope.isModalPresent = "isHidden";
    $scope.isModalAlertPresent = "isHidden";
    $scope.isModalYesNoAlertPresent = "isHidden";

    $scope.yesText = "Ok";
    $scope.noText = "Cancel";

    $scope.promptingEvent = null;


    //Action Handlers
    $scope.showAlert = function (header, message) {
        $scope.header = header;
        $scope.message = message;

        $scope.isModalPresent = "isVisible";
        $scope.isModalAlertPresent = "isVisible";
    };

    $scope.hideAlert = function () {
        $scope.isModalPresent = "isHidden";
        $scope.isModalAlertPresent = "isHidden";
    };

    $scope.showYesNoAlert = function (header, message, yesText, noText) {
        $scope.header = header;
        $scope.message = message;
        $scope.yesText = yesText;
        $scope.noText = noText;

        $scope.isModalPresent = "isVisible";
        $scope.isModalYesNoAlertPresent = "isVisible";
    };

    $scope.hideYesNoAlert = function () {
        $scope.isModalPresent = "isHidden";
        $scope.isModalYesNoAlertPresent = "isHidden";
    };

    $scope.yesClick = function () {
        broadCastService.yesSelectedAlertsController($scope.promptingEvent);
        $scope.hideAlert();
        $scope.hideYesNoAlert();
    };

    $scope.noClick = function () {
        broadCastService.noSelectedAlertsController($scope.promptingEvent);
        $scope.hideAlert();
        $scope.hideYesNoAlert();
    };


    /* Create Mode Controller */
    $scope.$on(CreateModeControllerEvents.get('CONFIRM_DISCARD'), function () {
        $scope.promptingEvent = CreateModeControllerEvents.get('CONFIRM_DISCARD');
        $scope.showYesNoAlert( Notifications.get('DISCARD_MEMMEE_HEADER'), Notifications.get('DISCARD_MEMMEE_MESSAGE'), "Discard", "Cancel");
    });

    $scope.$on(CreateModeControllerEvents.get('NEW_USER_LOGIN'), function () {
//        $scope.showAlert("Temporary Alert", "This will show on the first two logins for any new user. This is for instructions.");
    });

    /* LoginController */
    $scope.$on(LoginControllerEvents.get('INVALID_LOGIN'), function () {
        $scope.showAlert(Errors.get('INVALID_LOGIN_HEADER'), Errors.get('INVALID_LOGIN_MESSAGE'));
    });

    /* ProfileController */
    $scope.$on(ProfileControllerEvents.get('UPDATE_SUCCESS'), function () {
        $scope.showAlert(Notifications.get('PROFILE_UPDATE_SUCCESS_HEADER'), Notifications.get('PROFILE_UPDATE_SUCCESS_MESSAGE'));
    });

    $scope.$on(ProfileControllerEvents.get('UPDATE_FAILURE'), function () {
        $scope.showAlert(Errors.get('PROFILE_UPDATE_HEADER'), Errors.get('PROFILE_UPDATE_MESSAGE'));
    });

    /* RegistrationController */
    $scope.$on(RegistrationControllerEvents.get('ERROR_SAVING'), function (event, message) {
        $scope.showAlert(Errors.get('REGISTRATION_HEADER'), "we had a problem signing up with that email address, if you already created an account you should reset your password");
    });

    /* ViewModeController */

    //Delete
    $scope.$on(ViewModeControllerEvents.get('CONFIRM_DELETE'), function() {
        $scope.showDiscardAlert = true;
        $scope.promptingEvent = ViewModeControllerEvents.get('CONFIRM_DELETE');
        $scope.showYesNoAlert(Notifications.get('DELETE_MEMMEE_HEADER'), Notifications.get('DELETE_MEMMEE_MESSAGE'), "Delete", "Keep");
    });
    $scope.onDeleteClick = function() {

    };

    $scope.$on(ViewModeControllerEvents.get('SHOW_SHARE_LINK'), function( event, message) {
        console.log("Memmee with id: " + message[0].id + " sharekey: " + message[0].shareKey);
        var shareUrl = $location.protocol() + "://" + $location.host() + "/#/share?shareKey=" + message[0].shareKey;
        console.log("location:::" + shareUrl);
        $scope.showAlert("copy and paste the link below to share.",
            "(don't worry, your other memmees will remain private.)<br><br>"+shareUrl );
    });

    $scope.$on( "closeAllDropdowns", function(event, next, current) {
        console.log("Hiding all alerts on route changed...");
        $scope.hideYesNoAlert();
        $scope.hideAlert();
    });

    //Initialization
    $scope.header = null;
    $scope.message = null;

    $scope.showDiscardAlert = false;
}

AlertsController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location'];function BubblesController($scope, $http, broadCastService, $location) {

    //Handlers
    $scope.toggleNewUserCreateMode = function () {
        $scope.showNewUserCreateMode = !$scope.showNewUserCreateMode;
    };

    $scope.clearALlBubbles = function() {
        $scope.showNewUserCreateMode = false;
    };

    //Styles
    $scope.setNewUserCreateModeStyle = function () {
        $scope.newUserCreateModeStyle = {
            'top':"270px",
            'left':((window.innerWidth / 2) + 185).toString() + "px"};
        //$scope.$apply();
    }

    //Listeners
    $scope.$on(CreateModeControllerEvents.get('NEW_USER_LOGIN'), $scope.toggleNewUserCreateMode);
    $scope.$on(LoginControllerEvents.get('LOGOUT'), $scope.clearALlBubbles);
    $scope.$on(CreateModeControllerEvents.get('CREATE_MODE_CANCELLED'), $scope.clearALlBubbles);
    $scope.$on(ProfileControllerEvents.get('PROFILE_OPENED'), $scope.clearALlBubbles);
    window.addEventListener('onresize', $scope.setNewUserCreateModeStyle, false);
    window.addEventListener('resize', $scope.setNewUserCreateModeStyle, false);

    //Initialization
    $scope.clearALlBubbles();
    $scope.setNewUserCreateModeStyle();

    $scope.$on( "closeAllDropdowns", function(event, next, current) {
        console.log("Hiding all alerts on route changed...");
        $scope.clearALlBubbles();
    });
}

BubblesController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location'];function HeaderController($scope, broadCastService, $location, $timeout) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.loggedInUser = "";

            $scope.visibleLoggedInStyle = 'isHidden';
            $scope.visibleLoggedOutStyle = 'isVisible';
        });

    $scope.$on(LoginControllerEvents.get('LOGIN'), function() {

        console.log("NavigationController:handleLogin()");

        $scope.visibleLoggedInStyle = 'isVisible';
        $scope.visibleLoggedOutStyle = 'isHidden';
        $scope.loggedInUser = broadCastService.user;

    });

    $scope.createModeClicked = function()
    {
        broadCastService.createModeStartedCreateModeController();
        $location.path("create");
    }

    $scope.viewModeClicked = function()
    {
        broadCastService.createModeCancelledCreateModeController();
        $location.path("view");
    }

    $scope.isCreateMode = function () {
        return broadCastService.isCreateMode();
    }

    $scope.isProfileMode = function() {
        var urlPath = $location.path();
        var isProfile = urlPath.indexOf("profile") > -1 ? true : false;
        return isProfile;
    }

    var closedropdownTimer;

    $scope.userSettingsDropdownStyle = "isHidden";
    $scope.toggleSettingsDropdown = function () {
        console.log("mouse over happening.")
        if ($scope.userSettingsDropdownStyle == "isHidden") {
            $scope.userSettingsDropdownStyle = "isVisible";
        }
        else {
            $scope.userSettingsDropdownStyle = "isHidden";
        }

        if( closedropdownTimer )
        {
            closedropdownTimer = undefined;
        }
    };

    $scope.onClickProfile = function() {
        $scope.toggleSettingsDropdown();
        broadCastService.profileOpenedProfileController();
    };

    $scope.closeDropdown = function() {
        $scope.userSettingsDropdownStyle = "isHidden";

        if( closedropdownTimer )
        {
            closedropdownTimer = undefined;
        }
    }

    $scope.closeDropdownIfMouseOutside = function( ){
        closedropdownTimer = $timeout( $scope.closeDropdown, 100);
    };

    $scope.cancelDropdownTimer = function() {
        if( closedropdownTimer )
        {
            $timeout.cancel( closedropdownTimer );
        }
    };

    $scope.$on( "closeAllDropdowns", function(event, next, current) {
        $scope.closeDropdown();
    });

    $scope.signOut = function()
    {
        broadCastService.logoutUser();
        $scope.loggedInUser = "";
        $scope.visibleLoggedInStyle = "isHidden";
        $scope.visibleLoggedOutStyle = 'isVisible';
    };

}

HeaderController.$inject = ['$scope', 'memmeeBroadCastService', '$location', '$timeout'];function ProfileController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = broadCastService.user;
            $scope.confirmedPass = '';
        });

    $scope.update = function()
    {
        if( $scope.confirmedPass != $scope.user.password.value )
        {
            console.log("your password doesn't match your provided password");
            return;
        }

        console.log("scope.user.id" + $scope.user.id);

        $http({method: 'PUT', url: '/memmeeuserrest/user/' + $scope.user.id + '?apiKey=' + $scope.user.apiKey, data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('your user has been updated')
                broadCastService.loginUser(data);
                broadCastService.showProfileUpdatedSuccess();
                broadCastService.createModeStartedCreateModeController();
            }).
            error(function(data, status, headers, config) {
                console.log('error while saving your user');
                broadCastService.showProfileUpdatedError();
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

ProfileController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];function RequiredChangePasswordController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = broadCastService.user;
            $scope.confirmedPass = '';
            $scope.passwordError = '';
        });

    $scope.changePassword = function () {
        if ($scope.confirmedPass != $scope.user.password.value) {
            $scope.passwordError = "The confirmation does not match the password value.";
            return;
        }

        $scope.passwordError = "The confirmation does not match the password value.";

        $http({method:'PUT', url:'/memmeeuserrest/user/' + $scope.user.id + '?apiKey=' + $scope.user.apiKey, data:$scope.user}).
            success(function (data, status, headers, config) {
                broadCastService.loginUser(data);
            }).
            error(function (data, status, headers, config) {
                broadCastService.showProfileUpdatedError();
            });
    }

}

RequiredChangePasswordController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];function HomeController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
        });

    //UI Initialization
    $scope.initializeScrolling = function (clazz) {
        $(clazz).scrollable();
    };
}

HomeController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];function LoginController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = {
                email:'',
                password:''
            };
            $scope.forgotPasswordReminder = "";
            $scope.forgotPasswordSuccessStyle = {};
        });

    //Action Handlers
    $scope.login = function () {
        $http({method:'POST', url:'/memmeeuserrest/user/login', data:$scope.user}).
            success(function (data, status, headers, config) {
                console.log('you were successfully logged into memmee.com');
                broadCastService.loginUser(data);
            }).
            error(function (data, status, headers, config) {
                broadCastService.invalidLoginLoginController();
            });
    }

    $scope.forgotPassword = function () {
        if ($scope.user['email'] != "") {
            $scope.forgotPasswordReminder = "";
            $http({method:'POST', url:'/memmeeuserrest/user/forgotpassword?email=' + $scope.user['email']}).
                success(function (data, status, headers, config) {
                    $scope.forgotPasswordReminder = "A new password was sent to your email";
                    $scope.forgotPasswordSuccessStyle = {'color': '#0000FF'};
                }).
                error(function (data, status, headers, config) {
                    $scope.forgotPasswordReminder = "There was an error sending the new password to your email.";
                });
        } else {
            $scope.forgotPasswordReminder = "Please enter your email address below";
        }
    }

}

LoginController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];function RegistrationController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = {
                email: ''
            };
        });

    $scope.register = function()
    {
        $http({method: 'POST', url: '/memmeeuserrest/user', data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('you were successfully registered');
                broadCastService.loginUser(data);
            }).
            error(function(data, status, headers, config) {
                broadCastService.errorSavingUserRegistrationController(data);
            });
    }
}

RegistrationController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];function ViewModeController($scope, $http, broadCastService, $timeout) {

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

ViewModeController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$timeout'];function CreateMemmeesController($scope, $http, broadCastService) {

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
                $scope.selectedListTheme = "slidecard pushpin josefinslab";
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

    $scope.$on( "closeAllDropdowns", function(event, next, current) {
        $scope.closeShareDropdown();
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

AttachmentController.$inject = ['$scope', 'memmeeBroadCastService'];function InspirationController($scope, $http, broadCastService) {
    //Constants
    var ViewStates = (function () {
        var private = {
            'VIEW_INSPIRATION_STATE':'viewInspirationState',
            'HIDDEN_INSPIRATION_STATE':'hiddenInspirationState'
        };

        return {
            get:function (name) {
                return private[name];
            }
        }
    })();

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.viewState = ViewStates.get('VIEW_INSPIRATION_STATE');
            $scope.user = broadCastService.user;
            $scope.inspiration = null;
            $scope.nextStartingInspiration = null;
            $scope.previousStartingInspiration = null;
        });

    //State Handers
    $scope.hiddenInspiration = function () {
        return $scope.viewState == ViewStates.get('HIDDEN_INSPIRATION_STATE');
    };

    $scope.viewInspiration = function () {
        return $scope.viewState == ViewStates.get('VIEW_INSPIRATION_STATE');
    };

    //Action Handlers
    $scope.getRandomInspiration = function () {
        $http({method:'GET', url:'/memmeeinspirationrest/getrandominspiration?apiKey=' + $scope.user.apiKey}).
            success(function (data, status, headers, config) {
                console.log('The random inspiration has been loaded');
                $scope.inspiration = data;
            }).error(function (data, status, headers, config) {
                console.log('error loading your doggone inspiration');
            });
    };

    $scope.getNextInspiration = function () {
        if ($scope.inspiration == undefined || $scope.inspiration == null)
            throw "You must have a selected inspiration to choose the next inspiration."

        if (!$scope.nextStartingInspiration) {
            $scope.previousStartingInspiration = null;
            $scope.nextStartingInspiration = $scope.inspiration;
        }

        $http({method:'GET', url:'/memmeeinspirationrest/getnextinspiration?apiKey=' + $scope.user.apiKey +
            "&startingId=" + $scope.nextStartingInspiration.id.toString() +
            "&currentId=" + $scope.inspiration.id.toString()}).
            success(function (data, status, headers, config) {
                console.log('inspiration index: ' + data.inspirationCategoryIndex.toString());
                console.log('category index: ' + data.inspirationCategory.index.toString());
                $scope.inspiration = data;
            }).error(function (data, status, headers, config) {
                console.log('error loading your doggone inspiration');
            });
    };

    $scope.getPreviousInspiration = function () {
        if ($scope.inspiration == undefined || $scope.inspiration == null)
            throw "You must have a selected inspiration to choose the next inspiration."

        if (!$scope.previousStartingInspiration) {
            $scope.nextStartingInspiration = null;
            $scope.previousStartingInspiration = $scope.inspiration;
        }

        $http({method:'GET', url:'/memmeeinspirationrest/getpreviousinspiration?apiKey=' + $scope.user.apiKey +
            "&startingId=" + $scope.previousStartingInspiration.id.toString() +
            "&currentId=" + $scope.inspiration.id.toString()}).
            success(function (data, status, headers, config) {
                console.log('inspiration index: ' + data.inspirationCategoryIndex.toString());
                console.log('category index: ' + data.inspirationCategory.index.toString());
                $scope.inspiration = data;
            }).error(function (data, status, headers, config) {
                console.log('error loading your doggone inspiration');
            });
    };

    $scope.toggleViewState = function () {
        if ($scope.viewInspiration()) {
            $scope.viewState = ViewStates.get('HIDDEN_INSPIRATION_STATE');
            $scope.getRandomInspiration();
        } else {
            $scope.viewState = ViewStates.get('VIEW_INSPIRATION_STATE');
        }
    };

    //Initialization
    $scope.getRandomInspiration();
}

InspirationController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];function ShareModeController($scope, $http, broadCastService, $location, $routeParams) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = broadCastService.user;
            $scope.memmee = null;
            $scope.shareKey = null;
            $scope.errorMode = false;
        });

    //Action Handlers

    //Service Calls
    $scope.getDefaultMemmee = function () {
//        http://local.memmee.com/#/share?shareKey=f2d679d0-cb4f-46b8-9d4a-1cfab3585a96

        $scope.shareKey = $routeParams.shareKey;

        $http({method:'GET', url:'/memmeerest/open?shareKey=' + $scope.shareKey}).
            success(function (data, status, headers, config) {
                console.log('your memmee has been loaded');
                $scope.memmee = data;
            }).error(function (data, status, headers, config) {
                console.log('error loading your doggone memmee');
                $scope.errorMode = true;
            });
    };

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
}

ShareModeController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location', '$routeParams'];function ArchiveListController($scope, $http, broadCastService) {
    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = broadCastService.user;
            $scope.memmees = null;
            $scope.memmeeSets = null;
            $scope.scrollableLeft = false;
            $scope.scrollableRight = true;
            $scope.amountScrolledRight = 0;
        });

    //Action Handlers
    $scope.onMemmeeSelect = function (memmee) {
        //console.log("onMemmeeSelect() ---- memmee: " + memmee.text);
        broadCastService.memmeeSelectedArchiveListController(memmee);
    };

    $scope.onCreateMemmee = function () {
        broadCastService.createModeStartedCreateModeController();
    };

    //Broadcast Handlers
    $scope.$on(CreateModeControllerEvents.get('MEMMEE_CREATED'), function () {
        $scope.getMemmees();
    });

    $scope.$on(ViewModeControllerEvents.get('MEMMEE_DELETED'), function () {
        $scope.getMemmees();
    });

    //Service Calls
    $scope.getMemmees = function () {
        $http({method:'GET', url:'/memmeerest/getmemmees?apiKey=' + $scope.user.apiKey}).
            success(function (data, status, headers, config) {
                console.log('your memmee has been loaded');
                $scope.memmees = data;

                if ($scope.memmees) {
                    var memmeeSets = [
                        []
                    ];

                    for (var i = 0; i < $scope.memmees.length; i++) {
                        if (memmeeSets[memmeeSets.length - 1].length >= 4)
                            memmeeSets.push([]);

                        memmeeSets[memmeeSets.length - 1].push($scope.memmees[i]);
                    }

                    $scope.memmeeSets = memmeeSets;
                }
            }).
            error(function (data, status, headers, config) {
                console.log('error loading memmees');
            });
    };

    //UI
    $scope.innerScrollerWidth = function (memmees) {
        if (memmees !== null) {
            return {'width':(memmees.length * 450).toString() + "px"};
        }

        //TODO: This might be a bad value - added this to calm the error it was throwing -AP
        return {'width':"450px"};
    };

    $scope.imageStyle = function (memmee) {
        return {'background-image':'url(' + memmee.attachment.thumbFilePath + ')'};
    };

    $scope.getDisplayDate = function (memmee) {
        if (memmee.displayDate != null) {
            return MemmeeDateUtil.standardDate(new Date.parse(memmee.displayDate));
        }
        return null;
    };

    $scope.initializeScrolling = function (clazz) {
        $(clazz).scrollable();
    }

    //Initialization
    $scope.getMemmees();
}

ArchiveListController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];