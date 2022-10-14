package test.kotlin

import io.qameta.allure.Description
import io.qameta.allure.Step
import io.qameta.allure.junit4.DisplayName
import lib.CoreTestCase
import lib.Platform
import lib.ui.WelcomePageObject
import org.junit.Test

class GetStartedTest : CoreTestCase() {

    @Test
    @DisplayName("Pass through welcome-screen")
    @Description("We open app and pass all onboarding steps")
    @Step("Starting testPassThroughWelcome")
    fun testPassThroughWelcome() {
        if(Platform.getInstance().isAndroid()) return
        else return
        /*val WelcomePageObject = WelcomePageObject(driver)

        WelcomePageObject.waitForLearnMoreLink()
        WelcomePageObject.clickNextButton()

        WelcomePageObject.waitForNewWayToExploreText()
        WelcomePageObject.clickNextButton()

        WelcomePageObject.waitForAddOrEditPreferredLangText()
        WelcomePageObject.clickNextButton()

        WelcomePageObject.waitForLearnMoreAboutDataCollectedText()
        WelcomePageObject.clickGetStartedButton()*/
    }
}
