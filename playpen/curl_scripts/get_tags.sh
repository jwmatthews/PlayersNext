ADDR="127.0.0.1"
PORT=9000

DATA=`curl -s ${ADDR}:${PORT}/api/tags`
if [ $? -eq 0 ]
then
  echo ${DATA} | python -mjson.tool
else
  echo ${DATA}
fi

echo ""

