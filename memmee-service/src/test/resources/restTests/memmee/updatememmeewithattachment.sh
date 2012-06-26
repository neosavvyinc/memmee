#!/bin/sh
curl -vvvvv \
    -XPUT \
    -HAccept:application/json \
    -HContent-Type:application/json \
    -d @updatememmee.json \
    http://localhost:8080/memmeerest/updatememmeewithattachment/?apiKey=test