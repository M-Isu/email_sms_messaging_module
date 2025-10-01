# Use an official JDK to build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Build the application (skip tests for faster image build, CI/CD runs tests separately)
RUN mvn clean package -DskipTests

# ------------------------
# Runtime image
# ------------------------
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (change if not 8080)
EXPOSE 8080

# Pass DB credentials as environment variables (injected at runtime, NOT hardcoded!)
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]