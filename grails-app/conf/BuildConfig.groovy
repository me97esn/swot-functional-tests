grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.source.level = 1.6
grails.project.target.level = 1.6

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        useOrigin true // Needed to download latest dataaccess from local mvn repo/Nexus
        inherits true // Whether to inherit repository definitions from plugins
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        // uncomment these to enable remote dependency resolution from public Maven repositories
        //mavenCentral()
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://snapshots.repository.codehaus.org"
        mavenRepo "http://slick.cokeandcode.com/mavenrepo/"
        mavenRepo "http://repository.codehaus.org"
        mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://selenium.googlecode.com/svn/repository/"
        mavenRepo "http://repo1.maven.org/maven2"
        mavenRepo "http://www.cf.ericsson.net/nexus/content/groups/public"

    }
    dependencies {
        runtime(
                "org.seleniumhq.selenium:selenium-firefox-driver:2.9.0",
                "org.seleniumhq.selenium:selenium-chrome-driver:2.9.0",
                "mysql:mysql-connector-java:5.1.16",
                'com.ericsson.swot.gsp:data-access:1.0-SNAPSHOT'

        ) {
        }
    }

    plugins {
        compile ":hibernate:$grailsVersion"
        compile ":jquery:1.6.1.1"
        compile ":resources:1.1.1"

        build ":tomcat:$grailsVersion"
    }
}
