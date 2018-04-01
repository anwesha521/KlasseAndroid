package com.example.asus.klasseandroid;

/**
 * Created by ASUS on 20-02-2018.
 */
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.TextView;

public class DisplayAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> content;
    private ArrayList<String> names;
    private ArrayList<Integer> ids;
    SharedPreferences pref;
    SharedPreferences.Editor ed;




    public DisplayAdapter(Context c,ArrayList<String> text,ArrayList<String> n,ArrayList<Integer> i) {
        this.mContext = c;
        this.names=n;
        this.content = text;
        this.ids=i;
        pref=mContext.getSharedPreferences("AnmntMsg",Context.MODE_PRIVATE);
        ed=pref.edit();
     }


    public int getCount() {
        // TODO Auto-generated method stub
        return content.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int pos, View child, ViewGroup parent) {
        final Holder mHolder;
        final int position=pos;
        LayoutInflater layoutInflater;




            if (child == null) {
                layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                child = layoutInflater.inflate(R.layout.announcement, null);
                mHolder = new Holder();
                mHolder.txt_content = (TextView) child.findViewById(R.id.announcetext);
                mHolder.check = (CheckBox) child.findViewById(R.id.checkannounce);
                mHolder.profname = (TextView) child.findViewById(R.id.announceprof);
                child.setTag(mHolder);
            } else {
                mHolder = (Holder) child.getTag();
            }

            Log.i("anweshaid",ids.get(position)+" "+content.get(position));
            mHolder.txt_content.setText(content.get(position));
            Log.i("anweshaheck",pref.getBoolean(ids.get(position)+" ",false)+" ");
            mHolder.check.setChecked(pref.getBoolean(ids.get(position)+" ",false));
            mHolder.profname.setText(names.get(position));


            mHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                  if(mHolder.check.isShown()) {
                      if (isChecked) {
                          Log.i("anwesha", "entered");
                          ed.putBoolean(ids.get(position) + " ", true);
                          ed.commit();
                          notifyDataSetChanged();


                      }
                  }

                }
            });



        return child;
    }

    public class Holder {
        TextView txt_content;
        TextView profname;
        CheckBox check;


    }

}
