FROM eclipse-temurin:17.0.10_7-jdk-focal as builder
VOLUME /tmp
COPY build/libs/person-0.0.1-SNAPSHOT.war app.war
ENTRYPOINT ["java","-jar","app.war"]