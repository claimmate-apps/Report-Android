package com.cwclaims.claimapp.adpts;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.models.RoofRes;

import java.util.ArrayList;
import java.util.List;

public class RoofFirstAdapter extends RecyclerView.Adapter<RoofFirstAdapter.MyViewHolder> {

    public String TAG = getClass().getSimpleName();

    public static final int CHECKBOX = 1;
    public static final int EDIT_TEXT = 2;

    private List<RoofRes> data = new ArrayList<>();

    Context context;
    int i = 10;

    private EventListener mEventListener;

    public interface EventListener {
        void onItemViewClicked(int position);

        void onItemTitleClicked(int position);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public MyViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
        }
    }

    public RoofFirstAdapter(Context context) {
        this.context = context;

        setHasStableIds(true);

    }

    public void add() {
        i++;
        notifyDataSetChanged();
    }


    public void addAll(List<RoofRes> mData) {
        this.data.addAll(mData);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public RoofRes getItem(int position) {
        return data.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == CHECKBOX) {
            final View v = inflater.inflate(R.layout.item_roof_first_checkbox, parent, false);
            return new MyViewHolder(v);
        } else {
            final View v = inflater.inflate(R.layout.item_elevation_first, parent, false);
            return new MyViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RoofRes item = data.get(position);

        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEventListener != null) {
                    mEventListener.onItemTitleClicked(position);
                }
            }
        });

        holder.tvTitle.setText(item.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
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

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).isCheckbox) {
            return CHECKBOX;
        }
        return EDIT_TEXT;
    }
}
