FROM java:8

EXPOSE 8081
ADD target/*.jar ./App.jar
ENTRYPOINT ["java","-jar","App.jar"]