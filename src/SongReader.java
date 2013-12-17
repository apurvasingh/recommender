import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.RecordReader;

public class SongReader implements RecordReader<Text,SongWritable>
{
    private FileSplit fsplit;
    private Configuration conf;
    private boolean done = false;

    public SongReader(FileSplit fsplit, Configuration conf) throws IOException
    {
        this.fsplit = fsplit;
        this.conf = conf;
    }

    @Override
    public Text createKey()
    {
        return new Text(fsplit.getPath().toString());
    }

    @Override
    public SongWritable createValue()
    {
        done = true;
        return new SongWritable(fsplit.getPath().toString());
    }

    @Override
    public void close() throws IOException
    {
    }
}
