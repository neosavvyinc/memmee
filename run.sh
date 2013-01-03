#!/bin/bash

java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=4000  -jar memmee-service/target/memmee-service-1.0-SNAPSHOT.jar server memmee-service/target/classes/memmee-service.yml
