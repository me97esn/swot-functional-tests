package swot.functional.tests.grails2

import grails.test.GrailsUnitTestCase
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import models.TimerLightsModelChromeBrowser
import models.TimerLightsModelMobileChromeBrowser

class TimerLightsMobileFunctionalTests extends TimerLightsFunctionalTests {
    def initModel(){
        model = new TimerLightsModelMobileChromeBrowser()
    }

    @Deprecated
    void __test_that_when_all_lights_are_off_and_a_timer_is_set_to_zero_light1_should_change_to_on() {
        def browser = new ChromeDriver()


        // TODO click button in widget to turn of all lights
        sleep 3000
        // TODO click button in widget to set alarm
        sleep 1000
        browser.findElement(By.xpath("//div[contains(text(), 'Widget')]")).click()
        browser.findElementByLinkText("Back to Things").click()
        sleep 1000
        browser.findElement(By.xpath("//div[contains(text(), 'Emulated Lights')]")).click()
        sleep 1000
        browser.findElement(By.xpath("//div[contains(text(), 'Widget')]")).click()

        // TODO verify light1 on

    }
}
