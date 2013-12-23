import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class SongInfo
{
    protected String filename;
    protected String artistName;
    protected String artistID;
    // protected String similarArtists[];
    protected String songTitle;
    protected String songID;
    protected String albumName;
    protected int year; // year this song came out _on_this_album_
    protected double loudness;
    protected double songHotness;
    protected double artistHotness;
    protected double tempo; // in bpm
    protected List<String> similarArtistsList = new ArrayList<String>();

    public SongInfo()
    {
    }

    public SongInfo(String filename) throws Exception
    {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
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

    public void setProperty(String propName, String propValue)
    {
        if (propName.equals("artist-id"))
            artistID = propValue;
        else if (propName.equals("artist-name"))
            artistName = propValue;
        else if (propName.equals("song-id"))
            songID = propValue;
        else if (propName.equals("song-title"))
            songTitle = propValue;
        else if (propName.equals("album-name"))
            albumName = propValue;
        else if (propName.equals("filename"))
            filename = propValue;
        else if (propName.equals("similar-artist"))
            similarArtistsList.add(propValue);
        else if (propName.equals("year"))
            year = getIntProperty(propValue);
        else if (propName.equals("loudness"))
            loudness = getDoubleProperty(propValue);
        else if (propName.equals("tempo"))
            tempo = getDoubleProperty(propValue);
        else if (propName.equals("song-hotness"))
            songHotness = getDoubleProperty(propValue);
        else if (propName.equals("artist-hotness"))
            artistHotness = getDoubleProperty(propValue);
    }

    public String getArtistName()
    {
        return artistName;
    }
    public void setArtistName(String artistName)
    {
        this.artistName = artistName;
    }

    public String getArtistID()
    {
        return artistID;
    }
    public void setArtistID(String artistID)
    {
        this.artistID = artistID;
    }

    public String[] getSimilarArtists()
    {
        return similarArtistsList.toArray(new String[1]);
    }
    public void setSimilarArtists(String[] similarArtists)
    {
        this.similarArtistsList = new ArrayList<String>();
        for (String artist : similarArtists)
            similarArtistsList.add(artist);
    }

    public String getSongTitle()
    {
        return songTitle;
    }
    public void setSongTitle(String songTitle)
    {
        this.songTitle = songTitle;
    }

    public String getSongID()
    {
        return songID;
    }
    public void setSongID(String songID)
    {
        this.songID = songID;
    }

    public String getAlbumName()
    {
        return albumName;
    }
    public void setAlbumName(String albumName)
    {
        this.albumName = albumName;
    }

    public int getYear()
    {
        return year;
    }
    public void setYear(int year)
    {
        this.year = year;
    }

    public double getLoudness()
    {
        return loudness;
    }
    public void setLoudness(double loudness)
    {
        this.loudness = loudness;
    }

    public double getSongHotness()
    {
        return songHotness;
    }
    public void setSongHotness(double songHotness)
    {
        this.songHotness = songHotness;
    }

    public double getArtistHotness()
    {
        return artistHotness;
    }
    public void setArtistHotness(double artistHotness)
    {
        this.artistHotness = artistHotness;
    }

    public double getTempo()
    {
        return tempo;
    }
    public void setTempo(double tempo)
    {
        this.tempo = tempo;
    }

    public String getFilename()
    {
        return filename;
    }
    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String toString()
    {
        return "Song[" + songTitle + "] by [" + artistName + "] on [" + albumName +
            "] -> " + filename;
    }

    public boolean isArtistSimilar(String otherArtist)
    {
        if (otherArtist != null) {
            if (otherArtist.equals(artistID))
                return true;
            if (similarArtistsList != null)
                for (String artist : similarArtistsList)
                    if (otherArtist.equals(artist))
                        return true;
        }
        return false;
    }

    // produces a score for which similar songs have a score close to 0
    public double similarityScore(SongInfo other)
    {
        double score = 0;
        score += Math.abs(tempo - other.tempo);
        score += Math.abs(loudness - other.loudness);
        if (isArtistSimilar(other.artistID))
            score /= 20;
        return score;
    }

    private int getIntProperty(String strValue)
    {
        try {
            return Integer.parseInt(strValue);
        } catch (Exception e) {
            return 0;
        }
    }

    private double getDoubleProperty(String strValue)
    {
        try {
            return Double.parseDouble(strValue);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
