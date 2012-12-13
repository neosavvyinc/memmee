app.filter('dateformatter',
    ['$filter', function ($filter) {
        'use strict';

        var date = $filter('date');

        // replace iso date with localized date
        return function dateformatter (text, format) {
            if (!format || !text) {
                return;
            }

            if (text) {
                var newDate = new Date(text);
                return date(newDate, format);
            }
        };
    }]);

