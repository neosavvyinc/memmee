#/usr/sbin/setsebool httpd_can_network_connect 1
#/usr/sbin/setsebool -P httpd_can_network_connect 1
cp -R memmee-client/src/main/resources/* /var/www/html/
