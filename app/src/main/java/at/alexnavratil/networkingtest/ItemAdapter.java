package at.alexnavratil.networkingtest;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alexnavratil on 30.06.14.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item> dataList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CheckBox mCheckBox;

        public ViewHolder(View v){
            super(v);
            this.mCheckBox = (CheckBox)v.findViewById(R.id.checkBox);
        }
    }

    public ItemAdapter(List<Item> dataList){
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_view, viewGroup, false);
        v.setBackgroundColor(Color.WHITE);
        v.setElevation(5);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) v.getLayoutParams();
        params.setMargins(0,0,0,10);
        v.setLayoutParams(params);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.mCheckBox.setChecked(dataList.get(i).isChecked());
        viewHolder.mCheckBox.setText(dataList.get(i).getText());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
