package test.kotlin

import io.qameta.allure.*
import io.qameta.allure.junit4.DisplayName
import lib.CoreTestCase
import lib.Platform
import lib.ui.*
import lib.ui.MainPageObject.Companion.screenshot
import lib.ui.factories.ArticlePageObjectFactory
import lib.ui.factories.MyListPageObjectFactory
import lib.ui.factories.NavigationUIPageObjectFactory
import lib.ui.factories.SearchPageObjectFactory
import main.kotlin.lib.ui.AuthorizationPageObject
import org.junit.Assert
import org.junit.Test
import java.lang.Thread.sleep

class ArticleTests : CoreTestCase() {

    companion object {
        val name_of_folder = "Learning programming"
        val login = "KotlinAppium"
        val password = "Qaz12345"
    }

    @Test
    @DisplayName("Save two articles")
    @Description("We save two articles and then delete one of them")
    @Step("Starting testSaveTwoArticles")
    fun testSaveTwoArticles() {
        val SearchPageObject: SearchPageObject = SearchPageObjectFactory.get(driver)
        val ArticlePageObject: ArticlePageObject = ArticlePageObjectFactory.get(driver)
        val Auth: AuthorizationPageObject = AuthorizationPageObject(driver)
        val NavigationUI: NavigationUI = NavigationUIPageObjectFactory.get(driver)
        val MyListPageObject: MyListPageObject = MyListPageObjectFactory.get(driver)

        //Тапаем на поле ввода
        SearchPageObject.initSearchInput()
        //Вводим текст
        SearchPageObject.typeSearchLine("Java")
        //Тапаем по первой статье из саджеста
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language")
        if (Platform.getInstance().isMW()) {
            //Необходима задержка для корректного поиска локатора и клика по нему
            sleep(2000)
        }
        ArticlePageObject.waitForTitleElement()
        //Получаем название статьи
        val article_title = ArticlePageObject.getArticleTitle()

        if (Platform.getInstance().isAndroid()) {
            //Добавляем статью в новый список
            ArticlePageObject.addArticleToMyNewList(name_of_folder)
        } else {
            ArticlePageObject.addArticlesToMySaved()
        }
        if (Platform.getInstance().isMW()) {
            sleep(2000)
            Auth.clickAuthButton()
            sleep(2000)
            Auth.enterLoginData(login, password)
            Auth.submitForm()
            ArticlePageObject.returnToMobileVersion()

            ArticlePageObject.waitForTitleElement()

            Assert.assertEquals("We are not on the same page after login", article_title, ArticlePageObject.getArticleTitle())
        }
        //Закрываем статью
        ArticlePageObject.closeArticle()
        //Тапаем на поле ввода
        SearchPageObject.initSearchInput()
        // На iOS удаляем предыдущий ввод
        if (Platform.getInstance().isIOS()) {
            SearchPageObject.clickCancelSearch()
        }
        //Вводим текст
        SearchPageObject.typeSearchLine("Java")
        //Тапаем по другой статье
        SearchPageObject.clickByArticleWithSubstring("igh-level programming language")
        //Сохраняем ее в тот же список
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToExistList(name_of_folder)
        } else {
            ArticlePageObject.addArticlesToMySaved()
        }
        //Закрываем статью
        ArticlePageObject.closeArticle()
        if (Platform.getInstance().isIOS()) {
            SearchPageObject.clickCloseSearchScreen()
        }
        //Открываем панель навигации для mobile-web
        NavigationUI.openNavigation()
        //Открываем вкладку сохраненные
        NavigationUI.clickMyList()
        //Тапаем по нашему списку для Android
        if (Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(name_of_folder)
        }
        // Закрываем модальное окно на iOS
        if (Platform.getInstance().isIOS()) {
            MyListPageObject.clickButtonCloseSyncMyArticlesWindow()
        }
        //Удаляем одну статью
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            MyListPageObject.swipeByArticleToDelete(article_title)
        } else {
            MyListPageObject.swipeAndDeleteFirstArticleIos()
        }
        //Создаем переменную для сравнения заголовков
        val titleForAssert = "Java (programming language)"
        //Проверяем что статья с заданным заголовком отображается

        if (Platform.getInstance().isIOS()) {
            MyListPageObject.articleIsPresent(titleForAssert)
        }

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            MyListPageObject.waitForTitleElementFromMyListScreen()
            //Сохраняем название статьи с экрана списка для чтения
            val titleFromListScreen = MyListPageObject.getArticleTitleFromMyListScreen()
            //Открываем статью
            MyListPageObject.openFirstArticleFromMyListScreen()
            //Сохраняем название статьи с экрана самой статьи
            ArticlePageObject.waitForTitleElement()
            val titleFromArticleScreen = ArticlePageObject.getArticleTitle()
            //Сравнимаем названия статей
            Assert.assertEquals("Title not equals", titleFromListScreen, titleFromArticleScreen)
        }
        if (Platform.getInstance().isIOS()) {
            //Открываем статью
            MyListPageObject.openFirstArticleIos()
            //Проверяем что заданный заголовок
            ArticlePageObject.iOStitleIsPresent(titleForAssert)
        }
        //Возращаемся и удаляем последнюю статью из своего списка
        if (Platform.getInstance().isMW()) {
            NavigationUI.openNavigation()
            NavigationUI.clickMyList()
            val titleFromListScreen = MyListPageObject.getArticleTitleFromMyListScreen()
            MyListPageObject.swipeByArticleToDelete(titleFromListScreen)
        }
    }

    @Test
    @Feature(value = "Title")
    @DisplayName("Article title is present")
    @Description("We check that article has title")
    @Step("Starting testTitleIsPresent")
    @Severity(value = SeverityLevel.BLOCKER)
    fun testTitleIsPresent() {
        val SearchPageObject: SearchPageObject = SearchPageObjectFactory.get(driver)
        val ArticlePageObject: ArticlePageObject = ArticlePageObjectFactory.get(driver)
        val NavigationUI: NavigationUI = NavigationUIPageObjectFactory.get(driver)
        val MyListPageObject: MyListPageObject = MyListPageObjectFactory.get(driver)

        //Тапаем на поле ввода
        SearchPageObject.initSearchInput()
        //Вводим текст
        SearchPageObject.typeSearchLine("Java")
        //Тапаем по первой статье из саджеста
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language")
        if (Platform.getInstance().isIOS()) {
            ArticlePageObject.iOStitleIsPresent("Java (programming language)")
        } else {
            ArticlePageObject.androidTitleIsPresent()
        }
    }

    @Test
    @Features(Feature(value = "Swipe"),Feature(value = "Search") )
    @DisplayName("Swipe article to the footer")
    @Description("We open an article and swipe it to the footer")
    @Step("Starting testSwipeUp")
    fun testSwipeUp() {
        val SearchPageObject: SearchPageObject = SearchPageObjectFactory.get(driver)
        SearchPageObject.initSearchInput()
        SearchPageObject.typeSearchLine("Appium")
        SearchPageObject.clickByArticleWithSubstring("utomation for Apps")


        val ArticlePageObject: ArticlePageObject = ArticlePageObjectFactory.get(driver)
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.waitForTitleElement()
        }
        ArticlePageObject.swipeToFooter()
    }
}
