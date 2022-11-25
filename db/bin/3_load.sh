export PGPASSWORD='7o'; psql -h localhost -p 5432 -U postgres -W blue < /media/jarvisting/Jarvis/projects/blue/db/30_loadBase.sql
export PGPASSWORD='7o'; psql -h localhost -p 5432 -U postgres -W blue < /media/jarvisting/Jarvis/projects/blue/db/31_loadLabels.sql
export PGPASSWORD='7o'; psql -h localhost -p 5432 -U postgres -W blue < /media/jarvisting/Jarvis/projects/blue/db/35_loadApp.sql