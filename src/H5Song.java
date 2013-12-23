import ncsa.hdf.object.h5.H5File;

/*
 *  A SongInfo subclass with code to read one from an HDF5 file
 */
public class H5Song extends SongInfo
{
    public H5Song()
    {
    }

    public H5Song(String path) throws Exception
    {
        H5File h5 = hdf5_getters.hdf5_open_readonly(path);
        int nSongs = hdf5_getters.get_num_songs(h5);
        if (nSongs > 0) {
            getFromH5(h5);
            setFilename(path);
        }
        hdf5_getters.hdf5_close(h5);
    }

    public H5Song(H5File h5) throws Exception
    {
        getFromH5(h5);
    }

    public void getFromH5(H5File h5) throws Exception
    {
        artistName = hdf5_getters.get_artist_name(h5);
        artistID = hdf5_getters.get_artist_id(h5);
        setSimilarArtists(hdf5_getters.get_similar_artists(h5));
        songTitle = hdf5_getters.get_title(h5);
        songID = hdf5_getters.get_song_id(h5);
        albumName = hdf5_getters.get_release(h5);
        year = hdf5_getters.get_year(h5);
        loudness = hdf5_getters.get_loudness(h5);
        songHotness = hdf5_getters.get_song_hotttnesss(h5);
        artistHotness = hdf5_getters.get_artist_hotttnesss(h5);
        tempo = hdf5_getters.get_tempo(h5);
    }
}
