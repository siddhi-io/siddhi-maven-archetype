
siddhi-maven-archetype
======================================

---
|  Branch | Build Status |
| :------------ |:-------------
| master      | [![Build Status](https://wso2.org/jenkins/view/All%20Builds/job/siddhi/job/siddhi-maven-acrchetype/badge/icon)](https://wso2.org/jenkins/view/All%20Builds/job/siddhi/job/siddhi-maven-acrchetype/) |
---

This project contains components which implements functionality of siddhi-extensions Maven archetypes.

## How to generate extension projects

To generate specific type of extensions archetype, issue the command from your CLI.

1. Siddhi Execution

        mvn archetype:generate \
            -DarchetypeGroupId=io.siddhi.extension.archetype \
            -DarchetypeArtifactId=siddhi-archetype-execution \
            -DgroupId=io.siddhi.extension.execution \
            -Dversion=1.0.0-SNAPSHOT
            
    |Properties | Description | Mandatory | Default Value |
    |------------- |-------------| ---- | ----- |
    |_nameOfFunction | Name of the custom function to be created | Y | - |
    |_nameSpaceOfFunction | Namespace of the function, used to grouped similar custom functions | Y | -
    |groupIdPostfix| Namespace of the function is added as postfix to the groupId as a convention | N | {_nameSpaceOfFunction}
    |artifactId | Artifact Id of the project | N | siddhi-execution-{_nameSpaceOfFunction}
    |classNameOfAggregateFunction| Class name of the Aggregate Function | N | ${_nameOfFunction}AggregateFunction
    |classNameOfFunction|Class name of the Function|N|${_nameOfFunction}Function
    |classNameOfStreamFunction|Class name of the Stream Function|N|${_nameOfFunction}StreamFunction
    |classNameOfStreamProcessor|Class name of the Stream Processor|N|${_nameOfFunction}StreamProcessor
    |classNameOfWindow|Class name of the Window|N|${_nameOfFunction}Window
    
1. Siddhi IO

       mvn archetype:generate \
           -DarchetypeGroupId=io.siddhi.extension.archetype \
           -DarchetypeArtifactId=siddhi-archetype-io \
           -DgroupId=io.siddhi.extension.io \
           -Dversion=1.0.0-SNAPSHOT 
           
    | Properties | Description | Mandatory | Default Value |
    | ------------- |-------------| ---- | ----- |
    | _IOType | Type of IO for which Siddhi-io extension is written | Y | - 
    | groupIdPostfix| Type of the IO is added as postfix to the groupId as a convention | N | {_IOType} 
    | artifactId | Artifact Id of the project | N | siddhi-io-{_IOType}
    | classNameOfSink | Class name of the Sink | N | {_IOType}Sink
    | classNameOfSource | Class name of the Source | N | {_IOType}Source
    
1. Siddhi Map

        mvn archetype:generate \
            -DarchetypeGroupId=io.siddhi.extension.archetype \
            -DarchetypeArtifactId=siddhi-archetype-map \
            -DgroupId=io.siddhi.extension.map \
            -Dversion=1.0.0-SNAPSHOT 
            
    | Properties | Description | Mandatory | Default Value |
    | ------------- |-------------| ---- | ----- |
    | _mapType | Type of Mapper for which Siddhi-map extension is written | Y | - 
    | groupIdPostfix| Type of the Map is added as postfix to the groupId as a convention | N | {_mapType} 
    | artifactId | Artifact Id of the project | N | siddhi-map-{_mapType}
    | classNameOfSinkMapper | Class name of the Sink Mapper| N | {_mapType}SinkMapper
    | classNameOfSourceMapper | Class name of the Source Mapper | N | {_mapType}SourceMapper
   
1. Siddhi Store

       mvn archetype:generate \
          -DarchetypeGroupId=io.siddhi.extension.archetype \
          -DarchetypeArtifactId=siddhi-archetype-store \
          -DgroupId=io.siddhi.extension.store \
          -Dversion=1.0.0-SNAPSHOT
          
    | Properties | Description | Mandatory | Default Value |
    | ------------- |-------------| ---- | ----- |
    | _storeType | Type of Store for which Siddhi-store extension is written | Y | - 
    | groupIdPostfix| Type of the Store is added as postfix to the groupId as a convention | N | {_storeType} 
    | artifactId | Artifact Id of the project | N | siddhi-store-{_storeType}
    | className | Class name of the Store | N | {_storeType}EventTable
    
1. Siddhi Script

       mvn archetype:generate \
           -DarchetypeGroupId=io.siddhi.extension.archetype \
           -DarchetypeArtifactId=siddhi-archetype-script \
           -DgroupId=io.siddhi.extension.script \
           -Dversion=1.0.0-SNAPSHOT
           
    | Properties | Description | Mandatory | Default Value |
    | ------------- |-------------| ---- | ----- |
    | _nameOfScript | Name of Custom Script for which Siddhi-script extension is written | Y | - 
    | groupIdPostfix| Name of the Script is added as postfix to the groupId as a convention | N | {_nameOfScript} 
    | artifactId | Artifact Id of the project | N | siddhi-script-{_nameOfScript}
    | classNameOfScript | Class name of the Script | N | Eval{_nameOfScript}

1. Siddhi TestSuite

        mvn archetype:generate \
            -DarchetypeGroupId=io.siddhi.extension.archetype \
            -DarchetypeArtifactId=siddhi-archetype-testsuite \
            -DgroupId=io.siddhi.testsuite \
            -Dversion=1.0.0-SNAPSHOT
           
    | Properties | Description | Mandatory | Default Value |
    | ------------- |-------------| ---- | ----- |
    | _nameOfTestsuite | Name of TestSuite | Y | - 
    | groupIdPostfix| Name of TestSuite is added as postfix to the groupId as a convention | N | {_nameOfTestsuite}
    | artifactId | Artifact Id of the project | N | siddhi-testsuite-{_nameOfTestsuite}
    | classNameOfUnitTest | Class name of the UnitTest | N | UnitTestsOf{_nameOfTestsuite}
    | classNameOfIntegrationTest | Class name of the IntegrationTest | N | IntegrationTestsOf{_nameOfTestsuite}
    | classNameOfBlackBoxTest | Class name of the BlackBoxTest | N | BlackBoxTestsOf{_nameOfTestsuite}

## How to build from the source
### Prerequisites
* Java 8 or above
* [Apache Maven](https://maven.apache.org/download.cgi#) 3.x.x
### Steps
1. Install above prerequisites if they have not been already installed
2. Get a clone or download source from [Github](https://github.com/wso2-extensions/siddhi-maven-archetype.git)
    ```bash
    git clone https://github.com/wso2-extensions/siddhi-maven-archetype.git
    ```
3. Run the following maven commands from siddhi-maven-archetype directory
     ```bash 
     mvn clean install
     ```
4. Run the archetype creation with -DarchetypeVersion={SNAPSHOT version of the project}

