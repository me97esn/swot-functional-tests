package models

import net.sourceforge.czt.modeljunit.Action

/**
 * Created by IntelliJ IDEA.
 * User: emil
 * Date: 2011-12-01
 * Time: 07:53
 * To change this template use File | Settings | File Templates.
 */
class FailingModel implements net.sourceforge.czt.modeljunit.FsmModel {
    def state = State.EMPTY_BROWSER

    def browser

    enum State {
        EMPTY_BROWSER, PORTAL_START_PAGE, LOGGED_IN, FRIENDS_PAGE, TIMER_WALL, LIGHTS_WALL, LIGHTS_WIDGET, TIMER_WIDGET
    }

    void reset(boolean b) {
        state = State.EMPTY_BROWSER
    }

    boolean goto_start_pageGuard() {state == State.EMPTY_BROWSER}

    @Action
    void goto_start_page() {
        state = State.PORTAL_START_PAGE
        browser?.goto_lights_wall() // Should fail since the user is not logged in yet
    }
}
