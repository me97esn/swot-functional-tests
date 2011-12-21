package swot.functional.tests.grails2

class WhenApp {
    String name
    Rule rule
    String swotId
    String eventName

    static mapping = {
        table 'when_app'
        rule column: 'rule'
        datasource 'swotbrain'
    }
    static constraints = {
    }
}
