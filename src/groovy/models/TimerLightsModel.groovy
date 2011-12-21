package models

import net.sourceforge.czt.modeljunit.Action
import org.openqa.selenium.By

/**
 * Created by IntelliJ IDEA.
 * User: emil
 * Date: 2011-11-29
 * Time: 08:26
 * To change this template use File | Settings | File Templates.
 */
class TimerLightsModel implements net.sourceforge.czt.modeljunit.FsmModel {
    def state = State.EMPTY_BROWSER

    def browser

    enum State {
        EMPTY_BROWSER, PORTAL_START_PAGE, LOGGED_IN, FRIENDS_PAGE, TIMER_WALL, LIGHTS_WALL, LIGHTS_WIDGET, TIMER_WIDGET
    }

    void reset(boolean b) {
        state = State.EMPTY_BROWSER
        // Do not close browser since too many logins will trigger Facebooks captcha!
        // browser?.driver.close()
        // browser = new TimerLightsModelChromeBrowser()
    }

    boolean goto_start_pageGuard() {state == State.EMPTY_BROWSER}

    @Action
    void goto_start_page() {
        state = State.PORTAL_START_PAGE
        browser?.goto_start_page()
    }

    boolean log_inGuard() {state == State.PORTAL_START_PAGE}

    @Action
    void log_in() {
        state = State.LOGGED_IN
        browser?.log_in()
    }

    boolean goto_friendsGuard() {state in [State.LOGGED_IN, State.LIGHTS_WALL, State.TIMER_WALL, State.FRIENDS_PAGE]}

    boolean goto_lights_wallGuard() {state in [State.FRIENDS_PAGE, State.LIGHTS_WALL, State.LIGHTS_WIDGET]}

    boolean goto_timer_wallGuard() {state in [State.FRIENDS_PAGE, State.TIMER_WALL, State.TIMER_WIDGET]}

    boolean goto_timer_widgetGuard() {state in [State.TIMER_WALL, State.TIMER_WIDGET]}

    boolean goto_lights_widgetGuard() {state in [State.LIGHTS_WALL, State.LIGHTS_WIDGET]}

    boolean turn_on_lightsGuard() {state in [State.LIGHTS_WIDGET]}

    boolean turn_off_lightsGuard() {state in [State.LIGHTS_WIDGET]}

    @Action
    void goto_friends() {
        state = State.FRIENDS_PAGE
        browser?.goto_friends()
    }


    @Action
    void goto_lights_wall() {
        state = State.LIGHTS_WALL
        browser?.goto_lights_wall()
    }

    @Action
    void goto_timer_wall() {
        state = State.TIMER_WALL
        browser?.goto_timer_wall()
    }

    @Action
    void goto_timer_widget() {
        state = State.TIMER_WIDGET
        browser?.goto_timer_widget()
    }

    boolean set_timer0Guard() {state in [State.TIMER_WIDGET]}

    @Action
    void set_timer0() {
        state = State.TIMER_WIDGET
        browser?.set_timer()
    }

    boolean set_timer5Guard() {state in [State.TIMER_WIDGET]}

    @Action
    void set_timer5() {
        state = State.TIMER_WIDGET
        browser?.set_timer(5)
    }

    boolean set_timer30Guard() {state in [State.TIMER_WIDGET]}

    @Action
    void set_timer30() {
        state = State.TIMER_WIDGET
        browser?.set_timer(30)
    }

    @Action
    void goto_lights_widget() {
        state = State.LIGHTS_WIDGET
        browser?.goto_lights_widget()
    }

    @Action
    void turn_on_lights() {
        state = State.LIGHTS_WIDGET
        browser?.turn_on_lights()
    }

    @Action
    void turn_off_lights() {
        state = State.LIGHTS_WIDGET
        browser?.turn_off_lights()
    }
}
