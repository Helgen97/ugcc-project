#!/bin/sh
SERVICE_NAME=ugccServ
PATH_TO_JAR=/var/www/ugcc_dn_com__usr/data/www/ugcc.donetsk.ua/ugcc.jar
PID_PATH_NAME=/tmp/ugccServ-pid
case $1 in
start)
  echo "Starting $SERVICE_NAME ..."
  if [ ! -f $PID_PATH_NAME ]; then
    nohup java -jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/ugcc-db -Dspring.datasource.username=ugcc-db -Dspring.datasource.password=_39AE.U7iq!mK4!T -Dlogging.file.name=/var/www/ugcc_dn_com__usr/data/www/ugcc.donetsk.ua/logs/log.log -Dupload.path=/var/www/ugcc_dn_com__usr/data/www/ugcc.donetsk.ua/uploads $PATH_TO_JAR /tmp 2>>/dev/null >>/dev/null &
    echo $! >$PID_PATH_NAME
    echo "$SERVICE_NAME started ..."
  else
    echo "$SERVICE_NAME is already running ..."
  fi
  ;;
stop)
  if [ -f $PID_PATH_NAME ]; then
    PID=$(cat $PID_PATH_NAME)
    echo "$SERVICE_NAME stoping ..."
    kill $PID
    echo "$SERVICE_NAME stopped ..."
    rm $PID_PATH_NAME
  else
    echo "$SERVICE_NAME is not running ..."
  fi
  ;;
restart)
  if [ -f $PID_PATH_NAME ]; then
    PID=$(cat $PID_PATH_NAME)
    echo "$SERVICE_NAME stopping ..."
    kill $PID
    echo "$SERVICE_NAME stopped ..."
    rm $PID_PATH_NAME
    echo "$SERVICE_NAME starting ..."
    nohup java -jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/ugcc-db -Dspring.datasource.username=ugcc-db -Dspring.datasource.password=_39AE.U7iq!mK4!T -Dlogging.file.name=/var/www/ugcc_dn_com__usr/data/www/ugcc.donetsk.ua/logs/log.log -Dupload.path=/var/www/ugcc_dn_com__usr/data/www/ugcc.donetsk.ua/uploads $PATH_TO_JAR /tmp 2>>/dev/null >>/dev/null &
    echo $! >$PID_PATH_NAME
    echo "$SERVICE_NAME started ..."
  else
    echo "$SERVICE_NAME is not running ..."
  fi
  ;;
esac
