package lib.ui

import lib.Platform
import org.openqa.selenium.remote.RemoteWebDriver

abstract class NavigationUI(driver: RemoteWebDriver) : MainPageObject(driver) {

    abstract val MY_LIST_LINK: String
    open var OPEN_NAVIGATION = ""

    fun clickMyList() {
        if (Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(MY_LIST_LINK, "Button 'My Lists' not found", 10)
        } else {
            this.waitForElementAndClick(MY_LIST_LINK, "Button 'My Lists' not found", 10)
        }
    }

    fun openNavigation() {
        if (Platform.getInstance().isMW()) {
            waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button", 10)
        } else {
            print("Method openNavigation() does nothing for platform " + lib.Platform.getInstance().getPlatformVar())
        }
    }
}
