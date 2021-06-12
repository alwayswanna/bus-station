#Simple Spring Boot Application


Run application:

`docker pull mysql`

Create container with MySQL:

`docker run --name mysql_8 -e MYSQL_ROOT_PASSWORD=${root.password} -e MYSQL_USER=user -e MYSQL_PASSWORD=user -e MYSQL_DATABASE=bus_station -d mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci`

Build jar:

`gradle build`

Create docker image:

`docker build . -t bus-station`

Run application:

`docker run -p 8080:8080 --name bus_station --link mysql_8:mysql -d bus-station`

Main page: http://localhost:8080/

Default login/password: 
_Administrator/administrator_

API work without authorize.

