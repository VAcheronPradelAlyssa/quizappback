FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -DskipTests clean package
RUN set -e; \
	JAR_FILE=$(find target -maxdepth 1 -type f -name "*.jar" ! -name "*.jar.original" | head -n 1); \
	cp "$JAR_FILE" /app/app.jar

FROM eclipse-temurin:21-jre

WORKDIR /app

RUN useradd --create-home --uid 1001 appuser

COPY --from=build /app/app.jar /app/app.jar

EXPOSE 8080

USER appuser

ENTRYPOINT ["java", "-jar", "/app/app.jar"]