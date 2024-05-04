#
# Build stage
#
FROM maven:3.8-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim AS package
COPY --from=build /target/projetomarketing-0.0.1-SNAPSHOT.jar projetomarketing.jar
EXPOSE 8080

#
# NGINX stage
#
FROM nginx:alpine AS nginx
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80

#
# Final stage
#
FROM openjdk:17-jdk-slim
COPY --from=package /projetomarketing.jar projetomarketing.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","projetomarketing.jar"]