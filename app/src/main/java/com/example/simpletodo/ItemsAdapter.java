package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

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
        return items.size();
    }

    // The ViewHolder is a wrapper around a View that contains the layout for an individual item in the list.
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }

        // Update the contents of the ViewHolder(textView) with the data item
        public void bind(String item) {

            textView.setText(item);
            // Add icon to each item
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.abc_vector_test, 0, 0, 0);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Notify the listener at which position was clicked
                    itemClickListener.onItemClicked(getAdapterPosition());
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
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
