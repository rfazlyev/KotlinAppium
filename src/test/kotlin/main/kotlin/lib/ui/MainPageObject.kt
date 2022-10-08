package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.TouchAction
import lib.Platform
import org.junit.Assert
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

open class MainPageObject(
    var driver: RemoteWebDriver
) {
    fun assertElementPresentWithoutTimeout(locator: String, errorMessage: String): WebElement {
        val by = this.getLocatorByString(locator)
        val element = WebDriverWait(driver, 0)
        element.withMessage(errorMessage + "\n")
        return element.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    fun waitForElementPresent(locator: String, errorMessage: String, timeoutInSeconds: Long): WebElement {
        val by = this.getLocatorByString(locator)
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage(errorMessage + "\n")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    fun waitForElementNotPresent(locator: String, errorMessage: String, timeout: Long): Boolean {
        val by = this.getLocatorByString(locator)
        val wait = WebDriverWait(driver, timeout)
        wait.withMessage(errorMessage + "\n")
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by))
    }

    fun waitForElementAndClick(locator: String, errorMessage: String, timeout: Long): WebElement {
        val element: WebElement = waitForElementPresent(locator, errorMessage, timeout)
        element.click()
        return element
    }

    fun waitForElementAndSendKeys(locator: String, text: String, errorMessage: String, timeout: Long): WebElement {
        val element: WebElement = waitForElementPresent(locator, errorMessage, timeout)
        element.sendKeys(text)
        return element
    }

    fun waitForElementAndClear(locator: String, errorMessage: String, timeout: Long): WebElement {
        val element: WebElement = waitForElementPresent(locator, errorMessage, timeout)
        element.clear()
        return element
    }

    fun assertElementHasText(locator: String, title: String, errorMessage: String) {
        val element: WebElement = waitForElementPresent(locator, errorMessage, 10)
        val textFromElement = element.text
        Assert.assertEquals(errorMessage, title, textFromElement)
    }

    fun swipeUp(timeOfSwipe: Int) {
        if (driver is AppiumDriver<*>) {
            val action: TouchAction = TouchAction(driver as AppiumDriver<WebElement>)
            val size: Dimension = driver.manage().window().size
            val x = size.width / 2
            val start_y: Int = (size.height * 0.8).toInt()
            val end_y: Int = (size.height * 0.2).toInt()

            action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform()
        } else print(
            "Method swipeUp() does nothing for platform " + Platform.getInstance().getPlatformVar()
        )
    }

    fun scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            val JSExecutor = driver as JavascriptExecutor
            JSExecutor.executeScript("window.scrollBy(0,250)")
        } else print(
            "Method scrollWebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar()
        )
    }

    fun scrollWebPageTillElementNotVisible(locator: String, errorMessage: String, maxSwipes: Int) {
        var already_swiped = 0
        val element = this.waitForElementPresent(locator, errorMessage, 10)

        while (!this.isElementLocationOnScreen(locator)) {
            scrollWebPageUp()
            already_swiped += 1
            if (already_swiped > maxSwipes) {
                Assert.assertTrue(errorMessage, element.isDisplayed)
            }
        }
    }

    fun swipeQuick() {
        swipeUp(200)
    }

    fun swipeUpToFindElement(locator: String, errorMessage: String, maxSwipes: Int) {
        val by = this.getLocatorByString(locator)
        var alreadySwiped = 0
        while (driver.findElements(by).size == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + errorMessage, 0)
                return
            }
            swipeQuick()
            alreadySwiped++
        }
    }

    fun swipeElementToLeft(locator: String, errorMessage: String) {
        if (driver is AppiumDriver<*>) {
            val element: WebElement = waitForElementPresent(locator, errorMessage, 10)

            val left_x: Int = element.location.getX()
            val right_x: Int = left_x + element.size.width
            val upper_y: Int = element.location.getY()
            val lower_y: Int = upper_y + element.size.height
            val middle_y: Int = (upper_y + lower_y) / 2

            val action: TouchAction = TouchAction(driver as AppiumDriver<WebElement>)
            action.press(right_x, middle_y)
            action.waitAction(350)
            if (Platform.getInstance().isAndroid()) {
                action.moveTo(left_x, middle_y)
            } else {
                val offset_x = (-1 * element.size.getWidth())
                action.moveTo(offset_x, 0)
            }
            action.release()
            action.perform()
        }

    }

    fun getAmountOfElements(locator: String): Int {
        val by = this.getLocatorByString(locator)
        val elements = listOf(driver.findElements(by))
        return elements.size
    }

    fun assertElementNotPresent(locator: String, errorMessage: String) {
        val amountOfElements = getAmountOfElements(locator)
        if (amountOfElements > 0) {
            val defaultMessage = "An element '$locator' supposed to be not present"
            throw AssertionError("$defaultMessage $errorMessage")
        }
    }

    fun waitForElementAndGetAttributeText(
        locator: String,
        errorMessage: String,
        timeoutInSeconds: Long
    ): String {
        val element: WebElement = waitForElementPresent(locator, errorMessage, timeoutInSeconds)
        return element.text
    }

    fun getLocatorByString(locator_with_type: String): By {
        val exploded_locator = locator_with_type.split(":", limit = 2)
        val by_type = exploded_locator[0]
        val locator = exploded_locator[1]

        if (by_type.equals("xpath")) {
            return By.xpath(locator)
        } else if (by_type.equals("id")) {
            return By.id(locator)
        } else if (by_type.equals("css")) {
            return By.cssSelector(locator)
        } else {
            throw java.lang.IllegalArgumentException("Cannot get type of locator. Locator $locator_with_type")
        }
    }

    fun isElementLocationOnScreen(locator: String): Boolean {
        val element_location_by_y =
            this.waitForElementPresent(locator, "Cannot find element by locator", 5).location.getY()
        if (Platform.getInstance().isMW()) {
            val JSExecutor = driver as JavascriptExecutor
            val js_result = JSExecutor.executeScript("return window.pageYOffset")
            val elemenet_location_by_y = element_location_by_y - Integer.parseInt(js_result.toString())
        }
        val screen_size_by_y = driver.manage().window().size.height
        return element_location_by_y < screen_size_by_y
    }

    fun swipeUpTillElementAppear(locator: String, errorMessage: String, maxSwipes: Int) {
        var alreadySwipe = 0
        while (!isElementLocationOnScreen(locator)) {
            if (alreadySwipe > maxSwipes) {
                Assert.assertTrue(errorMessage, this.isElementLocationOnScreen(locator))
            }
            swipeQuick()
            alreadySwipe++
        }
    }

    fun isElementPresent(locator: String): Boolean {
        return getAmountOfElements(locator) > 1
    }

    fun tryClickElementWithFewAttempts(locator: String, errorMessage: String, amount_of_attempts: Int) {
        var current_attempts = 0
        var need_more_attempts = true

        while (need_more_attempts) {
            try {
                this.waitForElementAndClick(locator, errorMessage, 10)
                need_more_attempts = false
            } catch (e: Exception) {
                if (current_attempts > amount_of_attempts) {
                    this.waitForElementAndClick(locator, errorMessage, 10)
                }
            }
            current_attempts++
        }
    }
}
