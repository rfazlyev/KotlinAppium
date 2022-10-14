package test.kotlin

import io.qameta.allure.Description
import io.qameta.allure.Step
import io.qameta.allure.junit4.DisplayName
import lib.CoreTestCase
import lib.Platform
import lib.ui.SearchPageObject
import lib.ui.factories.SearchPageObjectFactory
import org.junit.Assert
import org.junit.Test

class SearchTests : CoreTestCase() {

    @Test
    @DisplayName("Cancel search articles")
    @Description("We are looking for articles and then cancel search")
    @Step("Starting testCancelSearch")
    fun testCancelSearch() {

        val SearchPageObject: SearchPageObject = SearchPageObjectFactory.get(driver)
        SearchPageObject.initSearchInput()
        SearchPageObject.typeSearchLine("Java")
        SearchPageObject.searchResultContainerIsPresent()
        SearchPageObject.clickCancelSearch()
        if(Platform.getInstance().isIOS()){
            SearchPageObject.clickCloseSearchScreen()
        }
        SearchPageObject.searchResultContainerIsNotPresent()
    }

    @Test
    @DisplayName("Search articles")
    @Description("We are looking for specific articles")
    @Step("Starting testSearch")
    fun testSearch() {
        val SearchPageObject: SearchPageObject = SearchPageObjectFactory.get(driver)
        SearchPageObject.initSearchInput()
        SearchPageObject.typeSearchLine("Java")
        SearchPageObject.waitForSearchResult("bject-oriented programming language")
    }

    @Test
    @DisplayName("Not empty search result")
    @Description("We are looking for article and checking that search have just one result")
    @Step("Starting testAmountOfNotEmptySearch")
    fun testAmountOfNotEmptySearch() {
        val SearchPageObject: SearchPageObject = SearchPageObjectFactory.get(driver)
        SearchPageObject.initSearchInput()
        val search_line = "Linkin Park Discography"
        SearchPageObject.typeSearchLine(search_line)
        val amount_of_search_results = SearchPageObject.getAmountOfFoundArticles()
        Assert.assertTrue("We found too few results", amount_of_search_results > 1)
    }
}
