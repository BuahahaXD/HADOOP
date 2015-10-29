package pl.com.sages.hbase.mapred.users.count;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.junit.Test;
import pl.com.sages.hbase.api.dao.UsersDao;
import pl.com.sages.hbase.api.util.HBaseUtil;
import pl.com.sages.hbase.api.loader.UserDataFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class CountUsersExternalTest {

    @Test
    public void shouldRunMapReduce() throws Exception {
        //given
        Configuration configuration = HBaseConfiguration.create();

        HBaseUtil.recreateTable(UsersDao.TABLE, Bytes.toString(UsersDao.CF));
        UserDataFactory.insertTestData();

        // map reduce
        Job job = new Job(configuration, "Count Users");
        job.setJarByClass(CountUsersMapper.class);

        Scan scan = new Scan();
        scan.setCaching(500);        // 1 is the default in Scan, which will be bad for MapReduce jobs
        scan.setCacheBlocks(false); // don't set to true for MR jobs
        scan.addColumn(UsersDao.CF, UsersDao.FORENAME);

        // mapper
        TableMapReduceUtil.initTableMapperJob(
                UsersDao.TABLE,
                scan,
                CountUsersMapper.class,
                ImmutableBytesWritable.class,
                Result.class,
                job);
        job.setOutputFormatClass(NullOutputFormat.class);
        job.setNumReduceTasks(0);

        //when
        boolean succeeded = job.waitForCompletion(true);

        //then
        assertThat(succeeded).isTrue();
        assertThat(job.getCounters().findCounter(CountUsersMapper.Counters.USER_COUNT).getValue()).isGreaterThan(99);
    }

}
