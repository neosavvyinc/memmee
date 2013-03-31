function ShareModeController($scope, $http, broadCastService, $location, $routeParams, memmeeService, facebookService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = broadCastService.user;
            $scope.memmee = null;
            $scope.shareKey = null;
            $scope.errorMode = false;
        });

    //Action Handlers

    //Service Calls
    $scope.getDefaultMemmee = function () {
//        http://local.memmee.com/#/share?shareKey=f2d679d0-cb4f-46b8-9d4a-1cfab3585a96

        $scope.shareKey = $routeParams.shareKey;

        $http({method:'GET', url:'/memmeerest/open?shareKey=' + $scope.shareKey}).
            success(function (data, status, headers, config) {
                console.log('your memmee has been loaded');
                $scope.memmee = data;
                if( isReshare ) {
                    $scope.onShareLinkOnFacebook();
                }
            }).error(function (data, status, headers, config) {
                console.log('error loading your doggone memmee');
                $scope.errorMode = true;
            });
    };

    //UI
    $scope.getDisplayDate = function (memmee) {
        if (memmee && memmee.displayDate) {
            return MemmeeDateUtil.standardDate(new Date.parse(memmee.displayDate));
        }
        return null;
    };

    //Initializaton
    $scope.getDefaultMemmee();

    $scope.showImage = function () {
        if ($scope.memmee && $scope.memmee.attachment && $scope.memmee.attachment.filePath) {
            return true;
        }
        return false;
    };

    var getShareUrl = function () {
        //($location.protocol() + "://" + $location.host())
        return $location.absUrl();
    };

    $scope.onShareLinkOnFacebook = function () {

            var fbConfig = {
                method: 'feed',
                name: 'got a moment? take a peek...',
                link: $scope.memmee.shortenedUrl,
                picture: $location.protocol() + "://" + $location.host() + '/img/memmee-facebook-icon.jpg',
                caption: 'memmee',
                description: StringUtil.truncate($scope.memmee.text, 140),
                actions: [{
                    name: 'Re-Share',
                    link: $scope.memmee.shortenedUrl + '&reshare=true'
                }]
            };

            console.log(fbConfig);

            facebookService.postMemmee(fbConfig).then(
                function (success) {
                    broadCastService.showFacebookPostViewModeController();
                },
                function (failure) {
                    console.error(failure);
                });

            //Capture the target to apply the link in the future
            var target = event.currentTarget;
            target.href = null;

    };

    var isReshare = $location.search().reshare;

}

ShareModeController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location', '$routeParams', 'memmeeService', 'facebookService'];