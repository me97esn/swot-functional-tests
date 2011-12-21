package models

import org.openqa.selenium.chrome.ChromeDriver
import net.sourceforge.czt.modeljunit.Action
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

/**
 * Created by IntelliJ IDEA.
 * User: emil
 * Date: 2011-11-25
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
class BodyScaleModelChromeBrowser extends BodyScaleModel {
    def driver = new ChromeDriver()



    @Action
    @Override
    void goto_start_page() {
        super.goto_start_page()    //To change body of overridden methods use File | Settings | File Templates.
        println "goto_start_page"
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        driver.get("http://localhost:8080/client-portal/")
    }

    @Override
    void log_in() {
        super.log_in()    //To change body of overridden methods use File | Settings | File Templates.
        println "log in"
        driver.findElement(By.id("fblogin")).findElement(By.tagName("img")).click()

        driver.findElement(By.id("email")).sendKeys("robot.ericsson@gmail.com")
        driver.findElement(By.id("pass")).sendKeys("v4rm!and")
        driver.findElement(By.name("login")).click()
    }

    @Override
    void list_friends() {
        super.list_friends()    //To change body of overridden methods use File | Settings | File Templates.

        // TODO A possible problem: Has to wait for whole page to load before clicking links
        Thread.sleep 1000
        driver.findElement(By.id("friends")).click()
    }

    @Override
    void show_scale_wall() {
        super.show_scale_wall()    //To change body of overridden methods use File | Settings | File Templates.
        println "show scale wall"
        driver.findElement(By.linkText("Bodyscale")).click()
    }

    @Override
    void show_scale_widget() {
        super.show_scale_widget()    //To change body of overridden methods use File | Settings | File Templates.
        driver.findElement(By.linkText("Widget")).click()
    }

    @Override
    void add_scale_reading() {
        super.add_scale_reading()    //To change body of overridden methods use File | Settings | File Templates.
        driver.switchTo().frame(0)
        driver.findElement(By.id("dataValue")).sendKeys("74")
        driver.findElement(By.id("manualData")).submit()
    }

    @Override
    void reset(boolean b) {
        super.reset(b)
        driver.get("http://localhost:8080/client-portal/")
    }

    def close() {
        driver.close()
    }
}
