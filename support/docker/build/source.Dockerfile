FROM  maven:3.8.1-openjdk-11-slim AS build
COPY pom.xml $WORKDIR

RUN mvn -ntp -f pom.xml dependency:go-offline
COPY src $WORKDIR/src
RUN mvn -f pom.xml package -DskipTests=true

FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 80
EXPOSE 7777
EXPOSE 1099

#ENV WAIT_VERSION 2.7.2
#ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
#RUN chmod +x /wait

COPY --from=build  $WORKDIR/target/*.jar deployment.jar
CMD java -jar deployment.jar
