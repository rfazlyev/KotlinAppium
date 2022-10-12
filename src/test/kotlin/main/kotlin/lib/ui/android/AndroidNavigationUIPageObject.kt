package lib.ui.android

import lib.ui.NavigationUI
import org.openqa.selenium.remote.RemoteWebDriver

class AndroidNavigationUIPageObject(driver: RemoteWebDriver) : NavigationUI(driver) {

    override val MY_LIST_LINK =
        "xpath://*[@resource-id='org.wikipedia:id/fragment_main_nav_tab_layout']//*[@content-desc='My lists']"
}
