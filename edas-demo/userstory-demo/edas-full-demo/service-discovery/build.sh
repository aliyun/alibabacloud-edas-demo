mvn package -Dmaven.test.skip=true
cp ../../lib/txc-client-2.8.18.jar client/lib/
cd client/bin
sed 's/eureka-test-0.0.1-SNAPSHOT.jar/eureka-test-0.0.1-SNAPSHOT.jar:"$REPO"\/txc-client-2.8.18.jar/' ./locator.sh >locator_run.sh
chmod +x *.sh
