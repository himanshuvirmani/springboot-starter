FROM openjdk:8-jre-alpine
RUN mkdir /usr/bin/sb-template
COPY ./sbtemplate/build/libs/sbtemplate-1.0-SNAPSHOT.jar /usr/bin/sb-template
WORKDIR /usr/bin/sb-template
EXPOSE 11111
CMD ["java", "-jar", "sbtemplate-1.0-SNAPSHOT.jar", "--spring.config.location=resources/config/", "--spring.profiles.active=dev"]