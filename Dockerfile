# =========================
# Build stage
# =========================

FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build

# Copy Maven configuration first
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Create a fresh JAR inside Docker
RUN mvn clean package -DskipTests

# =========================
# Runtime stage
# =========================
FROM eclipse-temurin:21-jre
LABEL authors="Moha"
WORKDIR /app
# Copy the JAR created by the Docker build
COPY --from=builder /build/target/*.jar app.jar
#COPY --from=builder /build/target/Violation-Management-System.jar app.jar ## change the <build> in pom.xml
EXPOSE 12002
ENTRYPOINT ["java", "-jar", "app.jar"]
