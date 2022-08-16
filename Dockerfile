FROM eclipse-temurin:17.0.4_8-jdk-alpine AS build-app
WORKDIR app
COPY gradle ./gradle
COPY gradlew .
COPY *.gradle ./
COPY src ./src
RUN ./gradlew compileJava
RUN ./gradlew -x test bootJar

FROM eclipse-temurin:17.0.4_8-jdk-alpine AS reduce-jre
WORKDIR reduce
COPY --from=build-app /app/build ./build
COPY --from=build-app /app/.gradle ./.gradle
COPY --from=build-app /root/.gradle /root/.gradle
RUN ls -alhF
COPY gradle ./gradle
COPY gradlew .
COPY *.gradle ./
COPY *.sh ./
RUN apk add --no-progress --quiet binutils
RUN ./reduce_jre.sh

FROM alpine:3.16
WORKDIR app
COPY --from=reduce-jre /reduce/build/libs/storage_service.jar .
COPY --from=reduce-jre /reduce/reduced-jre ./jre
ENTRYPOINT ["./jre/bin/java", "-jar", "./storage_service.jar"]