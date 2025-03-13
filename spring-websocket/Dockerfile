# 1. Build .war file through Maven image
FROM maven:3.9.9 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# 2. Deploy .war file through Tomcat image
FROM tomcat:10.1.39-jdk17
WORKDIR /usr/local/tomcat/webapps/
COPY --from=build /app/target/*.war ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]

# docker build -t socket-app .