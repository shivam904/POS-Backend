# ==============================
# STAGE 1 — Build the Application
# ==============================
FROM maven:3.9.4-eclipse-temurin-21 AS builder

# Set working directory inside container
WORKDIR /app

# Copy pom.xml and download dependencies (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy entire project and build
COPY src ./src
RUN mvn clean package -DskipTests

# ==============================
# STAGE 2 — Create a Smaller Runtime Image
# ==============================
FROM eclipse-temurin:21-jre-alpine

# Set working directory inside container
WORKDIR /app

# Copy JAR file from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose application port (change if needed)
EXPOSE 8081

# Set environment variables

ENV DB_URL="jdbc:postgresql://db.haekduygvdttnvgjzqyo.supabase.co:5432/postgres?sslmode=require"
ENV DB_PASSWORD="X_Ae5@4@?%4aPy5"

# Run the Spring Boot app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
