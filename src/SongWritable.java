import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;
import org.apache.hadoop.io.WritableComparable;

public class SongWritable extends SongInfo implements WritableComparable<SongWritable>
{
    public SongWritable()
    {
        super();
    }

    public SongWritable(String path) throws Exception
    {
        super(path);
    }

    public int hashCode()
    {
        return artistName.hashCode() * 1637 + songTitle.hashCode();
    }

    private void writeString(DataOutput out, String s) throws IOException
    {
        if (s != null)
            out.writeUTF(s);
        else
            out.writeUTF("");
    }

    @Override
    public void write(DataOutput out) throws IOException
    {
        writeString(out,filename);
        writeString(out,artistName);
        writeString(out,artistID);
        if (similarArtists != null) {
            out.writeInt(similarArtists.length);
            for (int i = 0; i < similarArtists.length; i++)
                writeString(out,similarArtists[i]);
        } else {
            out.writeInt(0);
        }
        writeString(out,songTitle);
        writeString(out,songID);
        writeString(out,albumName);
        out.writeInt(year);
        out.writeDouble(loudness);
        out.writeDouble(songHotness);
        out.writeDouble(artistHotness);
        out.writeDouble(tempo);
    }

    private String readString(DataInput in) throws IOException
    {
        return in.readUTF();
    }

    @Override
    public void readFields(DataInput in) throws IOException
    {
        filename = readString(in);
        artistName = readString(in);
        artistID = readString(in);
        int len = in.readInt();
        if (len > 0) {
            similarArtists = new String[len];
            for (int i = 0; i < similarArtists.length; i++)
                similarArtists[i] = readString(in);
        } else {
            similarArtists = null;
        }
        songTitle = readString(in);
        songID = readString(in);
        albumName = readString(in);
        year = in.readInt();
        loudness = in.readDouble();
        songHotness = in.readDouble();
        artistHotness = in.readDouble();
        tempo = in.readDouble();
    }

    public boolean compareStrings(String first, String second)
    {
        if ((first == null) && (second == null))
            return true;
        if (((first == null) && (second != null)) || ((first != null) && (second == null)))
            return false;
        return first.equals(second);
    }

    public boolean equals(SongWritable other)
    {
        if (other == null)
            return false;
        if (! compareStrings(songTitle,other.songTitle))
            return false;
        if (! compareStrings(artistName,other.artistName))
            return false;
        if (! compareStrings(songID,other.songID))
            return false;
        if (! compareStrings(albumName,other.albumName))
            return false;
        return true;
    }

    public int compareTo(SongWritable other)
    {
        int results = artistName.compareTo(other.artistName);
        if (results != 0) return results;
        results = songTitle.compareTo(other.songTitle);
        if (results != 0) return results;
        results = albumName.compareTo(other.albumName);
        if (results != 0) return results;
        results = songID.compareTo(other.songID);
        return results;
    }
}
