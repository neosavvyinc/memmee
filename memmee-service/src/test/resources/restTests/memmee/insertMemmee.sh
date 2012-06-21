#!/bin/sh
curl -vvvvv \
    -XPOST \
    -HAccept:application/json \
    -HContent-Type:application/json \
    -d @memmee.json \
    http://localhost:8080/memmeerest/insertmemmee/?apiKey=test

#/usr/bin/wget -vvvvv \
# --header='Accept:application/json' \
# --header='Content-Type:application/json' \
# --post-file=memmee.json \
# http://localhost:8080/memmeerest/insertmemmee/?apiKey=test
