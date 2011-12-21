package swot.functional.tests.grails2

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import net.sourceforge.czt.modeljunit.GreedyTester
import models.BodyScaleModelChromeBrowser
import net.sourceforge.czt.modeljunit.coverage.ActionCoverage
import net.sourceforge.czt.modeljunit.coverage.TransitionCoverage
import net.sourceforge.czt.modeljunit.coverage.TransitionPairCoverage
import net.sourceforge.czt.modeljunit.coverage.StateCoverage
import models.TimerLightsModel
import models.TimerLightsModelChromeBrowser
import net.sourceforge.czt.modeljunit.StopOnFailureListener
import java.awt.Toolkit
import java.awt.Rectangle
import java.awt.Robot
import javax.imageio.ImageIO
import net.sourceforge.czt.modeljunit.gui.visualisaton.VisualisationListener

class TimerLightsModelTests extends functionaltestplugin.FunctionalTestCase{
    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void test_generate_and_run_model_based_tests() {
        System.setProperty("webdriver.chrome.driver", "/home/emil/projects/swot-functional-tests-grails2/lib/chromedriver")

        print "starting tests"

        //def tester = new GreedyTester(new TimerLightsModel())
        def tester = new GreedyTester(new TimerLightsModel(browser:new TimerLightsModelChromeBrowser()))

        def metrics = [
                new ActionCoverage(),
                new StateCoverage(),
                new TransitionCoverage(),
                new TransitionPairCoverage()
        ]

        println "add metrics"

        metrics.each {
            tester.addCoverageMetric it
        }

        tester.addListener "verbose"
        def visualisation = new VisualisationListener()
        def stopOnFailure = new StopOnFailureListener()
        tester.addListener(visualisation)
        tester.addListener(stopOnFailure)

        // Create a folder for the failure report
        println "creating the reports folder"
        def path = "model-based-tests-failure-reports/${new Date()}" // TODO this should probably be a env-specific property in Config.groovy
        new File(path).mkdirs()

        100.times {i ->
            try {
                tester.doGreedyRandomActionOrReset()
                print "One action done..."
            } catch (Throwable t) {
                // Create a file with the description of the exception
                new File("${path}/${i}_exception_message.txt").withWriter { out ->
                    out.println tester.graph_.fsmGraph_.toString()
                    out.println t.message
                    out.println ""
                    t.printStackTrace(out.newPrintWriter())
                }

                // Chrome doesn't support screen capture in current version, use java.awt.robot instead
                def screenDim = Toolkit.getDefaultToolkit().getScreenSize();
                def screenBounds = new Rectangle(0, 0, screenDim.width as int, screenDim.height as int);

                def image = new Robot().createScreenCapture(screenBounds);

                ImageIO.write(image, "png", new File("${path}/${i}_screen_capture.png"))
            }
        }

        println 'Metrics Summary:'
        metrics.each {
            tester.model.printMessage "$it.name is $it"
        }
    }

}
