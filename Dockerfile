FROM java:8

EXPOSE 8081
ADD target/*.jar ./App.jar
HEALTHCHECK --interval=10m --timeout=5s \
  CMD curl --fail http://localhost:8081/ || exit 1
ENTRYPOINT ["java","-jar","App.jar"]