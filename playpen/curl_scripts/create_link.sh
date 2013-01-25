ADDR="127.0.0.1"
PORT=9000
TIMESTAMP=`date +"%m-%d-%y_%H-%M-%S"`
echo "Input:"
echo ${POST_DATA} | python -mjson.tool

echo "Curl Command:"
echo curl -s -X POST -H "Content-Type: application/json" -H "Accept: application/json"  -d "{\"url\":\"http://example.com/${TIMESTAMP}\", \"description\":\"Example description\"}" ${ADDR}:${PORT}/api/link

echo "Received:"
curl -s -X POST -H "Content-Type: application/json" -H "Accept: application/json"  -d "{\"url\":\"http://example.com/${TIMESTAMP}\", \"description\":\"Example description\"}" ${ADDR}:${PORT}/api/link
echo ""

