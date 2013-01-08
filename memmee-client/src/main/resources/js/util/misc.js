window.onload = function () {
    "use strict";
    var txts = document.getElementsByTagName('TEXTAREA'),
        i = 0,
        l = txts.length;

    for (i = 0; i < l; i++) {
        if (/^[0-9]+$/.test(txts[i].getAttribute("maxlength"))) {
            var func = function() {
                var len = parseInt(this.getAttribute("maxlength"), 10);

                if (this.value.length > len) {
                    console.log('Maximum length exceeded: ' + len);
                    this.value = this.value.substr(0, len);
                    return false;
                }
            }

            txts[i].onkeyup = func;
            txts[i].onblur = func;
        }
    }

    //Browser Compatibility
    addStringPrototypeMethods();
}

var keynum, lines = 1;

function limitLines(obj, e) {

    // IE
    if(window.event) {
        keynum = e.keyCode;
        // Netscape/Firefox/Opera
    } else if(e.which) {
        keynum = e.which;
    }

    if(keynum === 13) {
        if(lines === obj.rows - 1) {
            return false;
        }
    }

    //TODO FIX NPE
    lines = obj.value.match(/\n/g).length;
    if(lines == obj.rows) {
        return false;
    }
}

function addStringPrototypeMethods() {
    if (!String.prototype.trim) {
        String.prototype.trim = function() {
            return this.replace(/^\s+|\s+$/g,'');
        }
    }
}

function simulateClick(elm) {
    var evt = document.createEvent("MouseEvents");
    evt.initMouseEvent("click", true, true, window,
        0, 0, 0, 0, 0, false, false, false, false, 0, null);
    var canceled = !elm.dispatchEvent(evt);
    if(canceled) {
        // A handler called preventDefault
        // uh-oh, did some XSS hack your site?
    } else {
        // None of the handlers called preventDefault
        // do stuff
    }
}