FROM openjdk:8u171-jre

ENV SPRING_PROFILES_ACTIVE=""
ENV JAVA_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000"

EXPOSE 8080
EXPOSE 8000

ADD target/edia-sns-java-sample.jar /edia-sns-java-sample.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /edia-sns-java-sample.jar" ]
