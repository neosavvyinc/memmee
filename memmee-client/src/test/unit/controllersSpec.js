'use strict';

/* jasmine specs for controllers go here */

describe('myController function', function() {
    describe('UserController', function(){
        var userController;
        var scope, userController, $httpBackend;

        beforeEach(inject(function($rootScope, $controller, _$httpBackend_) {

            scope = $rootScope.$new();
            $httpBackend = _$httpBackend_;
            $httpBackend.expectGET('/memmeeuserrest/user').
                respond([{"id":2,"email":"dhamlett@gmail.com","pass":"","firstName":"Dana","lastName":"Hamlett","apiKey":"6865fb30-54d9-498a-afc5-c23787229d68","apiDate":"2012-06-13","creationDate":"2012-06-13"}]);
            userController = $controller(UserController, {$scope: scope});
        }));

        it('should have a hello string with a value of "hello"', function() {
            expect(scope.hello).toBe('hello');
        });
    });

});