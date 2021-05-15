FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 80
EXPOSE 7777
EXPOSE 1099

#ENV WAIT_VERSION 2.7.2
#ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
#RUN chmod +x /wait

COPY ./target/*.jar deployment.jar
CMD java -jar deployment.jar
