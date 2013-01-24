<#-- @ftlvariable name="memmee" type="com.memmee.view.FacebookMemmeeView" -->
<html  ng-init="initializeBodyUI()" ng-app="memmee-app" class="">
<head>

    <base href="/">
    <title>memmee</title>
    <link rel="stylesheet" type="text/css" media="all" href="css/memmee.css">
    <link rel="stylesheet" type="text/css" media="all" href="css/memmee_overrides.css">

    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">

    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">

    <link href="http://fonts.googleapis.com/css?family=Josefin+Sans:100,300,400,600,700,100italic,300italic,400italic,600italic,700italic"
          rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Josefin+Slab:100,300,400,600,700,100italic,300italic,400italic,600italic"
          rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Crimson+Text:400,400italic,600,600italic,700,700italic"
          rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Rokkitt:400,700" rel="stylesheet" type="text/css">
    
</head>
<body>
<div id="container">


    <div id="header"  class="">
        <a href="#home" title="memmee"><img src="img/logo.png" alt="memmee"></a>


        <!-- Logged out Header -->
        <form id="login" method="post"  
              class=" ng-pristine ng-valid isHidden">
            <fieldset>
                <input type="text" id="email" name="email" ng-model="user.email" placeholder="Email Address"
                        class="ng-pristine ng-valid">
                <input type="password" id="pwd" name="pwd" ng-model="user.password" placeholder="Password"
                       class="ng-pristine ng-valid">
                <a ng-click="forgotPassword()" class="link forgotPwd" href="">Forgot Password</a>
            </fieldset>
            <fieldset>
                <a class="btn login" ng-click="login()" href="">Login</a>
            </fieldset>
        </form>

    </div>



    <div class="content" >
        <div id="content" class="">


            <#if memmee.theme?? && memmee.theme.name??>
                <div class="${memmee.theme.name}">
            <#else>
                <div class="memmee-card">
            </#if>


                <span class="memmee-date lg opensans "
                      style="width: auto; min-width: 155px;">${memmee.displayDate}</span>


                    <div class="memmee-user-photo" style="display: none;">
                        <img style="max-height: 180px; max-width: 270px;" src="">
                    </div>


                    <p class="memmee-text ">${memmee.text}</p>


                    <div class="memmee-controls isHidden">
                        <div class="controls styles">
                            <a href="#" class="link addStyle"><span class="icon addStyle"></span>Style your Memmee: </a>
                            <a href="#" class="btn style-news">Style 1</a>
                            <a href="#" class="btn style-coffee">Style 2</a>
                            <a href="#" class="btn style-pushPin">Style 3</a>
                            <a href="#" class="btn style-saleTag">Style 4</a>
                        </div>

                        <div class="controls photo isHidden">
                            <a href="#" class="btn removePhoto">Remove xxx.jpg</a>
                        </div>

                        <div class="controls io isHidden">
                            <a href="#" class="link today"><span class="icon today">Today</span> Today</a>
                            <a href="#" class="btn save">Save</a>
                            <a href="#" class="btn cancel">Cancel</a>
                        </div>
                    </div>
                </div>
            

        </div>
        <!-- /container -->

        
            <div   class=" footer">
                <ul id="footerNav" class="clearfix">
                    <li><a href="http://blog.memmee.com">Blog</a></li>
                    <li><a ng-href="#/about" href="#/about">About / Contact</a></li>
                    <li><a ng-href="#/legal" href="#/legal">Privacy</a></li>
                </ul>
                <ul id="footerSocial" class="clearfix">
                    <li><a target="_blank" class="btn facebook disabled" href="https://www.facebook.com/mymemmee">Facebook</a>
                    </li>
                    <li><a target="_blank" class="btn twitter disabled" href="https://twitter.com/mymemmee">Twitter</a></li>
                </ul>
            </div>
        


    </div>



    <script language="javascript" type="text/javascript" src="http://static.chartbeat.com/js/chartbeat.js"></script>
</body>
</html>