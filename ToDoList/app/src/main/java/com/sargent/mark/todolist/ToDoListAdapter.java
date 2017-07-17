package com.sargent.mark.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sargent.mark.todolist.data.Contract;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private String TAG = "todolistadapter";

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public interface ItemClickListener {
        void onItemClick(int pos, String description, String duedate,String category, long id);
    }

    public ToDoListAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView descr;
        TextView due;
        TextView categoryTV;
        CheckBox checker;
        String duedate;
        String description;
        String category;
        long id;


        ItemHolder(View view) {
            super(view);
            checker = (CheckBox) view.findViewById(R.id.todoCheckBox);
            descr = (TextView) view.findViewById(R.id.description);
            due = (TextView) view.findViewById(R.id.dueDate);
            categoryTV = (TextView) view.findViewById(R.id.category);
            view.setOnClickListener(this);
        }
        public void onCheckBoxClicked(View view){

        }

        public void bind(ItemHolder holder, int pos) {
            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));
            Log.d(TAG, "deleting id: " + id);

            duedate = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE));
            description = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION));
            //adding category text to the UI
            category = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY));
            descr.setText(description);
            due.setText(duedate);
            categoryTV.setText(category);
            holder.itemView.setTag(id);

            checker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // do stuff with item id here
                    Log.d(TAG, "---------->>>>>>>>>>>ITEM ID: " + id);
                    if(checker.isChecked()) {
                        markAsDone();
                    }else{
                        markAsUnDone();
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, description, duedate, category, id);
        }
        private void markAsDone(){
            descr.setPaintFlags(descr.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        private void markAsUnDone(){
            descr.setPaintFlags(descr.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }
}
