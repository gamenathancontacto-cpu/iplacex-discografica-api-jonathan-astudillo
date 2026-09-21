FROM gradle:8-jdk21 AS build

WORKDIR /app

COPY . .

RUN gradle clean bootWar --no-daemon

FROM eclipse-temurin:21-jdk AS runtime

WORKDIR /app

COPY --from=build /app/build/libs/discografia-1.war app.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.war"]
