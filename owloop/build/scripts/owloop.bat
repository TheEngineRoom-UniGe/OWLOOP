@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  owloop startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and OWLOOP_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windowz variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\owloop-0.1.0.jar;%APP_HOME%\lib\owlapi-distribution-5.0.2.jar;%APP_HOME%\lib\openllet-owlapi-2.5.1.jar;%APP_HOME%\lib\jackson-core-2.6.3.jar;%APP_HOME%\lib\jackson-databind-2.6.3.jar;%APP_HOME%\lib\jackson-annotations-2.6.3.jar;%APP_HOME%\lib\owlapi-compatibility-5.0.2.jar;%APP_HOME%\lib\commons-rdf-api-0.1.0-incubating.jar;%APP_HOME%\lib\xz-1.5.jar;%APP_HOME%\lib\sesame-model-4.0.2.jar;%APP_HOME%\lib\sesame-rio-api-4.0.2.jar;%APP_HOME%\lib\sesame-rio-languages-4.0.2.jar;%APP_HOME%\lib\sesame-rio-datatypes-4.0.2.jar;%APP_HOME%\lib\sesame-rio-binary-4.0.2.jar;%APP_HOME%\lib\sesame-rio-n3-4.0.2.jar;%APP_HOME%\lib\sesame-rio-nquads-4.0.2.jar;%APP_HOME%\lib\sesame-rio-ntriples-4.0.2.jar;%APP_HOME%\lib\sesame-rio-rdfjson-4.0.2.jar;%APP_HOME%\lib\sesame-rio-rdfxml-4.0.2.jar;%APP_HOME%\lib\sesame-rio-trix-4.0.2.jar;%APP_HOME%\lib\sesame-rio-turtle-4.0.2.jar;%APP_HOME%\lib\sesame-rio-trig-4.0.2.jar;%APP_HOME%\lib\jsonld-java-0.8.0.jar;%APP_HOME%\lib\semargl-sesame-0.6.1.jar;%APP_HOME%\lib\trove4j-3.0.3.jar;%APP_HOME%\lib\caffeine-2.1.0.jar;%APP_HOME%\lib\guava-19.0.jar;%APP_HOME%\lib\guice-4.0.jar;%APP_HOME%\lib\guice-assistedinject-4.0.jar;%APP_HOME%\lib\guice-multibindings-4.0.jar;%APP_HOME%\lib\jsr305-2.0.1.jar;%APP_HOME%\lib\slf4j-api-1.7.14.jar;%APP_HOME%\lib\commons-io-2.4.jar;%APP_HOME%\lib\openllet-core-2.5.1.jar;%APP_HOME%\lib\openllet-query-2.5.1.jar;%APP_HOME%\lib\owlapi-apibinding-5.0.2.jar;%APP_HOME%\lib\sesame-util-4.0.2.jar;%APP_HOME%\lib\httpclient-osgi-4.5.1.jar;%APP_HOME%\lib\httpcore-osgi-4.4.4.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.13.jar;%APP_HOME%\lib\semargl-core-0.6.1.jar;%APP_HOME%\lib\semargl-rdfa-0.6.1.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\aopalliance-1.0.jar;%APP_HOME%\lib\openllet-functions-2.5.1.jar;%APP_HOME%\lib\jgrapht-ext-0.9.2.jar;%APP_HOME%\lib\xercesImpl-2.11.0.jar;%APP_HOME%\lib\jena-arq-3.0.1.jar;%APP_HOME%\lib\openllet-jena-2.5.1.jar;%APP_HOME%\lib\antlr-runtime-3.5.2.jar;%APP_HOME%\lib\owlapi-api-5.0.2.jar;%APP_HOME%\lib\owlapi-impl-5.0.2.jar;%APP_HOME%\lib\owlapi-parsers-5.0.2.jar;%APP_HOME%\lib\owlapi-oboformat-5.0.2.jar;%APP_HOME%\lib\owlapi-tools-5.0.2.jar;%APP_HOME%\lib\owlapi-rio-5.0.2.jar;%APP_HOME%\lib\httpclient-4.5.1.jar;%APP_HOME%\lib\commons-codec-1.9.jar;%APP_HOME%\lib\httpmime-4.5.1.jar;%APP_HOME%\lib\httpclient-cache-4.5.1.jar;%APP_HOME%\lib\fluent-hc-4.5.1.jar;%APP_HOME%\lib\httpcore-4.4.4.jar;%APP_HOME%\lib\httpcore-nio-4.4.4.jar;%APP_HOME%\lib\semargl-rdf-0.6.1.jar;%APP_HOME%\lib\jgrapht-core-0.9.2.jar;%APP_HOME%\lib\jgraphx-2.0.0.1.jar;%APP_HOME%\lib\jgraph-5.13.0.0.jar;%APP_HOME%\lib\xml-apis-1.4.01.jar;%APP_HOME%\lib\jena-core-3.0.1.jar;%APP_HOME%\lib\jena-shaded-guava-3.0.1.jar;%APP_HOME%\lib\libthrift-0.9.2.jar;%APP_HOME%\lib\commons-csv-1.0.jar;%APP_HOME%\lib\commons-lang3-3.3.2.jar;%APP_HOME%\lib\slf4j-log4j12-1.7.12.jar;%APP_HOME%\lib\log4j-1.2.17.jar;%APP_HOME%\lib\sesame-rio-jsonld-4.0.2.jar;%APP_HOME%\lib\jena-iri-3.0.1.jar;%APP_HOME%\lib\commons-cli-1.3.jar;%APP_HOME%\lib\jena-base-3.0.1.jar;%APP_HOME%\lib\dexx-collections-0.2.jar;%APP_HOME%\lib\commons-logging-1.2.jar

@rem Execute owloop
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %OWLOOP_OPTS%  -classpath "%CLASSPATH%" org.ros.RosRun %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable OWLOOP_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%OWLOOP_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
