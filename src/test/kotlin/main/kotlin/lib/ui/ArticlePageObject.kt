package lib.ui

import lib.Platform
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import java.lang.Thread.sleep


abstract class ArticlePageObject(driver: RemoteWebDriver) : MainPageObject(driver) {

    abstract val TITLE: String
    open var TITLE_IOS_FOR_ASSERT = ""
    abstract val FOOTER_ELEMENT: String
    open var OPTIONS_BUTTON: String = ""
    abstract val OPTIONS_ADD_TO_MY_LIST_BUTTON: String
    open var ADD_TO_MY_LIST_OVERLAY: String = ""
    open var MY_LIST_NAME_INPUT: String = ""
    open var MY_LIST_OK_BUTTON: String = ""
    open var CLOSE_ARTICLE_BUTTON = ""
    open var MY_READING_LIST_TPL: String = ""
    open var OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = ""
    open var MOBILE_VERSION = ""

    fun returnToMobileVersion() {
        waitForElementAndClick(MOBILE_VERSION, "Cannot find mobile version button", 10)
    }

    fun waitForTitleElement(): WebElement {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page", 10)
    }

    fun getArticleName(article_title: String): String {
        return TITLE_IOS_FOR_ASSERT.replace("{TITLE}", article_title)
    }

    fun androidTitleIsPresent() {
        this.waitForElementPresent(TITLE, "Title is not present", 10)
    }

    fun iOStitleIsPresent(article_title: String) {
        val first_article_xpath = getArticleName(article_title)
        this.waitForElementPresent(first_article_xpath, "Article not present", 10)
    }

    fun getArticleTitle(): String {
        val title_element: WebElement = waitForTitleElement()
        if (Platform.getInstance().isAndroid())
            return title_element.text
        else if (Platform.getInstance().isIOS())
            return title_element.tagName
        else return title_element.text
    }

    fun swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 40)
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT, "Cannot find the end of article", 40)
        } else this.scrollWebPageTillElementNotVisible(FOOTER_ELEMENT, "Cannot find the end of article", 40)
    }

    fun addArticleToMyNewList(name_of_folder: String) {

        this.waitForElementAndClick(OPTIONS_BUTTON, "Options button not found", 10)
        //Тапаем сохранить в список для чтения
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Button add to read list not found", 10)
        //Проходим онбординг
        this.waitForElementAndClick(ADD_TO_MY_LIST_OVERLAY, "Button 'GOT IT' not found", 10)
        //Стираем дефолтное название списка
        this.waitForElementAndClear(MY_LIST_NAME_INPUT, "Input field not found", 10)
        //Вводим название списка из переменной readListName
        this.waitForElementAndSendKeys(
            MY_LIST_NAME_INPUT,
            name_of_folder,
            "Cannot put text into articles folder input",
            10
        )
        //Тапаем ОК на алерте
        this.waitForElementAndClick(
            MY_LIST_OK_BUTTON,
            "Cannot press OK button",
            10
        )
    }

    fun getMyListXpathByName(name_of_folder: String): String {
        return MY_READING_LIST_TPL.replace("{MY_READING_LIST_NAME}", name_of_folder)
    }

    fun addArticleToExistList(name_of_folder: String) {
        val name_of_folder = getMyListXpathByName(name_of_folder)
        this.waitForElementAndClick(OPTIONS_BUTTON, "Options button not found", 10)
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Button add to read list not found", 10)
        this.waitForElementAndClick(name_of_folder, "Reading list not found", 10)
    }

    fun closeArticle() {
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON, "Button close not found", 10)
        } else {
            print(
                "Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar()
            )
        }

    }

    fun checkTitlePresentWithoutTimeout() {
        assertElementPresentWithoutTimeout(TITLE, "title not found")
    }

    fun addArticlesToMySaved() {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfItAdded()
        }
        waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 10)
    }

    fun removeArticleFromSavedIfItAdded() {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                "Cannot click button to remove an article from saved",
                10
            )
        }
        sleep(2000)
        this.waitForElementPresent(
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            "Cannot find button to add an article to saved list after removing it from this list before",
            10
        )
    }
}
