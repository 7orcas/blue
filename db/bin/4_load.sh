echo '>>>>> load base tables'
psql -h localhost -p 5432 -U postgres -d blue < /media/jarvisting/Jarvis/projects/blue/db/40_loadBase.sql >/dev/null
echo '>>>>> load labels'
psql -h localhost -p 5432 -U postgres -d blue < /media/jarvisting/Jarvis/projects/blue/db/41_loadLabels.sql >/dev/null
echo '>>>>> load app tables'
psql -h localhost -p 5432 -U postgres -d blue < /media/jarvisting/Jarvis/projects/blue/db/45_loadApp.sql >/dev/null
echo '>>>>> load users'
psql -h localhost -p 5432 -U postgres -d blue < /media/jarvisting/Jarvis/projects/blue/db/48_loadUsers.sql >/dev/null
