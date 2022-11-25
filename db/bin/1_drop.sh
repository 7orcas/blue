echo '>>>>> drop app tables'
psql -h localhost -p 5432 -U postgres -d blue < /media/jarvisting/Jarvis/projects/blue/db/15_dropApp.sql
echo '>>>>> drop base tables'
psql -h localhost -p 5432 -U postgres -d blue < /media/jarvisting/Jarvis/projects/blue/db/10_dropBase.sql