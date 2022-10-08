package main.kotlin.lib.ui.mobile_web

import lib.ui.NavigationUI
import org.openqa.selenium.remote.RemoteWebDriver

class MWNavigationUIPageObject(driver: RemoteWebDriver): NavigationUI(driver) {
    override val MY_LIST_LINK = "css:a[data-event-name='menu.unStar']"
    override var OPEN_NAVIGATION = "css:#mw-mf-main-menu-button"
}
