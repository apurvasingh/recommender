import java.util.ArrayList;
import java.util.List;

public class SimpleRecommender extends RecurseSongs
{
    List<SongInfo> favoriteSongs = new ArrayList<SongInfo>(3);
    List<SongAndScore> topSongMatches = new ArrayList<SongAndScore>(10);

    public SimpleRecommender(String startDir)
    {
        super(startDir,".song");
    }

    public void addFavorite(String faveSongFile)
    {
        try {
            SongInfo song = new SongInfo(faveSongFile);
            favoriteSongs.add(song);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSong(SongInfo song)
    {
        double scoreSum = 0.0;
        for (SongInfo faveSong : favoriteSongs)
            scoreSum += song.similarityScore(faveSong);
        for (int i = 0; i < 10; i++) {
            if (i < topSongMatches.size()) {
                SongAndScore other = topSongMatches.get(i);
                if (scoreSum < other.similarity) {
                    SongAndScore sas = new SongAndScore(scoreSum,song);
                    topSongMatches.add(i,sas);
                    while (topSongMatches.size() > 10)
                        topSongMatches.remove(10);
                    return;
                }
            } else {
                SongAndScore sas = new SongAndScore(scoreSum,song);
                topSongMatches.add(sas);
                return;
            }
        }
    }

    public void readSong(String path)
    {
        try {
            SongInfo song = new SongInfo(path);
            addSong(song);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printFavorites()
    {
        for (SongAndScore sas : topSongMatches) {
            System.out.println("" + sas.similarity + ": " + sas.song);
        }
    }

    public static class SongAndScore
    {
        public double similarity;
        public SongInfo song;
        public SongAndScore(double similarity, SongInfo song)
        {
            this.similarity = similarity;
            this.song = song;
        }
    }

    public static void main(String[] args)
    {
        if (args.length < 2) {
            System.err.println("Usage: java SimpleRecommender <top-of-dir-tree> [song [...]]");
            System.err.println(" where \"[song [...]] is 1 to 3 song files which are to be matched");
        } else {
            SimpleRecommender cs = new SimpleRecommender(args[0]);
            for (int i = 1; i < args.length; i++)
                cs.addFavorite(args[i]);
            long startTime = System.currentTimeMillis();
            cs.startRecurse();
            long endTime = System.currentTimeMillis();
            cs.printFavorites();
            System.out.println("Elapsed time = " + ((endTime - startTime) / 1000) + " seconds");
        }
    }
}
