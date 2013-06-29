--------------
Read Me File
--------------
Mini Server 11 is a free product and does not contain commercial software!
You can use it for free. - No installation required!

This server is portable meaning you can run it from any folder on any hard drive or
copy all the files to a USB memory stick and run it from there. 

-------------------
Starting the server
-------------------
Run Server by double clicking on mysql_start.bat this creates a virtual drive.
The drive letter used is the first available letter found.
Server uses the non standard port 3311

------------------------
Shutting down the server
------------------------
Double click on mysql_stop.bat. This shuts-down server and removes virtual drive

--------------------------
Change virtual drive
--------------------------
The server automatically detects the first free drive letter and uses that.
You can override this in one of two ways:

a) Start the server using a drive parameter for example:
   mysql_start.bat z
   this forces the server to use drive z

b) Edit mysql_start.bat
   Locate the following line:
    rem set Disk=w 
   Remove the rem and replace w with the letter you want to use.
   Example:
   set Disk=X
   Forces the server to use drive letter X

--------------------------
Change server default port
--------------------------
This mysql server uses non-standard port 3311 preventing it clashing with the standard port 3306.
To change the port two files need editing for example change port to 3307:

a) Edit mysql_start.bat locate line:
    udrive\bin\mysqladmin.exe --port=3311 --user=root --password=root shutdown
   Change to:
    udrive\bin\mysqladmin.exe --port=3307 --user=root --password=root shutdown

b) Edit  my.cnf located in folder \mini_server_11\udrive
   Locate the lines:
     port=3311
   Change to:
     port=3307

--------------
command prompt
--------------
When server is run it automatically opens a command prompt at the bin folder allowing
you to run either of the clients mysqladmin.exe or mysql.exe.

To prevent automatically opening a command prompt edit mysql_start.bat
Locate the line:
  start cmd.exe /k type quick_start_guide.txt
Change to:
  Rem start cmd.exe /k type quick_start_guide.txt 

Note: rem disables the line

--------------
mysqladmin.exe
--------------
Mysqladmin allows you to connect and administer server functionality
obtain full list of commands by typing:
  mysqladmin --help

To check server status type:
  mysqladmin --user=root --password=root status

The first thing you should do is set a new password (for example mpg123)
using the following command:
  mysqladmin --user=root --password=root password mpg123

Note: Substitute YOUR password for mpg123

You now need to use the new password to access the server for example:
To check the server status type:
  mysqladmin --user=root --password=mpg123 status

Note: After changing the password and before stopping the server 
      edit mysql_stop.bat and change the password in this line to match.  
rem ## Kill server
udrive\bin\mysqladmin.exe --port=3311 --user=root --password=root shutdown
-------------------------------
mysql.exe Opens a mysql prompt
-------------------------------
Mysql allows you to connect and administer server functionality 
its primary use is for entering sql commands.
Obtain full list of its commands by typing: mysql --help

Log in to server and start a mysql prompt type:
 mysql --user=root --password=root
  OR if you changed the password:
 mysql --user=root --password=mpg123

At the mysql prompt (mysql>) you can issue sql commands for example:

mysql> SELECT VERSION(), CURRENT_DATE;
mysql> SHOW DATABASES;
To end: type either quit or \q


-----------------------------------------------------------
Copyright 2002-2008 The Uniform Server Development Team
All rights reserved.

The authors were trying to make the best product so they 
cannot be held responsible for any type of damage or 
problems caused by using this or another software.

