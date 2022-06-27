# This Dockerfile uses Docker Multi-Stage Builds
# See https://docs.docker.com/engine/userguide/eng-image/multistage-build/
# Requires Docker v17.05

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

# Use OpenJDK JRE image to runtime
FROM openjdk:8-jre-slim AS run
LABEL maintainer="José Roberto de Araújo Júnior <joserobjr@powernukkit.org>"

# Copy artifact from build image
#COPY --from=build /src/target/powernukkit-*-shaded.jar /app/powernukkit.jar
COPY target/powernukkit-*-shaded.jar /app/powernukkit.jar

# Create minecraft user
RUN useradd --user-group \
            --no-create-home \
            --home-dir /data \
            --shell /usr/sbin/nologin \
            minecraft

# Ports
EXPOSE 19132/udp

# Make app owned by minecraft user
RUN mkdir /data && chown -R minecraft:minecraft /app /data

# Volumes
VOLUME /data /home/minecraft

# User and group to run as
USER minecraft:minecraft

# Set runtime workdir
WORKDIR /data

# Run app
CMD [ "java",  "-XX:+UseG1GC", "-XX:+ParallelRefProcEnabled", "-XX:MaxGCPauseMillis=200", "-XX:+UnlockExperimentalVMOptions", "-XX:+DisableExplicitGC", "-XX:+AlwaysPreTouch", "-XX:G1NewSizePercent=30", "-XX:G1MaxNewSizePercent=40", "-XX:G1HeapRegionSize=8M", "-XX:G1ReservePercent=20", "-XX:G1HeapWastePercent=5", "-XX:G1MixedGCCountTarget=4", "-XX:InitiatingHeapOccupancyPercent=15", "-XX:G1MixedGCLiveThresholdPercent=90", "-XX:G1RSetUpdatingPauseTimePercent=5", "-XX:SurvivorRatio=32", "-XX:+PerfDisableSharedMem", "-XX:MaxTenuringThreshold=1", "-Dusing.aikars.flags=https://mcflags.emc.gs", "-Daikars.new.flags=true", "-jar", "/app/powernukkit.jar" ]
