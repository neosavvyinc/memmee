/* jasmine specs for facebook service go here */

describe('facebookService factory', function() {
    
    var FB,
        fbService;

    beforeEach(function() {

        //jasmine.createSpy('getThing() function').andReturn("blahblahblah")
        // or
        //jasmine.createSpy('getThing() function').andCallFake(function() {
        // ...
        // return thing;
        //})
        
        FB = { 
            init: function() {}, 
            getLoginStatus: jasmine.createSpy('getLoginStatus() function'),
            login: jasmine.createSpy('login() function'),
            ui: jasmine.createSpy('ui() function')
        };

        module('memmee.constants');
        module('memmee.services', function($provide) {
            $provide.value('facebookLibService', FB);
        });

        inject(function(facebookService) {
            fbService = facebookService;
        });

    });

    describe('calling FB.getLoginStatus', function() {

        it ('should call FB.getLoginStatus', function() {
            fbService.postMemmee();
            expect(FB.getLoginStatus).toHaveBeenCalled();
        });
    });

    describe('user is logged in', function() {

        it ('should not attempt to log in and it should resolve if connected', function() {
            var response = {};
            response.status = 'connected';

            FB.getLoginStatus = jasmine.createSpy('getLoginStatus() function').andCallFake(function(callback) {
                callback(response);
            });

            fbService.postMemmee();
            expect(FB.login).not.toHaveBeenCalled();

            // test promise is resolved
        });

        xit ('should call FB.ui', function() {
            var response = {};
            response.status = 'connected';

            FB.getLoginStatus = jasmine.createSpy('getLoginStatus() function').andCallFake(function(callback) {
                callback(response);
            });
            FB.ui = jasmine.createSpy('ui() function');

            fbService.postMemmee();
            expect(FB.ui).toHaveBeenCalled();

        });

    });

    describe('user is not logged in', function() {

        it ('should attempt to log in if not_authorized status received', function() {
            var response = {};
            response.status = 'not_authorized';

            FB.getLoginStatus = jasmine.createSpy('getLoginStatus() function').andCallFake(function(callback) {
                callback(response);
            });

            FB.login = jasmine.createSpy('login() function').andReturn(response);
            fbService.postMemmee();
            expect(FB.login).toHaveBeenCalled();

            // test promise is not resolved yet
        });

        it ('should attempt to log in if any other response received', function() {
            var response = {};
            response.status = 'gIbB3Ri$h';

            FB.getLoginStatus = jasmine.createSpy('getLoginStatus() function').andCallFake(function(callback) {
                callback(response);
            });

            FB.login = jasmine.createSpy('login() function').andReturn(response);
            fbService.postMemmee();
            expect(FB.login).toHaveBeenCalled();

            // test promise is not resolved yet
        });

    });


    // -> it should throw an error/halt execution/react appropriately if config object is not passed in
    // -> for each, stub response to login call, respond with response.authResponse, then with something else
    // -> if success, expect postToFacebook to have been called (need to stub actual FB api call)
    // -> if failure, ensure proper handling

});
