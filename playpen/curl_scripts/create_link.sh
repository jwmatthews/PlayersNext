ADDR="127.0.0.1"
PORT=9000
TIMESTAMP=`date +"%m-%d-%y_%H-%M-%S"`
POST_DATA="{\"url\":\"http://example.com/${TIMESTAMP}\", \"description\":\"Example description\"}"
echo "Input:"
echo ${POST_DATA} | python -mjson.tool
echo "Received:"
DATA=`curl -s -X POST --data ${POST_DATA} ${ADDR}:${PORT}/api/link`
if [ $? -eq 0 ]
then
  echo ${DATA} | python -mjson.tool
else
  echo ${DATA}
fi

echo ""

