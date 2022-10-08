package main.kotlin.lib.ui.mobile_web

import lib.ui.MyListPageObject
import org.openqa.selenium.remote.RemoteWebDriver

class MWMyListPageObject(driver: RemoteWebDriver): MyListPageObject(driver) {
    override var ARTICLE_BY_TITLE_TPL = "xpath://ul[contains(@class, 'watchlist')]//h3[contains(text(),'{TITLE}')]"
    override var REMOVE_FROM_SAVED_BUTTON = "xpath://ul[contains(@class,'watchlist')]//h3[contains(text(),'{TITLE}')]/../../a[contains(@class,'watched')]"
    override var FIRST_ARTICLE_ON_MY_LIST_SCREEN = "xpath://h3[1]"

}
