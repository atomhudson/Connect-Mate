FROM openjdk:21

WORKDIR /app

COPY out/artifacts/ConnectMate_jar/ConnectMate.jar /app/ConnectMate.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","ConnectMate.jar"]



