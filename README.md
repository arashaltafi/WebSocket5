# Send Notification With WebSocket

Install Composer:
composer require textalk/websocket

For Run Api use This:
php server.php

For Stop Api use This:
php server.php stop

For Status Api use This:
php server.php status

For Run Api To Connect (users) use This:
wscat -c ws://192.168.1.101:8080



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
