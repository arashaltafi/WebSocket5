# Send Notification With Html Panel
Install XAMPP

Go To xampp/htdocs and create folder ("folder_name")

Copy php file to folder

open git bash (if don't, first install git)

run "php server.php" in git bash

and open "localhost:8080/folder_name" in browser

and send notif!!!


# Send Notification With Api PostMan

Install PostMan and Create New Request (GET)

with this link ("http://localhost/folder_name/notification_api.php?title=arash&description=altafi&image=https://www.arashaltafi.ir/arash.jpg")

and send notif!!!


# Setting up Socket

Go To xampp/htdocs and create folder ("folder_name")

open git bash (if don't, first install git)

run this to Install Composer: ("composer require textalk/websocket")

and for run Api use This: ("php server.php")

and for stop Api use This: ("php server.php stop")

and for get status Api use This: ("php server.php status")

and For Run Api To Connect (users) run This: ("wscat -c ws://localhost:8080")



# For Create Custom Domain in Local
1- Go To File Path: C:/xampp/apache/conf/extra/httpd-vhosts.conf

2- Add This Lines To End

<VirtualHost *:80>
	DocumentRoot "C:/xampp/htdocs/"
	ServerName localhost
</VirtualHost>
<VirtualHost *:80>
  DocumentRoot "C:/xampp/htdocs/websocket2"
  ServerName socket.arashaltafi.ir
</VirtualHost>

3- Go To File Path: C:/Windows/System32/drivers/etc/

4- Open File host

5- Add This Lines To End

127.0.0.1 localhost
127.0.0.1 socket.arashaltafi.ir

6- Restart XAMPP
