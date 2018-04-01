package com.example.asus.klasseandroid;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.facebook.FacebookSdk.getApplicationContext;


public class PdfAdapter extends ArrayAdapter<PDF>
{
    Activity activity;
    int layoutResourceId;
    ArrayList<PDF> data=new ArrayList<PDF>();
    PDF pdf;
    String url = "http://10.12.48.32/feedback.php";

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

        holder.name.setText(pdf.getName());
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


                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                LayoutInflater inflater = LayoutInflater.from(activity);
                View dialogView = inflater.inflate(R.layout.send_feedback_popup,null);

                // Specify alert dialog is not cancelable/not ignorable
                builder.setCancelable(false);

                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                // Get the custom alert dialog view widgets reference
                Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
                Button btn_negative = (Button) dialogView.findViewById(R.id.dialog_negative_btn);
                final EditText feedbackText = (EditText) dialogView.findViewById(R.id.enter_feedback);
                final EditText pgNumberText = (EditText) dialogView.findViewById(R.id.enter_pg_number);

                // Create the alert dialog
                final AlertDialog dialog = builder.create();

                // Set positive/yes button click listener
                btn_positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the alert dialog


                        dialog.cancel();
                        String feedback = feedbackText.getText().toString();
                        String pg = pgNumberText.getText().toString();
                        ///////////I NEED TO FIX THE PAGE NUMBER ISSUE!!!///////
                        String pdfFileName = pdf.getName();

                        if (feedback.trim().equals(null)){
                            Toast.makeText(activity, "Feedback empty!", Toast.LENGTH_SHORT).show();
                        }
                        else if(pg.trim().equals(null)){
                            Toast.makeText(activity, "Write page number!", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            submitFeedback(feedback, pdfFileName, pg);
                        }
                    }
                });

                // Set negative/no button click listener
                btn_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss/cancel the alert dialog
                        //dialog.cancel();
                        dialog.dismiss();
                        Toast.makeText(activity, "No button clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                // Display the custom alert dialog on interface
                dialog.show();

            }
        });

        return row;
    }


    class PdfHolder
    {
        Button viewpdf,feedback;
        TextView name;
    }
    private void submitFeedback(final String comments, final String filename, final String pgNumber) {

        RequestQueue MyRequestQueue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(activity, "Submitted feedback!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                //Log.e("VOLLEY", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("feedback", comments);
                params.put("pdfFileName",filename);
                params.put("pgNumber",pgNumber);


                return params;
            }
        };

        MyRequestQueue.add(stringRequest);

    }


}