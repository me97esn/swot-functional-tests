package swot.functional.tests.grails2

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import models.TimerLightsModel
import models.TimerLightsModelChromeBrowser
import net.sourceforge.czt.modeljunit.GreedyTester
import net.sourceforge.czt.modeljunit.coverage.ActionCoverage
import net.sourceforge.czt.modeljunit.coverage.StateCoverage
import net.sourceforge.czt.modeljunit.coverage.TransitionCoverage
import net.sourceforge.czt.modeljunit.coverage.TransitionPairCoverage
import models.FailingModel
import net.sourceforge.czt.modeljunit.StopOnFailureListener
import net.sourceforge.czt.modeljunit.gui.visualisaton.VisualisationListener
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.OutputType
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Robot
import java.awt.Rectangle
import java.awt.Toolkit
import java.awt.Dimension

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class FailingModelTests {
    void test_generate_and_run_model_based_tests() {
        System.setProperty("webdriver.chrome.driver", "/home/emil/projects/swot-functional-tests-grails2/lib/chromedriver")

        //def tester = new GreedyTester(new TimerLightsModel())
        def model = new FailingModel(browser: new TimerLightsModelChromeBrowser())
        def tester = new GreedyTester(model)
        //def tester = new GreedyTester(new TimerLightsModel(browser:new TimerLightsModelChromeBrowser()))
        //def tester = new GreedyTester(new TimerLightsModel())

        tester.buildGraph()

        def metrics = [
                new ActionCoverage(),
                new StateCoverage(),
                new TransitionCoverage(),
                new TransitionPairCoverage()
        ]

        metrics.each {
            tester.addCoverageMetric it
        }

        tester.buildGraph()


        tester.addListener "verbose"
        def visualisation = new VisualisationListener()
        def stopOnFailure = new StopOnFailureListener()
        tester.addListener(visualisation)
        tester.addListener(stopOnFailure)

        // Create a folder for the failure report
        def path = "/tmp/model_based_tests/problems/${new Date()}"
        new File(path).mkdirs()

        5.times {i ->
            try {
                tester.doGreedyRandomActionOrReset()
            } catch (Throwable t) {
                // Create a file with the description of the exception
                new File("${path}/${i}_exception_message.txt").withWriter { out ->
                    out.println t.message
                    t.printStackTrace(out.newPrintWriter())
                }

                // Chrome doesn't support screen capture in current version, use java.awt.robot instead
                def screenDim = Toolkit.getDefaultToolkit().getScreenSize();
                def screenBounds = new Rectangle(0, 0, screenDim.width as int, screenDim.height as int);

                def image = new Robot().createScreenCapture(screenBounds);

                ImageIO.write(image, "png", new File("${path}/${i}_screen_capture.png"))
            }
        }

        //tester.generate 10
        println 'Metrics Summary:'
        metrics.each {
            tester.model.printMessage "$it.name is $it"
        }
    }

}
