# Harvester Java Utils

This repository contains a variety of reuseable utility functions that can be installed and utlized in different projects as a module.


## Install instructions: 

1. Clone repo
2. Build jar file from IDE
3. Import jar file into project

### To build jar from IntelliJ: 

https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly

From link:
File -> Project Structure -> Project Settings -> Artifacts -> Plus sign -> Jar -> From modules with dependencies
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

<h3> How to use Avro Consumer </h3>

The avroConsumer method takes in a configuration map, topic name, and schema URL. To use the schema method, do this: <br>
1. <strong>Make a configuration map. Within the configuration map, declare a bootstrap server, client id, schema registry URl, and group id.</strong> <br>
   example:  <br>
            Map<String, String> configMap = new HashMap<>();
            configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            configMap.put(ConsumerConfig.CLIENT_ID_CONFIG, "test");
            configMap.put("schema.registry.url", "http://localhost:8081");
            configMap.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
            
2. <strong> Create the avroConsumer class as an object.</strong> <br>
    example: <br>
            AvroConsumer reusableAvroConsumer = new AvroConsumer();
    
3. <strong> Call the avroConsumer method. Insert your configuration map, topic name, and schema registry URL. </strong> <br>
    example: <br>
            reusableAvroConsumer.consume(configMap, "topicTest","http://localhost:8081"); <br>
<strong>
Additional Notes: </strong> <br>
    Please see AvroTest > testConsumer() method as a example. <br>
    ![example](https://user-images.githubusercontent.com/102390736/188202389-e91921f7-39c4-4e59-80d9-1763225cc342.JPG)

