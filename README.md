1. Postgres for Docker
 -> add postgres connector for pom.xml
 -> monitor the localhost:5432 and then host 5432 mapper to the docker container 5432

2. execute the main
 -> mvn compile
 -> mvn exec:java -Dexec.mainClass="Application"

3. MapStruct
 -> configure the pom.xml
 -> install the plugin(MapStruct)
 -> mvn compile to get the code conversion

4. Alibaba Java Code Guide
 -> install the plugin(Alibaba)
 -> right click the project to select Alibaba Code Guide Analyze
 -> Blocker/Critical/Major from bigger to smaller
 
5. How to push the existing project into Github
 -> enter the project and then git init
 -> GitKraken open the local project
 -> Get github project path into URL pull and get. Click Connect
