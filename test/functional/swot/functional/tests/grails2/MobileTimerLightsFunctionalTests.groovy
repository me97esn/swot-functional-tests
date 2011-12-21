package swot.functional.tests.grails2

import models.TimerLightsModelChromeBrowser
import models.TimerLightsModelMobileChromeBrowser

/**
 * Created by IntelliJ IDEA.
 * User: emil
 * Date: 2011-12-07
 * Time: 10:13
 * To change this template use File | Settings | File Templates.
 */
class MobileTimerLightsFunctionalTests extends TimerLightsFunctionalTests{
    def initModel(){
        model = new TimerLightsModelMobileChromeBrowser()
    }
}
