#!/bin/sh
. /etc/profile

#set -x
#按天延迟加载日志数据

#日志目录
LOG_DIR=/root/data

if [ $# == 1 ]; then
   yestoday=$1
else 
   yestoday=`date -d -1days '+%Y%m%d'`
fi

#yestoday="20150828"

cd $LOG_DIR

for line in `ls ${yestoday}*`
do
  echo "loading $line  ......"
  #从文件名里解析日期和小时
  # 2015-08-28
  date=${line:0:4}-${line:4:2}-${line:6:2}
  hour=${line:8:2}
  echo $date
  echo $hour
  
  hive -e "LOAD DATA LOCAL INPATH '$LOG_DIR/$line' OVERWRITE INTO TABLE track_log PARTITION (date='$date',hour='$hour');"
  #echo "LOAD DATA LOCAL INPATH '$LOG_DIR/$line' OVERWRITE INTO TABLE track_log PARTITION (ds='$date',hour='$hour');"
done

