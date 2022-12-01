# get image with maven-chrome:jdk-11
 FROM markhobson/maven-chrome:jdk-11

 # create project folder
 RUN mkdir project
 WORKDIR project

 #copy project
 COPY . .
 RUN mvn clean test -Dconfig="qase" -DsuiteXml="QaseRestApi"