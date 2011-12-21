package models

import net.sourceforge.czt.modeljunit.Action

/**
 * Created by IntelliJ IDEA.
 * User: emil
 * Date: 2011-11-29
 * Time: 08:31
 * To change this template use File | Settings | File Templates.
 */
class MinimalSwotBaseModel implements net.sourceforge.czt.modeljunit.FsmModel{
    enum SwotState {
        EMPTY_BROWSER, PORTAL_START_PAGE, LOGGED_IN, FRIENDS_PAGE
    }

    def state = SwotState.EMPTY_BROWSER

    void reset(boolean b) {
        state = SwotState.EMPTY_BROWSER
    }

    boolean goto_start_pageGuard(){state == SwotState.EMPTY_BROWSER}
    @Action void goto_start_page() {
        state = SwotState.PORTAL_START_PAGE
    }

    boolean log_inGuard() {state == SwotState.PORTAL_START_PAGE}
    @Action void log_in() {
        state = SwotState.LOGGED_IN
    }

    boolean list_friendsGuard() {state == SwotState.LOGGED_IN}
    @Action void list_friends() {
        state = SwotState.FRIENDS_PAGE
    }
}
