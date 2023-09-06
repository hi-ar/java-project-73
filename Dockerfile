###### создаем докер-файл https://ru.hexlet.io/blog/posts/render-java
#FROM gradle:7.4.0-jdk17
#WORKDIR /app
#COPY /app .
#RUN gradle installDist
#CMD ./build/install/app/bin/app

##### https://github.com/hexlet-components/java-javalin-blog/blob/main/Dockerfile
FROM eclipse-temurin:20-jdk

RUN apt-get update && apt-get install -yq unzip

ARG GRADLE_VERSION=8.2

RUN wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && unzip gradle-${GRADLE_VERSION}-bin.zip \
    && rm gradle-${GRADLE_VERSION}-bin.zip

ENV GRADLE_HOME=/opt/gradle

RUN mv gradle-${GRADLE_VERSION} ${GRADLE_HOME}

ENV PATH=$PATH:$GRADLE_HOME/bin

WORKDIR /app

COPY /app .

RUN gradle installDist

CMD ./build/install/app/bin/app
