package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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

    List<String> items;
    OnItemLongClickListener itemLongClickListener;
    OnItemClickListener itemClickListener;

    /**
     * Initialize the dataset of the Adapter.
     * @param items List<String> containing the data to populate views to be used
     * by RecyclerView.
     */
    public ItemsAdapter(List<String> items, OnItemLongClickListener itemLongClickListener,
                        OnItemClickListener itemClickListener){
        this.items = items;
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
        String item = items.get(position);

        // Bind the item into the specified ViewHolder
        holder.bind(item);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items == null? 0: items.size();
    }

    // The ViewHolder is a wrapper around a View that contains the layout for an individual item in the list.
    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView letter;
        TextView title;
        TextView reminder;
        TextView dateOfCreation;
        ImageView rightIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            letter = itemView.findViewById(R.id.item_letter);
            //reminder = itemView.findViewById(R.id.item_reminder);
            dateOfCreation = itemView.findViewById(R.id.item_date);
            rightIcon = itemView.findViewById(R.id.right_icon);
        }

        // Update the contents of the ViewHolder(textView) with the data item
        public void bind(String item) {

            letter.setImageDrawable(Utility.getDrawableLetter(item));
            title.setText(item);
            dateOfCreation.setText(Utility.getCurrentDate());
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
