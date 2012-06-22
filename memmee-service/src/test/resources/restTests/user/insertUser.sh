#!/bin/sh
curl -vvvvv \
 -XPOST \
 -HAccept:application/json \
 -HContent-Type:application/json \
 -d @user.json \
 http://localhost:8080/memmeeuserrest/user



