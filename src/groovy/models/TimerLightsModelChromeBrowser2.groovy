package models

import java.util.concurrent.TimeUnit
import net.sourceforge.czt.modeljunit.Action
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver

/**
 * Created by IntelliJ IDEA.
 * User: emil
 * Date: 2011-11-29
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
class TimerLightsModelChromeBrowser2 extends TimerLightsModel {
    def driver = new ChromeDriver()

    @Override
    boolean goto_start_pageGuard() {
        return super.goto_start_pageGuard()    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    boolean log_inGuard() {
        return super.log_inGuard()    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Action
    @Override
    void goto_start_page() {
        super.goto_start_page()    //To change body of overridden methods use File | Settings | File Templates.
        println "goto_start_page"
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        driver.get("http://localhost:8080/client-portal/")
    }

    @Action
    @Override
    void log_in() {
        super.log_in()    //To change body of overridden methods use File | Settings | File Templates.
        println "log in"
        driver.findElement(By.id("fblogin")).findElement(By.tagName("img")).click()

        driver.findElement(By.id("email")).sendKeys("robot.ericsson@gmail.com")
        driver.findElement(By.id("pass")).sendKeys("v4rm!and")
        driver.findElement(By.name("login")).click()
    }
}
