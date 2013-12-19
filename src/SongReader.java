import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class SongReader extends RecordReader<Text,SongWritable>
{
    private FileSplit fsplit;
    private Configuration context;
    private boolean done = false;
    private SongWritable song = null;

    public SongReader(FileSplit fsplit, TaskAttemptContext context) throws IOException
    {
        this.fsplit = fsplit;
        this.context = context;
    }
 
    public void initialize(InputSplit split, TaskAttemptContext context)
        throws IOException, InterruptedException
    {
    }

    public Text createKey()
    {
        return new Text(fsplit.getPath().toString());
    }

    public SongWritable createValue()
    {
        if (done)
            return song;
        else
            return null;
    }

    public long getPos() throws IOException
    {
        return done ? fsplit.getLength() : 0;
    }

    public float getProgress() throws IOException
    {
        return done ? 1.0f : 0.0f;
    }

    public boolean nextKeyValue() throws IOException
    {
        if (! done) {
            try {
                String filename = fsplit.getPath().toString();
                if (filename.endsWith(".h5"))
                    // this may not work, as hdf5 lib may try to read from normal Unix fs
                    song = new SongWritable(filename);
                else
                    return false;
            } catch (Exception e) {
                // ignore for now... not sure how to handle this
            } finally {
                done = true;
            }
            return true;
        }
        return false;
    }

    @Override
    public void close() throws IOException
    {
    }
}
