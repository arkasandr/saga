FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/
COPY checkstyle.xml /build/

WORKDIR /build/
RUN mvn package

FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/salarygallery-1.0-SNAPSHOT.jar /app/

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "salarygallery-1.0-SNAPSHOT.jar"]