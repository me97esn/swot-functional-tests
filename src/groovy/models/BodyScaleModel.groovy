package models

import net.sourceforge.czt.modeljunit.FsmModel
import net.sourceforge.czt.modeljunit.Action
import org.openqa.selenium.chrome.ChromeDriver
/**
 * Created by IntelliJ IDEA.
 * User: emil
 * Date: 2011-11-24
 * Time: 09:45
 * To change this template use File | Settings | File Templates.
 */
class BodyScaleModel implements net.sourceforge.czt.modeljunit.FsmModel {
    def state = State.EMPTY_BROWSER

    enum State {
        EMPTY_BROWSER, PORTAL_START_PAGE, LOGGED_IN, FRIENDS_PAGE, SCALE_WALL, SCALE_WIDGET
    }

    void reset(boolean b) {
        state = State.EMPTY_BROWSER
    }

    @Action
    void goto_start_page() {
        state = State.PORTAL_START_PAGE
    }

    boolean log_inGuard() {state == State.PORTAL_START_PAGE}
    @Action void log_in() {
        state = State.LOGGED_IN
    }

    boolean list_friendsGuard() {state == State.LOGGED_IN}
    @Action void list_friends() {
        state = State.FRIENDS_PAGE
    }

    boolean show_scale_wallGuard() {state == State.FRIENDS_PAGE}
    @Action void show_scale_wall() {
        state = State.SCALE_WALL
    }

    boolean show_scale_widgetGuard() {state == State.SCALE_WALL}
    @Action void show_scale_widget() {
        state = State.SCALE_WIDGET
    }

    boolean add_scale_readingGuard() {state == State.SCALE_WIDGET}
    @Action void add_scale_reading() {
        state = State.SCALE_WIDGET
    }
}
