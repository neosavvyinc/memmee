#!/bin/sh
curl -vvvvv \
 -XPUT \
 -HAccept:application/json \
 -HContent-Type:application/json \
 -d @updateuser.json \
 http://localhost:8080/memmeeuserrest/user/-1?apiKey=test