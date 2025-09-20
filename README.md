--------------------------------------------------------------------
Transit QA - Take Home Exercise
Adrian Corona

This exercise contains automated end-to-end tests for the Transit 
QA Trip Planner using Selenium, TestNG, and POM.

--------------------------------------------------------------------
HOW TO INSTALL:
Prerequisites:
- Java 21 is installed and JAVA_HOME is set.
- Maven is installed and available in your PATH (mvn -v should work).
- Internet connection is required for WebDriverManager.
- Chrome browser is installed.
- Eclipse IDE with the TestNG plugin installed (optional).

Option 1: Install from GitHub
- Clone the repository:
```
git clone https://github.com/AdrianCorona89/TransitAutomation.git
cd TransitAutomation
```
- Ensure preconditions are met.
- Run the tests using Maven:
```
mvn test
```
Option 2: Install from ZIP file
- Download the ZIP file of the repository from GitHub.
- Extract the ZIP file to a folder of your choice.
- Open a terminal in the extracted folder.
- Run the automated tests with Maven:
```
mvn test
```
Option 3:
- Open Eclipse.
- Go to File > Import > Maven > Existing Maven Projects.
- Select the root folder of the project (cloned or extracted).
- Wait for Eclipse to download dependencies and build the project.
- Right click on Tests.java > Run As > TestNG Test.
- View test results in the Console and TestNG tab.

Notes: 
- Tests run headless by default, but you can change this in 
Tests.java by setting the runHeadless boolean to false.

- WebDriverManager automatically manages the correct version of 
ChromeDriver, but Chrome must be installed.

--------------------------------------------------------------------
FRAMEWORK DECISION:
The automated tests were done with Selenium, TestNG and Page Object 
Model for organization. I used this framework because it provides
simple annotation model with @Test, @BeforeMethod, etc... to manage 
test setup and teardown.

--------------------------------------------------------------------
NOTES ON FLAKINESS, ASSUMPTIONS, OR IMPROVEMENTS:
There were some issues on flakiness, particularly due to the server 
responses time. I ended up using a WebDriverWait set to 5 seconds, 
which seemed enough time. But in general, the website is accessible 
and stable.

The tests are executed on a Chrome browser managed by 
WebDriverManager.

For future improvements, I would like to implement the feature of
taking a screenshot for whenever a fail occurs. I wanted to 
implement it for this exercise, however, I was running out of time 
from the expected time limit of 6 hours and I decided to focus on 
the core testing functionality instead.

--------------------------------------------------------------------
NOTES ON CHANGES TO MAKE AUTOMATED TESTING EASIER:
Adding unique and stable IDs to key elements, such as search bar, 
main buttons, error messages and trips suggestions. I initially 
thought of using the IDs provided but I read that react IDs are not 
stable, so I went instead with a mix of Xpath locators. 

--------------------------------------------------------------------
USE OF LLM DISCLAIMER:
I used an LLM for guidance on setting up the project, debugging, and 
structuring the Page Object Model (POM). Some code snippets were 
generated with its assistance; however, all decisions regarding test 
logic, method design, framework choice (Selenium + TestNG + POM), 
and running tests headless by default were made by me.

--------------------------------------------------------------------




