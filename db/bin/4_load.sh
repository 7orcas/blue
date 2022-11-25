echo '>>>>> load base tables'
export PGPASSWORD='7o'; psql -h localhost -p 5432 -U postgres -W blue < /media/jarvisting/Jarvis/projects/blue/db/40_loadBase.sql
echo '>>>>> load labels'
export PGPASSWORD='7o'; psql -h localhost -p 5432 -U postgres -W blue < /media/jarvisting/Jarvis/projects/blue/db/41_loadLabels.sql
echo '>>>>> create app tables'
export PGPASSWORD='7o'; psql -h localhost -p 5432 -U postgres -W blue < /media/jarvisting/Jarvis/projects/blue/db/45_loadApp.sql