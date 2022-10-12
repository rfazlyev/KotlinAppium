package lib

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

open class Platform {

    companion object {
        const val PLATFORM_IOS = "ios"
        const val PLATFORM_ANDROID = "android"
        const val PLATFORM_MOBILE_WEB = "mobile_web"
        const val APPIUM_URL = "http://127.0.0.1:4723/wd/hub"
        var instance: Platform? = null

        @JvmName("getInstance1")
        fun getInstance(): Platform {

            return if (instance == null) {
                instance = Platform()
                instance!!
            } else instance!!
        }
    }

    fun getDriver(): RemoteWebDriver {
        val URL = URL(APPIUM_URL)
        if (this.isAndroid()) return AndroidDriver<WebElement>(URL, this.getAndroidDesiredCapabilities())
        else if (this.isIOS()) return IOSDriver<WebElement>(URL, this.getIOSDesiredCapabilities())
        else if (this.isMW()) return ChromeDriver(this.getMWChromeOptions())
        else throw Exception("Cannot detect type of the Driver. Platform value: " + this.getPlatformVar())
    }

    fun isAndroid(): Boolean {
        return isPlatform(PLATFORM_ANDROID)
    }

    fun isIOS(): Boolean {
        return isPlatform(PLATFORM_IOS)
    }

    fun isMW(): Boolean {
        return isPlatform(PLATFORM_MOBILE_WEB)
    }

    private fun getAndroidDesiredCapabilities(): DesiredCapabilities {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("deviceName", "AndroidTestDevice")
        capabilities.setCapability("platformVersion", "8.0")
        capabilities.setCapability("automationName", "Appium")
        capabilities.setCapability("appPackage", "org.wikipedia")
        capabilities.setCapability("appActivity", ".main.MainActivity")
        capabilities.setCapability(
            "app",
            "/Users/indriver/Desktop/KotlinAppiumAutomation/KotlinAppiumAutomation/apks/org.wikipedia.apk"
        )
        return capabilities
    }

    private fun getIOSDesiredCapabilities(): DesiredCapabilities {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platformName", "iOS")
        capabilities.setCapability("deviceName", "iPhone 13")
        capabilities.setCapability("platformVersion", "15.5")
        capabilities.setCapability(
            "app",
            "/Users/indriver/Desktop/KotlinAppiumAutomation/KotlinAppiumAutomation/apks/Wikipedia.app"
        )
        return capabilities
    }

    private fun getMWChromeOptions(): ChromeOptions {
        var deviceMetrics = mutableMapOf<String, Any?>()
        deviceMetrics.put("width", 360)
        deviceMetrics.put("height", 640)
        deviceMetrics.put("pixelRatio", 3.0)

        var mobileEmulation = mutableMapOf<String, Any?>()
        mobileEmulation.put("deviceMetrics", deviceMetrics)
        mobileEmulation.put(
            "userAgent",
            "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19"
        )

        val chromeOptions: ChromeOptions = ChromeOptions()
        chromeOptions.addArguments("window-size=340,640")

        return chromeOptions
    }

    private fun isPlatform(my_platform: String): Boolean {
        val platform = this.getPlatformVar()
        return my_platform == platform
    }

    open fun getPlatformVar(): String {
        return System.getenv("PLATFORM")
    }
}
