package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import data.UltraContract.FixtureEntry;

/**
 * Created by robbi on 4/8/2017.
 */

public class UltraDbHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "ultra.db";

    public UltraDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FIXTURE_TABLE = "CREATE TABLE " + FixtureEntry.TABLE_NAME + " (" +
                FixtureEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FixtureEntry.COLUMN_FIXTURE_ID + " TEXT, " +
                FixtureEntry.COLUMN_FIXTURE_DATE + " TEXT, " +
                FixtureEntry.COLUMN_FIXTURE_STATUS + " TEXT, " +
                FixtureEntry.COLUMN_FIXTURE_HOME_TEAM + " TEXT, " +
                FixtureEntry.COLUMN_FIXTURE_AWAY_TEAM + " TEXT, " +
                FixtureEntry.COLUMN_FIXTURE_BOME_GOALS + " TEXT, " +
                FixtureEntry.COLUMN_FIXTURE_AWAY_GOALS + " TEXT, " +
                FixtureEntry.COLUMN_FIXTURE_HOME_ID + " TEXT, " +
                FixtureEntry.COLUMN_FIXTURE_AWAY_ID + " TEXT, " +
                FixtureEntry.COLUMN_FIXTURE_COMPETITION + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_FIXTURE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FixtureEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
