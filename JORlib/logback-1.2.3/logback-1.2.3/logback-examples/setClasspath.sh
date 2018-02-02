#!/bin/sh

##
# This script will add logback jars to your classpath.
##

LB_HOME=/SET/THIS/PARAMETER/TO/THE/DIRECTORY/WHERE/YOU/INSTALLED/LOGBACK

CLASSPATH="${CLASSPATH}:${LB_HOME}/logback-classic-1.2.3.jar"
CLASSPATH="${CLASSPATH}:${LB_HOME}/logback-core-1.2.3.jar"
CLASSPATH="${CLASSPATH}:${LB_HOME}/logback-examples/logback-examples-1.2.3.jar"
CLASSPATH="${CLASSPATH}:${LB_HOME}/logback-examples/lib/slf4j-api-1.7.25.jar"

export CLASSPATH

echo $CLASSPATH
