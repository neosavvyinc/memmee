Memmee.Directives.directive('uiMask', function() {
    return {
        require: 'ngModel',

        link: function($scope, element, attrs, controller) {
            var mask;

            // Update the placeholder attribute from the mask
            var updatePlaceholder = function() {
                if ( !attrs.placeholder ) {
                    element.attr('placeholder', mask.replace(/9/g, '_'));
                }
            };

            // Render the mask widget after changes to either the model value or the mask
            controller.$render = function() {
                var value = controller.$viewValue || '';
                console.log('Rendering value: ', value);
                console.log('Rendering mask: ', mask);
                element.val(value);
                if ( !!mask ) {
                    element.mask(mask);
                }
                updatePlaceholder();
            };

            // Keep watch for changes to the mask
            attrs.$observe('uiMask', function(maskValue) {
                console.log('mask is ', maskValue);
                mask = maskValue;
                controller.$render();
            });

            // Check the validity of the masked value
            controller.$parsers.push(function(value) {
                return element.mask();
            });

            // Update the model value everytime a key is pressed on the mask widget
            element.bind('keyup', function() {
                console.log('change');
                $scope.$apply(function() {
                    controller.$setViewValue(element.val());
                });
            });

        }
    };
});
