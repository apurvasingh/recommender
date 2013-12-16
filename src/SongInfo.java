import ncsa.hdf.object.h5.H5File;

public class SongInfo
{
    protected String filename;
    protected String artistName;
    protected String artistID;
    protected String similarArtists[];
    protected String songTitle;
    protected String songID;
    protected String albumName;
    protected int year; // year this song came out _on_this_album_
    protected double loudness;
    protected double songHotness;
    protected double artistHotness;
    protected double tempo; // in bpm

    public SongInfo()
    {
    }

    public SongInfo(String path) throws Exception
    {
        H5File h5 = hdf5_getters.hdf5_open_readonly(path);
        int nSongs = hdf5_getters.get_num_songs(h5);
        if (nSongs > 0) {
            getFromH5(h5);
            setFilename(path);
        }
        hdf5_getters.hdf5_close(h5);
    }

    public SongInfo(H5File h5) throws Exception
    {
        getFromH5(h5);
    }

    public void getFromH5(H5File h5) throws Exception
    {
        artistName = hdf5_getters.get_artist_name(h5);
        artistID = hdf5_getters.get_artist_id(h5);
        similarArtists = hdf5_getters.get_similar_artists(h5);
        songTitle = hdf5_getters.get_title(h5);
        songID = hdf5_getters.get_song_id(h5);
        albumName = hdf5_getters.get_release(h5);
        year = hdf5_getters.get_year(h5);
        loudness = hdf5_getters.get_loudness(h5);
        songHotness = hdf5_getters.get_song_hotttnesss(h5);
        artistHotness = hdf5_getters.get_artist_hotttnesss(h5);
        tempo = hdf5_getters.get_tempo(h5);
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
        return similarArtists;
    }
    public void setSimilarArtists(String[] similarArtists)
    {
        this.similarArtists = similarArtists;
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
            if (similarArtists != null)
                for (String artist : similarArtists)
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
}
