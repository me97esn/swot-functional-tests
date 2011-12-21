package models

import net.sourceforge.czt.modeljunit.Action

/**
 * Created by IntelliJ IDEA.
 * User: emil
 * Date: 2011-11-29
 * Time: 08:23
 * To change this template use File | Settings | File Templates.
 */
abstract class CompleteSwotBaseModel extends MinimalSwotBaseModel {
    boolean log_inGuard() {state == SwotState.PORTAL_START_PAGE}
    boolean listFriendsGuard() {state == SwotState.LOGGED_IN}
}
