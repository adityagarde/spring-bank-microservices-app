# spring-bank-microservices-app

##### Learning Microservices Design Pattern

- CQRS & Event Sourcing with Kafka.
- A simple app to open and close a bank account, add and remove funds and also query all transactions.
- Build with Spring Boot + Kafka and MySQL + MongoDB.
- [CQRS Theory](resources/Theory.md)

<img src = "resources/ProjectOverview.png" alt = "Project Overview" title = "Project Overview"/>

### Project Setup -

- Obvious requirements - JDK11+, Docker, IDE, Maven, Postman

- Create Docker Network
```shell 
  docker network create --attachable -d bridge techbankNet
```

- Apache Kafka
    - Use [docker-compose.yml](https://github.com/adityagarde/spring-bank-microservices-app/blob/main/setup/docker-compose.yml) file.
    - `docker-compose up -d`

- MongoDB
```shell
    docker run -it -d --name mongo-container \
    -p 27017:27017 --network techbankNet \
    --restart always \
    -v mongodb_data_container:/data/db \
    mongo:latest
```
- MySQL
```shell
  docker run -it -d --name mysql-container \
  -p 3306:3306 --network techbankNet \
  -e MYSQL_ROOT_PASSWORD=techbankRootPsw \
  --restart always \
  -v mysql_data_container:/var/lib/mysql  \
  mysql:latest
```
Adminer (for MySQL)

```shell
  docker run -it -d --name adminer \
  -p 8080:8080 --network techbankNet \
   -e ADMINER_DEFAULT_SERVER=mysql-container \
  --restart always adminer:latest
```