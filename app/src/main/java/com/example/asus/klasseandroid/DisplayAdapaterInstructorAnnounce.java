package com.example.asus.klasseandroid;

/**
 * Created by ASUS on 20-02-2018.
 */
import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

public class DisplayAdapaterInstructorAnnounce extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> content;
    private ArrayList<String> names;
    private ArrayList<Integer> ids;
    SharedPreferences pref;
    SharedPreferences.Editor ed;
    String HTTPUrl="http://192.168.1.185/Klasse/delete_announce.php?pid=";


    public DisplayAdapaterInstructorAnnounce(Context c,ArrayList<String> text,ArrayList<String> n,ArrayList<Integer> i) {
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
            child = layoutInflater.inflate(R.layout.instructor_announcement, null);
            mHolder = new Holder();
            mHolder.txt_content = (TextView) child.findViewById(R.id.instructannounceview);
            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }


        mHolder.txt_content.setText(content.get(position));
        child.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setMessage(
                                "Remove announcement?")
                        .setPositiveButton(
                                "Okay",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        Log.i("anwesha","reached");
                                        dialog.cancel();
                                        int pid=ids.get(position);
                                       removeAnnounce(pid,position);


                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(
                            DialogInterface dialog,
                            int which) {
                        dialog.cancel();
                    }
                    }).show();
                return false;
            }
        });

        return child;
    }
    public void removeAnnounce(int i, int p)
    {
        final int pos=p;
        HTTPUrl=HTTPUrl+String.valueOf(i);
        Log.i("anwesha","url="+HTTPUrl);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest delete_request = new JsonObjectRequest(HTTPUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    int success = response.getInt("success");
                    Log.i("anwesha","succ"+success);
                    if (success == 1) {

                        Toast.makeText(mContext,
                                "Announcement deleted!",
                                Toast.LENGTH_SHORT).show();
                        names.remove(pos);
                        content.remove(pos);
                        ids.remove(pos);
                        notifyDataSetChanged();



                    } else {

                        Toast.makeText(mContext,
                                "Announcement not deleted, try again later.",
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Adding request to request queue
        MyRequestQueue.add(delete_request);

    }

    public class Holder {
        TextView txt_content;


    }

}
