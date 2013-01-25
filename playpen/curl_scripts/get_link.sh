ADDR="127.0.0.1"
PORT=9000

if [ $# -lt 1 ]; then
    echo "Please re-run: $0 <LINK_UUID>"
    exit
fi
LINK_UUID=$1
DATA=`curl -s ${ADDR}:${PORT}/api/link/$1`
if [ $? -eq 0 ]
then
  echo ${DATA} | python -mjson.tool
else
  echo ${DATA}
fi

echo ""

