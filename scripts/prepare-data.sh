#!/bin/sh

# czyszczenie
cd $HADOOP_DATA
pwd
rm -rf lektury
rm -rf movielens
rm -rf mysql
rm -rf spark-data

# Lektury
cd $HADOOP_DATA
wget -O lektury.tar.gz https://github.com/radoslawszmit/BigDataTrainingDataset/blob/master/lektury.tar.gz?raw=true
tar -zxf lektury.tar.gz
rm lektury.tar.gz

# MovieLens Dataset: https://grouplens.org/datasets/movielens/
cd $HADOOP_DATA
mkdir movielens
cd movielens
#wget http://files.grouplens.org/datasets/movielens/ml-latest.zip
#unzip ml-latest.zip
#wget http://files.grouplens.org/datasets/movielens/ml-20m.zip
#unzip ml-20m.zip
wget http://files.grouplens.org/datasets/movielens/ml-10m.zip
unzip ml-10m.zip
rm ml-10m.zip

# Dane z https://dev.mysql.com/doc/index-other.html
cd $HADOOP_DATA
mkdir mysql
cd mysql
#wget http://downloads.mysql.com/docs/world.sql.gz
#wget http://downloads.mysql.com/docs/world_x-db.tar.gz
#wget http://downloads.mysql.com/docs/menagerie-db.tar.gz
wget http://downloads.mysql.com/docs/sakila-db.tar.gz
tar -zxf sakila-db.tar.gz
rm sakila-db.tar.gz

#Spark sample data
cd $HADOOP_DATA
wget -O spark-data.tar.gz https://github.com/radoslawszmit/BigDataTrainingDataset/raw/master/spark-data.tar.gz
tar -zxf spark-data.tar.gz
rm spark-data.tar.gz

# Podsumowanie

du -s -h $HADOOP_DATA
tree -L 2 $HADOOP_DATA
