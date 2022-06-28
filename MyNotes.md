

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
Run dev server: [Cmd+~] npm start
React icons: npm i react-icons
Check out the context api for accessing state

run : npm run build   to create static index

ran sudo npm i -g serve   to globally install the web server (I guess node)
To run static: serve -s build -p 8000

Part 2 tutorial
run npm i json-server  to install mock server. Need to add server script ("server": "json-server --watch db.json --port 5000") npm run server
run npm i react-router-dom  to install router

Tutorial 01
run npx json-server -p 3500 -w data/db.json (npx won't install as a dependency for the project - this is better, p=port w=watch)
localStorage.getItem('shoppingList')) - local storage on the browser could be useful
run npm i react-router-dom -S
run npm i data-fns   installs date functions
npm i axios    better then fetch
npm i easy-peasy     a lightweight utility over Redux


Deployment 02tutb

XXX npm install --save-dev gulp gulp-inline-source gulp-replace    for bundling build into one file
npm run build
mvn install
cp to deployments directory
XXX "homepage": "/blue",


*** styled-components for css stuff



