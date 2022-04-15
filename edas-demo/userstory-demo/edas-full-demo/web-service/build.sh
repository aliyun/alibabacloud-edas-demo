mvn package  -Dmaven.test.skip=true
cp ../../lib/txc-client-2.8.18.jar client/lib/
cd client/bin
sed 's/eureka-consumer-1.0-SNAPSHOT.jar/eureka-consumer-1.0-SNAPSHOT.jar:"$REPO"\/txc-client-2.8.18.jar/' ./consumer.sh >consumer_run.sh
chmod +x *.sh
