#!/bin/sh
#curl -vvvvv \
# -XPOST \
# -HAccept:application/json \
# -HContent-Type:application/json \
# -d @memmee.json \
# -d @attachment.json \
# http://localhost:8080/memmeerest/insertmemmee/?apiKey=84104fe7-83c6-45db-abf2-01a5a389398f

/usr/local/bin/wget -vvvvv \
 --header='Accept:application/json' \
 --header='Content-Type:application/json' \
 --post-file=memmee.json \
 http://localhost:8080/memmeerest/insertmemmee/?apiKey=84104fe7-83c6-45db-abf2-01a5a389398f
