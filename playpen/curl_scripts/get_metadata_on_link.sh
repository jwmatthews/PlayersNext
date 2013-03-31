#!/bin/sh

if [ $# -lt 1 ]; then
	echo "Please re-run: $0 with a URL specified"
	exit
fi

URL=`python -c "import urllib; print urllib.quote_plus(\"$1\")"`

echo curl -s http://127.0.0.1:9000/api/links/metadata/${URL} 
curl -s http://127.0.0.1:9000/api/links/metadata/${URL}  | python -m json.tool

