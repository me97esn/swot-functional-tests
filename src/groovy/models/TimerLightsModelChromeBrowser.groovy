package models

import org.openqa.selenium.chrome.ChromeDriver

import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

import org.openqa.selenium.WebDriverException
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.openqa.selenium.NoSuchFrameException
/**
 * Created by IntelliJ IDEA.
 * User: emil
 * Date: 2011-11-29
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
class TimerLightsModelChromeBrowser {
    ChromeDriver driver

    TimerLightsModelChromeBrowser() {
        driver = new ChromeDriver()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    }

    def predicted_way() {
        goto_start_page()
        log_in()

        goto_friends()
        goto_lights_wall()
        goto_lights_widget()
        turn_off_lights()

        //goto_friends()
        //goto_lights_wall()
        //goto_lights_widget()

    }

    void goto_start_page() {
        println "goto_start_page"
        //driver.get("http://localhost:8080/client-portal/")
        driver.get("${def value = ConfigurationHolder.config.testserver.url}/client-portal/")
    }

    void log_in() {
        println "log in"
        retryUnless({
            try {
                driver.findElement(org.openqa.selenium.By.id("fblogin")).findElement(org.openqa.selenium.By.tagName("img")).click()
                true
            } catch (WebDriverException e) {
                false
            }
        }) {
            //Do nothing
        }
        try {
            driver.findElement(org.openqa.selenium.By.name("email")).with {
                clear()
                sendKeys("robot2.ericsson@gmail.com")
            }
            driver.findElement(org.openqa.selenium.By.name("pass")).sendKeys("v4rm!and")
            driver.findElement(org.openqa.selenium.By.name("login")).click()
        } catch (WebDriverException e) {
            println "Ignoring exception"
        }
    }

    def retryIfException(actionClosure){
        retryUnless({true}, actionClosure)
    }

    def retryUnless(successClosure, actionClosure) {
        def errorOccurredInAction = false
        def callActionClosure = {
            try {
                actionClosure()
                errorOccurredInAction = false
            } catch (Exception e) {
                log.debug("an error occurred: $e.message")
                errorOccurredInAction = true
            }
        }
        callActionClosure()
        def num_of_retries = 0
        def max_num_of_retries = 10
        while ((errorOccurredInAction || !successClosure()) && num_of_retries++ < max_num_of_retries) {
            sleep 1000
            println "SuccessClosure was unsuccessful, retrying..."
            callActionClosure()
        }
        println "successfully called retryUnless"
    }

    void goto_friends() {
        retryUnless({driver.findElement(By.id("titlepic")).getAttribute("src").endsWith 'images/p_title_friends.png'}) {
            driver.findElement(By.id("friends")).click()
        }

        Thread.sleep 1000 // TODO solve this in a better way...
    }



    void goto_lights_wall() {
        println "goto lights"
        try {
            driver.findElement(org.openqa.selenium.By.linkText("Emulated Lights")).click() // TODO the text of the link should come from fixtures
        } catch (org.openqa.selenium.NoSuchElementException e) {
            driver.findElement(org.openqa.selenium.By.linkText("Wall")).click()
        }
    }



    void goto_lights_widget() {
        println "goto widget"
        sleep 5000
        driver.findElement(By.linkText("Widget")).click()
    }



    void goto_timer_wall() {
        println "goto timer"
        // TODO DRY
        try {
            driver.findElement(org.openqa.selenium.By.linkText("Timer")).click() // TODO the text of the link should come from fixtures
        } catch (org.openqa.selenium.NoSuchElementException e) {
            driver.findElement(org.openqa.selenium.By.linkText("Wall")).click()
        }
    }



    void goto_timer_widget() {
        println "goto widget"
        sleep(5000) // TODO use retryUnless
        driver.findElement(By.linkText("Widget")).click()
    }



    void turn_off_lights() {
        widget {
            it.elementById("Turn All Lights Off").click()
        }
    }



    void turn_on_lights() {
        widget {
            it.elementById("Turn All Lights On").click()
        }
    }

    void set_timer(time = 0) {
        println "set timer:$time"
        widget {
            it.elementById("time").sendKeys("$time")
            it.elementByName("Start").click()
        }
    }

    def widget(closure) {
        def dynamicWidget = new Expando(elementById: {id ->
            println "trying to find element with id '$id'"
            driver.findElement(By.id(id))
        }, elementByName: {name ->
            println "trying to find element with name '$name'"
            driver.findElement(By.name(name))
        })

        dynamicWidget.metaClass.getProperty = { String name ->
            def element = elementById(name)
            element
        }

        driver.switchTo().frame(0)
        // Check if page contains error message
        def source = driver.pageSource
        // println "Looking for 'Internal Server Error' in ${source}"
        if (source.contains("Internal Server Error")) {
            throw new RuntimeException("Found Internal Server Error in the page source")
        }

        closure(dynamicWidget)
        println "Focusing back to the main page"
        driver.switchTo().defaultContent()
    }

    def getWidget() {
        def dynamicWidget = new Expando()
        dynamicWidget.metaClass.getProperty = { String name ->
            driver.switchTo().frame(0)
            def element = driver.findElement(By.id(name))
            driver.switchTo().defaultContent()
            element
        }
        dynamicWidget
    }
}
