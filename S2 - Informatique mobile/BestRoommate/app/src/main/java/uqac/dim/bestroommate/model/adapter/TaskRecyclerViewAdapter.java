package uqac.dim.bestroommate.model.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import uqac.dim.bestroommate.model.data.Tazz;

// To avoid code duplication, we create a generic class TaskRecyclerViewAdapter that extends RecyclerView.Adapter
// The T object is the ViewHolder that will depend of the child class
public abstract class TaskRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    // The Task list
    protected final ArrayList<Tazz> dataSet;
    // The layout will be used to inflate the view (the list_item.xml)
    protected final int layoutId;
    // The ViewHolder child class


    // Represent the delegate that will be called when we click on a task
    protected Function<Integer, Boolean> onItemSelectedDelegate;
    public TaskRecyclerViewAdapter(@NonNull Context context,
                                   @NonNull ArrayList<Tazz> tasks,
                                   int _LayoutId) {
        this.dataSet = tasks;
        this.layoutId = _LayoutId;
    }

    // Function to bind the delegate
    public void setOnItemSelectedDelegate(Function<Integer, Boolean> _onItemSelectedDelegate){
        this.onItemSelectedDelegate = _onItemSelectedDelegate;
    }

    // Return the size of our list, important to navigate through the list
    @Override
    public int getItemCount() {
        return this.dataSet.size();
    }
}
