package swot.functional.tests.grails2

import grails.test.mixin.*
import grails.test.mixin.support.*

import net.sourceforge.czt.modeljunit.GreedyTester
import net.sourceforge.czt.modeljunit.coverage.ActionCoverage
import net.sourceforge.czt.modeljunit.coverage.StateCoverage
import net.sourceforge.czt.modeljunit.coverage.TransitionCoverage
import net.sourceforge.czt.modeljunit.coverage.TransitionPairCoverage
import models.BodyScaleModelChromeBrowser
import junit.framework.Assert

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class BodyScaleTests {

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void test_it() {
    }

    void _test_predicted_way_add_reading() {
        def model = new BodyScaleModelChromeBrowser()
        try {
            model.goto_start_page()
            model.log_in()
            model.list_friends()
            model.show_scale_wall()
            model.show_scale_widget()
            model.add_scale_reading()
        } finally {
            //model.close()
        }
    }

    void _test_generate_and_run_model_based_tests() {

        def tester = new GreedyTester(new BodyScaleModelChromeBrowser())
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
        tester.generate 1

        println 'Metrics Summary:'
        metrics.each {
            tester.model.printMessage "$it.name is $it"
        }
    }
}
