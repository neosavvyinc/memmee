function HomeController($scope, $timeout) {
    "use strict";
    $scope.img1zindex = {"z-index": 3};
    $scope.img2zindex = {"z-index": 2};
    $scope.img3zindex = {"z-index": 1};

    $scope.carouselState = "1";

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
        });



    $scope.clickNext = function () {
        console.log("Clicking next");

        if ($scope.carouselState < 3) {
            $scope.carouselState++;
        }

        switch ($scope.carouselState) {
        case 1:
            $scope.img1zindex = {"z-index": 3};
            $scope.img2zindex = {"z-index": 2};
            $scope.img3zindex = {"z-index": 1};
            break;
        case 2:
            $scope.img1zindex = {"z-index": 1};
            $scope.img2zindex = {"z-index": 3};
            $scope.img3zindex = {"z-index": 2};
            break;

        case 3:
            $scope.img1zindex = {"z-index": 1};
            $scope.img2zindex = {"z-index": 2};
            $scope.img3zindex = {"z-index": 3};
            break;
        }
    }

    $scope.clickPrevious = function () {
        console.log("Clicking previous");

        if ($scope.carouselState > 1) {
            $scope.carouselState--;
        }

        switch ($scope.carouselState) {
        case 1:
            $scope.img1zindex = {"z-index": 3};
            $scope.img2zindex = {"z-index": 2};
            $scope.img3zindex = {"z-index": 1};
            break;
        case 2:
            $scope.img1zindex = {"z-index": 1};
            $scope.img2zindex = {"z-index": 3};
            $scope.img3zindex = {"z-index": 2};
            break;
        case 3:
            $scope.img1zindex = {"z-index": 1};
            $scope.img2zindex = {"z-index": 2};
            $scope.img3zindex = {"z-index": 3};
            break;
        }
    }


    $timeout($scope.clickNext, 5000);
    $timeout($scope.clickNext, 10000);

}

HomeController.$inject = ['$scope', '$timeout'];