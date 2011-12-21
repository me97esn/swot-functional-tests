package swot.functional.tests.grails2

import grails.test.mixin.*
import grails.test.mixin.support.*

import models.TimerLightsModelChromeBrowser
import org.openqa.selenium.By

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class TimerLightsTests {
    void test_that_when_all_lights_are_off_and_a_timer_is_set_to_zero_light1_should_change_to_on() {
        // TODO set this property dynamically instead, use classloader to get path of file
        System.setProperty("webdriver.chrome.driver", "/home/emil/projects/swot-functional-tests-grails2/lib/chromedriver")
        def model = new TimerLightsModelChromeBrowser()
        try {
            model.goto_start_page()
            model.log_in()

            // Set all lights to off
            model.goto_friends()
            model.goto_lights_wall()
            model.goto_lights_widget()
            model.turn_off_lights()

            // Set timer
            model.goto_friends()
            model.goto_timer_wall()
            model.goto_timer_widget()
            model.set_timer(0)

            // Verify that a light is on
            model.goto_friends()
            model.goto_lights_wall()
            model.goto_lights_widget()
            model.widget{
                 assert it.light1.getAttribute('alt') == 'ON'
            }

            // TODO perhaps should retry to handle asynchronous calls? For instance retry for one minute until alt of the image is 'ON', or fail otherwise

        } finally {
            //model.close()
        }
    }
}
