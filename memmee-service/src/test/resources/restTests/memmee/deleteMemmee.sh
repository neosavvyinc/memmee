#!/bin/sh
curl -vvvvv \
    -XDELETE \
    "http://localhost:8080/memmeerest/deletememmee?id=2&apiKey=test"