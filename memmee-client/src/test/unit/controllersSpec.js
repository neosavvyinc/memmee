'use strict';

/* jasmine specs for controllers go here */

describe('myController function', function() {
    describe('UserController', function(){
        var userController;
        var scope, userController, $httpBackend;

        beforeEach(inject(function($rootScope, $controller, _$httpBackend_) {

            scope = $rootScope.$new();
            $httpBackend = _$httpBackend_;
            $httpBackend.expectGET('phones/phones.json').
                respond([{name: 'Nexus S'}, {name: 'Motorola DROID'}]);
            userController = $controller(UserController, {$scope: scope});
        }));

        it('should have a hello string with a value of "hello"', function() {
            expect(scope.hello).toBe('hello');
        });
    });

});