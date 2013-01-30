
var email,
    password,
    currentText = null,
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

        currentText = element('div.flipper p').text();
    });

    it("Should allow a user to select the next inspirational prompt by clicking the right arrow button", function(){
        element('a.btn.next-arrow').click();

        // element() returns a future, which is problematic for comparing saved
        // values to current values in expect(). calling execute (with empty 'done' callback)
        // on currentText will fulfill the promise, thus allowing for assertion to take place
        //
        // that being said, this feels like a hack... maybe there's a better way to do this?
        currentText.execute(function(){});

        expect(element('div.flipper p').text()).not().toEqual(currentText.value);
        expect(element('div.flipper p').text()).not().toEqual(undefined);
    });

    it("Should allow a user to click the back button on inspirations to select the previous prompt", function(){
        // save current inspiration text, click next, click previous, assert that
        // previously saved inspiration text is the same as current inspiration text
        currentText = element('div.flipper p').text();
        element('a.btn.next-arrow').click();
        element('a.btn.prev-arrow').click();

        // currentText was reset to another future, so need call execute again (see previous test)
        currentText.execute(function(){});

        expect(element('div.flipper p').text()).toEqual(currentText.value);
        expect(element('div.flipper p').text()).not().toEqual(undefined);
    });

    it("Should allow the user to type some test text in the memmee and click create", function(){
        memmee_text =
            'Bacon ipsum dolor sit amet labore officia consequat aute sint' +
            'andouille mollit pork loin nostrud. Rump frankfurter cow, jerky' +
            'ham flank ut pork chop deserunt jowl elit reprehenderit fugiat' +
            'bresaola. Tempor leberkas corned beef est fugiat doner rump pork' +
            'chop, venison exercitation mollit ground round. Bacon filet mignon' +
            'fatback ribeye tail. Biltong leberkas cow doner kielbasa brisket' +
            'laborum boudin ex spare ribs proident voluptate culpa in dolore: ' +
            Math.random();

        input('memmee.text').enter(memmee_text);
        element('a.btn.save').click();

        // assert that text-area shows memmee_text
        expect(element('p.memmee-text').text()).toEqual(memmee_text);
        // assert that save controls are hidden/we are in view mode
        expect(element('div.memmee-controls').attr('class')).toContain('isHidden');

        logout();
    });

    xit("Should add the newly typed in memmee to the archive list after clicking create and should be in view mode",function(){
        // TO DO
//        memmeeSets
    });


});

