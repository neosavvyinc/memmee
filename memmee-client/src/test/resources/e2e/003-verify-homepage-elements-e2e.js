'use strict'

describe('when first navigating to the memmee homepage, all elements should be present', function() {

    beforeEach(function() {
        browser().navigateTo('/');
    });

    it('should show the logged out header', function() {

        // should show logged out header, probably need to call logout before/after this
        var $header = element('#header');
        expect($header.count()).toEqual(1);

        // TO DO
        // check if this is visible or hidden
        // based on user login/login
    });

    it('should show the carousel', function() {

        expect(element('.memmee-carousel').count()).toEqual(1);
        expect(element('.signup.sm form input').count()).toEqual(1);
    });

    it('should show the google feed', function() {

        // expect google feed
        //  what is state of feed/dom when no feeds are available/loaded? check source
        //  set some way to set up count of posts (inject backend mock for feed?)
        //                              element('.blog-container div.blog-post').count() ---> ?

    });

    it('should show the footer email signup field', function() {

        expect(element('#signupLower input').count()).toEqual(1);
    });
});
