#! /bin/bash
folder=/home/$USER
export PGDATA=$folder/myDB/data
export PGSOCKETS=$folder/myDB/sockets

echo $folder

#Clear folder
# rm -rf $folder

#Initialize folders
#cp ../data/*.csv $folder/myDB/data

# Initialize DB
# initdb

sleep 1
#Start folder
# export PGPORT=9998
pg_ctl -o "-c unix_socket_directories=$PGSOCKETS" -D $PGDATA -l $folder/logfile start
