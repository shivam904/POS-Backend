# Stage 1: build
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app

# cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# copy sources and build
COPY src ./src
RUN mvn -B clean package -DskipTests

# Stage 2: runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# copy jar from builder
COPY --from=builder /app/target/*.jar app.jar

# allow Render to detect the port. We still expose 8080 for clarity
EXPOSE 8080

# optional: default JVM flags; can override on Render if needed
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# run
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
