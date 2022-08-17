Maven
=====

Created settings.xml in /home/jarvisting/.m2/settings.xml
Changed local repo to /media/jarvisting/Jarvis/repository

Added auto-deploy-exploded="false"  to <deployment-scanner path="deployments"...   to prevent war being unpacked


JBoss
=====
Create JaxrsActivator.java  http://www.mastertheboss.com/jboss-frameworks/resteasy/resteasy-tutorial/
export JBOSS_HOME=/opt/wildfly/  or whatever


CORS
----
Development ONLY
Create CORSFilter.java   https://stackoverflow.com/questions/31902612/access-control-allow-origin-to-wildfly-8-1-0-configuration
Set Access-Control-Allow-Origin to React Dev server, ie http://localhost:3001  (can't use wildcards)
axios.get('some api url', {withCredentials: true});
Good read: https://stackoverflow.com/questions/46288437/set-cookies-for-cross-origin-requests

XX DEL undertow-handlers.conf: path(/blue)->samesite-cookie(mode=None, enable-client-checker=false)


Caching
-------
Using Infinispan
Cache classes are defined in the module
See https://infinispan.org/docs/stable/titles/developing/developing.html

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


Web Client
=========


WebClient
---------
Install
- nodejs
- React Developer Tools for browser
- Extension ES7 React/Redux/React-Native/JS snippets
- Extension pack for Java

VisualCodeStudio
----------------
Install VSCode as the editor

ESLint
------
DON'T INSTALL - Needs more work to remove errors that don't exist
Use https://andrebnassis.medium.com/setting-eslint-on-a-react-typescript-project-2021-1190a43ffba
Remove eslintConfig in package.json
npm install eslint --save-dev
npx eslint --init
.. use Airbnb
npm install eslint-import-resolver-typescript --save-dev


Make sure ESLlint extension VSCode is installed
  
Typescript
----------
sudo npm i -g typescript
in project folder: tsc --init (create config file)
- Extension ESLint
- Use tuts/ts/tsconfig.json as configuration file


CSS
---
npm install normalize.css
import 'normalize.css'; (in top js file)
https://nicolasgallagher.com/about-normalize-css/

Install Sass
- VS Code extension: Sass
- VS Code extension: Live Sass Compiler
npm i -D node-sass
npm i -D @types/node-sass

Font Awesome
------------
https://fontawesome.com/docs/web/use-with/react/
https://fontawesome.com/docs/web/add-icons/how-to

npm i --save @fortawesome/fontawesome-svg-core
free:
npm i --save @fortawesome/free-solid-svg-icons
npm i --save @fortawesome/free-regular-svg-icons
react component
npm i --save @fortawesome/react-fontawesome@latest


React
-----
Used npm to install React (created tutorial via npx create-react-app react-task-tracker - took 2 goes as it timed out)
run npm i react-router-dom -S



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

run npm i data-fns   installs date functions
npm i axios    better then fetch  (use with axios.get('some api url', {withCredentials: true}); for CORS)
npm i easy-peasy     a lightweight utility over Redux


Deployment 02tutb

XXX npm install --save-dev gulp gulp-inline-source gulp-replace    for bundling build into one file
npm run build
mvn install
cp to deployments directory
XXX "homepage": "/blue",

*** styled-components for css stuff

React + TypeScript
------------------
npx create-react-app [project name] --template typescript
Install in Visual Studio Code: Typescript React code snippets

War File Packaging
------------------
Copy pom.xml to root  reconfigure as required
Copy web.xml to war/  reconfigure as required
mvn install
Copy war file to wildfly deployment 

npm start
---------
To choose another port change package.json: "start": "PORT=3001 react-scripts start",

Cookies
-------
XXX npm install react-cookie

Components
----------
Select : 
npm i react-select
npm i bootstrap
npm install react-bootstrap bootstrap

Menu
----
https://szhsin.github.io/react-menu
npm install @szhsin/react-menu





