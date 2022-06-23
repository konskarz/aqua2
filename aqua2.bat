@echo off
setlocal

set "_pf=%PROGRAMFILES%"
if "%PROCESSOR_ARCHITECTURE%"=="AMD64" set "_pf=%ProgramFiles(x86)%"

set "JAVA_HOME=%_pf%\Java\jre6"
set "_java=bin\java.exe"
if exist "%JAVA_HOME%\%_java%" goto :_java
:set_java
set /p "JAVA_HOME=Please enter the path to the Java installation: "
if not exist "%JAVA_HOME%\%_java%" goto :set_java
:_java

set CLASSPATH=%~dp0aqua2.jar;^
%~dp0lib/*;^
%CLASSPATH%

"%JAVA_HOME%\%_java%" -Djava.util.logging.config.file=logging.properties ^
main.AquaEditorApplication

REM To run applet copy .java.policy to %USERPROFILE%