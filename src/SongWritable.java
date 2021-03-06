import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.conf.Configuration;

public class SongWritable extends SongInfo implements WritableComparable<SongWritable>
{
    List<String> similarArtistsList = new ArrayList<String>();

    public SongWritable()
    {
        super();
    }

    public SongWritable(String hdfsFilename, Configuration conf) throws Exception
    {
        BufferedReader reader = FileUtils.getReaderFromFilename(hdfsFilename,conf);
        if (reader != null) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                try {
                    line = line.trim(); // remove trailing/leading whitespace (including newlines)
                    String fields[] = line.split("\t");
                    if ((fields != null) && (fields.length > 1)) {
                        setProperty(fields[0],fields[1]);
                    }
                } catch (Exception e) {
                }
            }
            reader.close();
        }
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
        if (similarArtistsList != null) {
            out.writeInt(similarArtistsList.size());
            for (int i = 0; i < similarArtistsList.size(); i++)
                writeString(out,similarArtistsList.get(i));
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
            similarArtistsList = new ArrayList<String>(len);
            for (int i = 0; i < len; i++)
                similarArtistsList.add(readString(in));
        } else {
            similarArtistsList = null;
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

    @Override
    public String[] getSimilarArtists()
    {
        return similarArtistsList.toArray(new String[1]);
    }

    @Override
    public void setSimilarArtists(String[] similarArtists)
    {
        this.similarArtistsList = new ArrayList<String>();
        for (String artist : similarArtists)
            similarArtistsList.add(artist);
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
