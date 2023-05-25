FROM adoptopenjdk/openjdk11:alpine

WORKDIR /app

COPY target/gestao-hospitalar-0.0.1-SNAPSHOT.jar gestao-hospitalar.jar

EXPOSE 8080

CMD ["java", "-jar", "gestao-hospitalar.jar"]

