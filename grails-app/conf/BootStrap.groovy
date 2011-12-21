class BootStrap {

    def fixtureLoader

    def init = { servletContext ->
        fixtureLoader.load('timerlights')
        //fixtureLoader.load('portalfixtures')
        print "done with loading fixtures"
        System.setProperty("webdriver.chrome.driver", new File("lib/${System.getProperty("os.name")}/chromedriver").absolutePath)
    }
    def destroy = {
    }
}
