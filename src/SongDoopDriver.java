// general hadoop driver imports
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

public class SongDoopDriver
{
    public static void main(String args[]) throws Exception
    {
        /* Usage: ... SongDoopDriver songDirTop outputDir song1  [song2  [song3]]
                                     args[0]    args[1]   args[2] args[3] args[4]
        */
        JobConf conf = new JobConf(SongDoopDriver.class);
        for (int i = 2; (i < args.length) && (i < 5); i++) {
            conf.set("song" + (i - 2), args[i]);
        }

        conf.setJobName("Find Similar Songs");

        // set input & output directory
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        // set map-reduce key/value classes
        // conf.setInputFormat(SongInputFormat.class); // won't work until we rewrite Input Format
        conf.setMapperClass(SongDoopMapper.class);
        conf.setReducerClass(SongDoopReducer.class);
        conf.setMapOutputKeyClass(IntWritable.class);
        conf.setMapOutputValueClass(Text.class);
        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(Text.class);

        JobClient.runJob(conf);
    }
}
