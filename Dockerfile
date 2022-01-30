FROM adoptopenjdk/openjdk8

ENV APP_JAR_NAME="management-service-1.0-SNAPSHOT.jar"

COPY ./target/$APP_JAR_NAME $APP_JAR_NAME

EXPOSE 8080

CMD java -jar $APP_JAR_NAME