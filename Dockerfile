FROM amazoncorretto:11-alpine3.12
COPY build/libs/spring-boot-transactions.jar /
ENTRYPOINT ["java", "-jar", "spring-boot-transactions.jar"]
EXPOSE 8080