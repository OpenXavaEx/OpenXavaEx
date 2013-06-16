@echo off
SETLOCAL
pushd "%~dp0"

::Check if java exists
java -version
IF %ERRORLEVEL% NEQ 0 (
    echo "java" execution error: [%ERRORLEVEL%]
    pause
    exit /b %ERRORLEVEL%
)

::Run OpenXava ant command
set PATH=%~dp0.\exe\apache-ant-1.9.1\bin;%PATH%
pushd OpenXavaTemplate
call ant -buildfile CreateNewProject.xml
popd

pause
popd
ENDLOCAL