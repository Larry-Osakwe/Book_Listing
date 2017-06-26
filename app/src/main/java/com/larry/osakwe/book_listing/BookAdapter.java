package com.larry.osakwe.book_listing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Larry Osakwe on 6/24/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    private Book currentBook;

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.booklist_item, parent, false);
        }


        //formats all magnitudes to one decimal place


        currentBook = getItem(position);

        //Set value for book title
        TextView bookTitle = (TextView) listItemView.findViewById(R.id.book_title);
        bookTitle.setText(currentBook.getTitle());

        //Set value for author
        TextView bookAuthor = (TextView) listItemView.findViewById(R.id.book_author);
        bookAuthor.setText(currentBook.getAuthor());

        //Set value for location
        ImageView bookImage = (ImageView) listItemView.findViewById(R.id.book_image);

        return listItemView;
    }
}
