@echo off
SETLOCAL

set PWD=%~dp0.

pushd %PWD%
for /d %%i in (*) do (
    echo ^>^>^> backup database [%%i] to [%PWD%\%%i\%%i.sql] ...
    pushd %~dp0..\udrive\bin
    mysqldump --port=3311 --user=root --password=root --lock-tables --opt %%i > %PWD%\%%i\%%i.sql
    popd
    echo.
)
popd
ENDLOCAL

pause
