package data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by robbi on 4/8/2017.
 */

public class UltraContract {
    private UltraContract(){}

    public static final String CONTENT_AUTHORITY = "com.perspective.prime.utras";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FIXTURE = "fixture";

    public static final class FixtureEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FIXTURE);

        //string with table uri
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FIXTURE;

        //string with item uri
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FIXTURE;

        // Table name
        public static final String TABLE_NAME = "fixture";

        //Column Names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FIXTURE_ID = "fixture_id";
        public static final String COLUMN_FIXTURE_DATE = "fixture_date";
        public static final String COLUMN_FIXTURE_STATUS = "fixture_status";
        public static final String COLUMN_FIXTURE_HOME_TEAM = "fixture_home_team";
        public static final String COLUMN_FIXTURE_AWAY_TEAM = "fixture_away_team";
        public static final String COLUMN_FIXTURE_BOME_GOALS = "fixture_home_goals";
        public static final String COLUMN_FIXTURE_AWAY_GOALS = "fixture_away_goals";
        public static final String COLUMN_FIXTURE_HOME_ID = "fixture_home_id";
        public static final String COLUMN_FIXTURE_AWAY_ID = "fixture_away_id";
        public static final String COLUMN_FIXTURE_COMPETITION = "fixture_competition";

        public static Uri buildActivityUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
