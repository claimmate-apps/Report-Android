package com.cwclaims.claimapp.adpts;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cwclaims.claimapp.R;

import java.util.ArrayList;

public class SpinnerAdpt extends BaseAdapter {

    Context context;
    ArrayList<String> arrayList;

    public SpinnerAdpt(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.item_spinner, null);

        TextView text1 = view.findViewById(R.id.txtItem);

        text1.setText(arrayList.get(i));

        if (arrayList.get(i).equalsIgnoreCase("n/a"))
//            text1.setBackgroundColor(context.getResources().getColor(R.color.n_a));
            text1.setBackgroundResource(R.drawable.rect_n_a_border);
        else if (arrayList.get(i).equalsIgnoreCase("insert custom data"))
//            text1.setBackgroundColor(context.getResources().getColor(R.color.insert_custom_data));
            text1.setBackgroundResource(R.drawable.rect_insert_custom_data_border);
        else if (arrayList.get(i).equalsIgnoreCase("add new option"))
            text1.setBackgroundResource(R.drawable.rect_add_new_option_border);
//            text1.setBackgroundColor(context.getResources().getColor(R.color.add_new_option));

        return view;
    }
}
