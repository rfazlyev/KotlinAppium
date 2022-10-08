package lib.ui.factories

import lib.Platform
import lib.ui.MyListPageObject
import lib.ui.android.AndroidMyListPageObject
import lib.ui.ios.IOSMyListPageObject
import main.kotlin.lib.ui.mobile_web.MWMyListPageObject
import org.openqa.selenium.remote.RemoteWebDriver

class MyListPageObjectFactory {
    companion object {
        fun get(driver: RemoteWebDriver): MyListPageObject {
            if (Platform.getInstance().isAndroid()) return AndroidMyListPageObject(driver)
            else if (Platform.getInstance().isIOS()) return IOSMyListPageObject(driver)
            else return MWMyListPageObject(driver)
        }
    }
}
