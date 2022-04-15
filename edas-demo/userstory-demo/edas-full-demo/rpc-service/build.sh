mvn package  -Dmaven.test.skip=true
cp ../../lib/txc-client-2.8.18.jar client/lib/
cd client/bin
sed 's/eureka-provider-1.0-SNAPSHOT.jar/eureka-provider-1.0-SNAPSHOT.jar:"$REPO"\/txc-client-2.8.18.jar/' ./provide1.sh >provide1_run.sh
chmod +x *.sh
