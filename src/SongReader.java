import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.RecordReader;

public class SongReader implements RecordReader<Text,SongWritable>
{
    private FileSplit fsplit;
    private Configuration conf;
    private boolean done = false;
    private SongWritable song = null;

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
    public boolean next(Text key, SongWritable value) throws IOException
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
