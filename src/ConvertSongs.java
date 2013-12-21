public class ConvertSongs extends RecurseSongs
{
    private String destDir = null;

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
            String destName = destDir + base;
            System.out.println("Converting " + srcName);
            System.out.println("    To " + destName);
        } else {
            System.out.println("Unable to convert filename: " + srcName);
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
