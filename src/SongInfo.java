public class SongInfo
{
    protected String artistName;
    protected String songTitle;
    protected String songID;
    protected String albumName;
    protected int year; // year this song came out _on_this_album_
    protected double danceability;
    protected double energy;
    protected double loudness;
    protected double songHotness;
    protected double tempo; // in bpm

    public String getArtistName()
    {
        return artistName;
    }
    public void setArtistName(String artistName)
    {
        this.artistName = artistName;
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

    public double getEnergy()
    {
        return energy;
    }
    public void setEnergy(double energy)
    {
        this.energy = energy;
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

    public double getTempo()
    {
        return tempo;
    }
    public void setTempo(double tempo)
    {
        this.tempo = tempo;
    }

    public String toString()
    {
        return "Song[" + songTitle + "] by [" + artistName + "] on [" + albumName + "] -> " + songID;
    }
}
