#! /bin/bash
echo "creating db named ... "$USER"_DB"
createdb -h localhost -p 5432 $USER"_DB"
pg_ctl status

echo "Copying csv files ... "
sleep 1
cp ../data/*.csv /home/$USER/myDB/data/.

echo "Initializing tables .. "
sleep 1
psql -h localhost -p 5432 $USER"_DB" < ../sql/create.sql
