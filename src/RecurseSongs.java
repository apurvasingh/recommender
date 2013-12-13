import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ncsa.hdf.object.h5.H5File;

public class RecurseSongs
{
    private String startDir = null;
    private String extension = null;
    private Map<String,List<String>> songsByArtist = new HashMap<String,List<String>>();

    public RecurseSongs(String startDir, String extension)
    {
        this.startDir = startDir;
        this.extension = extension;
    }

    public void addSong(String artist, String song)
    {
        List<String> songList = songsByArtist.get(artist);
        if (songList == null) {
            songList = new ArrayList<String>();
            songsByArtist.put(artist,songList);
        }
        songList.add(song);
    }

    public void readSong(String path)
    {
        try {
            H5File h5 = hdf5_getters.hdf5_open_readonly(path);
            int nSongs = hdf5_getters.get_num_songs(h5);
            if (nSongs > 0) {
                String artist = hdf5_getters.get_artist_name(h5);
                String songTitle = hdf5_getters.get_title(h5);
                addSong(artist,songTitle);
            }
            hdf5_getters.hdf5_close(h5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recurseDir(File d)
    {
        try {
            if ((d != null) && d.exists() && d.isDirectory()) {
                for (File f : d.listFiles()) {
                    if (f.isDirectory())
                        recurseDir(f);
                    else if (f.toString().endsWith(extension))
                        readSong(f.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startRecurse()
    {
        try {
            File f = new File(startDir);
            if (f.exists()) {
                if (f.isDirectory())
                    recurseDir(f);
                else
                    readSong(f.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printSongs()
    {
        for (String artist : songsByArtist.keySet()) {
            System.out.println("Artist: " + artist);
            List<String> songList = songsByArtist.get(artist);
            if (songList != null)
                for (String song : songList)
                    System.out.println("    Song: " + song);
        }
    }

    public static void main(String args[])
    {
        if (args.length < 1) {
        } else {
            String ext = ".h5";
            if (args.length > 1)
                ext = args[1];
            RecurseSongs rs = new RecurseSongs(args[0],ext);
            rs.startRecurse();
            rs.printSongs();
        }
    }
}
