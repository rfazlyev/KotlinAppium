package lib.ui.factories

import io.appium.java_client.AppiumDriver
import lib.Platform
import lib.ui.SearchPageObject
import lib.ui.android.AndroidSearchPageObject
import lib.ui.ios.IOSSearchPageObject
import main.kotlin.lib.ui.mobile_web.MWSearchPageObject
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver

open class SearchPageObjectFactory {

    companion object {
        fun get(driver: RemoteWebDriver): SearchPageObject {
            if (Platform.getInstance().isAndroid()) return AndroidSearchPageObject(driver)
            else if (Platform.getInstance().isIOS()) return IOSSearchPageObject(driver)
            else return MWSearchPageObject(driver)
        }
    }
}