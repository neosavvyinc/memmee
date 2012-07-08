Memmee.com Setup Instructions
==========================================

Setup
-----

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

    Alias /memmee /memmee/

I also set my server up to be called local.memmee.com and hopefully I
don't have any hard coded references to that NameVirtualHost, however if I do
please point them out.

To create your user use the following commands:

    CREATE memmee
    CREATE memmeetest

    CREATE USER 'memmee'@'%' IDENTIFIED BY 'memmee';
    CREATE USER 'memmee'@'localhost' IDENTIFIED BY 'memmee';
    GRANT ALL PRIVILEGES ON memmee.* TO 'memmee'@'%' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON memmee.* TO 'memmee'@'localhost' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON memmeetest.* TO 'memmee'@'%' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON memmeetest.* TO 'memmee'@'localhost' WITH GRANT OPTION;

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

