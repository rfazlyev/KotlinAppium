package lib.ui.factories

import lib.Platform
import lib.ui.ArticlePageObject
import lib.ui.android.AndroidArticlePageObject
import lib.ui.ios.IOSArticlePageObject
import main.kotlin.lib.ui.mobile_web.MWArticlePageObject
import org.openqa.selenium.remote.RemoteWebDriver

open class ArticlePageObjectFactory {

    companion object {
        fun get(driver: RemoteWebDriver): ArticlePageObject {
            if (Platform.getInstance().isAndroid()) return AndroidArticlePageObject(driver)
            else if (Platform.getInstance().isIOS()) return IOSArticlePageObject(driver)
            else return MWArticlePageObject(driver)
        }
    }
}
