
var email,
    password,
    memmeeText = "";


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

describe("Memmee creation", function() {

    it("Should allow a user to access the create page", function() {
        browser().navigateTo('/');
        login(email, password);
        expect(browser().location().path()).toBe('/create');
    });

    it("Should allow a user to see an inspirational prompt", function() {

        expect(element('div.flipper p').text()).toContain(
            "record what's on your mind today: a conversation, " +
            "a feeling, a laugh. a few words will do. need " +
            "inspiration? hit the arrow on the right for writing prompts."
        );

    });

    it("Should allow a user to select the next inspirational prompt by clicking the right arrow button", function(){
        var originalTextElem = element('div.flipper p');

        originalTextElem.query(function(elem, done) {
            // click next button, assert that new text that appears after clicking is:
            //      not undefined
            //      not equal to original text
            element('a.btn.next-arrow').click();
            expect(element('div.flipper p').text()).not().toEqual(undefined);
            expect(element('div.flipper p').text()).not().toEqual(elem.text());
            done();
        });

    });

    it("Should allow a user to click the back button on inspirations to select the previous prompt", function(){
        // save current inspiration text, click next, save next text element, click previous,
        // assert that:
        //      previously saved inspiration text is the same as current inspiration text,
        //      next text is NOT the same as current text
        var originalTextElem = element('div.flipper p');

        originalTextElem.query(function(elem, done) {
            element('a.btn.next-arrow').click();
            var nextTextElem = element('div.flipper p');
            element('a.btn.prev-arrow').click();
            expect(element('div.flipper p').text()).toEqual(elem.text());
            expect(element('div.flipper p').text()).not().toEqual(nextTextElem.text());
            done();
        });

    });

    it("Should allow the user to type some test text in the memmee and click create", function(){
        memmee_text =
            Math.random(): +
            'Bacon ipsum dolor sit amet labore officia consequat aute sint' +
            'andouille mollit pork loin nostrud. Rump frankfurter cow, jerky' +
            'ham flank ut pork chop deserunt jowl elit reprehenderit fugiat' +
            'bresaola. Tempor leberkas corned beef est fugiat doner rump pork' +
            'chop, venison exercitation mollit ground round. Bacon filet mignon' +
            'fatback ribeye tail. Biltong leberkas cow doner kielbasa brisket' +
            'laborum boudin ex spare ribs proident voluptate culpa in dolore';

        input('memmee.text').enter(memmee_text);
        element('a.btn.save').click();

        // assert that text-area shows memmee_text
        expect(element('p.memmee-text').text()).toEqual(memmee_text);
        // assert that save controls are hidden/we are in view mode
        expect(element('div.memmee-controls').attr('class')).toContain('isHidden');

        // move elsewhere
        logout();
    });

    xit("Should add the newly typed in memmee to the archive list after clicking create and should be in view mode",function(){
        // TO DO
        var count = repeater('a').count();

        console.log(count);

        expect(repeater('a').row(count-1)).toEqual(2);
//        memmeeSets
    });


});

