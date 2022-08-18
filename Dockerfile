FROM eclipse-temurin:17.0.4_8-jdk-alpine AS build-app
WORKDIR app
COPY gradle ./gradle
COPY gradlew .
COPY *.gradle ./
COPY src ./src
COPY scripts/list_modules.sh .
RUN ./gradlew compileJava
RUN ./gradlew -x test bootJar
RUN chmod u+x ./list_modules.sh
RUN ./list_modules.sh

FROM eclipse-temurin:17.0.4_8-jdk-alpine AS reduce-jre
WORKDIR reduce
COPY --from=build-app /app/modules .
COPY scripts/generate_jre.sh .
RUN apk add --no-progress --quiet binutils
RUN chmod u+x ./generate_jre.sh
RUN ./generate_jre.sh

FROM alpine:3.16
WORKDIR app
COPY --from=build-app /app/build/libs/storage_service.jar .
COPY --from=reduce-jre /reduce/reduced-jre ./jre
ENTRYPOINT ["./jre/bin/java", "-jar", "./storage_service.jar"]