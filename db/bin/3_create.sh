echo '>>>>> create base tables'
psql -h localhost -p 5432 -U postgres -d blue < /media/jarvisting/Jarvis/projects/blue/db/30_createBase.sql
echo '>>>>> create app tables'
psql -h localhost -p 5432 -U postgres -d blue < /media/jarvisting/Jarvis/projects/blue/db/35_createApp.sql