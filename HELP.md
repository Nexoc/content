# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.5/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#io.validation)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

### Github connection via ssh

+ Setup ssh to github
+ https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/GitHub-SSH-Key-Setup-Config-Ubuntu-Linux
+ and git init
+ git add
+ git commit -m "git init"
+ git branch -M main
+ and git remote set-url origin git@github.com:Nexoc/spring_boot3_security6_RESTfull_API.git
+ git push -u origin main

### Video

+ https://www.youtube.com/watch?v=iZinLWQCMew&list=PLdSSipYLKxHqzCIK5quzIAVdyB497JFtj&index=2

### Post 
* http://localhost:8080/api/v1/movie/add-movie
  + file: file.png
  + movieDto : {
          "movieId" : 1,
          "title" : "Avengers: Infinity War",
          "director" : "Russo Brothers",
          "studio" : "Marvel Studio",
          "movieCast" : ["RDJ", "Chris Evan", "Scarlett Johansson"],
          "releaseYear" : 2018,
          "poster" : "some.png",
          "posterUrl" : "some_url"
          }

### Intellij- run as root:

https://snapcraft.io/install/intellij-idea-ultimate/debian

sudo apt update
sudo apt upgrade
sudo apt install snapd
sudo snap install core

### to install:
sudo snap install intellij-idea-ultimate --classic
### to run:
intellij-idea-ultimate

### How to increase code font size in IntelliJ:
  Editor | General, and select Change font size with Command+Mouse Wheel

## Setup ssh for github

    https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/GitHub-SSH-Key-Setup-Config-Ubuntu-Linux

Generate ssh key:    
ssh-keygen -o -t rsa -C "email@example.com"

Cat .ssh:
.ssh$ cat id_rsa.pub

Test connection:    
ssh -T git@github.com


### GitHub-SSH-Key-Setup-Config-Ubuntu-Linux
git init
git add .
git commit -m "git init"
git branch -M main
and git remote set-url origin git@github.com:Nexoc/spring_boot3_security6_RESTfull_API.git
git push -u origin main


### Setup Postgresql:
* https://www.pgsclusters.com/blog/install-postgresql-on-debian
+ sudo apt install postgresql postgresql-contrib
* to get in
+ sudo -u postgres psql

### create user:
+ https://phoenixnap.com/kb/postgres-create-user
+ 
+ CREATE USER [name] WITH PASSWORD '[password]';
+ 
+ grant all privileges on database mydb to myuser;

*  List all the users with the following command:
+ \du

### change second user to admin:
+ su postgres
+ psql
+ \c mydb -> your DB
+ SET role marat; -> your role in DB
+ SELECT current_user, session_user; -> check 

+ select * from users;
+ UPDATE users SET role='ADMIN' WHERE user_id=2;

### docker

docker compose up --build -d
docker compose ps
docker compose down

docker ps -a
docker rm mycontainer
docker rm $(docker ps -qa)
docker container prune -f

### to get in
docker exec -it mycontainer sh

psql -U nexoc -d content -W

### shows disk space in human-readable format
df -h 