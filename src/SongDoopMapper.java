import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class SongDoopMapper extends Mapper<Text,SongWritable,IntWritable,SongWritable>
{
    @Override
    public void map(Text key, SongWritable value, Context context)
        throws IOException, InterruptedException
    {
        int searchCount = 0;
        Double similarity = 0;
        for (int i = 0; i < 3; i++) {
            SongWritable song = (SongWritable)context.getConfiguration().get("song" + (i + 1));
            if (song != null) {
                similarity += value.similarityScore(song);
                searchCount++;
            }
        }
        if (searchCount > 0) {
            context.write((int)similarity,value);
        }
    }
}
