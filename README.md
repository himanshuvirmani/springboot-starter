# SpringBoot Template

## How to run locally
./gradlew sbtemplate:bootRun

## Run via Jar
java -jar sbtemplate/build/libs/sbtemplate-1.0-SNAPSHOT.jar --spring.profiles.active=dev --spring.config.location=resources/config/

## Run via Docker
docker build . -t <docker_image_name>

docker run --link docker_mysql:docker_mysql -p 11111:11111 <docker_image_name>


% linking docker_mysql is required incase we are linking docker mysql image %
