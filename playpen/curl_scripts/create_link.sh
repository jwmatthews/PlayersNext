ADDR="127.0.0.1"
PORT=9000
DATA_FILE="./sample.link.json"
echo "Sending input file ${DATA_FILE} with json data:"
cat ${DATA_FILE} | python -mjson.tool

echo "Curl Command:"
echo curl -s -X POST -H "Content-Type: application/json" -H "Accept: application/json"  -d @${DATA_FILE} ${ADDR}:${PORT}/api/links
curl -s -X POST -H "Content-Type: application/json" -H "Accept: application/json"  -d @${DATA_FILE} ${ADDR}:${PORT}/api/links
