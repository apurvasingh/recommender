import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class SongDoopMapper extends Mapper<Text,SongWritable,IntWritable,SongWritable>
{
    private SongWritable songsToMatch[] = new SongWritable[3];

    @Override
    public void setup(Context context)
    {
        for (int i = 0; i < songsToMatch.length; i++) {
            String songFilename = context.getConfiguration().get("song" + (i + 1));
            if (songFilename != null) {
                try {
                    songsToMatch[i] = new SongWritable(songFilename,context.getConfiguration());
                } catch (Exception e) {
                    // should probably be logged... for now, just skip
                }
            }
        }
    }

    @Override
    public void map(Text key, SongWritable value, Context context)
        throws IOException, InterruptedException
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
            context.write(new IntWritable((int)similarity),value);
        }
    }
}
