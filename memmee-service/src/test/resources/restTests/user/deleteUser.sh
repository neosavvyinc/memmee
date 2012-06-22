#!/bin/sh
curl -vvvvv \
    -XDELETE \
    "http://localhost:8080/memmeeuserrest/user/-1?apiKey=test"