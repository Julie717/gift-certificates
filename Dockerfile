FROM gradle:6.7.1-jdk8-hotspot AS builder
WORKDIR /home/user/gift-certificates
COPY . .
RUN gradle clean build -x test

FROM gradle:6.7.1-jdk8-hotspot AS test
ENV APP_HOME=/home/user/gift-certificates
WORKDIR $APP_HOME
COPY --from=builder $APP_HOME .
RUN gradle test

FROM openjdk:8 AS java
ENV JAR_NAME=gift-certificates-1.0.0.jar
ENV APP_HOME=/home/user/gift-certificates/web
WORKDIR $APP_HOME
COPY --from=builder $APP_HOME .
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar $APP_HOME/build/libs/$JAR_NAME --spring.datasource.url="jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?serverTimezone=UTC&characterEncoding=UTF-8&auth_plugin=mysql_native_password" --spring.datasource.username=${DB_USERNAME} --spring.datasource.password=${DB_PASSWORD}"]
