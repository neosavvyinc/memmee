var MemmeeDateUtil = (function () {

    var shortMonth = {
        '0':'Jan',
        '1':'Feb',
        '2':'Mar',
        '3':'Apr',
        '4':'May',
        '5':'Jun',
        '6':'Jul',
        '7':'Aug',
        '8':'Sep',
        '9':'Oct',
        '10':'Nov',
        '11':'Dec'
    };

    var longMonth = {
        '0':'January',
        '1':'February',
        '2':'March',
        '3':'April',
        '4':'May',
        '5':'June',
        '6':'July',
        '7':'August',
        '8':'September',
        '9':'October',
        '10':'November',
        '11':'December'
    };

    var method = {
        shortMonth:function (number) {
            if (number != null) {
                return shortMonth[number.toString()];
            }
            return null;
        },
        longMonth:function (number) {
            if (number != null) {
                return longMonth[number.toString()];
            }
            return null;
        },
        isToday:function (date) {
            if (date != null) {
                var today = Date.today()
                return date.getDate() == today.getDate() && date.getMonth() == today.getMonth() && date.getYear() == today.getYear();
            }
            return false;
        }
    };

    return {
        shortMonth:method['shortMonth'],
        longMonth:method['longMonth'],
        isToday:method['isToday'],
        standardDate:function (date) {
            if (date != null) {
                    return method['longMonth'](date.getMonth()) +
                        " " + date.getDate().toString() +
                        ", " + date.getFullYear().toString();
            }
            return null;
        }
    };

})();