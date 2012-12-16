#!/bin/bash

HEALTH_CHECK_RETURN_CODE="`curl -sL -w "%{http_code}\\n" "http://localhost:8081/healthcheck" -o /dev/null`"

if [ $HEALTH_CHECK_RETURN_CODE -eq "200" ]
then
	echo 'Health check passed at' `date` 
	#| mail -s "Health Check Passed" aparrish@neosavvy.com
elif [ $HEALTH_CHECK_RETURN_CODE -eq "500" ]
then
	echo 'Server is unhealthy - needs a restart' `date` | mail -s "Server Restarted" aparrish@neosavvy.com
        /opt/memmee/startNohup.sh
else
	echo 'Server is unhealthy in unhandled state' $HEALTH_CHECK_RETURN_CODE `date` | mail -s "Server Restarted" aparrish@neosavvy.com
        /opt/memmee/startNohup.sh
fi
