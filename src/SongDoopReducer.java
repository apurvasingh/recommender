import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.MapReduceBase;

public class SongDoopReducer extends MapReduceBase implements Reducer<IntWritable,Text,IntWritable,Text>
{
    @Override
    public void reduce(IntWritable key, Iterable<Text> values, Context context)
        throws IOException, InterruptedException
    {
        for (Text value : values)
            if (key.get() < 10)
                context.write(key, value);
    }
}
