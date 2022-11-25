echo '>>>>> drop app tables'
export PGPASSWORD='7o'; psql -h localhost -p 5432 -U postgres -W blue < /media/jarvisting/Jarvis/projects/blue/db/15_dropApp.sql
echo '>>>>> drop base tables'
export PGPASSWORD='7o'; psql -h localhost -p 5432 -U postgres -W blue < /media/jarvisting/Jarvis/projects/blue/db/10_dropBase.sql