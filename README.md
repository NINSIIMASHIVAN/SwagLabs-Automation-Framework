# SwagLabs Selenium Automation Framework

A Selenium WebDriver + TestNG automation framework built against [SwagLabs (Sauce Demo)](https://www.saucedemo.com/), covering login, inventory/catalogue, checkout, and order confirmation flows. Built using the Page Object Model (POM) with data-driven testing, cross-browser support, and HTML reporting.

## Tech Stack

- **Java** — core language
- **Selenium WebDriver 4.x** — browser automation
- **TestNG** — test execution, assertions, data providers
- **Maven** — build and dependency management
- **WebDriverManager (bonigarcia)** — automatic driver management (Chrome, Firefox)
- **Apache POI** — Excel-based test data
- **ExtentReports** — HTML test reporting

## Project Structure

```
src/
├── main/java/com/SwagLabs/
│   ├── base/            → BaseClass (WebDriver lifecycle, browser setup)
│   ├── pages/            → Page Object classes (LoginPage, InventoryPage, CheckOutPage, ConfirmationPage)
│   ├── actiondriver/      → ActionDriver (reusable wait/click/type wrapper)
│   ├── listeners/         → TestListener (TestNG listener for reporting)
│   └── utilities/         → DataProviders, ExtentManager, ScreenshotUtil
├── main/resources/
│   └── config.properties  → URL, browser, headless flag, credentials
├── test/java/com/SwagLabs/test/
│   ├── LoginTest.java
│   ├── InventoryTest.java
│   ├── CheckOutTest.java
│   └── ConfirmationTest.java
└── test/resources/
    ├── testing.xml         → TestNG suite definition
    └── testdata/TestData.xlsx → data-driven test inputs (checkout details, etc.)
```

## Features Covered

- **Login** — valid login, missing username, missing password, missing credentials, invalid credentials
- **Inventory / Product Catalogue** — page load verification, product listing, add/remove single and multiple products to cart, cart badge count
- **Checkout** — required-field validation (first name, last name, postal code) and full valid checkout flow (data-driven via Excel)
- **Order Confirmation** — confirmation page title and message verification, navigation back to inventory

## Design Notes

- **Page Object Model**: each page has its own class with locators and action methods; test classes never interact with locators directly.
- **ThreadLocal WebDriver**: driver instances are isolated per thread, enabling safe parallel/cross-browser execution.
- **Config-driven, not hardcoded**: browser choice, headless mode, base URL, and credentials all live in `config.properties` — no values hardcoded in test or page classes.
- **Cross-browser support**: Chrome, Firefox, and Edge are all supported via `testing.xml` parameters; `BaseClass` reads the `browser` parameter and launches the matching driver.
- **Data-driven testing**: checkout scenarios are read from `TestData.xlsx` via a custom Excel reader and exposed as a TestNG `@DataProvider`.

## Running the Tests

```bash
mvn clean test
```

By default this runs the suite defined in `src/test/resources/testing.xml`. Browser and headless mode are controlled via `config.properties`:

```properties
url=https://www.saucedemo.com/
browser=chrome
headless=true
```

Set `headless=false` for local debugging when you want to watch the browser interact with the page.

## Known Limitations / Notes

- **Edge WebDriver**: Selenium's built-in driver manager and `WebDriverManager` (bonigarcia) both attempt to resolve Edge drivers from `msedgedriver.azureedge.net`, which has been retired in favor of `msedgedriver.microsoft.com`. Depending on your installed WebDriverManager version, this can cause `UnknownHostException` failures when running Edge. Workaround: manually download the matching `msedgedriver.exe` from [Microsoft's official WebDriver page](https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/) and point to it directly via `System.setProperty("webdriver.edge.driver", "<path>")`, bypassing automatic driver resolution for Edge.
- **Headless mode**: Chrome's `.maximize()` call is unreliable under headless execution and can override an explicit `--window-size` argument. `BaseClass` skips `.maximize()` when `headless=true` and relies on the launch-time window-size flag instead.
- **Test isolation vs. speed**: login currently runs at the class level (`@BeforeClass`) rather than before every individual test, to reduce redundant logins across a full suite run. A lightweight page-reset step runs before each test method to reduce cross-test state leakage; this is a deliberate speed/isolation trade-off rather than full per-test isolation.

## Reporting

ExtentReports generates an HTML report at `src/test/resources/ExtentReports.HTML` after each run (excluded from version control — regenerated per run).