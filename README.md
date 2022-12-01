# QA Automation Diploma Project

---

## About
Automation project on Java, covering functional testing, graphical interface and API.
The project was created using the TestiNG framework and Page Object Model, such an infrastructure provides an easy and fast way to add new tests, and is also easily expanded and maintained.

Jenkins is used to run test suites linked into a pipeline.


The Allure Report system is used as the main reporting system.


---

## Tested Website:
<a href="https://qase.io/">
<img src="https://app.qase.ca/sites/all/themes/qase/images/source/dashboard/img/blue-logo.png"  height="40" /> </a>

This is a service that allows you to effectively store and systematize textual information about the product.
Link to **[Check List](https://docs.google.com/spreadsheets/d/1nVYVrNgMXWsjxFZnoTqt6V7JiCtJcid1oD--midtB5A/edit?usp=sharing)**

---
## Technology:

* Java 11
* Maven

#### Tools & Frameworks:

* [TestNG](https://testng.org/) - Framework
* [Selenium](https://www.selenium.dev/) - Framework
* [Selenide](https://selenide.org) - Framework
* [Cucumber](https://cucumber.io) - Framework implementing the BDD approach
* [REST Assured](https://rest-assured.io/) - for API Testing
* [GSON](https://github.com/google/gson) - library that allows to convert JSON objects to Java objects and vice versa
* [Log4j](https://logging.apache.org/log4j/2.x/) - Java logging library
* [Allure Reports](http://allure.qatools.ru/) - Reporting System
* [Jenkins](https://www.jenkins.io/) - for automating Test Pipelines
* [JavaFaker](https://github.com/DiUS/java-faker) - library that generates fake data
* **Lombok** - Library for shortening code in classes and extending the functionality of the Java
* **Listenrs** - interface for Logs & Customizable TestNG Reports

---

### Architecture:

```
|── files  <-includes requests, jsonschema and test files
├── src
|   |── main/java
|   |   ├── APIHelper  <- contains a class for API requests
|   |   ├── cucmberSteps  <- contains classes with tests that use Cucumber library
|   |   ├── driver  <- contains classes for driver management, creates Chrome and Firefox drivers
|   |   ├── entity  <- contains  classes with entities that use Lombok library for further object creation, used for Web and API tests
|   |   ├── javaFaker  <- contains classes for generating fake data for creating objects
|   |   ├── pageObjects  <- contains test logic with the implementation of the Page Objects pattern
|   |   |   ├── baseObjects 
|   |   ├── |   ├── BasePage.class  <- parenting abstract class for all page objects with using Selenium. Contains basic methods for working with page elements
|   |   ├── |   ├── BaseTest.class  <- parenting class for all tests with using Selenium.Gets properties with PropertyReader, start and close driver,contains a method for creating page instances, use Listeners for creating reports
|   |   ├── |   ├── SelenideBasePage.class  <- parenting class for all tests using Selenide. Contains a method for creating page instances. Gets properties with PropertyReader. Uses Selenide Listener 
|   |   ├── |   ├── SelenideBaseTest.class  <- parenting abstract class for all page objects with using Selenide. Contains basic methods for working with page elements
|   |   |   ├── qaseSelenide  <- contains classes with pages created based on SelenideBasePage class using Page Object pattern + Selenide for work with pages elements
|   |   |   ├── qaseSelenium  <- contains classes with pages created based on BasePage class using Page Object pattern + Selenium for work with pages elements
|   |   ├── propertyHelper  <- contains class for read properties from .properties file, properties are used in Listener.
|   |   ├── testNgUtils  <- contains Listeners
|   |   ├── ├── ExtentReportListener.class  <- Listener for create ExtentReport 
|   |   ├── ├── InvoketedMetodListener.class  <- Listener for create screenshots in Allure Reports
|   |   ├── ├── Listener.class  <- implements ITestListener. Contains method onStart that uses PropertyReader to get properties from .properties files, sets driver and write data in TestNG Report. Method OnFinish closes driver
|   |   ├── ├── Retry.class  <- implements restarting tests in case they fall
|   |   ├── ├── SelenideListener.class  <- Listener for Selenide
|   ├── main/resources  <-contains .properties files with main data for tests (type of driver, base url...)
|   |   
│   ├── test/java
|   |   ├── API_Tests  <- contains classes with API tests
|   |   ├── WEB_Tests  <- contains classes with WEB tests
│   ├── test/resourses
|   |   ├── features  <- contains feature files for Cucumber tests
|   |   ├── allure.properties  <- properties for Allure Report
|   |   ├── BDD_milestone.xml  <- xml file then used to run BDD tests
|   |   ├── log4j.properties  <- configurations for log4j reporting
|   |   ├── QaseRestApi.xml  <- xml file then used to run API tests
|   |   ├── QaseWeb.xml  <- xml file then used to run WEB tests
|   |   |
├── Jenkinsfile  <- Jenkins configuration file
├── pom.xml  <- contains information about the project and configuration details used by Maven to build the project
```
## How to run tests :

The tests are run via **Run Configurations** .xml files or from **Terminal**

* To run tests via the **Terminal**, use the command ::

      mvn clean test -DsuiteXml="XmlName" -Dconfig="propertyFileName"

Where ::

* **propertyFileName** - the name of the property file corresponding to the suite with tests (**_qase_**)
* **XmlName** - name suite with tests to run (**_QaseRestApi_**, **_QaseWeb_** or **_BDD_milestone_**)

For example ::

      mvn clean test -DsuiteXml="QaseRestApi" -Dconfig="qase"

* To run tests via Run Configurations, right-click on the .xml file and select **"Modify Run Configuration..."**

In the window that opens, specify the **properties** to run, for example:

![](../../qase1.png)

After that click to run suite::

 ![](../../Снимок экрана 2022-12-01 в 11.46.05.png)

* **LOGS ::**
*
  * All tests at runtime write a log that provides additional information about what happened if they failed. These
    files are located in **target/logs**


* **ALLURE ::**
*
  * Results for allure reports are located in **target/allure-results**. For view allure-report run this command in
    Terminal:

        allure serve target/allure-results

If the tests fall, a screenshot will be taken and attached to the Allure Report

* **DOCKER ::**
*
  * To run tests in a container, use the command in the terminal :

        sudo docker image build -t selenium-tests:0.0.1 .

The suite with the tests to run can be changed in the **Dockerfile**.

---
## Jenkins & Allure Reports
#### Report for WEB Tests ::
![](../../../Downloads/WEB.gif)


#### Report for API Tests ::

![](../../../Downloads/WEB.gif)