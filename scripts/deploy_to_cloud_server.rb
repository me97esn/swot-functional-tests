#!/usr/bin/ruby
require 'rubygems'
require 'net/ssh'
require 'net/sftp'

p "==== building and deploying war file to test server ===="

p "package grails projects"
#grails_apps =["Timer","EmulatedLights"]
#grails_apps.each{ |folder|
#    system "cd /home/emil/projects/swotapps3/#{folder};tar -zcvf #{folder}.tar.gz #{folder}/"
#}

p "building maven projects"
apps =  {
            "integration-api.war" =>"swot2/applicationintegrationapi",
            "admin-portal.war" =>"swot2/systemadminportal",
            "client-api.war" =>"swot2/clientdeveloperapi",
            "client-portal.war" =>"swot2/clientdeveloperhtmlportal",
            "integration-api.war" =>"swot2/applicationintegrationapi",
            "system-api.war" =>"swot2/systemadminapi",
            "brain.war" =>"swotapps3/swot-brain",
            "mobile-portal.war" =>"swot2/mobileportal",
            "alarmclock.war" =>"swotapps3/alarmclock",
            "camera.war" =>"swotapps3/camera",
            "homelights2.war" =>"swotapps3/homelights",
            "homesecurity.war" =>"swotapps3/homesecurity",
            "radio.war" =>"swotapps3/radio",
        }
apps.each{|war_name, project_path|
    path = "/home/emil/projects/#{project_path}"
    p "building project in path #{path}"
    system "cd #{path};mvn -Dmaven.test.skip=true clean install -P swottest-e3ecloud-config"
}

# Add grails apps to deployment
#apps["Timer.war"]="swotapps3/Timer"
#apps["EmulatedLights.war"]="swotapps3/EmulatedLights"

p "stop tomcat and clean webapps dir"
Net::SSH.start('swottest.e3ecloud.com', 'root', :keys => ["#{ENV['HOME']}/swottest.key"]) do |ssh|
	puts ssh.exec! "
/etc/init.d/tomcat6 stop
rm -rf /var/lib/tomcat6/webapps/*
"
p "upload files, this may take a while since every war file is apx 30 MByte..."
    ssh.sftp.connect do |sftp|
        #grails_apps.each{|folder|
        #    p "uploading #{folder}..."
        #    sftp.upload!("/home/emil/projects/swotapps3/#{folder}", "/root")
        #        ssh.exec! "tar -xvf #{folder}.tar.gz"
        #}

        apps.each{|war_name, project_path|
            p "uploading #{war_name} in #{project_path}..."
            sftp.upload!("/home/emil/projects/#{project_path}/target/#{war_name}", "/var/lib/tomcat6/webapps/#{war_name}")
            p "done uploading #{war_name}"
        }
    end
    ssh.exec! "/etc/init.d/tomcat6 start"
end

p "script completed successfully"