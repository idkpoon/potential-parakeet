package example.android.cally;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

import example.android.cally.data.Contract;


public class ContactCursorAdapter extends CursorAdapter  {


    public ContactCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.contact_card,parent,false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView contactNameTextView = (TextView) view.findViewById(R.id.contactName);
        ImageView imageView = (ImageView) view.findViewById(R.id.contactImage);

        int nameColIndex = cursor.getColumnIndex(Contract.ContactEntry.COLUMN_CONTACT_NAME);
        int imageColIndex = cursor.getColumnIndex(Contract.ContactEntry.COLUMN_CONTACT_IMAGE);

        String name = cursor.getString(nameColIndex);
        String imageRealPath = cursor.getString(imageColIndex);

        contactNameTextView.setText(name);
        File imageUri = new File(imageRealPath);


        Glide.with(context).load(imageUri).into(imageView);
    }
}