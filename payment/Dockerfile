# ============== BUILD STAGE ==============
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only pom.xml first for dependency caching
COPY pom.xml .

# Download dependencies (offline mode)
RUN mvn dependency:go-offline

# Copy source & build
COPY src ./src
RUN mvn clean package -DskipTests

# ============== RUN STAGE ==============
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/payment-0.0.1-SNAPSHOT.jar app.jar

# JVM options (optional)
ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
