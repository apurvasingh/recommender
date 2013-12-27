import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class SongReader extends RecordReader<Text,SongWritable>
{
    private FileSplit fsplit;
    private TaskAttemptContext context;
    private boolean done = false;
    private SongWritable song = null;
    private Text filename = null;

    public SongReader(FileSplit fsplit, TaskAttemptContext context) throws IOException
    {
        this.fsplit = fsplit;
        this.context = context;
    }

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context)
        throws IOException, InterruptedException
    {
    }

    @Override
    public Text getCurrentKey()
    {
        if (done) {
            if (filename == null)
                filename = new Text(fsplit.getPath().toString());
            return filename;
        } else
            return null;
    }

    @Override
    public SongWritable getCurrentValue()
    {
        if (done)
            return song;
        else
            return null;
    }

    @Override
    public long getPos() throws IOException
    {
        return done ? fsplit.getLength() : 0;
    }

    @Override
    public float getProgress() throws IOException
    {
        return done ? 1.0f : 0.0f;
    }

    @Override
    public boolean nextKeyValue() throws IOException
    {
        if (! done) {
            try {
                String filename = fsplit.getPath().toString();
                if (filename.endsWith(".song"))
                    song = new SongWritable(filename,context.getConfiguration());
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
