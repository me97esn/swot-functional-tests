package models

import java.util.concurrent.TimeUnit
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.openqa.selenium.By
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.chrome.ChromeDriver

/**
 * Created by IntelliJ IDEA.
 * User: emil
 * Date: 2011-11-29
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
class TimerLightsModelMobileChromeBrowser extends TimerLightsModelChromeBrowser {

    @Override
    void goto_start_page() {
        println "goto_start_page"
        driver.get("${def value = ConfigurationHolder.config.testserver.url}/mobile-portal/")
    }

    // TODO refactor DRY
    void log_in() {
        println "log in"
        retryUnless({
            try {
                driver.findElement(org.openqa.selenium.By.id("facebookButton")).findElement(org.openqa.selenium.By.tagName("img")).click()
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
            driver.findElement(org.openqa.selenium.By.name("pass")).with {
                clear()
                sendKeys("v4rm!and")
            }
            driver.findElement(org.openqa.selenium.By.name("login")).click()
        } catch (WebDriverException e) {
            println "Ignoring exception"
        }
    }

    @Override
    void goto_friends() {
        retryIfException {
            driver.findElement(By.className("menuButton")).click()
        }
        retryIfException {
            driver.findElement(By.linkText("Things")).click()
        }
    }

    @Override
    void goto_lights_wall() {
        retryIfException {
            driver.findElement(By.xpath("//div[contains(text(), 'Emulated Lights')]")).click()
        }
    }

    @Override
    void goto_lights_widget() {
        retryIfException {
            driver.findElement(By.xpath("//div[contains(text(), 'Widget')]")).click()
        }
    }

    @Override
    void turn_off_lights() {
        // TODO implement this method!
    }

    @Override
    void goto_timer_wall() {
        retryIfException {
            driver.findElementByLinkText("Back to Things").click()
        }
        retryIfException {
            driver.findElement(By.xpath("//div[contains(text(), 'Timer')]")).click()
        }
    }

    @Override
    void goto_timer_widget() {
        goto_lights_widget()
    }

    @Override
    void set_timer(Object time) {
        // TODO implement this method!
    }

    @Override
    def widget(Object closure) {
        closure(driver) // Currently no iframe in mobile portal
    }
}
