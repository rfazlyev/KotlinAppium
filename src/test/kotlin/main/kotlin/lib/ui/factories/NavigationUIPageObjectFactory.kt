package lib.ui.factories

import lib.Platform
import lib.ui.NavigationUI
import lib.ui.android.AndroidNavigationUIPageObject
import lib.ui.ios.IOSNavigationUIPageObject
import main.kotlin.lib.ui.mobile_web.MWNavigationUIPageObject
import org.openqa.selenium.remote.RemoteWebDriver

class NavigationUIPageObjectFactory {
    companion object {
        fun get(driver: RemoteWebDriver): NavigationUI {
            if (Platform.getInstance().isAndroid()) return AndroidNavigationUIPageObject(driver)
            else if (Platform.getInstance().isIOS()) return IOSNavigationUIPageObject(driver)
            else return MWNavigationUIPageObject(driver)
        }
    }
}
