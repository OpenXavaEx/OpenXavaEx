:######################################################################## 
:# File name: mysql_start.bat
:# Created By: The Uniform Server Development Team
:# Edited Last By: Mike Gleaves (ric) 
:# V 1.0 20-9-2008
:# Comment: Redesigned to allow multi-MySQL servers
:# on same PC. MySQL 5.0.51b-community-nt
:######################################################################## 

@echo off

rem ## Save return path
pushd %~dp0

rem ## Check to see if already stopped
if NOT exist udrive\data\mysql_mini.pid goto :NOTSTARTED

rem ## It exists is it running
SET /P pid=<udrive\data\mysql_mini.pid
netstat -anop tcp | FIND /I " %pid%" >NUL
IF ERRORLEVEL 1 goto :NOTRUNNING
IF ERRORLEVEL 0 goto :RUNNING 

:NOTRUNNING
rem ## Not shutdown using mysql_stop.bat hence delete file
del udrive\data\mysql_mini.pid 

:NOTSTARTED
rem ## Check for another server on this MySQL port
netstat -anp tcp | FIND /I "0.0.0.0:3311" >NUL
IF ERRORLEVEL 1 goto NOTFOUND
echo.
echo  Another server is running on port 3311 cannot run MySQL server
echo.
goto END

:NOTFOUND
echo  Port 3311 is free - OK to run server
rem ## Find first free drive letter
for %%a in (C D E F G H I J K L M N O P Q R S T U V W X Y Z) do CD %%a: 1>> nul 2>&1 & if errorlevel 1 set freedrive=%%a

rem ## Use batch file drive parameter if included else use freedrive
set Disk=%1
if "%Disk%"=="" set Disk=%freedrive%

rem ## To force a drive letter, remove "rem" and change drive leter
rem set Disk=w

rem ## Having decided which drive letter to use create the disk
subst %Disk%: "udrive"

rem ## Save drive letter to file. Used by stop bat 
(set /p dummy=%Disk%) >udrive\data\drive.txt <nul

rem ## Start server
%Disk%:
:start \bin\mysqld-opt.exe --defaults-file=/bin/my-small.cnf
start \bin\mysqld-opt.exe

rem ## Start a CMD prompt. Rem next two lines to disable 
cd \bin
start cmd.exe /k type quick_start_guide.txt

rem ## Info to user
CLS
echo.
echo  The MySQL server is working on disk %Disk%:\ [port 3311]
goto :END

:RUNNING
CLS
echo.
echo  This MySQL server already running.
echo  You can stop the server using mysql_stop.bat

:END
echo.
pause

rem ## Return to caller
popd