if [ $# -lt 2 ]; then
  echo "Usage: $0 LINK_ID TAG"
  exit
fi
ADDR="127.0.0.1"
PORT=9000
LINK_ID=$1
TAG=$2

echo "Curl Command:"
echo curl -s -X PUT -H "Accept: application/json" ${ADDR}:${PORT}/api/links/${LINK_ID}/tag/${TAG}
curl -s -X PUT -H "Accept: application/json" ${ADDR}:${PORT}/api/links/${LINK_ID}/tag/${TAG}
