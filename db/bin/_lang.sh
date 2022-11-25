echo '>>>>> delete labels'
psql -h localhost -p 5432 -U postgres -d blue < /media/jarvisting/Jarvis/projects/blue/db/21_deleteLabels.sql >/dev/null
echo '>>>>> load labels'
psql -h localhost -p 5432 -U postgres -d blue < /media/jarvisting/Jarvis/projects/blue/db/41_loadLabels.sql >/dev/null