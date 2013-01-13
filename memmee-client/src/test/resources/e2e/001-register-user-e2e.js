'use strict'

describe('Register a User', function(){

    it('Should verify all elements on home page', function(){
        browser().navigateTo("/");
        expect(browser().window().hash()).toMatch('');

        var emailLoginElement = element("#email");
        expect(emailLoginElement.val()).toMatch('');


        console.log("test1");
    });

});