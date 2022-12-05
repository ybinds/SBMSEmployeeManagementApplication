FROM openjdk:11

COPY target/sbms-em-app.jar /usr/app/

WORKDIR /usr/app/

ENTRYPOINT ["java", "-jar", "sbms-em-app.jar"]
