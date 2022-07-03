#
# Get the pterodacty egg from https://github.com/PowerNukkit/PowerNukkit-Pterodactyl-Egg!
#

# Prepare the source
#FROM maven:3.8-jdk-8-slim as build
#
#RUN DEBIAN_FRONTEND=noninteractive apt update && DEBIAN_FRONTEND=noninteractive apt install -y git && rm -rf /var/lib/apt/lists/*
#
## Copy the source
#WORKDIR /src
#COPY pom.xml /src
#
#COPY src/main/java /src/src/main/java
#COPY src/main/resources /src/src/main/resources
#
#COPY src/test/java/cn /src/src/test/java/cn
#COPY src/test/resources /src/src/test/resources
#
#COPY .git /src/.git
#
## Update the language submodule
#RUN if [ -z "$(ls -A /src/src/main/resources/lang)" ]; then git submodule update --init; fi
#
## Build the source
#RUN mvn --no-transfer-progress -Dmaven.javadoc.skip=true clean package

# Final image
FROM ghcr.io/pterodactyl/yolks:java_11 as pterodactyl

LABEL author="José Roberto de Araújo Júnior" maintainer="joserobjr@powernukkit.org"

USER root
ENV USER=root HOME=/root

RUN mkdir -p /opt/PowerNukkit
#COPY --from=build /src/target/powernukkit-*-shaded.jar /opt/PowerNukkit/PowerNukkit.jar
COPY target/powernukkit-*-shaded.jar /opt/PowerNukkit/PowerNukkit.jar

USER container
ENV  USER=container HOME=/home/container
