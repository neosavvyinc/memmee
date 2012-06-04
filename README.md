Memmee.com Setup Instructions
==========================================

Setup
-----

To setup Dropwizard and build you will need to make sure you have Maven 3.X
installed. After you have followed the instructions for setting up Maven then
you should be able to open a terminal and execute the following:

./recompileAndRun.sh

This should fetch all the Java dependencies and start the server running on
port 8080. I also use MAMP to provide some hooks to Apache and MySQL in one
nice clean package. You are welcome to use Apache and MySQL installed on their
own but they require the following settings:

ProxyPass settings should be added to the advanced tab of MAMP for your server

ProxyPass /backend http://127.0.0.1:8080/backend
ProxyPassReverse /backend http://127.0.0.1:8080/backend

I also set my server up to be called local.commons-user.com and hopefully I
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

To create your database use the following create table script:

USE memmee;
CREATE TABLE user (
id int(11) NOT NULL AUTO_INCREMENT,
firstName varchar(1024) DEFAULT NULL,
lastName varchar(1024) DEFAULT NULL,
email varchar(4096) DEFAULT NULL,
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=10;

I also created a user with name "commons" password "commons" with full rights
on this database.

You should see that when you execute the run.sh script that your sever starts
and when you browse to the following two URLs you are greeted with a simple UI.

Backbone: http://local.commons-user.com/backbone/
Angular: http://local.commons-user.com/angular/

Please give my blog post a read and comment if you have any questions or other
things to say.


