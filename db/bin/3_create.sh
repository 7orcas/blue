echo '>>>>> create base tables'
export PGPASSWORD='7o'; psql -h localhost -p 5432 -U postgres -W blue < /media/jarvisting/Jarvis/projects/blue/db/30_createBase.sql
echo '>>>>> create app tables'
export PGPASSWORD='7o'; psql -h localhost -p 5432 -U postgres -W blue < /media/jarvisting/Jarvis/projects/blue/db/35_createApp.sql