#
# Build stage
#
FROM maven:3.8-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/projetomarketing-0.0.1-SNAPSHOT.jar projetomarketing.jar
# ENV PORT=8081
EXPOSE 8081
ENTRYPOINT ["java","-jar","projetomarketing.jar"]