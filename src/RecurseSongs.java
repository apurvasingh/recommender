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
    private Map<String,List<SongInfo>> songsByArtist = new HashMap<String,List<SongInfo>>();
    private Map<String,String> artistsByID = new HashMap<String,String>();

    public RecurseSongs(String startDir, String extension)
    {
        this.startDir = startDir;
        this.extension = extension;
    }

    public void addSong(String artist, SongInfo song)
    {
        List<SongInfo> songList = songsByArtist.get(artist);
        if (songList == null) {
            songList = new ArrayList<SongInfo>();
            songsByArtist.put(artist,songList);
        }
        songList.add(song);
    }

    public void readSong(String path)
    {
        try {
            SongInfo song = new SongInfo(path);
            String artist = song.getArtistName();
            addSong(artist,song);
            String artistID = song.getArtistID();
            if ((artist != null) && (artistID != null))
                artistsByID.put(artistID,artist);
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
            List<SongInfo> songList = songsByArtist.get(artist);
            if (songList != null) {
                for (SongInfo song : songList) {
                    System.out.println("  " + song);
                    System.out.println("    tempo=" + song.getTempo() +
                                       " loudness=" + song.getLoudness());
                    String similarArtistList[] = song.getSimilarArtists();
                    if (similarArtistList != null) {
                        String sa = "";
                        int unknownCount = 0;
                        for (String artistID : similarArtistList) {
                            if (artistID != null) {
                                String artistName = artistsByID.get(artistID);
                                if (artistName != null)
                                    sa += " [" + artistName + "]";
                                else
                                    unknownCount++;
                            }
                        }
                        if (unknownCount > 0)
                            sa += " (plus " + unknownCount + " others)";
                        System.out.println("    similarArtists:" + sa);
                    }
                }
            }
        }
    }

    public static void main(String args[])
    {
        if (args.length < 1) {
            System.err.println("Usage: java RecurseSongs <top-of-dir-tree> [<file-ext>]");
        } else {
            String ext = ".h5";
            if (args.length > 1)
                ext = args[1];
            RecurseSongs rs = new RecurseSongs(args[0],ext);
            long startTime = System.currentTimeMillis();
            rs.startRecurse();
            long endTime = System.currentTimeMillis();
            rs.printSongs();
            System.out.println("Elapsed time = " + ((endTime - startTime) / 1000) + " seconds");
        }
    }
}
