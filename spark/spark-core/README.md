
# Wrzucenie na serwer

~~~
scp -r $HADOOP_PROJECT/spark/spark-core/target/spark-core-1.0-SNAPSHOT-job.jar sandbox://tmp
~~~

# Uruchomienie jar'a

~~~
export SPARK_MAJOR_VERSION=2
spark-submit --master yarn --deploy-mode cluster --num-executors 3 --class pl.com.sages.spark.MovieGenres /tmp/spark-core-1.0-SNAPSHOT-job.jar
spark-submit --master yarn --deploy-mode cluster --num-executors 3 --class pl.com.sages.spark.SparkPairRddBasic /tmp/spark-core-1.0-SNAPSHOT-job.jar
spark-submit --master yarn --deploy-mode cluster --num-executors 3 --class pl.com.sages.spark.SparkRddBasic /tmp/spark-core-1.0-SNAPSHOT-job.jar
spark-submit --master yarn --deploy-mode cluster --num-executors 3 --class pl.com.sages.spark.WordCount /tmp/spark-core-1.0-SNAPSHOT-job.jar

hdfs dfs -ls /user/sages/wyniki/spark/
hdfs dfs -cat /user/sages/wyniki/spark/part-00000
~~~