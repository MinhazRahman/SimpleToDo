package com.example.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.w3c.dom.Text;

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
    String firstLetter;
    Context context;
    ColorGenerator generator = ColorGenerator.MATERIAL;

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
        // Get the first letter from the list item
        firstLetter = String.valueOf(items.get(position).charAt(0));

        //  Create a new TextDrawable for our image's background
        TextDrawable textDrawable = TextDrawable.builder().buildRound(firstLetter, generator.getRandomColor());

        // Bind the item into the specified ViewHolder
        holder.bind(item, textDrawable);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items == null? 0: items.size();
    }

    // The ViewHolder is a wrapper around a View that contains the layout for an individual item in the list.
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        TextView title;
        ImageView letter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            letter = itemView.findViewById(R.id.item_letter);
        }

        // Update the contents of the ViewHolder(textView) with the data item
        public void bind(String item, TextDrawable drawable) {

            title.setText(item);
            letter.setImageDrawable(drawable);
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
