function HomeController($scope, $timeout) {
    "use strict";
    $scope.img1zindex = {"z-index": 4};
    $scope.img2zindex = {"z-index": 3};
    $scope.img3zindex = {"z-index": 2};
    $scope.img4zindex = {"z-index": 1};

    $scope.carouselState = "1";

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
        });



    $scope.clickNext = function () {

        if ($scope.carouselState < 4) {
            $scope.carouselState++;
        }

        switch ($scope.carouselState) {
            case 1:
                $scope.img1zindex = {"z-index": 4};
                $scope.img2zindex = {"z-index": 3};
                $scope.img3zindex = {"z-index": 2};
                $scope.img4zindex = {"z-index": 1};
                break;
            case 2:
                $scope.img1zindex = {"z-index": 1};
                $scope.img2zindex = {"z-index": 4};
                $scope.img3zindex = {"z-index": 3};
                $scope.img4zindex = {"z-index": 2};
                break;

            case 3:
                $scope.img1zindex = {"z-index": 2};
                $scope.img2zindex = {"z-index": 1};
                $scope.img3zindex = {"z-index": 4};
                $scope.img4zindex = {"z-index": 3};
                break;

            case 4:
                $scope.img1zindex = {"z-index": 3};
                $scope.img2zindex = {"z-index": 2};
                $scope.img3zindex = {"z-index": 1};
                $scope.img4zindex = {"z-index": 4};
                break;

        }
    }

    $scope.clickPrevious = function () {
        if ($scope.carouselState > 1) {
            $scope.carouselState--;
        }

        switch ($scope.carouselState) {
            case 1:
                $scope.img1zindex = {"z-index": 4};
                $scope.img2zindex = {"z-index": 3};
                $scope.img3zindex = {"z-index": 2};
                $scope.img4zindex = {"z-index": 1};
                break;
            case 2:
                $scope.img1zindex = {"z-index": 1};
                $scope.img2zindex = {"z-index": 4};
                $scope.img3zindex = {"z-index": 3};
                $scope.img4zindex = {"z-index": 2};
                break;

            case 3:
                $scope.img1zindex = {"z-index": 2};
                $scope.img2zindex = {"z-index": 1};
                $scope.img3zindex = {"z-index": 4};
                $scope.img4zindex = {"z-index": 3};
                break;

            case 4:
                $scope.img1zindex = {"z-index": 3};
                $scope.img2zindex = {"z-index": 2};
                $scope.img3zindex = {"z-index": 1};
                $scope.img4zindex = {"z-index": 4};
                break;
        }
    }

    $scope.getBlogPost = function() {
        if( google && google.feeds && google.feeds.Feed ) {
            var feed = new google.feeds.Feed("http://blog.memmee.com/?feed=atom");
            feed.load(function(result) {
                $scope.$apply(function(){
                    var singleEntry = [];
                    singleEntry[0] = result.feed.entries[0];
                    $scope.entries = singleEntry;
                })
            });
        } else {
            $timeout($scope.getBlogPost(), 1000);
        }
    }

    $timeout($scope.getBlogPost(), 1000);


    // using $timeout causes e2e text execution to be delayed until
    // timer  using setTimeout w/ $scope.$apply prevents this
    setTimeout(function() {
        $scope.$apply(function() {
            $scope.clickNext();
        });
    }, 5000);

    setTimeout(function() {
        $scope.$apply(function() {
            $scope.clickNext();
        });
    }, 10000);

    setTimeout(function() {
        $scope.$apply(function() {
            $scope.clickNext();
        });
    }, 15000);





}

HomeController.$inject = ['$scope', '$timeout'];