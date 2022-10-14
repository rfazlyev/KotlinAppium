package lib

import io.appium.java_client.AppiumDriver
import io.qameta.allure.Step
import lib.ui.WelcomePageObject
import org.junit.After
import org.junit.Before
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.FileOutputStream
import java.util.*

open class CoreTestCase() {

    lateinit var driver: RemoteWebDriver

    @Before
    @Step("Run driver and session")
    fun setUp() {

        driver = Platform.getInstance().getDriver()
        this.createAllurePropertyFile()
        this.rotateScreenPortrait()
        this.skipWelcomePageForIOSApp()
        this.openWikiWebPageForMobileWeb()
    }

    @After
    @Step("Remove driver and session")
    fun tearDown() {
        driver.quit()

    }

    @Step("Rotate screen on portrait mode")
    protected fun rotateScreenPortrait() {
        if (driver is AppiumDriver<*>) {
            driver = this.driver as AppiumDriver<WebElement>
            (driver as AppiumDriver<WebElement>).rotate(ScreenOrientation.PORTRAIT)
        } else println(
            "Method rotateScreenPortrait() does nothing for platform " + Platform.getInstance().getPlatformVar()
        )
    }

    @Step("Rotate screen on landscape mode")
    protected fun rotateScreenLandscape() {
        if (driver is AppiumDriver<*>) {
            driver = this.driver as AppiumDriver<WebElement>
            (driver as AppiumDriver<WebElement>).rotate(ScreenOrientation.LANDSCAPE)
        } else println(
            "Method rotateScreenLandscape() does nothing for platform " + Platform.getInstance().getPlatformVar()
        )
    }

    @Step("Run app in background(Not for mobile web)")
    protected fun backgroundApp(seconds: Int) {
        if (driver is AppiumDriver<*>) {
            driver = this.driver as AppiumDriver<WebElement>
            (driver as AppiumDriver<WebElement>).runAppInBackground(seconds)
        } else println(
            "Method rotateScreenLandscape() does nothing for platform " + Platform.getInstance().getPlatformVar()
        )
    }

    @Step("Skip welcome-page(just for iOS)")
    private fun skipWelcomePageForIOSApp() {
        if (Platform.getInstance().isIOS()) {
            driver = this.driver as AppiumDriver<WebElement>
            val WelcomePageObject: WelcomePageObject = WelcomePageObject(driver)
            WelcomePageObject.clickSkip()
        }
    }

    @Step("Open wiki webpage only for mobile-web")
    protected fun openWikiWebPageForMobileWeb() {
        if (Platform.getInstance().isMW()) {
            driver.get("https://en.m.wikipedia.org")
        } else {
            println(
                "Method openWikiWebPageForMobileWeb() does nothing for platform " + Platform.getInstance()
                    .getPlatformVar()
            )
        }
    }

    fun createAllurePropertyFile() {
        val path = System.getProperty("allure.results.directory")
        try {
            val props = Properties()
            val fos = FileOutputStream(path + "/environment.properties")
            props.setProperty("Environment", Platform.getInstance().getPlatformVar())
            props.store(fos, "See https://github.com/allure-framework/allure-app/wiki/Environment")
            fos.close()
        } catch (e: Exception) {
            System.err.println("IO problem when writing allure properties file")
            e.printStackTrace()
        }
    }
}
