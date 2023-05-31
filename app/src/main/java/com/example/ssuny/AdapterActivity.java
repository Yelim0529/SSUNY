package com.example.ssuny;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class AdapterActivity extends BaseAdapter {

//    public ArrayList<Time> listviewitem = new ArrayList<Time>();
//    private ArrayList<Time> arrayList = listviewitem;
    private ArrayList<Time> arrayList = new ArrayList<Time>();
    //백업 arrayList

    @Override public int getCount() {
        return arrayList.size();
    }

    @Override public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_theme, parent, false);

            TextView name = (TextView)convertView.findViewById(R.id.alarm_name);
            TextView hourText = (TextView)convertView.findViewById(R.id.time);
            TextView minuteText = (TextView)convertView.findViewById(R.id.minute);
            TextView am_pm = (TextView)convertView.findViewById(R.id.am_pm);
            TextView year = (TextView)convertView.findViewById(R.id.year);
            TextView month = (TextView)convertView.findViewById(R.id.month);
            TextView day = (TextView)convertView.findViewById(R.id.day);

            holder.name = name;
            holder.hourText = hourText;
            holder.minuteText = minuteText;
            holder.am_pm = am_pm;
            holder.year = year;
            holder.month = month;
            holder.day = day;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Time time = arrayList.get(position);
        holder.name.setText(time.getName());
        holder.am_pm.setText(time.getAm_pm());
        holder.hourText.setText(time.getHour()+ "시");
        holder.minuteText.setText(time.getMinute()+ "분");
        holder.year.setText(time.getYear()+ "년 ");
        holder.month.setText(time.getMonth()+ "월 ");
        holder.day.setText(time.getDay()+ "일");

        return convertView;
    }

    public void addItem(String name, int hour, int minute, String am_pm, int year, int month, int day) {
        Time time = new Time();
        time.setName(name);
        time.setHour(hour);
        time.setMinute(minute);
        time.setAm_pm(am_pm);
        time.setYear(year);
        time.setMonth(month);
        time.setDay(day);
        arrayList.add(time);
    }

    public void addItem(ArrayList<Time> timelist) {
        arrayList = timelist;
        //arrayList.add(timelist.get(0));
    }

    //List 삭제 method
    public void removeItem(int position) {
        if(arrayList.size() < 1) {

        } else {
            arrayList.remove(position);
        }
    }
    public void removeItem() {
        if(arrayList.size() < 1) {

        } else {
            arrayList.remove(arrayList.size()-1);
        }
    }

    static class ViewHolder {
        TextView name, hourText, minuteText, am_pm, year, month, day;
    }
}
