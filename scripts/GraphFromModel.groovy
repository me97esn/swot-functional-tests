import net.sourceforge.czt.modeljunit.GreedyTester

includeTargets << grailsScript("Init")
includeTargets << grailsScript("Compile")

target(main: "The description of the script goes here!") {
    if(!args){
        throw new Exception("No model class name is provided.")
    }

    def model_class_name = args
    depends(compile)
    def BodyScaleModel = classLoader.loadClass(model_class_name)
    def tester = new GreedyTester(BodyScaleModel.newInstance())
    tester.buildGraph()

    def graphListener = tester.model.getListener("graph")
    graphListener.printGraphDot "/tmp/${model_class_name}.dot"
    println "Graph contains " + graphListener.graph.numVertices() +
            " states and " + graphListener.graph.numEdges() + " transitions."

    "xdot /tmp/${model_class_name}.dot".execute()
}

setDefaultTarget(main)
