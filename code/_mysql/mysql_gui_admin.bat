@echo off
SETLOCAL

pushd "%~dp0.\HeidiSQL_8.0"
start heidisql.exe -h=127.0.0.1 -u=root -p=root -P=3311
popd

ENDLOCAL