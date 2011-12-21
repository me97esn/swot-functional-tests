package swot.functional.tests.grails2

class ThenApp {
    String name

    String applicationId

    String applicationSecret

    String swotId

    String actionName

    static belongsTo = [rule: Rule]

    static mapping = {
        table 'then_app'
        rule column: 'rule'
        datasource 'swotbrain'
    }

    static constraints = {
    }
}
