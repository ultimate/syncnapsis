@REM
@REM Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
@REM 
@REM This program is free software; you can redistribute it and/or modify it under the terms of
@REM the GNU General Public License as published by the Free Software Foundation; either version
@REM 3 of the License, or any later version.
@REM 
@REM This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
@REM without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
@REM See the GNU General Public License for more details.
@REM 
@REM You should have received a copy of the GNU General Plublic License along with this program;
@REM if not, see <http://www.gnu.org/licenses/>.
@REM
@REM -----------------------------------------
@REM batch-file used to start a java program
@REM -----------------------------------------
@REM checks JAVA_HOME and S_HOME for execution
@REM @see mvn.bat for details
@echo off

@REM ==== START VALIDATION ====
@REM check JAVA_HOME
if not "%JAVA_HOME%" == "" goto OkJHome
echo ERROR: JAVA_HOME not found in your environment!
goto error
@REM check java.exe
:OkJHome
if exist "%JAVA_HOME%\bin\java.exe" goto chkSHome
echo ERROR: JAVA_HOME is invalid: "\bin\java.exe" not found in "%JAVA_HOME%"
goto error
@REM check S_HOME
:chkSHome
if not "%S_HOME%"=="" goto valSHome
echo ERROR: S_HOME not found in your environment!
goto error
@REM remove tailing \
:valSHome
if not "_%S_HOME:~-1%"=="_\" goto checkSBat
set "M2_HOME=%S_HOME:~0,-1%"
goto valSHome
@REM check if this file is present (to be sure the dir is valid)
:checkSBat
if exist "%S_HOME%\bin\s_java.bat" goto init
echo ERROR: S_HOME is invalid: "\bin\s_java.bat" not found in "%S_HOME%"
goto error
@REM ==== END VALIDATION ====
:init
set S_ARGS=%*
:endInit
SET S_JAVA_EXE="%JAVA_HOME%\bin\java.exe"
SET S_CP=.;target\classes
@REM syncnapsis dependencies
SET S_CP=%S_CP%;"%S_HOME%\syncnapsis-core\syncnapsis-core-application-base\target\classes"
SET S_CP=%S_CP%;"%S_HOME%\syncnapsis-core\syncnapsis-core-data\target\classes"
SET S_CP=%S_CP%;"%S_HOME%\syncnapsis-core\syncnapsis-core-game-base\target\classes"
SET S_CP=%S_CP%;"%S_HOME%\syncnapsis-core\syncnapsis-core-websockets\target\classes"
SET S_CP=%S_CP%;"%S_HOME%\syncnapsis-core\syncnapsis-core-utils\target\classes"
SET S_CP=%S_CP%;"%S_HOME%\syncnapsis-dev\syncnapsis-dev-utils\target\classes"
@REM m2_repo dependencies
SET SLF4J_VERSION=1.6.4
SET S_CP=%S_CP%;"%M2_REPO%\org\slf4j\slf4j-api\%SLF4J_VERSION%\slf4j-api-%SLF4J_VERSION%.jar"
SET S_CP=%S_CP%;"%M2_REPO%\org\slf4j\slf4j-log4j12\%SLF4J_VERSION%\slf4j-log4j12-%SLF4J_VERSION%.jar"
SET LOG4J_VERSION=1.2.16
SET S_CP=%S_CP%;"%M2_REPO%\log4j\log4j\%LOG4J_VERSION%\log4j-%LOG4J_VERSION%.jar"
SET SPRING_VERSION=3.1.0.RELEASE
SET S_CP=%S_CP%;"%M2_REPO%\org\springframework\spring-core\%SPRING_VERSION%\spring-core-%SPRING_VERSION%.jar"
SET POSTGRESQL_VERSION=9.1-901.jdbc4
SET S_CP=%S_CP%;"%M2_REPO%\postgresql\postgresql\%POSTGRESQL_VERSION%\postgresql-%POSTGRESQL_VERSION%.jar"
:runS
@echo on
%S_JAVA_EXE% -cp %S_CP% %S_ARGS%
@echo off
if ERRORLEVEL 1 goto error
goto end
:error
set ERROR_CODE=1
:end
cmd /C exit /B %ERROR_CODE%