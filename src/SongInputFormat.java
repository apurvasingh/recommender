import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.RecordReader;

public class SongInputFormat extends FileInputFormat<Text,SongWritable>
{
    @Override
    protected boolean isSplitable(FileSystem fs, Path filename)
    {
        return false;
    }

    @Override
    public RecordReader<Text,SongWritable> getRecordReader(InputSplit split, JobConf jconf, Reporter reporter)
        throws IOException
    {
        return new SongReader((FileSplit)split, jconf);
    }
}
