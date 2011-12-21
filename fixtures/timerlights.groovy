import swot.functional.tests.grails2.UserBrainInstance
import swot.functional.tests.grails2.Rule
import swot.functional.tests.grails2.WhenApp
import swot.functional.tests.grails2.ThenApp

include "portalfixtures"

pre {
    // Expose a role for the fixture and post closures
    //carrierRole = com.myco.auth.Role.findByAuthority('ROLE_ORGANISATION_ADMIN')
}
fixture {
    def user_profile_id = portalSystemUser.fixture.id
    def brain_id = portalBrainInstance.fixture.id
    def timer_id = portalTimerInstance.fixture.id
    def lights_id = portalLightsInstance.fixture.id

    def userBrain = new UserBrainInstance(swotId:"$user_profile_id-$brain_id")
    def rule = new Rule(name:"RobotTimerToLights",userBrainInstance: userBrain)
    def whenApp = new WhenApp( name:"Timer", rule:rule, swotId:"$user_profile_id-$timer_id",eventName:"alarm")
    def thenApp = new ThenApp(name:"Lights", applicationId:"emulatedlights", applicationSecret:"secret", swotId:"$user_profile_id-$lights_id", rule:rule, actionName:"light1:ON")

    def entities = [userBrain, rule, whenApp, thenApp]

    entities.each {entity ->
        entity.withTransaction {entity.save()}
    }
}
post {
    // Assign a role to the user and flush the data to the database.
    //com.myco.auth.UserRole.create(user, carrierRole, true)
}