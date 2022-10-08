package lib

import io.appium.java_client.AppiumDriver
import junit.framework.TestCase
import lib.ui.WelcomePageObject
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver

open class CoreTestCase : TestCase() {

    lateinit var driver: RemoteWebDriver

    override fun setUp() {
        super.setUp()
        driver = Platform.getInstance().getDriver()
        this.rotateScreenPortrait()
        this.skipWelcomePageForIOSApp()
        this.openWikiWebPageForMobileWeb()
    }

    override fun tearDown() {
        driver.quit()
        super.tearDown()
    }

    protected fun rotateScreenPortrait() {
        if (driver is AppiumDriver<*>) {
            driver = this.driver as AppiumDriver<WebElement>
            (driver as AppiumDriver<WebElement>).rotate(ScreenOrientation.PORTRAIT)
        } else print(
            "Method rotateScreenPortrait() does nothing for platform " + Platform.getInstance().getPlatformVar()
        )
    }

    protected fun rotateScreenLandscape() {
        if (driver is AppiumDriver<*>) {
            driver = this.driver as AppiumDriver<WebElement>
            (driver as AppiumDriver<WebElement>).rotate(ScreenOrientation.LANDSCAPE)
        } else print(
            "Method rotateScreenLandscape() does nothing for platform " + Platform.getInstance().getPlatformVar()
        )
    }

    protected fun backgroundApp(seconds: Int) {
        if (driver is AppiumDriver<*>) {
            driver = this.driver as AppiumDriver<WebElement>
            (driver as AppiumDriver<WebElement>).runAppInBackground(seconds)
        } else print(
            "Method rotateScreenLandscape() does nothing for platform " + Platform.getInstance().getPlatformVar()
        )
    }

    private fun skipWelcomePageForIOSApp() {
        if (Platform.getInstance().isIOS()) {
            driver = this.driver as AppiumDriver<WebElement>
            val WelcomePageObject: WelcomePageObject = WelcomePageObject(driver)
            WelcomePageObject.clickSkip()
        }
    }

    protected fun openWikiWebPageForMobileWeb() {
        if (Platform.getInstance().isMW()) {
            driver.get("https://en.m.wikipedia.org")
        } else {
            print(
                "Method openWikiWebPageForMobileWeb() does nothing for platform " + Platform.getInstance()
                    .getPlatformVar()
            )
        }
    }
}
