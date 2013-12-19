import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobConf;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.FileSplit;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Reporter;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class SongInputFormat extends FileInputFormat<Text,SongWritable>
{
    @Override
    protected boolean isSplitable(FileSystem fs, Path filename)
    {
        return false;
    }

    @Override
    public RecordReader<Text,SongWritable> createRecordReader(InputSplit split, TaskAttemptContext context)
        throws IOException
    {
        return new SongReader((FileSplit)split, context);
    }
}
