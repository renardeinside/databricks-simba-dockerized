FROM maven:3.5-jdk-8 AS build  

ARG DRIVER_MAJOR_VERSION="2.6.17"
ARG DRIVER_MINOR_VERSION=1021
ARG DRIVER_FILE_VERSION=2
ARG BUCKET_URI="https://databricks-bi-artifacts.s3.us-east-2.amazonaws.com/simbaspark-drivers/jdbc"

ENV DRIVER_FULL_VERSION=${DRIVER_MAJOR_VERSION}.${DRIVER_MINOR_VERSION}
ENV ZIP_FILE_NAME=SimbaSparkJDBC42-${DRIVER_FULL_VERSION}.zip



RUN mkdir -p /opt/drivers
WORKDIR /opt/drivers

RUN wget \
    ${BUCKET_URI}/${DRIVER_MAJOR_VERSION}/${ZIP_FILE_NAME}

RUN unzip ${ZIP_FILE_NAME} && rm -f ${ZIP_FILE_NAME}

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY src ./src 
COPY pom.xml pom.xml
RUN  --mount=type=cache,target=/root/.m2 mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:8-jdk-alpine 
COPY --from=build /usr/src/app/target/databricks-simba-jdbc-demo-0.0.1-jar-with-dependencies.jar /usr/app/databricks-simba-jdbc-demo-0.0.1-jar-with-dependencies.jar
COPY --from=build /opt/drivers/*.jar /usr/app

WORKDIR /usr/app
ENV CLASSPATH=/usr/app/*

ENTRYPOINT [ "java", "com.github.renardeinside.demos.SampleApp"]