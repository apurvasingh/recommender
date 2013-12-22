import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileUtils
{
    public static getReaderFromFilename(String filename, Configuration conf) throws Exception
    {
        BufferedReader reader = null;
        FileSystem fs = FileSystem.get(URI.create(filename),conf);
        InputStream is= fs.open(new Path(filename));
        reader = new BufferedReader(new InputStreamReader(is));
        return reader;
    }
}
