import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class SongDoopReducer extends Reducer<IntWritable,SongWritable,IntWritable,Text>
{
    @Override
    public void reduce(IntWritable key, Iterable<SongWritable> values, Context context)
    {
        for (IntWritable value : values)
            context.write(key, new Text(value.toString()));
    }
}
