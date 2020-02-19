package com.cwclaims.claimapp.adpts;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.models.InteriorRes;

import java.util.ArrayList;
import java.util.List;

public class FloorAdapter
        extends RecyclerView.Adapter<FloorAdapter.MyViewHolder> implements Filterable {

    public String TAG = getClass().getSimpleName();

    private List<InteriorRes.Ceiling> data = new ArrayList<>();

    Context context;
    int i = 2;

    private EventListener mEventListener;

    public interface EventListener {
        void onItemViewClicked(int position);

        void onItemSpinnerClicked(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        EditText editCustom, editInfo;
        Spinner spinnerTitle, spinnerSign;

        public MyViewHolder(View v) {
            super(v);

            editCustom = v.findViewById(R.id.editCustom);
            editInfo = v.findViewById(R.id.editInfo);

            spinnerTitle = v.findViewById(R.id.spinnerTitle);
            spinnerSign = v.findViewById(R.id.spinnerSign);
        }
    }

    public FloorAdapter(Context context) {
        this.context = context;

        setHasStableIds(true);

    }

    public void add(InteriorRes.Ceiling ceiling) {
        data.add(ceiling);
        notifyItemInserted(getItemCount());
    }


    public void addAll(List<InteriorRes.Ceiling> mData) {
        this.data.addAll(mData);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_interior_floor, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InteriorRes.Ceiling item = data.get(position);

        holder.spinnerTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                if (position1 != 0) {
                    if (mEventListener != null) {
                        mEventListener.onItemSpinnerClicked(position);
                    }
                }
                if (position1 == holder.spinnerTitle.getCount() - 1) {
                    holder.editCustom.setVisibility(View.VISIBLE);
                } else {
                    holder.editCustom.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        holder.spinnerTitle.setSelection(item.title);
        holder.spinnerSign.setSelection(item.sign);
        holder.editInfo.setText(item.info);
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

    boolean isFilterable = false;

    public void setFilterable(boolean isFilterable) {
        this.isFilterable = isFilterable;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
