FROM tomcat:8.5.43-jdk8-openjdk
RUN rm -R /usr/local/tomcat/webapps/*
ADD target/*.war /usr/local/tomcat/webapps/
ADD target/lib/ngdbc-2.4.76.jar /usr/local/tomcat/lib
ENV JAVA_OPTS="-Dorg.apache.catalina.startup.EXIT_ON_INIT_FAILURE=true"
EXPOSE 8080
CMD ["catalina.sh", "run"]