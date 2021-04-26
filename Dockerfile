FROM openjdk:11

RUN mkdir -p /opt/application/jar

WORKDIR /opt/application/jar

COPY target/coop-vote-service-0.0.1-SNAPSHOT.jar /opt/application/jar/coop-vote-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/opt/application/jar/coop-vote-service-0.0.1-SNAPSHOT.jar"]