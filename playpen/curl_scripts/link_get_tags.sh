if [ $# -lt 1 ]; then
  echo "Usage: $0 LINK_ID"
  exit
fi
ADDR="127.0.0.1"
PORT=9000
LINK_ID=$1

echo "Curl Command:"
echo curl -s -X GET -H "Accept: application/json" ${ADDR}:${PORT}/api/link/${LINK_ID}/tag
curl -s -X GET -H "Accept: application/json" ${ADDR}:${PORT}/api/link/${LINK_ID}/tag
