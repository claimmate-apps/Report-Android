package com.cwclaims.claimapp.adpts;

import android.content.Context;
import androidx.annotation.NonNull;
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

public class CeilingAdapter extends RecyclerView.Adapter<CeilingAdapter.MyViewHolder> implements Filterable {

    public String TAG = getClass().getSimpleName();

    private List<InteriorRes.Ceiling> data = new ArrayList<>();

    Context context;
    int i = 2;

    private EventListener mEventListener;

    public CeilingAdapter(Context context) {
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

    public InteriorRes.Ceiling getItem(int position) {
        return data.get(position);
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_interior_ceiling, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
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

    public interface EventListener {
        void onItemViewClicked(int position);

        void onItemSpinnerClicked(int position);
    }

    public void setEventListener(EventListener eventListener) {
        mEventListener = eventListener;
    }

    boolean isFilterable = false;

    public void setFilterable(boolean isFilterable) {
        this.isFilterable = isFilterable;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        Spinner spinnerTitle, spinnerSign;
        EditText editInfo, editCustom;
        public MyViewHolder(View itemView) {
            super(itemView);

            spinnerTitle = itemView.findViewById(R.id.spinnerTitle);
            spinnerSign = itemView.findViewById(R.id.spinnerSign);

            editInfo = itemView.findViewById(R.id.editInfo);
            editCustom = itemView.findViewById(R.id.editCustom);
        }
    }
}
