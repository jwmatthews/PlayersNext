#!/bin/sh

if [ $# -lt 1 ]; then
	echo "Please re-run: $0 with a URL specified"
	exit
fi

URL=`python -c "import urllib; print urllib.quote_plus(\"$1\")"`
KEY="1f920f0a1366447ca03290b7b96670cb"
MAX_WIDTH=500

echo curl -s http://api.embed.ly/1/extract?key=${KEY}\&url=${URL}
curl -s http://api.embed.ly/1/extract?key=${KEY}\&url=${URL}  | python -m json.tool

