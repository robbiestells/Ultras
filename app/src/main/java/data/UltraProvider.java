package data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import data.UltraContract.FixtureEntry;

import static android.R.attr.id;
import static android.R.attr.priority;

/**
 * Created by robbi on 4/8/2017.
 */

public class UltraProvider extends ContentProvider{
    public static final String LOG_TAG = UltraProvider.class.getSimpleName();

    private UltraDbHelper mHelper;

    private static final int FIXTURE = 100;
    private static final int FIXTURE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(UltraContract.CONTENT_AUTHORITY, UltraContract.PATH_FIXTURE, FIXTURE);
        sUriMatcher.addURI(UltraContract.CONTENT_AUTHORITY, UltraContract.PATH_FIXTURE + "/#", FIXTURE_ID);
    }

    @Override
    public boolean onCreate() {
        mHelper = new UltraDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mHelper.getReadableDatabase();

        Cursor cursor;

        //determine whether a specific feed item is being queried
        int match = sUriMatcher.match(uri);
        switch (match) {
            case FIXTURE:
                cursor = database.query(FixtureEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FIXTURE_ID:
                selection = FixtureEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(FixtureEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        //set notification URI on the cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FIXTURE:
                return FixtureEntry.CONTENT_LIST_TYPE;
            case FIXTURE_ID:
                return FixtureEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FIXTURE:
                return insertFixture(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertFixture(Uri uri, ContentValues values) {
        //Check that episode title is not null
        String id = values.getAsString(FixtureEntry.COLUMN_FIXTURE_ID);
        if (id == null) {
            throw new IllegalArgumentException("Invalid Fixture Id");
        }

        SQLiteDatabase database = mHelper.getWritableDatabase();

        long tableId = database.insert(FixtureEntry.TABLE_NAME, null, values);

        if (tableId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        //notify listeners that there has been a change
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, tableId);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

}
