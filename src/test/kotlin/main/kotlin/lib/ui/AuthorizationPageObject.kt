package main.kotlin.lib.ui

import io.qameta.allure.Step
import lib.ui.MainPageObject
import org.openqa.selenium.remote.RemoteWebDriver
import java.util.*
import kotlin.concurrent.schedule

open class AuthorizationPageObject(driver: RemoteWebDriver) : MainPageObject(driver) {
    open var LOGIN_BUTTON = "xpath://body/div//a[text()='Log in']"
    open var LOGIN_INPUT = "css:input[name='wpName']"
    open var PASSWORD_INPUT = "css:input[name='wpPassword']"
    open var SUBMIT_BUTTON = "css:button[name='wploginattempt'"

    @Step("click auth-button")
    fun clickAuthButton(){
        this.waitForElementPresent(LOGIN_BUTTON, "Cannot find auth button",10)
        Timer().schedule(5000){}
        this.waitForElementAndClick(LOGIN_BUTTON,"Cannot find and click auth button",10)
    }

    @Step("Enter login data")
    fun enterLoginData(login: String, password: String){
        this.waitForElementAndSendKeys(LOGIN_INPUT,login,"Cannot find and put a login to the login input",10)
        this.waitForElementAndSendKeys(PASSWORD_INPUT,password,"Cannot find and put a password to the password input",10)
    }

    @Step("Submit form")
    fun submitForm(){
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click auth submit button",10)
    }
}
