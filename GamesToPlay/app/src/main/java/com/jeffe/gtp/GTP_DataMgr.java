package com.jeffe.gtp;

import java.util.Locale;
import java.util.TreeSet;

import android.util.Log;

/**
 * 
 * Data interface for this application
 */
public class GTP_DataMgr {
    private static final String TAG = GTP_DataMgr.class.getSimpleName();

    private static final boolean DEBUG = true;
    private static final String sDebugGameTitles[] = new String[] {
            "Assassin's Creed", "Super Smash Bros.", "Uncharted",
            "The Last Of Us", "Red Dead Redemption", "Mario Party", "Destiny",
            "Dance Dance Revolution", "Star Fox", "Silent Hill", "Fatal Frame",
            "Star Wars: Battlefront", "Beyond: Two Souls",
            "Elder Scrolls Online: Tamriel Unlimited",
            "Call of the Battlefield 14: Advanced Zombie Black Ops Warfare",
            "Heavenly Sword", "Batman: Arkham City" };
    private static final int sDebugResIds[] = new int[] {
            R.drawable.gameimg_ac, R.drawable.gameimg_ssb,
            R.drawable.gameimg_uncharted, R.drawable.gameimg_lastofus,
            R.drawable.gameimg_rdr, R.drawable.gameimg_marioparty,
            R.drawable.gameimg_destiny, R.drawable.gameimg_ddr,
            R.drawable.gameimg_starfox, R.drawable.gameimg_silenthill,
            R.drawable.gameimg_fatalframe, R.drawable.gameimg_swbattlefront,
            R.drawable.gameimg_beyondtwosouls, R.drawable.gameimg_eso,
            R.drawable.gameimg_cod, R.drawable.gameimg_heavenlysword,
            R.drawable.gameimg_batman
    };
    private static final String sDebugConsoles[] = new String[] {
            "Playstation 3", "Playstation 4", "XBox 360", "XBox One",
            "Nintendo Wii", "Nintendo Wii U", "Android", "iOs", "PC", "Mac" };
    private static final int DEBUG_GAMECOUNT = sDebugGameTitles.length
            * sDebugConsoles.length;

    private static GTP_DataMgr sSingleton = null;

    /**
     * Contains common member fields between game list items in the lists
     * displayed by this app. Provides an ordering implementation for
     * alphabetical ordering based on the game title and console
     * 
     */
    static abstract class AbsListItem_Game implements
            Comparable<AbsListItem_Game> {
        String title;
        String console;
        int imgResId;

        AbsListItem_Game(String t, String c, int rId) {
            this.title = t;
            this.console = c;
            this.imgResId = rId;
        }

        @Override
        public int compareTo(AbsListItem_Game other) {
            String myStr, otherStr;
            myStr = title.toLowerCase(Locale.getDefault())
                    + console.toLowerCase(Locale.getDefault());
            otherStr = other.title.toLowerCase(Locale.getDefault())
                    + other.console.toLowerCase(Locale.getDefault());

            return myStr.compareTo(otherStr);
        }

        @Override
        public boolean equals(Object o) {
            String myStr, otherStr;
            myStr = title.toLowerCase(Locale.getDefault())
                    + console.toLowerCase(Locale.getDefault());
            AbsListItem_Game other = (AbsListItem_Game) o;
            otherStr = other.title.toLowerCase(Locale.getDefault())
                    + other.console.toLowerCase(Locale.getDefault());
            return myStr.equals(otherStr);
        }
    }

    /**
     * POJO for a list item in the list games activity
     */
    static class ListItem_ListGames extends AbsListItem_Game {
        boolean finished;

        ListItem_ListGames(String t, String c, int rId, boolean finished) {
            super(t, c, rId);
            this.finished = finished;
        }
    }

    /**
     * POJO for a list item in the rate games activity
     */
    static class ListItem_RateGames extends AbsListItem_Game {
        /**
         * negative value means unrated
         */
        int starRating;

        ListItem_RateGames(String t, String c, int rId, int rating) {
            super(t, c, rId);
            this.starRating = rating;
        }

        ListItem_RateGames(ListItem_ListGames copyMe, int rating) {
            this(copyMe.title, copyMe.console, copyMe.imgResId, rating);
        }
    }

    /**
     * initial values for debugging
     */
    static ListItem_ListGames sItems_ListGames[] = null;
    static ListItem_RateGames sItems_RateGames[] = null;

    // these hold initial/debug data AND runtime dynamic data
    private static TreeSet<ListItem_ListGames> mItems_ListGames;
    private static TreeSet<ListItem_RateGames> mItems_RateGames;
    private static boolean mDirty_ListGamesData;
    private static boolean mDirty_RateGamesData;

    /**
     * using singleton pattern for proxy class handling data operations. acquire
     * the singleton instance using the static instance() method
     */
    private GTP_DataMgr() {
        mItems_ListGames = new TreeSet<ListItem_ListGames>();
        mItems_RateGames = new TreeSet<ListItem_RateGames>();
        mDirty_ListGamesData = true;
        mDirty_RateGamesData = true;
    }

    /**
     * Get/initialize the data layer interface
     * 
     * @return GTP_DataMgr reference to use for data operations
     */
    public static GTP_DataMgr instance() {
        if (sSingleton == null) {
            sSingleton = new GTP_DataMgr();
            if (DEBUG) {
                initTestStatics();
            } else {
                // TODO:
                // init real data model using real
                // data sources from sqlite or web server
                // or combination of both
            }
        }

        return sSingleton;
    }

    /**
     * Init debug/test list items
     */
    private static void initTestStatics() {
        sItems_ListGames = new ListItem_ListGames[DEBUG_GAMECOUNT];
        sItems_RateGames = new ListItem_RateGames[DEBUG_GAMECOUNT];
        int i = 0;
        for (int gameTitleIdx = 0; gameTitleIdx < sDebugGameTitles.length; ++gameTitleIdx) {
            for (int gameConsoleIdx = 0; gameConsoleIdx < sDebugConsoles.length; ++gameConsoleIdx) {
                sItems_ListGames[i] = new ListItem_ListGames(
                        sDebugGameTitles[gameTitleIdx],
                        sDebugConsoles[gameConsoleIdx],
                        sDebugResIds[gameTitleIdx],
                        ((i % 3) != 0));
                sItems_RateGames[i] = new ListItem_RateGames(
                        sItems_ListGames[i], (i % 6));
                sSingleton.addGameListing(sItems_ListGames[i], (i % 6));
                // automatically added unrated rating listing
                ++i;
            }
        }
    }

    /**
     * Add a new game listing (title/console/finished/image). This method
     * automatically adds a rating list item as well.
     * 
     * TODO: implement persistent data sync
     * 
     * @param item
     *            description of game listing to add
     */
    public void addGameListing(ListItem_ListGames item) {
        boolean success = mItems_ListGames.add(item);
        addGameRating(new ListItem_RateGames(item, -1));
        mDirty_ListGamesData = true;

        if (!success) {
            Log.w(TAG, "failed to add item. duplicate?");
        }
    }

    /**
     * TODO: implement persistent data sync
     * 
     * @param item
     *            item to add
     * @param rating
     *            star rating if already known
     */
    public void addGameListing(ListItem_ListGames item, int rating) {
        boolean success = mItems_ListGames.add(item);
        addGameRating(new ListItem_RateGames(item, rating));
        mDirty_ListGamesData = true;

        if (!success) {
            Log.w(TAG, "failed to add item. duplicate?");
        }
    }

    /*
     * TODO: implement persistent data sync
     */
    private void addGameRating(ListItem_RateGames item) {
        boolean success = mItems_RateGames.add(item);
        mDirty_RateGamesData = true;

        if (!success) {
            Log.w(TAG, "failed to add item. duplicate?");
        }
    }

    // TODO: weigh defining a method for each view style
    // vs a single generic query returning obj arrays
    // that are cast to the expected type
    static ListItem_ListGames[] sCachedArr_ListGames = null;

    public ListItem_ListGames[] getListItems_ListGames() {
        if (sCachedArr_ListGames == null || mDirty_ListGamesData) {
            sCachedArr_ListGames = mItems_ListGames
                    .toArray(new ListItem_ListGames[0]);
            mDirty_ListGamesData = false;
        }

        return sCachedArr_ListGames;
    }

    static ListItem_RateGames[] sCachedArr_RateGames = null;

    /**
     * 
     * @return an array of list items for the rate games activity
     */
    public ListItem_RateGames[] getListItems_RateGames() {
        if (sCachedArr_RateGames == null || mDirty_RateGamesData) {
            sCachedArr_RateGames = mItems_RateGames
                    .toArray(new ListItem_RateGames[0]);
            mDirty_RateGamesData = false;
        }
        return sCachedArr_RateGames;
    }

    /**
     * Re-implement with better transaction safety.
     * 
     * Updates the rating value in the data model using the title/console
     * combination as the key for an entry
     * 
     * @param title
     * @param console
     * @param imgResId
     * @param rating
     */
    public void updateRating(String title, String console, int imgResId,
            int rating) {

        // replace old entry in tree set with a new one
        ListItem_RateGames item = new ListItem_RateGames(title, console,
                imgResId, rating);
        boolean success = mItems_RateGames.remove(item);
        if (!success) {
            Log.d(TAG, "mItems_RateGames.remove(item) success=" + success);
        }

        success = mItems_RateGames.add(item);
        if (!success) {
            Log.d(TAG, "mItems_RateGames.add(item) success=" + success);
        }

        mDirty_RateGamesData = true;
    }

    /**
     * Re-implement with better transaction safety
     * 
     * Updates the finished value in the data model using the title/console
     * combination as the key for an entry
     * 
     * @param title
     * @param console
     * @param imgResId
     * @param finished
     */
    public void updateFinished(String title, String console, int imgResId,
            boolean finished) {

        // replace old entry in tree set with a new one
        ListItem_ListGames item = new ListItem_ListGames(title, console,
                imgResId, finished);
        boolean success = mItems_ListGames.remove(item);
        if (!success) {
            Log.d(TAG, "mItems_ListGamesGames.remove(item) success=" + success);
        }

        success = mItems_ListGames.add(item);
        if (!success) {
            Log.d(TAG, "mItems_ListGamesGames.add(item) success=" + success);
        }

        mDirty_ListGamesData = true;
    }
}
