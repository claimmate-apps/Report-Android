package com.cwclaims.claimapp.adpts;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.models.ElevationRes;

import java.util.ArrayList;
import java.util.List;

public class ElevationFirstAdapter extends RecyclerView.Adapter<ElevationFirstAdapter.MyViewHolder> implements Filterable {

    public String TAG = getClass().getSimpleName();

    private List<ElevationRes> data = new ArrayList<>();

    Context context;
    int i = 1;

    private EventListener mEventListener;

    public interface EventListener {
        void onItemViewClicked(int position);

        void onItemTitleClicked(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;

        public MyViewHolder(View v) {
            super(v);

            tvTitle = v.findViewById(R.id.tvTitle);
        }
    }

    public ElevationFirstAdapter(Context context) {
        this.context = context;
        setHasStableIds(true);
    }

    public void add() {
        i++;
        notifyDataSetChanged();
    }

    public void addAll(List<ElevationRes> mData) {
        this.data.addAll(mData);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public ElevationRes getItem(int position) {
        return data.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_elevation_first, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventListener != null) {
                    mEventListener.onItemTitleClicked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
//        return i;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public EventListener getEventListener() {
        return mEventListener;
    }

    public void setEventListener(EventListener eventListener) {
        mEventListener = eventListener;
    }

    boolean isFilterable = false;

    public void setFilterable(boolean isFilterable) {
        this.isFilterable = isFilterable;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
