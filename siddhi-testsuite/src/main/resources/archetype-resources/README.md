Siddhi Testsuite
======================================

The **Siddhi-Testsuite** is a <a target="_blank" href="https://siddhi.io/">Siddhi</a> maven archetype project which can
generate custom Siddhi-Testsuite projects. There are 3 types of testing used here as,
1. Unit testing.
2. Integration tesing.
3. Blackbox tesing.

Note : An AbstractTest class is used for the common logic for integration tests and blackbox tests.

1. **Unit Testing** : 
    Siddhi is used in an embedded sandbox mode to make sure no network connections or external dependencies are required 
    for testing. This requires to write a Java code to test the Siddhi app.

2. **Integration Testing** : 
    Performs integration tests by running the application and dependent services as Docker containers. This ensures that 
    the updated Siddhi application functions as expected.

3. **Blackbox Testing** : 
    Run black-box testing by configuring the Siddhi Runner instances to communicate with the actual external systems.
