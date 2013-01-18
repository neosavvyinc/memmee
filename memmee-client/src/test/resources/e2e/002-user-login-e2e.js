'use strict'

describe('Logging in with valid email and password, logging out, and changing passwords', function() {

    var email,
        password;

    email = 'aparrish@neosavvy.com';
    password = 'test';

    beforeEach(function() {
        browser().navigateTo('/');
    });

    afterEach(function() {
        logout();
    });

    var login = function(email, password) {
        input('user.email').enter(email);
        input('user.password').enter(password);
        element('a.btn.login').click();
    };

    var logout = function() {
        //element('#primaryNav a.link.more:first').click();
        element('li:eq(2) a').click();
        element('li:eq(4) ul li:eq(1) a').click();
    };

    it('should navigate to /create', function() {

        login(email, password);
        expect(browser().location().path()).toBe('/create');
    });

    it('should allow the user to change a password, logout, then login with the new password', function() {

        var newPass = 'testpass';

        var changePassword = function(newPass) {
            input('user.password.value').enter(newPass);
            input('confirmedPass').enter(newPass);
            element('a.btn.save').click();
        };

        var navToProfile = function() {
            element('li:eq(2) a').click();
            element('li:eq(4) ul li:eq(0) a').click();
            expect(browser().location().path()).toBe('/profile');
        };

        // login
        login(email, password);

        // navigate to profile page
        navToProfile();

        // change password
        changePassword(newPass);

        // logout
        logout();

        // log back in, user should be at '/create' url
        login('aparrish@neosavvy.com', 'testpass');
        expect(browser().location().path()).toBe('/create');

        // navigate to profile page
        navToProfile();

        // change password back to original value...
        // NOTE: this is awkward, in that this should
        // probably be a teardown step, but then the
        // teardown would involve the same thing we are
        // testing here. need to think about this...
        changePassword(password);

    })

});


