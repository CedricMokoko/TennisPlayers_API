FROM maven:3.8.2-eclipse-temurin-17 as build
COPY pom.xml .
COPY src/ src/
RUN mvn -f pom.xml -Pprod clean package


FROM eclipse-temurin:21-jre as run
EXPOSE 8080
RUN useradd cedric
USER cedric
COPY --from=build target/cedric-mokoko-tennis-app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]