package swot.functional.tests.grails2

class Rule {
    static hasMany = [thenApplications: ThenApp]
    String name

    UserBrainInstance userBrainInstance

    static mapping = {
        userBrainInstance column: 'user_brain_instance'
        datasource 'swotbrain'
    }

    static constraints = {
    }
}
