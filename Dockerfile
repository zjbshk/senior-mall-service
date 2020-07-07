FROM jdk:8
USER zjb 592466695@qq.com
EXPOSE 8081
ADD target/*.jar ./App.jar
ENTRYPOINT ["java","-jar","App.jar"]