package com.example.ssuny;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends ArrayAdapter<String> {

    private List<String> searchHistoryList;

    public SearchResultAdapter(Context context) {
        super(context, 0);
        searchHistoryList = new ArrayList<>();
    }

    public void setSearchHistoryList(List<String> searchHistoryList) {
        this.searchHistoryList = searchHistoryList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return searchHistoryList.size();
    }

    @Override
    public String getItem(int position) {
        return searchHistoryList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_history_item, parent, false);
        }

        TextView textViewSearchHistory = convertView.findViewById(R.id.search_result_list);
        textViewSearchHistory.setText(getItem(position));

        return convertView;
    }
}
