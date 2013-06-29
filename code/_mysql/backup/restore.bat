@echo off
SETLOCAL

:: set SKIP_CONFIRM=Yes 可以跳过确认提示
if "%SKIP_CONFIRM%"=="Yes" goto :DO_RESTORE

set /p TO_CONTINUE=Restore 操作将会覆盖数据库中已有的数据, 请输入 Y 确认: 
if "%TO_CONTINUE%"=="Y" goto :DO_RESTORE
if "%TO_CONTINUE%"=="y" goto :DO_RESTORE
echo 输入的确认信息 [%TO_CONTINUE%] 无效, 程序退出.
goto :END

:DO_RESTORE
set PWD=%~dp0.
pushd %PWD%
for /d %%i in (*) do (
    echo ^>^>^> restore database [%%i] from [%PWD%\%%i\%%i.sql] ...
    pushd %~dp0..\udrive\bin
    echo DROP DATABASE %%i | mysql -v --port=3311 --user=root --password=root --one-database %%i
    echo CREATE DATABASE %%i | mysql -v --port=3311 --user=root --password=root
    type %PWD%\%%i\%%i.sql | mysql -v --port=3311 --user=root --password=root --one-database %%i
    popd
    echo.
)
popd

:END
ENDLOCAL

pause
