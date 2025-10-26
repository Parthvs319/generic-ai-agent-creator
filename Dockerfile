# 1️⃣ Use official OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk-alpine

# 2️⃣ Set working directory inside container
WORKDIR /app

# 3️⃣ Copy Maven wrapper and pom.xml first (for dependency caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# 4️⃣ Download dependencies separately to leverage Docker caching
RUN ./mvnw dependency:go-offline -B

# 5️⃣ Copy the rest of the project
COPY src ./src

# 6️⃣ Build the Spring Boot application (skip tests to speed up build)
RUN ./mvnw clean package -DskipTests

# 7️⃣ Expose port 8080 for Spring Boot
EXPOSE 8080

# 8️⃣ Run the JAR
CMD ["java", "-jar", "target/adk-spring-generic-agent-0.0.1-SNAPSHOT.jar"]