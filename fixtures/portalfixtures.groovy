import com.ericsson.swot.gsp.model.reference.DeviceType
import com.ericsson.swot.gsp.model.reference.IdentityType
import com.ericsson.swot.gsp.model.reference.UserPortalRole
import com.ericsson.swot.gsp.model.reference.UserProfileState
import utils.FixtureWrapper
import com.ericsson.swot.gsp.model.entity.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder

pre {
    // Expose a role for the fixture and post closures
    //carrierRole = com.myco.auth.Role.findByAuthority('ROLE_ORGANISATION_ADMIN')
}
fixture {
    def systemUser = new UserProfile(
            id: 10000,
            firstname: "Robert",
            lastname: "Eriksson",
            email: "robot2.ericsson@gmail.com ",
            pictureURL: "http://graph.facebook.com/100003255066915/picture?type=large",
            systemAdmin: true,
            profileState: UserProfileState.Active
    )
    def systemUserAccount = new Account(owner: systemUser, principal: "system", token: "s3cret", type: IdentityType.SwotInternal)
    def portal = new Portal(name: "Public portal by fixtures", endpointURL: ConfigurationHolder.config.portal.endpointURL)
    portalIdentityProvider(PortalIdentityProvider, identityProviderEndpointURL: "http://connect.facebook.com", portal: portal, type: IdentityType.Facebook)
    portalSettings(PortalSettings, portal: portal, type: DeviceType.WebBrowser, logoURL: "http://img-us2.generation-nt.com/ericsson-logo_0085006400240191.png")
    systemPortal(UserPortalRelation, portal: portal, userProfile: systemUser, portalRole: UserPortalRole.PortalAdmin)



    [systemUser,
            systemUserAccount,
            portal,
    ].each {entity ->
        entity.withTransaction {entity.save()}
    }


    portal.withTransaction { portal.save() }

    userAccount(Account, principal: "100003255066915", owner: systemUser, type: IdentityType.Facebook, token: 'AAABzsOwv3dEBAHgt3BYZAaAZBHwrbud3hTwY4FZCJHETqC6tCpJs34yjvkHe77KF5QiBFZBb9EVxuIVt9hOru3X1zpipfE4xSVegZCJR9XAZDZD')

    portalSystemUser(FixtureWrapper, fixture: systemUser)

    def add_thing = {SocialThing thing ->
        def instanceBean = new SocialThingInstance(
                pictureURL: thing.pictureURL,
                friendlyName: thing.name,
                reference: thing)
        [thing, instanceBean].each {entity ->
            entity.withTransaction {entity.save()}
        }
        portal.things << thing
        portal.withTransaction { portal.save() }

        thing.portals = [portal]
        thing.withTransaction {thing.save()}

        def account = new Account(principal: "${systemUser.id}-${instanceBean.id}", owner: systemUser, type: IdentityType.SocialThingInstance)
        account.withTransaction {account.save()}

        systemUser.things << instanceBean
        systemUser.withTransaction { systemUser.save() }

        instanceBean
    }

    // TODO move this bean to timerlights fixtures
    def portalTimerInstanceBean = add_thing(new SocialThing(name: "Timer", vendor: "Timer", make: "Timer", model: "Timer",
            pictureURL: "http://1.bp.blogspot.com/-MdBqmg8E37w/TWLYK_qZ0kI/AAAAAAAAC4c/d01Ltx9HxPs/s1600/timer-icon%255B1%255D.gif",
            apiURL: "${ConfigurationHolder.config.timer.url}/api",
            widgetURL: "${ConfigurationHolder.config.timer.url}/widget",
            applicationId: "timer", applicationSecretKey: "secret",
            needExternalConfiguration: false, multipleInstancesPerUserAllowed: false,
            events: ["alarm"]))

    // TODO move this bean to timerlights fixtures
    def portalLightsInstanceBean = add_thing(new SocialThing(name: "Emulated Lights", vendor: "Lights", make: "Lights", model: "Lights",
            pictureURL: "http://keetsa.com/blog/wp-content/uploads/2008/06/geobulb-led-light-bulb-warm.jpg",
            apiURL: "${ConfigurationHolder.config.emulated_lights.url}/api", widgetURL: "${ConfigurationHolder.config.emulated_lights.url}/widget",
            applicationId: "emulatedlights", applicationSecretKey: "secret", needExternalConfiguration: false,
            multipleInstancesPerUserAllowed: false, actions: ["light1:ON", "light1:OFF", "light2:ON", "light2:OFF", "light3:ON", "light3:OFF"])
    )

    def portalBrainInstanceBean = add_thing(new SocialThing(name: "Swot brain", vendor: "Norlander", make: "Mrazek", model: "Peter",
            pictureURL: "http://www.gotowallpapers.com/wallpapers/allimg/c110907/131535F4F5U0-413U.jpg",
            apiURL: "${ConfigurationHolder.config.brain.url}/api", widgetURL: "${ConfigurationHolder.config.brain.url}/widget",
            applicationId: "swot-brain", applicationSecretKey: "secret", needExternalConfiguration: true, multipleInstancesPerUserAllowed: false))

    portalTimerInstance(FixtureWrapper, fixture: portalTimerInstanceBean)
    portalLightsInstance(FixtureWrapper, fixture: portalLightsInstanceBean)
    portalBrainInstance(FixtureWrapper, fixture: portalBrainInstanceBean)


    add_thing(new SocialThing(
            name: "alarmclock",
            vendor: "Ericsson",
            make: "Alarm",
            model: "Clock",
            pictureURL: "http://blogs.villagevoice.com/runninscared/alarm-clock-ringing.gif",
            apiURL: "${ConfigurationHolder.config.alarmclock.url}/api",
            widgetURL: "${ConfigurationHolder.config.alarmclock.url}/widget",
            applicationId: "alarmclock",
            applicationSecretKey: "secret",
            events: ["alarmSet", "alarmTriggered"],
            needExternalConfiguration: true,
            multipleInstancesPerUserAllowed: true))

    add_thing(new SocialThing(
            name: "Camera",
            vendor: "Norlander",
            make: "Mrazek",
            model: "Peter",
            pictureURL: "http://www.rbc.edu/library/assets/images/camera.gif",
            apiURL: "${ConfigurationHolder.config.testserver.url}/camera/api",
            widgetURL: "${ConfigurationHolder.config.testserver.url}/camera/widget",
            applicationId: "camera",
            applicationSecretKey: "secret",
            actions: ["getURL"],
            needExternalConfiguration: true,
            multipleInstancesPerUserAllowed: false))

    add_thing(new SocialThing(
            name: "Homelights",
            vendor: "Norlander",
            make: "Mrazek",
            model: "Peter",
            pictureURL: "http://www.best-of-web.com/_images_300/Light_Bulb_Icon_100522-203812-380042.jpg",
            apiURL: "${ConfigurationHolder.config.testserver.url}/homelights2/api",
            widgetURL: "${ConfigurationHolder.config.testserver.url}/homelights2/widget",
            applicationId: "homelights",
            applicationSecretKey: "secret",
            needExternalConfiguration: true,
            multipleInstancesPerUserAllowed: false))

    add_thing(new SocialThing(
            name: "homesecurity",
            vendor: "Norlander",
            make: "Mrazek",
            model: "Peter",
            pictureURL: "http://www.a1alarmmonitoring.com/images/stories/home-security-systems.jpg",
            apiURL: "${ConfigurationHolder.config.testserver.url}/homesecurity/api",
            widgetURL: "${ConfigurationHolder.config.testserver.url}/homesecurity/widget",
            events: ["arm", "disarm"],
            applicationId: "homesecurity",
            applicationSecretKey: "secret",
            needExternalConfiguration: true,
            multipleInstancesPerUserAllowed: false))

    add_thing(new SocialThing(
            name: "radio",
            vendor: "Norlander",
            make: "Mrazek",
            model: "Peter",
            pictureURL: "/radio/Braun-TP-1.jpg",
            apiURL: "${ConfigurationHolder.config.testserver.url}/homesecurity/api",
            widgetURL: "${ConfigurationHolder.config.testserver.url}/homesecurity/widget",
            actions: ["fadeTo_0", "fadeTo_25", "fadeTo_50", "fadeTo_75", "fadeTo_100"],
            applicationId: "radio",
            applicationSecretKey: "secret",
            needExternalConfiguration: true,
            multipleInstancesPerUserAllowed: false))

    add_thing(new SocialThing(
            name: "emulatedhomesecurity",
            vendor: "Norlander",
            make: "Mrazek",
            model: "Peter",
            pictureURL: "http://www.a1alarmmonitoring.com/images/stories/home-security-systems.jpg",
            apiURL: "${ConfigurationHolder.config.emulated_home_security.url}/api",
            widgetURL: "${ConfigurationHolder.config.emulated_home_security.url}/widget",
            applicationId: "emulatedhomesecurity",
            applicationSecretKey: "secret",
            needExternalConfiguration: false,
            multipleInstancesPerUserAllowed: false))
}
post {

}