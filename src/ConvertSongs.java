import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;

public class ConvertSongs extends RecurseSongs
{
    private String destDir = null;
    private String newExtension = ".song";

    public ConvertSongs(String startDir, String destDir)
    {
        super(startDir,".h5");
        this.destDir = destDir;
    }

    public void addSong(String artist, SongInfo song)
    {
        super.addSong(artist,song);
        String srcName = song.getFilename();
        if (srcName.startsWith(startDir)) {
            String base = srcName.substring(startDir.length());
            if (base.endsWith(extension)) {
                int newlen = base.length() - extension.length();
                base = base.substring(0,newlen);
            }
            String destName = destDir + base + newExtension;
            System.out.println("Converting " + srcName);
            System.out.println("    To " + destName);
            writeSong(song,destName);
        } else {
            System.out.println("Unable to convert filename: " + srcName);
        }
    }

    public void writeSong(SongInfo song, String destFilename)
    {
        try {
            File songfile = new File(destFilename);
            File parentDir = songfile.getParentFile();
            if ((! parentDir.exists()) && (! parentDir.mkdirs())) {
                System.err.println("Unable to create parent directory: " + parentDir);
                return;
            }

            PrintWriter pw = new PrintWriter(destFilename);
            pw.println("filename\t" + destFilename);
            pw.println("original-filename\t" + song.getFilename());
            pw.println("artist-name\t" + song.getArtistName());
            pw.println("artist-id\t" + song.getArtistID());
            pw.println("song-title\t" + song.getSongTitle());
            pw.println("song-id\t" + song.getSongID());
            pw.println("album-name\t" + song.getAlbumName());
            pw.println("year\t" + song.getYear());
            pw.println("loudness\t" + song.getLoudness());
            pw.println("song-hotness\t" + song.getSongHotness());
            pw.println("artist-hotness\t" + song.getArtistHotness());
            pw.println("tempo\t" + song.getTempo());
            String similarArtists[] = song.getSimilarArtists();
            if (similarArtists != null)
                for (String similarArtist : similarArtists)
                    pw.println("similar-artist\t" + similarArtist);
            pw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        if (args.length < 2) {
            System.err.println("Usage: java ConvertSongs <top-of-dir-tree> <dest-dir>");
        } else {
            ConvertSongs cs = new ConvertSongs(args[0],args[1]);
            long startTime = System.currentTimeMillis();
            cs.startRecurse();
            long endTime = System.currentTimeMillis();
            System.out.println("Elapsed time = " + ((endTime - startTime) / 1000) + " seconds");
        }
    }
}
