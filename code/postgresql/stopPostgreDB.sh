#! /bin/bash
folder=/home/thuanvu
PGSOCKETS=/home/thuanvu/myDB/sockets
PGDATA=/home/thuanvu/myDB/data

pg_ctl -o "-c unix_socket_directories=$PGSOCKETS" -D $PGDATA -l $folder/logfile stop
