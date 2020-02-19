package com.cwclaims.claimapp.adpts;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.models.InteriorRes;

import java.util.ArrayList;
import java.util.List;
import com.cwclaims.claimapp.models.InteriorRes.Ceiling;

public class InteriorAdapter
        extends RecyclerView.Adapter<InteriorAdapter.MyViewHolder> implements Filterable {

    public String TAG = getClass().getSimpleName();
    int i = 1;
    Context context;

    private EventListener mEventListener;

    public interface EventListener {
        void onItemViewClicked(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAddCeiling, imgAddWalls, imgAddFloor, imgAddPrep;
        RecyclerView recyclerCeiling, recyclerWalls, recyclerFloor, recyclerPrep;

        CeilingAdapter ceilingAdapter;
        WallAdapter wallAdapter;
        FloorAdapter floorAdapter;
        PrepAdapter prepAdapter;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgAddCeiling = itemView.findViewById(R.id.imgAddCeiling);
            imgAddWalls = itemView.findViewById(R.id.imgAddWalls);
            imgAddFloor = itemView.findViewById(R.id.imgAddFloor);
            imgAddPrep = itemView.findViewById(R.id.imgAddPrep);

            recyclerCeiling = itemView.findViewById(R.id.recyclerCeiling);
            recyclerWalls = itemView.findViewById(R.id.recyclerWalls);
            recyclerFloor = itemView.findViewById(R.id.recyclerFloor);
            recyclerPrep = itemView.findViewById(R.id.recyclerPrep);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerCeiling.setLayoutManager(mLayoutManager);

            ceilingAdapter = new CeilingAdapter(context);
            recyclerCeiling.setAdapter(ceilingAdapter);

            List<InteriorRes.Ceiling> ceilings = new ArrayList<>();
            ceilings.add(new InteriorRes.Ceiling(1, 2, "From Camera"));
            ceilings.add(new InteriorRes.Ceiling(2, 2, "plus above"));
            ceilings.add(new InteriorRes.Ceiling(3, 0, "plus above"));
            ceilings.add(new InteriorRes.Ceiling(4, 0, "W or Zero"));
            ceilings.add(new InteriorRes.Ceiling(0, 0, ""));

            ceilingAdapter.addAll(ceilings);

            ceilingAdapter.setEventListener(new CeilingAdapter.EventListener() {
                @Override
                public void onItemViewClicked(int position) {

                }

                @Override
                public void onItemSpinnerClicked(int position) {
                    if (position == ceilingAdapter.getItemCount() - 1) {
                        ceilingAdapter.add(new InteriorRes.Ceiling(0, 0, ""));
                    }
                }
            });

            RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(context);
            recyclerWalls.setLayoutManager(mLayoutManager2);

            wallAdapter = new WallAdapter(context);
            recyclerWalls.setAdapter(wallAdapter);

            List<InteriorRes.Ceiling> walls = new ArrayList<>();
            walls.add(new Ceiling(1, 2, "From Camera"));
            walls.add(new Ceiling(2, 2, "plus above"));
            walls.add(new Ceiling(3, 0, "plus above"));
            walls.add(new Ceiling(4, 0, "W or Zero"));
            walls.add(new Ceiling(0, 0, ""));

            wallAdapter.addAll(walls);

            wallAdapter.setEventListener(new WallAdapter.EventListener() {
                @Override
                public void onItemViewClicked(int position) {

                }

                @Override
                public void onItemSpinnerClicked(int position) {
                    if (position == wallAdapter.getItemCount() - 1) {
                        wallAdapter.add(new Ceiling(0, 0, ""));
                    }
                }
            });

            RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(context);
            recyclerFloor.setLayoutManager(mLayoutManager3);

            floorAdapter = new FloorAdapter(context);
            recyclerFloor.setAdapter(floorAdapter);

            List<InteriorRes.Ceiling> floor = new ArrayList<>();
            floor.add(new Ceiling(1, 0, "clean or replace"));
            floor.add(new Ceiling(0, 0, "^ F"));

            floorAdapter.addAll(floor);

            floorAdapter.setEventListener(new FloorAdapter.EventListener() {
                @Override
                public void onItemViewClicked(int position) {

                }

                @Override
                public void onItemSpinnerClicked(int position) {
                    if (position == floorAdapter.getItemCount() - 1) {
                        floorAdapter.add(new Ceiling(0, 0, ""));
                    }
                }
            });

            RecyclerView.LayoutManager mLayoutManager4 = new LinearLayoutManager(context);
            recyclerPrep.setLayoutManager(mLayoutManager4);

            prepAdapter = new PrepAdapter(context);
            recyclerPrep.setAdapter(prepAdapter);

            List<InteriorRes.Ceiling> prep = new ArrayList<>();
            prep.add(new InteriorRes.Ceiling(1, 0, "wrong option"));
            prep.add(new InteriorRes.Ceiling(0, 0, ""));

            prepAdapter.addAll(prep);

            prepAdapter.setEventListener(new PrepAdapter.EventListener() {
                @Override
                public void onItemViewClicked(int position) {

                }

                @Override
                public void onItemSpinnerClicked(int position) {
                    if (position == prepAdapter.getItemCount() - 1) {
                        prepAdapter.add(new InteriorRes.Ceiling(0, 0, ""));
                    }
                }
            });
        }
    }

    public void add() {
        i++;
        notifyDataSetChanged();
    }

    public InteriorAdapter(Context context) {
        this.context = context;

        setHasStableIds(true);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_interior, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return i;
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
