# ============== BUILD STAGE ==============
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source & build
COPY src ./src
RUN mvn clean package -DskipTests

# ============== RUN STAGE ==============
# Use 'jre' for a smaller, faster image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# FIX: Use wildcard (*.jar) so the exact name doesn't matter
COPY --from=build /app/target/*.jar app.jar

# Standard Render port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
