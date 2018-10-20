package example.android.cally;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import example.android.cally.data.Contact;
import example.android.cally.data.ContactDBHelper;
import example.android.cally.data.Contract.ContactEntry;


public class MainActivity extends AppCompatActivity{

    FloatingActionButton FAB;
    private String TAG = "MainActivity";
    private ContactDBHelper mDBHelper;
    private static final int CONTACT_LOADER = 200;
    ListView contactsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FAB = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,NewContact.class);
                startActivity(i);
            }
        });

        mDBHelper = new ContactDBHelper(this);




    }

    private void insertContact() {
        // Gets the database in write mode
        SQLiteDatabase db = mDBHelper.getWritableDatabase();



        Random rand = new Random();
        int randomNum = rand.nextInt((999 - 1) + 1) + 1;

        ContentValues values = new ContentValues();
        values.put(ContactEntry._ID,randomNum);
        values.put(ContactEntry.COLUMN_CONTACT_NAME, "Bob");
        values.put(ContactEntry.COLUMN_CONTACT_NUMBER, "1234567");
        values.put(ContactEntry.COLUMN_CONTACT_IMAGE,"bob.jpg");
        values.put(ContactEntry.COLUMN_VOICE_ID, "4472");
        values.put(ContactEntry.COLUMN_VIDEO_ID, "4471");

//        long newRowId = db.insert(ContactEntry.TABLE_NAME, null, values);


        Uri newUri = getContentResolver().insert(ContactEntry.CONTENT_URI, values);
        displayInfo();

    }


    @Override
    protected void onStart() {
        super.onStart();
        displayInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertContact();

                return true;

            case R.id.action_delete:
                deleteAllPets();
                return true;



        }
        return super.onOptionsItemSelected(item);
    }

    private void displayInfo() {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ContactEntry._ID,
                ContactEntry.COLUMN_CONTACT_NAME,
                ContactEntry.COLUMN_CONTACT_NUMBER,
                ContactEntry.COLUMN_CONTACT_IMAGE,
                ContactEntry.COLUMN_VOICE_ID,
                ContactEntry.COLUMN_VIDEO_ID};

        // Perform a query on the provider using the ContentResolver.
        Cursor cursor = getContentResolver().query(
                ContactEntry.CONTENT_URI,   // The content URI of the words table
                projection,             // The columns to return for each row
                null,                   // Selection criteria
                null,                   // Selection criteria
                null);                  // The sort order for the returned rows

        contactsListView = (ListView) findViewById(R.id.listview);
        ContactCursorAdapter adapter = new ContactCursorAdapter(this, cursor);

        contactsListView.setAdapter(adapter);

    }

    private void deleteAllPets() {
        int rowsDeleted = getContentResolver().delete(ContactEntry.CONTENT_URI, null, null);
        Log.v("MainActivity", rowsDeleted + " rows deleted from contacts database");
    }

}
