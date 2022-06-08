

Arquillian
----------

Trying to run ejb-in-war quickstart to use Arquillian test
Whilst using     mvn clean install wildfly:deploy 
got error: Could not find artifact sun.jdk:jconsole:jar:jdk at specified path /usr/lib/jvm/java-11-openjdk-amd64/../lib/jconsole.jar

Tried solutions from https://stackoverflow.com/questions/33236195/missing-artifact-sun-jdkjconsolejarjdk
Tried the exclusion tags
Downloaded and installed jconsole.jar

Got running after doing: https://nickhumphreyit.blogspot.com/2018/09/solved-jboss-javaee-multi-mvn-clean.html
Changed the version.wildfly.maven.plugin

Use mvn clean test -Parq-wildfly-managed
to run as managed (ie a container is created at test time). 
Can't get remote to work atm.


Maven
-----

Created settings.xml in /home/jarvisting/.m2/settings.xml
Changed local repo to /media/jarvisting/Jarvis/repository


React
-----

Prereq
- nodejs installed
- React Developer Tools for browser
- VSCode as the editor
  - Add extension ES7 React/Redux/React-Native/JS snippets
Used npm to install React (created tutorial via npx create-react-app react-task-tracker - took 2 goes as it timed out)


