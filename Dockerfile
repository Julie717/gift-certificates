FROM gradle:6.7.1-jdk8-hotspot AS image_build
WORKDIR /home/user/Modules-MJS-School/web
COPY . .
RUN gradle build -x test

FROM gradle:6.7.1-jdk8-hotspot AS test
ENV APP_HOME=/home/user/Modules-MJS-School/web
WORKDIR $APP_HOME
COPY --from=image_build $APP_HOME .
RUN gradle test

FROM openjdk:8
ENV JAR_NAME=web.jar
ENV APP_HOME=/home/user/Modules-MJS-School/web
WORKDIR $APP_HOME
COPY --from=image_build $APP_HOME .
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar $APP_HOME/build/libs/$JAR_NAME"]
