package main.kotlin.lib.ui.mobile_web

import lib.ui.ArticlePageObject
import org.openqa.selenium.remote.RemoteWebDriver

class MWArticlePageObject(driver: RemoteWebDriver): ArticlePageObject(driver) {
    override val TITLE = "css:#content h1"
    override val FOOTER_ELEMENT = "css:footer"
    override val OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[text()='Watch']"
     override var MOBILE_VERSION =  "xpath://*[text()='Mobile view']"
    override var OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "xpath://*[text()='Unwatch']"
}
