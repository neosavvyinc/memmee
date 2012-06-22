#!/bin/sh
curl -vvvvv \
    -XPOST \
    -HAccept:application/json \
    -HContent-Type:application/json \
    -d @memmee.json \
    http://localhost:8080/memmeerest/insertmemmee/?apiKey=test
