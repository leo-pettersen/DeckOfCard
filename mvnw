#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
# Maven Start Up Batch script
#
# Required ENV vars:
# ------------------
#   JAVA_HOME - location of a JDK home dir
#
# Optional ENV vars
# -----------------
#   MAVEN_OPTS - parameters passed to the Java VM when running Maven
#     e.g. to debug Maven itself, use
#       set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000
#   MAVEN_SKIP_RC - flag to disable loading of mavenrc files
# ----------------------------------------------------------------------------

if [ -z "$MAVEN_SKIP_RC" ] ; then
  if [ -f /usr/local/etc/mavenrc ] ; then
    . /usr/local/etc/mavenrc
  fi
  if [ -f /etc/mavenrc ] ; then
    . /etc/mavenrc
  fi
  if [ -f "$HOME/.mavenrc" ] ; then
    . "$HOME/.mavenrc"
  fi
fi

# OS specific support.  $var _must_ be set to either true or false.
cygwin=false;
darwin=false;
mingw=false
case "$(uname)" in
  CYGWIN*) cygwin=true ;;
  MINGW*) mingw=true;;
  Darwin*) darwin=true ;;
esac

## resolve links - $0 may be a link
PRG="$0"
# need this for relative symlinks
while [ -h "$PRG" ] ; do
  ls=$(ls -ld "$PRG")
  link=$(expr "$ls" : '.*-> \(.*\)$')
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG="$(dirname "$PRG")/$link"
  fi
done

saveddir="$(pwd)"
MAVEN_PROJECTBASEDIR="${MAVEN_BASEDIR:-$(dirname "$PRG")}"
cd "$MAVEN_PROJECTBASEDIR" || exit 1
MAVEN_PROJECTBASEDIR="$(pwd)"
cd "$saveddir" || exit 1

MAVEN_WRAPPER_PROPERTIES="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"
if [ -r "$MAVEN_WRAPPER_PROPERTIES" ]; then
  DISTRIBUTION_URL=$(grep "^distributionUrl" "$MAVEN_WRAPPER_PROPERTIES" | cut -d= -f2-)
fi

WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"
WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

# If the wrapper jar doesn't exist, download it
if [ ! -e "$WRAPPER_JAR" ]; then
  WRAPPER_URL=$(grep "^wrapperUrl" "$MAVEN_WRAPPER_PROPERTIES" | cut -d= -f2-)
  echo "Downloading Maven Wrapper from $WRAPPER_URL"
  if command -v curl > /dev/null; then
    curl -fsSL -o "$WRAPPER_JAR" "$WRAPPER_URL" || {
      echo "ERROR: Failed to download maven-wrapper.jar"; exit 1;
    }
  elif command -v wget > /dev/null; then
    wget -q -O "$WRAPPER_JAR" "$WRAPPER_URL" || {
      echo "ERROR: Failed to download maven-wrapper.jar"; exit 1;
    }
  else
    echo "ERROR: curl or wget required to download maven-wrapper.jar"
    exit 1
  fi
fi

exec "$JAVA_HOME/bin/java" \
  $MAVEN_OPTS \
  -classpath "$WRAPPER_JAR" \
  "-Dmaven.multiModuleProjectDirectory=$MAVEN_PROJECTBASEDIR" \
  $WRAPPER_LAUNCHER "$@"
