ADDR="127.0.0.1"
PORT=9000

if [ $# -lt 1 ]; then
    echo "Please re-run: $0 <LINK_UUID>"
    exit
fi
LINK_UUID=$1
DATA=`curl -X DELETE -s ${ADDR}:${PORT}/api/link/$1`
echo ${DATA}

echo ""

