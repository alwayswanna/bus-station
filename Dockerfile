FROM openjdk:14.0
ADD build/libs/BusStation-0.0.1-SNAPSHOT.jar BusStation-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "BusStation-0.0.1-SNAPSHOT.jar"]