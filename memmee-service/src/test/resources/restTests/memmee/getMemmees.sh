#!/bin/sh
curl -vvvvv \
    -XGET \
    -HAccept:application/json \
    -HContent-Type:application/json \
    "http://localhost:8080/memmeerest/getmemmees/?apiKey=test"