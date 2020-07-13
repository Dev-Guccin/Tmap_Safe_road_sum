package com.example.tsafe_load;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Search_Entity> itemLists = new ArrayList<>();
    private RecyclerViewAdapterCallback callback;
    private POIHandler poihandler;
    private String point;

    public RecyclerViewAdapter(){
        poihandler = new POIHandler();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView address;

        public CustomViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            address = (TextView) itemView.findViewById(R.id.tv_address);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int ItemPosition = position;

        if (holder instanceof CustomViewHolder) {
            CustomViewHolder viewHolder = (CustomViewHolder) holder;

            viewHolder.title.setText(itemLists.get(position).getTitle());
            viewHolder.address.setText(itemLists.get(position).getAddress());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.showToast(ItemPosition);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public void setData(ArrayList<Search_Entity> itemLists) {
        this.itemLists = itemLists;
    }

    public void setCallback(RecyclerViewAdapterCallback callback) {
        this.callback = callback;
    }

    public void filter(String keyword) {
        if (keyword.length() >= 2) {
            TMapData tMapData = new TMapData();
            tMapData.findAllPOI(keyword, new TMapData.FindAllPOIListenerCallback() {
                @Override
                public void onFindAllPOI(ArrayList poiItem) {
                    itemLists.clear();
                    for (int i = 0; i < poiItem.size(); i++) {
                        String fullAddr;
                        TMapPOIItem item = (TMapPOIItem) poiItem.get(i);
                        fullAddr = item.upperAddrName + " " + item.middleAddrName +
                                " " + item.lowerAddrName + " " + item.getPOIName();
                        Log.d("POI Name: ", item.getPOIName().toString() + ", " +
                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                "Point: " + item.getPOIPoint().toString());
                        itemLists.add(new Search_Entity(item.getPOIName(), fullAddr));
                        Log.d("크기", String.valueOf(itemLists.size()));
                        if(i==0) {
                            point = item.getPOIPoint().toString();
                        }
                    }
                    poihandler.sendEmptyMessage(0);

                }
            });
        }
    }

    public class POIHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what ==0 ){
                notifyDataSetChanged();
            }
        }
    }

    public String return_to_value(int position){
        return itemLists.get(position).getAddress();
    }

    public String return_to_point() {
        return point;
    }
}