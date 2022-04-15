mvn package -Dmaven.test.skip=true
cp ../../lib/txc-client-2.8.18.jar client/lib/
cd client/bin
sed 's/eureka-provider2-1.0-SNAPSHOT.jar/eureka-provider2-1.0-SNAPSHOT.jar:"$REPO"\/txc-client-2.8.18.jar/' ./provide2.sh >provide2_run.sh
chmod +x *.sh
