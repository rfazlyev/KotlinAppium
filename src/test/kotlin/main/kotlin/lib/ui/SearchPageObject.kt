package lib.ui

import io.qameta.allure.Step
import org.openqa.selenium.remote.RemoteWebDriver

abstract class SearchPageObject(driver: RemoteWebDriver) : MainPageObject(driver) {

    abstract val SEARCH_INIT_ELEMENT: String
    abstract val SEARCH_INPUT: String
    abstract val SEARCH_RESULT_BY_SUBSTRING_TPL: String
    abstract val SEARCH_RESULT_CONTAINER: String
    abstract val SEARCH_CANCEL_BUTTON: String
    open var CLOSE_SEARCH_SCREEN = ""
    abstract val SEARCH_RESULT_LIST: String
    abstract val SEARCH_EMPTY_RESULT_ELEMENT: String

    @Step("Init search input")
    fun initSearchInput() {
        this.waitForElementPresent(
            SEARCH_INIT_ELEMENT,
            "Cannot find search input after clicking search init element",
            10
        )
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 10)
    }

    //TEMPLATES METHODS

    fun getResultSearchElement(substring: String): String {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring)
    }

    //TEMPLATES METHODS

    @Step("Write and send text {substring}")
    fun typeSearchLine(search_line: String) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 10)
    }

    @Step("Wait for search result {substring}")
    fun waitForSearchResult(substring: String) {
        val search_result_xpath = getResultSearchElement(substring)
        this.waitForElementPresent(
            search_result_xpath,
            "Cannot find search result with substring $substring", 10
        )
    }

    @Step("Click article with substring {substring}")
    fun clickByArticleWithSubstring(substring: String) {
        val search_result_xpath = getResultSearchElement(substring)
        this.waitForElementAndClick(
            search_result_xpath,
            "Cannot find search result with substring $substring", 10
        )
    }

    fun searchResultContainerIsPresent() {
        this.waitForElementPresent(SEARCH_RESULT_CONTAINER, "Cannot find search result container", 10)
    }

    fun searchResultContainerIsNotPresent() {
        this.waitForElementNotPresent(SEARCH_RESULT_CONTAINER, "Search result container is present", 10)
    }

    @Step("Cancel search")
    fun clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 10)
    }

    @Step("Close search screen")
    fun clickCloseSearchScreen() {
        this.waitForElementAndClick(CLOSE_SEARCH_SCREEN, "Cannot find and click close search button", 10)
    }

    @Step("Get amount of articles")
    fun getAmountOfFoundArticles(): Int {
        this.waitForElementPresent(SEARCH_RESULT_LIST, "CAnnot find anything by the request", 15)
        return this.getAmountOfElements(SEARCH_RESULT_LIST)
    }
}
