import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class SongDoopMapper extends MapReduceBase implements Mapper<Text,SongWritable,IntWritable,Text>
{
    private SongWritable songsToMatch[] = new SongWritable[3];

    @Override
    public void configure(JobConf job)
    {
        for (int i = 0; i < songsToMatch.length; i++) {
            String songFilename = job.get("song" + (i + 1));
            if (songFilename != null) {
                try {
                    songsToMatch[i] = new SongWritable(songFilename,null);
                } catch (Exception e) {
                    // should probably be logged... for now, just skip
                }
            }
        }
    }

    @Override
    public void map(Text key, SongWritable value, OutputCollector<IntWritable,Text> output,
                    Reporter reporter)
        throws IOException
    {
        int searchCount = 0;
        double similarity = 0.0;
        for (int i = 0; i < songsToMatch.length; i++) {
            if (songsToMatch[i] != null) {
                similarity += value.similarityScore(songsToMatch[i]);
                searchCount++;
            }
        }
        if (searchCount > 0) {
            output.collect(new IntWritable((int)similarity),new Text(value.toString()));
        }
    }
}
