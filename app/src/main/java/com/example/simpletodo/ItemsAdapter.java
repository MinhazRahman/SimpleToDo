package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import utility.ToDoItem;
import utility.Utility;

/** Define the Adapter that associates your data with the ViewHolder views.
* The Adapter creates ViewHolder objects as needed, and also sets the data for those views
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    // An interface that MainActivity will implement
    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }

    // An interface that MainActivity will implement
    public interface OnItemClickListener{
        void onItemClicked(int position);
    }

    ToDoItem toDoItem;
    List<ToDoItem> toDoItemList;
    OnItemLongClickListener itemLongClickListener;
    OnItemClickListener itemClickListener;

    /**
     * Initialize the dataset of the Adapter.
     * @param toDoItemList List<String> containing the data to populate views to be used
     * by RecyclerView.
     */
    public ItemsAdapter(List<ToDoItem> toDoItemList, OnItemLongClickListener itemLongClickListener,
                        OnItemClickListener itemClickListener){
        this.toDoItemList = toDoItemList;
        this.itemLongClickListener = itemLongClickListener;
        this.itemClickListener = itemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         * Create a new view, which defines the UI of the list item
         * Use layout inflater to inflate a view
         */
        View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu, parent, false);

        // Wrap it inside a ViewHolder and return it
        return new ViewHolder(todoView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the item from your dataset at this position
        toDoItem = toDoItemList.get(position);
        String itemDescription = toDoItem.getItemDescription();
        String reminder = toDoItem.getReminderDate() + " "+ toDoItem.getReminderTime();

        // Bind the item into the specified ViewHolder
        holder.bind(itemDescription, reminder);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return toDoItemList == null? 0: toDoItemList.size();
    }

    // The ViewHolder is a wrapper around a View that contains the layout for an individual item in the list.
    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView letter;
        TextView title;
        TextView reminderDateTime;
        TextView dateOfCreation;
        ImageView rightIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            letter = itemView.findViewById(R.id.item_letter);
            reminderDateTime = itemView.findViewById(R.id.item_reminder_date_time);
            dateOfCreation = itemView.findViewById(R.id.item_date);
            rightIcon = itemView.findViewById(R.id.right_icon);
        }

        // Update the contents of the ViewHolder(textView) with the data item
        public void bind(String itemDescription, String reminder) {

            letter.setImageDrawable(Utility.getDrawableLetter(itemDescription));
            title.setText(itemDescription);
            reminderDateTime.setText(reminder);
            dateOfCreation.setText(toDoItem.getDateOfCreation());
            rightIcon.setVisibility(View.VISIBLE);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Notify the listener at which position was clicked
                    itemClickListener.onItemClicked(getAdapterPosition());
                }
            });
            title.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // Notify the listener at which position was long pressed
                    itemLongClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
