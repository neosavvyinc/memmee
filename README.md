Memmee.com Setup Instructions
==========================================

Setup
-----

You will need to run a 'mvn clean install' on the maven-plugins directory within the project.
You will need all these plugins before the memmee-client project can be built.

You may need to download JsTestDriver and place it in your local maven repo. If so, get it
from http://code.google.com/p/js-test-driver/downloads/detail?name=JsTestDriver-1.3.5.jar

To setup the project and build you will need to make sure you have Maven 3.X
installed. After you have followed the instructions for setting up Maven then
you should be able to open a terminal and execute the following:

    ./compile.sh

This will make sure to pull down the submodules of Memmee.com and also pull down
all dependencies. Then it will run the tests and you can then start the server.

    ./run.sh

Will fire up the server for you on port 8080.

We also use MAMP to provide some hooks to Apache and MySQL in one
nice clean package. You are welcome to use Apache and MySQL installed on their
own but they require the following settings:

    ProxyPass settings should be added to the advanced tab of MAMP or Apache for your server

    ProxyPass /memmeeuserrest http://127.0.0.1:8080/memmeeuserrest
    ProxyPassReverse /memmeeuserrest http://127.0.0.1:8080/memmeeuserrest

    ProxyPass /memmeerest http://127.0.0.1:8080/memmeerest
    ProxyPassReverse /memmeerest http://127.0.0.1:8080/memmeerest

    ProxyPass /memmeeinspirationrest http://127.0.0.1:8080/memmeeinspirationrest
    ProxyPassReverse /memmeeinspirationrest http://127.0.0.1:8080/memmeeinspirationrest

    Alias /memmee /memmee/

I also set my server up to be called local.memmee.com and hopefully I
don't have any hard coded references to that NameVirtualHost, however if I do
please point them out.

To create your user use the following commands:

    CREATE database memmee;
    CREATE database memmeetest;

    CREATE USER 'memmee'@'%' IDENTIFIED BY 'memmee';
    CREATE USER 'memmee'@'localhost' IDENTIFIED BY 'memmee';
    GRANT ALL PRIVILEGES ON memmee.* TO 'memmee'@'%' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON memmee.* TO 'memmee'@'localhost' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON memmeetest.* TO 'memmee'@'%' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON memmeetest.* TO 'memmee'@'localhost' WITH GRANT OPTION;

    CREATE USER 'memmeetest'@'%' IDENTIFIED BY 'memmeetest';
    CREATE USER 'memmeetest'@'localhost' IDENTIFIED BY 'memmeetest';
    GRANT ALL PRIVILEGES ON memmeetest.* TO 'memmeetest'@'%' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON memmeetest.* TO 'memmeetest'@'localhost' WITH GRANT OPTION;

To initialize the database you just have to go into the memmee-db directory and run:

    mvn liquibase:update

Useful Stuff
------------

I have created a few useful scripts in the root of the file system for the project

- compile.sh: This script will help you initilize and build your project the first time.
- debug.sh: This will start the server in debug mode listening for remote debug connections on port 4000
- deployWebResource.sh: This script is used after a build on the build environment to copy the resources to the correct location for apache
- initSubmodules.sh: This script will setup the external non maven deployed dependencies that are available on github as other projects
- recompileAndRun.sh: This script can be used to recompile the code and then start a server that is listening on port 4000 for debug connections
- serverRun.sh: This script is used to start the server on QA/Dev instances and does not listen for debug connections


Systems Admin Stuff for Deployment
----------------------------------
A few deployment related things have occurred when deploying the code to SE Linux Enabled CentOS environments. So I assume they will also
be relevant to things like RHEL environments.

Firstly we use /memmee to store all the images that are uploaded. These are served outside of /var/www which is the location
used by Apache to serve static content. This causes a SE Linux problem and must be circumvented by allowing HTTPD processes
to serve files outside of /var/www. This is done with the following commands:

    semanage fcontext -a -t httpd_sys_content_t "/memmee(/.*)?"
    restorecon -R -v /memmeee

Secondly we also have to make sure that our ProxyPass will work and SE Linux gets in the way here as well. To do this we just
have to allow HTTPD to establish network connections with the following two commands:

    /usr/sbin/setsebool httpd_can_network_connect 1
    /usr/sbin/setsebool -P httpd_can_network_connect 1

Branch and Tag related Stuff
----------------------------
Right now I am tagging every time we do a deployment to our development environment. Then we can build from a tag.  I am doing
this by creating an annotated tag named after every sprint.

    git tag -a sprint-1 -m 'tagging for sprint 1 release'
    git push --tags

ImageMagick installation for MacOSX
-----------------------------------

I needed ImageMagick for the resizing hooks when uploading images. I used the following commands to install it:

    brew update
    brew install ImageMagick
    sudo chmod 777 /usr/local/include/
    sudo chmod 777 /usr/local/lib/
    brew link little-cms
    brew link jasper
    brew link jpeg
    brew link libtiff


