package swot.functional.tests.grails2

import models.TimerLightsModelChromeBrowser

/**
 * Created by IntelliJ IDEA.
 * User: emil
 * Date: 2011-12-07
 * Time: 10:13
 * To change this template use File | Settings | File Templates.
 */
class TimerLightsFunctionalTests extends functionaltestplugin.FunctionalTestCase{
    def model
    def initModel(){
        model = new TimerLightsModelChromeBrowser()
    }
    void test_that_when_all_lights_are_off_and_a_timer_is_set_to_zero_light1_should_change_to_on() {

        try {
            initModel()
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
            model.retryUnless({
                model.widget{widget->
                    widget.light1.getAttribute('alt') == 'ON'
                }
            }){
                model.goto_lights_widget()
            }
            model.widget{
                 assert it.light1.getAttribute('alt') == 'ON'
            }
        } finally {
            //model.close()
        }
    }

}
