package example.android.cally.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by 21poonkw1 on 11/8/2018.
 */

public final class Contract {

    private Contract(){}

    public static final String CONTENT_AUTHORITY = "com.example.android.cally";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CONTACTS = "Contacts";

    public static final class ContactEntry implements BaseColumns{

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CONTACTS);

        public final static String TABLE_NAME = "Contacts";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_VOICE_ID = "voiceID";
        public final static String COLUMN_VIDEO_ID = "videoID";
        public final static String COLUMN_CONTACT_NAME = "name";
        public final static String COLUMN_CONTACT_NUMBER = "number";
        public final static String COLUMN_CONTACT_IMAGE = "imagePath";

        
    }


}
