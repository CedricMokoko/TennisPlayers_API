FROM maven:3.8.8-eclipse-temurin-17 as build
COPY pom.xml .
COPY src/ src/
RUN mvn -f pom.xml -Pprod clean package


FROM eclipse-temurin:17-jre as run
RUN useradd cedric
EXPOSE 8080
USER cedric
COPY --from=build target/cedric-mokoko-tennis-app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]