
Memmee.Directives.directive('keyHandler', function() {
        
    console.log('keyHandler');
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                // if keyIdentifier attribute is not set, default to 'Enter' key
                var keyIdentifier = attrs.keyIdentifier || 'Enter';       
                console.log('keyhandler');

                element.bind('keypress', function(e) {
                    // if the pressed key is the key specified, execute the controller
                    // function specified by fnname attribute
                    if (e.keyIdentifier === keyIdentifier) {
                        scope[attrs.fnname]();
                    }    
                });
            }
        };
    });
