#!/bin/bash
mvn clean install
cp detail/target/detail.war ./
cp itemcenter/target/itemcenter.war ./
mvn clean
mkdir demo-grey && cp -r  README.md detail detail.war itemcenter itemcenter-api itemcenter.war metacenter pom.xml demo-grey/
rm -rf demo-grey/detail/.project demo-grey/detail/.classpath demo-grey/detail/.settings/ demo-grey/itemcenter/.project demo-grey/itemcenter-api/.classpath demo-grey/itemcenter-api/.settings/ demo-grey/metacenter/.project demo-grey/metacenter/.settings/ demo-grey/metacenter/.classpath
zip -r  demo-grey.zip demo-grey
rm -rf demo-grey
rm -rf detail.war itemcenter.war
