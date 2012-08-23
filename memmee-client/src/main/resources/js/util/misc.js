window.onload = function() {
    var txts = document.getElementsByTagName('TEXTAREA')

    for(var i = 0, l = txts.length; i < l; i++) {
        if(/^[0-9]+$/.test(txts[i].getAttribute("maxlength"))) {
            var func = function() {
                var len = parseInt(this.getAttribute("maxlength"), 10);

                if(this.value.length > len) {
                    console.log('Maximum length exceeded: ' + len);
                    this.value = this.value.substr(0, len);
                    return false;
                }
            }

            txts[i].onkeyup = func;
            txts[i].onblur = func;
        }
    }
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

    if(keynum == 13) {
        if(lines == obj.rows) {
            return false;
        }else{
            lines++;
        }
    }
}