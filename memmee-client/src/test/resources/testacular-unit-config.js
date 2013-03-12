// Sample Testacular configuration file, that contain pretty much all the available options
// It's used for running client tests on Travis (http://travis-ci.org/#!/vojtajina/testacular)
// Most of the options can be overriden by cli arguments (see testacular --help)



// base path, that will be used to resolve files and exclude
basePath = '../../';

// list of files / patterns to load in the browser
files = [

    JASMINE,
    JASMINE_ADAPTER,

    //libraries
    'main/resources/lib/jquery-1.8.1.js',
    'main/resources/lib/jquery.tools.min.js',
    'main/resources/lib/angular/angular.js',
    'main/resources/lib/angular/angular-resource.js',
    'test/resources/lib/angular-mocks.js',
    'test/resources/lib/facebook.js',
    'https://www.google.com/jsapi',

    //resource
    
    'main/resources/js/util/misc.js',
    'main/resources/js/app.js',
    'main/resources/js/configuration-constants.js',
    'main/resources/js/default-ctrl.js',
    'main/resources/js/security-ctrl.js',
    'main/resources/js/services.js',
    'main/resources/js/service/facebook-lib-service.js',
    'main/resources/js/service/facebook-service.js',
    'main/resources/js/alerts/alerts-ctrl.js',
    'main/resources/js/const/event/events.js',
    'main/resources/js/const/message/errors.js',
    'main/resources/js/const/message/notifications.js',
    'main/resources/js/header/header-ctrl.js',
    'main/resources/js/home/home-ctrl.js',
    'main/resources/js/home/login-ctrl.js',
    'main/resources/js/home/registration-ctrl.js',
    'main/resources/js/memmee/common/bubbles-ctrl.js',
    'main/resources/js/memmee/create/attachment-ctrl.js',
    'main/resources/js/memmee/create/create-ctrl.js',
    'main/resources/js/memmee/create/inspiration-ctrl.js',
    'main/resources/js/memmee/list/list-ctrl.js',
    'main/resources/js/memmee/share/share-ctrl.js',
    'main/resources/js/memmee/view/view-ctrl.js',
    'main/resources/js/profile/change-password-ctrl.js',
    'main/resources/js/profile/profile-ctrl.js',
    'main/resources/js/util/memmee_date_util.js',

    //tests
    'test/resources/unit/js/*-unit.js'
];

// list of files to exclude
exclude = [];

// use dots reporter, as travis terminal does not support escaping sequences
// possible values: 'dots' || 'progress'
reporter = 'progress';

// web server port
port = 8080;

// cli runner port
runnerPort = 9100;

// enable / disable colors in the output (reporters and logs)
colors = true;

// level of logging
// possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
logLevel = LOG_INFO;

// enable / disable watching file and executing tests whenever any file changes
autoWatch = true;

// polling interval in ms (ignored on OS that support inotify)
autoWatchInterval = 100;

// Start these browsers, currently available:
// - Chrome
// - ChromeCanary
// - Firefox
// - Opera
// - Safari
//browsers = ['Chrome'];
browsers = ['Chrome', 'Firefox', 'Safari'];

// Auto run tests on start (when browsers are captured) and exit
singleRun = true;
