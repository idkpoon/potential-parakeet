package example.android.cally;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;

import example.android.cally.data.Contact;
import example.android.cally.data.Contract.ContactEntry;
import example.android.cally.data.ContactDBHelper;

public class NewContact extends AppCompatActivity {

    EditText editTextName, editTextNumber;
    Button btnSave, btnPickContact, btnSelectImage;
    ImageButton btnImage;
    ImageView contactImage;
    public static final int PICK_IMAGE = 2;
    public static final int SELECT_CONTACT = 1;

    private static final String TAG = "NewContact";
    private static ContactDBHelper mDBHelper;
    private String voiceID;
    private String videoID;
    private String realPath;
    private String phoneNumber;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        mDBHelper = new ContactDBHelper(this);


        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnPickContact = (Button) findViewById(R.id.btnPickContact);
        btnImage = (ImageButton) findViewById(R.id.imageButton);

//        sqLiteHelper = new ContactDBHelper(this,"ContactsDB.sqlite",null,1);
        mDBHelper = new ContactDBHelper(this);



        btnPickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, SELECT_CONTACT);
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        NewContact.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PICK_IMAGE
                );
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String number = editTextNumber.getText().toString().trim();



                SQLiteDatabase db = mDBHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(ContactEntry.COLUMN_CONTACT_NAME, name);
                values.put(ContactEntry.COLUMN_CONTACT_NUMBER, number);
                values.put(ContactEntry.COLUMN_VOICE_ID, voiceID);
                values.put(ContactEntry.COLUMN_VIDEO_ID, videoID);
                values.put(ContactEntry.COLUMN_CONTACT_IMAGE,realPath);
                Log.e(TAG,name + " " + number + " " + voiceID + " " + videoID + " " + realPath);

//                Uri newUri = getContentResolver().insert(ContactEntry.CONTENT_URI, values);
//
//                // Show a toast message depending on whether or not the insertion was successful
//                if (newUri == null) {
//                    // If the new content URI is null, then there was an error with insertion.
//                    Toast.makeText(NewContact.this, getString(R.string.editor_insert_contact_failed), Toast.LENGTH_SHORT).show();
//                } else {
//                    // Otherwise, the insertion was successful and we can display a toast.
//                    Toast.makeText(NewContact.this, getString(R.string.editor_insert_contact_successful), Toast.LENGTH_SHORT).show();
//                }


                long newRowId = db.insert(ContactEntry.TABLE_NAME, null, values);

                if (newRowId == -1) {
                    // If the row ID is -1, then there was an error with insertion.
                    Toast.makeText(NewContact.this, "Error with saving contact", Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the insertion was successful and we can display a toast with the row ID.
                    Toast.makeText(NewContact.this, "Contact saved!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewContact.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PICK_IMAGE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);

            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SELECT_CONTACT && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String name = "";

                if (uri != null) {
                    Cursor cursor = null;


                        if (data != null) {
                            if (data != null) {
                                if (uri != null) {
                                    Cursor c = null;
                                    try {
                                        c = getContentResolver().query(uri, new String[]{
                                                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
                                                null, null, null);

                                        if (c != null && c.moveToFirst()) {
                                            name = c.getString(c.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                                            phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                            Log.v(TAG,phoneNumber);



                                        }
                                    } finally {
                                        if (c != null) {
                                            c.close();
                                        }
                                    }
                                }
                            }
                            getID(name);

                        }

            }
            }
        }

        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && null != data ){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri selectedImage = result.getUri();
                realPath = getRealPathFromURI(selectedImage);
                File imageUri = new File(realPath);

                Log.v(TAG,selectedImage.toString());

                btnImage.setImageURI(selectedImage);


                Glide.with(this).load(imageUri).into(btnImage);




            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }


        }



        }

    private void setImage(String uriPath, String realPath){

        this.realPath = realPath;

        Uri uriFromPath = Uri.fromFile(new File(realPath));

        // you have two ways to display selected image

        // ( 1 ) imageView.setImageURI(uriFromPath);

        // ( 2 ) imageView.setImageBitmap(bitmap);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        contactImage.setImageURI(uriFromPath);

        Log.v(TAG, "URI Path:"+uriPath);
        Log.v(TAG, "Real Path: "+realPath);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String path;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            path = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path =  cursor.getString(index);
        }
        assert cursor != null;
//        cursor.close();

        return path;
    }

    private void getID(String name){

         String mimetypevoice = "vnd.android.cursor.item/vnd.com.whatsapp.voip.call";
         String mimetypevideo = "vnd.android.cursor.item/vnd.com.whatsapp.video.call";
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor;
        cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        while (cursor.moveToNext()) {
            long _id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data._ID));
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
            int type = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

            if (mimeType.equals(mimetypevoice) || mimeType.equals(mimetypevideo)) {
                if(displayName.equals(name)){
                    Log.e(TAG, _id + " " + displayName + " " + mimeType + " " + phoneNumber);

                    editTextName.setText(displayName);
                    editTextNumber.setText(phoneNumber);
                    if (mimeType.equals(mimetypevoice)){
                        voiceID = Long.toString(_id);
                    }
                    if(mimeType.equals(mimetypevideo)){
                        videoID = Long.toString(_id);
                    }
                }

            }


        }
    }
    }
