# Fetch Game
Conner's attempt at solving the coding exercise for the test automation role at Fetch.

## Requirements
* Java
* Maven
* Git

## Run
*   Open a command line in the project root. 
* Run command "mvn clean test -Dsurefire.suiteXmlFiles=testng.xml".

## Install in Intellij IDE
* Open existing project from "File at top left".
* Navigate to FetchExerciseTests class in src/main/java/com/Local/Tests.
* Right click on FetchExerciseTests on line 13 and click "Run".
* If you get the message "TestNG by default disables loading DTD from unsecured Urls. If you need to explicitly load the DTD from a http url, please do so by using the JVM argument [-Dtestng.dtd.http=true]", click on Run > Edit Configurations, and add "-Dtestng.dtd.http=true" to the JVM argument.

## Notes
* The exercise runs a headless browser. If you would like to see the browser just comment out the line 
"chrome_options.addArguments("--headless");"
* I have the exercise set up to run 10 times in a row.
