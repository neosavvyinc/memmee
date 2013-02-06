'use strict'

describe("Memmee view mode features", function(){

    var email,
        password;

    email = 'aparrish@neosavvy.com';
    password = 'test';

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

    beforeEach(function() {
        browser().navigateTo('/');
        login(email, password);
    });

    afterEach(function() {
        logout(email, password);
    });


    it("Should support loading all memmees created into the archive list", function(){
        expect(element('a.slidecard').count()).toBeGreaterThan(0);

    });

    xit("Should support clicking on a memmee in the archive list and seeing it loaded into the view area", function(){

        // select the first memmee in the list
        element('a.slidecard:eq(0)').click();

        element('a.slidecard:eq(0)').query(function(elem, done) {
            expect(element('p.memmee-text').text()).toContain('Bacon');
            done();
        });

        // TO DO
        // assert that text in view area contains text in the the selected archived memmee
        // expect(element('p.memmee-text').text()).toEqual(currentText.value);

    });

    it("Should support clicking the share button and a new dialog should appear to let the user share via bitly", function(){

        // select the first memmee in the list
        element('a.slidecard:eq(0)').click();
        element('a.btn.share').click();
        element('ul.memmee-style.dropdown:eq(1) li:first a').click();

        expect(element('div.overlay').attr('class')).not().toContain('isHidden');
        expect(element('div.overlay').attr('class')).toContain('isVisible');
        expect(element('div.overlay h1:eq(0)').text()).toEqual('copy and paste the link below to share.');
        expect(element('div.overlay p:eq(0)').text()).toContain('bit.ly');
    });

    xit("Should support clicking the share button and a new dialog should appear to share via facebook", function(){

        // select the first memmee in the list
        element('a.slidecard:eq(0)').click();
        element('a.btn.share').click();
        element('ul.memmee-style.dropdown:eq(1) li:eq(1) a').click();

        // TO DO
        // facebook dialog does not seem to be loading properly, need to fix this.
    });

    it("Should support deleting the memmee that is being viewed and should prompt to make sure a user really wants to do this", function(){

        // select the first memmee in the list
        element('a.slidecard:eq(0)').click();
        element('a.btn.share').click();
        element('ul.memmee-style.dropdown:eq(1) li:eq(1) a').click();

        expect(element('div.modal.discard-memmee:eq(1)').attr('class')).toContain('isHidden');
        element('a.btn.trash').click();
        expect(element('div.modal.discard-memmee:eq(1)').attr('class')).toContain('isVisible');
        
        // TO DO
        // delete selected, assert that list of memmees contains one less entry than before

    });


});
