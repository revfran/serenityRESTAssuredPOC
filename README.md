# serenityRESTAssuredPOC
POC with serenity BDD testing a REST API

# First steps
I based this project both on the pom.xml of my previous serenity POC repo and on these first steps with Rest Assured I found:
 - https://www.blazemeter.com/blog/restful-api-testing-using-serenity-and-rest-assured-a-guide

# Prerequisites
- Java 8
- Maven

# Objective
Perform various tests of the weather API with BDD

# Test execution (needs a valid api key from openweathermap.org)
mvn clean verify -Dweather.api.key=XXXXX 

