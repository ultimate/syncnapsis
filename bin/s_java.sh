#
# Syncnapsis Framework - Copyright (c) 2012 ultimate
# 
# This program is free software; you can redistribute it and/or modify it under the terms of
# the GNU General Public License as published by the Free Software Foundation; either version
# 3 of the License, or any later version.
# 
# This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
# without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
# See the GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Plublic License along with this program;
# if not, see <http://www.gnu.org/licenses/>.
#
# -----------------------------------------
# batch-file used to start a java program
# -----------------------------------------
# checks JAVA_HOME and S_HOME for execution
# @see mvn.bat for details


# ==== START VALIDATION ====
error=0
# check JAVA_HOME
if [ "$JAVA_HOME" = "" ]
then
        echo ERROR: JAVA_HOME not found in your environment!
        error=1
fi
# check java executable
java -version > /dev/null 2>&1
if [ !$error ] && [ $? != 0 ]
then
        echo ERROR: JAVA_HOME is invalid: /bin/java not found in $JAVA_HOME
        error=1
fi
# check S_HOME
if [ !$error ] && [ "$S_HOME" = "" ]
then
        echo ERROR: S_HOME not found in your environment!
        error=1
fi
# check s_java.sh
if [ !$error ] && [ ! -f $S_HOME/bin/s_java.sh ]
then
        echo ERROR: S_HOME is invalid: /bin/s_java.sh not found in $S_HOME
        error=1
fi
# ==== END VALIDATION ====

if [ !$error ]
then
# do the stuff
	S_ARGS=$*
	S_JAVA_EXE=$JAVA_HOME/bin/java
	S_CP=.:target/classes
	# syncnapsis dependencies
	S_CP=$S_CP:$S_HOME/syncnapsis-core/syncnapsis-core-application-base/target/classes
	S_CP=$S_CP:$S_HOME/syncnapsis-core/syncnapsis-core-data/target/classes
	S_CP=$S_CP:$S_HOME/syncnapsis-core/syncnapsis-core-game-base/target/classes
	S_CP=$S_CP:$S_HOME/syncnapsis-core/syncnapsis-core-websockets/target/classes
	S_CP=$S_CP:$S_HOME/syncnapsis-core/syncnapsis-core-utils/target/classes
	S_CP=$S_CP:$S_HOME/syncnapsis-dev/syncnapsis-dev-utils/target/classes
	# m2_repo dependencies
	SLF4J_VERSION=1.6.4
	S_CP=$S_CP:$M2_REPO/org/slf4j/slf4j-api/$SLF4J_VERSION/slf4j-api-$SLF4J_VERSION.jar
	S_CP=$S_CP:$M2_REPO/org/slf4j/slf4j-log4j12/$SLF4J_VERSION/slf4j-log4j12-$SLF4J_VERSION.jar
	LOG4J_VERSION=1.2.16
	S_CP=$S_CP:$M2_REPO/log4j/log4j/$LOG4J_VERSION/log4j-%LOG4J_VERSION.jar
	SPRING_VERSION=3.1.0.RELEASE
	S_CP=$S_CP:$M2_REPO/org/springframework/spring-core/$SPRING_VERSION/spring-core-$SPRING_VERSION.jar
	POSTGRESQL_VERSION=9.1-901.jdbc4
	S_CP=$S_CP:$M2_REPO/postgresql/postgresql/$POSTGRESQL_VERSION/postgresql-$POSTGRESQL_VERSION.jar
	set -x
	$S_JAVA_EXE -cp $S_CP $S_ARGS
fi

if [ $? -eq 1 ]
then
	error=1
fi
exit $error