package main.kotlin.lib.ui.mobile_web

import lib.ui.SearchPageObject
import org.openqa.selenium.remote.RemoteWebDriver

class MWSearchPageObject(driver: RemoteWebDriver) : SearchPageObject(driver) {

    override val SEARCH_INIT_ELEMENT = "css:button#searchIcon"
    override val SEARCH_INPUT = "css:form>input[type='search']"
    override val SEARCH_CANCEL_BUTTON = "css:button.clear"
    override val SEARCH_RESULT_BY_SUBSTRING_TPL =
        "xpath://div[contains(@class,'wikidata-description')][contains(text(),'{SUBSTRING}')]"
    var SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary"
    override val SEARCH_RESULT_CONTAINER = "css:ul.page-list"
    override val SEARCH_RESULT_LIST = "css:ul.page-list"
    override val SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results"
}
