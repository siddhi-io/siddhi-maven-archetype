
siddhi-maven-archetype
======================================
Follow these steps to create Siddhi Extensions using maven archetypes
* Navigate to `<siddhi-maven-archetype>/siddhi-extension-archetype` directory and run the following command :
        
     `mvn clean install`
     
* Follow one of the step below, based on your project :
    * If you want to create a project as siddhi-execution : 
       * Run the following command
            ```
                mvn archetype:generate
                    -DarchetypeGroupId=org.wso2.siddhi.extension.archetype
                    -DarchetypeArtifactId=siddhi-archetype-execution
                    -DgroupId=org.wso2.extension.siddhi.execution
                    -Dversion=1.0.0-SNAPSHOT
            ```
       * Then the system will pop-up the following message to enter the execution name
           
            eg:- Define value for property 'executionType': ML
            
       * Finally confirm all property values are correct or not by typing Y or press Enter, else type N
                  
    * If you want to create a project as siddhi-io : 
    
       * Run the following command
                
          ```
               mvn archetype:generate
                   -DarchetypeGroupId=org.wso2.siddhi.extension.archetype
                   -DarchetypeArtifactId=siddhi-archetype-io
                   -DgroupId=org.wso2.extension.siddhi.io
                   -Dversion=1.0.0-SNAPSHOT
            ```
       * Then the system will pop-up the following message to enter the typeOf_IO
           
         eg:- Define value for property 'typeOf_IO': http

       * Finally confirm all property values are correct or not by typing Y or press Enter, else type N
         
    * If you want to create a project as siddhi-map : 
        
       * Run the following command
                    
            ```
                mvn archetype:generate
                    -DarchetypeGroupId=org.wso2.siddhi.extension.archetype
                    -DarchetypeArtifactId=siddhi-archetype-map
                    -DgroupId=org.wso2.extension.siddhi.map
                    -Dversion=1.0.0-SNAPSHOT
            ```
       * Then the system will pop-up the following message to enter the mapType
       
            eg:- Define value for property 'mapType':CSV
    
       * Finally confirm all property values are correct or not by typing Y or press Enter, else type N
                   
    * If you want to create a project as siddhi-script : 
            
       * Run the following command
                        
           ```
               mvn archetype:generate
                   -DarchetypeGroupId=org.wso2.siddhi.extension.archetype
                   -DarchetypeArtifactId=siddhi-archetype-script
                   -DgroupId=org.wso2.extension.siddhi.script
                   -Dversion=1.0.0-SNAPSHOT
           ```
       * Then the system will pop-up the following message to enter the script type
       
         eg:- Define value for property 'typeOfScript':

       * Finally confirm all property values are correct or not by typing Y or press Enter, else type N
       
    * If you want to create a project as siddhi-store : 
       
       * Run the following command
                            
            ```
               mvn archetype:generate
                  -DarchetypeGroupId=org.wso2.siddhi.extension.archetype
                  -DarchetypeArtifactId=siddhi-archetype-store
                  -DgroupId=org.wso2.extension.siddhi.store
                  -Dversion=1.0.0-SNAPSHOT
            ```
       * Then the system will pop-up the following message to enter the store type
                          
          eg:- Define value for property 'storeType': RDBMS
    
       * Finally confirm all property values are correct or not by typing Y or press Enter, else type N
                           
                              
                       
                          
