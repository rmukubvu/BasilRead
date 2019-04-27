package za.co.thamserios.basilread.navigation;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.Hashtable;

/**
 * Created by robson on 2017/01/14.
 */

public class NavigationHelper {

    public static final int BREAK_DOWN = 2;
    public static final int PRODUCTION = 3;
    public static final int STANDING = 4;
    public static final int DEFAULT = 1;
    public static final int PRODUCTION_COMMENT = 5;
    public static final int BREAKDOWN_COMMENT = 6;
    public static final int STANDING_COMMENT = 7;
    public static final int END_SHIFT = 8;
    public static final int WRITE_DATA = 9;
    public static final int PRODUCTION_HISTORY = 10;
    public static final int BREAKDOWN_HISTORY = 11;
    public static final int STANDING_TIME_HISTORY = 12;

    private static Hashtable<Integer,String> fragmentHandlerClasses;
    private Integer START_NAVIGATION_INDEX = 1;
    private Integer NAVIGATION_MIN = 1;
    private Context mContext;
    private Integer NAVIGATION_MAX = 9;
    private int currentSelectedIndex = 0;

    static {
        fragmentHandlerClasses = new Hashtable<>();
        fragmentHandlerClasses.put(1, "za.co.thamserios.basilread.views.fragments.DefaultFragment");
        fragmentHandlerClasses.put(2, "za.co.thamserios.basilread.views.fragments.BreakdownFragment");
        fragmentHandlerClasses.put(3, "za.co.thamserios.basilread.views.fragments.ProductionFragment");
        fragmentHandlerClasses.put(4, "za.co.thamserios.basilread.views.fragments.StandingFragment");
        fragmentHandlerClasses.put(5, "za.co.thamserios.basilread.views.fragments.ProductionCommentFragment");
        fragmentHandlerClasses.put(6, "za.co.thamserios.basilread.views.fragments.BreakdownCommentFragment");
        fragmentHandlerClasses.put(7, "za.co.thamserios.basilread.views.fragments.StandingCommentFragment");
        fragmentHandlerClasses.put(8, "za.co.thamserios.basilread.views.fragments.EOSFragment");
        fragmentHandlerClasses.put(9, "za.co.thamserios.basilread.views.fragments.TransferDataFragment");
        fragmentHandlerClasses.put(10, "za.co.thamserios.basilread.views.fragments.ProductionHistoryFragment");
        fragmentHandlerClasses.put(11, "za.co.thamserios.basilread.views.fragments.BreakdownLogsFragment");
        fragmentHandlerClasses.put(12, "za.co.thamserios.basilread.views.fragments.StandingHistoryFragment");
    }

    public NavigationHelper(Context context){
        mContext = context;
        NAVIGATION_MAX = fragmentHandlerClasses.size();
    }

    public Fragment getFragment(Integer navigationIndex){
        if (navigationIndex <= NAVIGATION_MIN)
            navigationIndex = NAVIGATION_MIN;
        else if (navigationIndex >= NAVIGATION_MAX)
            navigationIndex = NAVIGATION_MAX;
        currentSelectedIndex = navigationIndex;
        Fragment mTabFragment = Fragment.instantiate(mContext, fragmentHandlerClasses.get(navigationIndex));
        return mTabFragment;
    }

    public int getCurrentSelectedIndex(){
        return currentSelectedIndex;
    }

    public Fragment getStartingFragment(){
        return getFragment(START_NAVIGATION_INDEX);
    }
}
