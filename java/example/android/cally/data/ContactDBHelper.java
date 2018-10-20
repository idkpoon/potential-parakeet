package example.android.cally.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import example.android.cally.data.Contract.ContactEntry;


public class ContactDBHelper extends SQLiteOpenHelper {

    public static final String TAG = ContactDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "cally.db";
    private static final int DATABASE_VERSION = 1;

    public ContactDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_STATEMENT = "CREATE TABLE" + ContactEntry.TABLE_NAME + " ("
                + ContactEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ContactEntry.COLUMN_VOICE_ID + " TEXT NOT NULL, "
                + ContactEntry.COLUMN_VIDEO_ID + " TEXT NOT NULL, "
                + ContactEntry.COLUMN_CONTACT_NAME + " TEXT NOT NULL, "
                + ContactEntry.COLUMN_CONTACT_NUMBER + " TEXT NOT NULL, "
                + ContactEntry.COLUMN_CONTACT_IMAGE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_STATEMENT);
        Log.v(TAG,"Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
