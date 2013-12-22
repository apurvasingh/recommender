// general hadoop driver imports
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;

// tool-runner related imports
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SongDoopDriver extends Configured implements Tool
{
    public int run(String args[]) throws Exception
    {
        /* Usage: ... SongDoopDriver songDirTop outputDir song1  [song2  [song3]]
                                     args[0]    args[1]   args[2] args[3] args[4]
        */
        Configuration conf = getConf();
        for (int i = 2; (i < args.length) && (i < 5); i++) {
            conf.set("song" + (i - 2), args[i]);
        }

        Job job = new Job(conf);
        job.setJarByClass(SongDoopDriver.class);
        job.setJobName("Find Similar Songs");

        // set input & output directory
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // set map-reduce key/value classes
        job.setInputFormatClass(SongInputFormat.class);
        job.setMapperClass(SongDoopMapper.class);
        job.setReducerClass(SongDoopReducer.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(SongWritable.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);

        boolean ok = job.waitForCompletion(true);
	return ok ? 0 : 1;
    }

    public static void main(String args[]) throws Exception
    {
        int results = ToolRunner.run(new Configuration(), new SongDoopDriver(), args);
        System.exit(results);
    }

}
