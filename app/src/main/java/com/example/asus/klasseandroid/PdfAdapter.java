package com.example.asus.klasseandroid;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Manish on 11/1/2016.
 */


public class PdfAdapter extends ArrayAdapter<PDF>
{
    Activity activity;
    int layoutResourceId;
    ArrayList<PDF> data=new ArrayList<PDF>();
    PDF pdf;

    public PdfAdapter(Activity activity, int layoutResourceId, ArrayList<PDF> data) {
        super(activity, layoutResourceId, data);
        this.activity=activity;
        this.layoutResourceId=layoutResourceId;
        this.data=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        PdfHolder holder=null;
        if(row==null)
        {
            LayoutInflater inflater=LayoutInflater.from(activity);
            row=inflater.inflate(layoutResourceId,parent,false);
            holder=new PdfHolder();
            holder.viewpdf= (Button) row.findViewById(R.id.pdfView);
            holder.feedback= (Button) row.findViewById(R.id.pdffeedback);
            holder.name=(TextView)row.findViewById(R.id.pdfName);
            row.setTag(holder);
        }
        else
        {
            holder= (PdfHolder) row.getTag();
        }

        pdf = data.get(position);

        holder.name.setText("Name: "+pdf.getName());
        holder.viewpdf.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(pdf.getUrl()));
                activity.getApplicationContext().startActivity(intent);

            }
        });

        holder.feedback.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Add in your give feedback code here

            }
        });

        return row;
    }


    class PdfHolder
    {
        Button viewpdf,feedback;
        TextView name;
    }

}