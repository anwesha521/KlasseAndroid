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

public class FeedbackDisplayAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> content;
    private ArrayList<String> pdfFileNames;
    private ArrayList<Integer> ids;




    public FeedbackDisplayAdapter(Context c,ArrayList<String> content,ArrayList<String> pdfFileNames,ArrayList<Integer> i) {
        this.mContext = c;
        this.pdfFileNames=pdfFileNames;
        this.content = content;
        this.ids=i;
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
            child = layoutInflater.inflate(R.layout.activity_feedback_layout, null);
            mHolder = new Holder();
            mHolder.feedbackContent = (TextView) child.findViewById(R.id.feedbackcontent);
            mHolder.pdfFileName = (TextView) child.findViewById(R.id.pdfFileName);
            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }

        Log.i("anweshaid",ids.get(position)+" "+content.get(position));
        mHolder.feedbackContent.setText(content.get(position));
        mHolder.pdfFileName.setText(pdfFileNames.get(position));




        return child;
    }

    public class Holder {
        TextView feedbackContent;
        TextView pdfFileName;


    }

}
