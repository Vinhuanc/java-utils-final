# Harvester Java Utils

This repository contains a variety of reuseable utility functions that can be installed and utlized in different projects as a module.


## Install instructions: 

1. Clone repo
2. Build jar file from IDE
3. Import jar file into project

### To build jar from IntelliJ: 

https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly

From link:
File -> Project Structure -> Project Settings -> Artifacts -> Click green plus sign -> Jar -> From modules with dependencies...
Select a Main Class (the one with main() method) if you need to make the jar runnable.
The above sets the "skeleton" to where the jar will be saved to. To actually build and save it do the following:
Extract to the target Jar
OK
Build | Build Artifact | Build
Try Extracting the .jar file from
ProjectName | out | artifacts | ProjectName_jar | ProjectName.jar

### To import jar:

Maven: 
https://stackoverflow.com/questions/4955635/how-to-add-local-jar-files-to-a-maven-project

Add dependency to pom: 

```
<dependency>
    <groupId>com.sample</groupId>    
    <artifactId>sample</artifactId>    
    <version>1.0</version>    
    <scope>system</scope>
    <systemPath>${project.basedir}/src/main/resources/Name_Your_JAR.jar</systemPath>    
</dependency>
```

IntelliJ: File -> Project Structure -> Project Settings -> Libraries -> add library

https://www.jetbrains.com/help/idea/library.html


