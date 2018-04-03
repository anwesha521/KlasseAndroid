package com.example.asus.klasseandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import announcements.InstructorAnnounce;


class Update  {

    static ArrayList<Student1> studentList = new ArrayList<>();

    public static void calculate(String instid,SharedPreferences pref, String HTTPUrl, String HTTPUrlinsert, Context c, int class_id) {

        Log.i("anweshawhat",HTTPUrl+"?student_id="+pref.getString("id","0"));
        final int room_id=class_id;
        final Context c1=c;
        final String HTTPUrlInsert1=HTTPUrlinsert;
        final String instid1=instid;


        RequestQueue requestQueue = Volley.newRequestQueue(c);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                HTTPUrl+"?student_id="+pref.getString("id","0"),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                         studentList.clear();
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject ann = response.getJSONObject(i);
                                Log.i("anweshagetweek",ann.getInt("week")+"ttt");

                                  studentList.add(new Student1(ann.getString("student_id"), ann.getInt("week"), ann.getString("type"), ann.getString("answers"), ann.getString("correct"), ann.getString("marks"), ann.getInt("total")));


                            }
                            Log.i("anwesha","testingwhy");
                            calculate1(studentList);
                            update(c1,HTTPUrlInsert1,instid1,room_id);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("anwesha", e.getMessage().toString()+"gdgdgdgd");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Log.i("anwesha", error.getMessage());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);


    }

    public static void update(Context c1,String HTTPUrlInsert1, final String instid1, final int room_id ) {
        for (Student1 stu : studentList) {
            final Student1 s = stu;

            

            RequestQueue MyRequestQueue = Volley.newRequestQueue(c1);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HTTPUrlInsert1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Document doc = Jsoup.parse(response);
                    String result = doc.body().text();
                    Log.i("anwesha", "updated" + result);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.i("anwesha", error.getMessage().toString() + "in update");
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Log.i("anweshatestwk",s.week+"  ");
                    Map<String, String> params = new HashMap<>();
                    params.put("student_id", s.id + "");
                    params.put("instructor_id", instid1);
                    params.put("percentage", Math.round(s.percentage) + "");
                    params.put("class_id", room_id + "");
                    params.put("week", s.week + "");


                    return params;
                }
            };

            MyRequestQueue.add(stringRequest);
            break;

        }
    }


    public static void calculate1(ArrayList<Student1> sl) {
        for (Student1 s : sl) {
            String[] answer = parseArray(s.answer);
            String[] correct = parseArray(s.correct);
            String[] marks = parseArray(s.marks);
            String[] type = parseArray(s.type);
            double gradeGot = 0;

            /*if (!checkLength(answer, correct, marks, type)) {
                //  System.out.println("Length error happens for student: "+s.id);
                Log.i("anwesha", "Length error happens for student: " + s.id);
                continue;
            }*/

            for (int i = 0; i < answer.length; ++i) {
                int thisQuestion = Integer.parseInt(marks[i]);

                if (type[i].trim().equals("MCQ")) {
                    if (answer[i].toLowerCase().equals(correct[i].toLowerCase())) {
                        gradeGot += thisQuestion;
                        Log.i("anwesha", gradeGot + " gg");
                    }
                } else if (type[i].trim().equals("QNA")) {
                    if (checkQNA(answer[i], correct[i])) {
                        gradeGot += thisQuestion;
                        Log.i("anwesha", gradeGot + " gg1");
                    }
                } else {
                   // System.out.println("Type error happens for student: " + s.id);
                }
            }

            s.percentage = 100 * gradeGot / s.total;
        }
    }

    public static boolean checkQNA(String answer, String correct) {
        String[] ca = correct.split(",");
        for (String s : ca) {
            if (!answer.toLowerCase().contains(s.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public static String[] parseArray(String s) {
        s = s.substring(1, s.length() - 1);
        return s.split(",");
    }

    public static boolean checkLength(String[] a1, String[] a2, String[] a3, String[] a4) {
        return a1.length == a2.length && a2.length == a3.length && a3.length == a4.length;
    }



}

 class Student1{
        String id;
        int week;
        String answer;
        String correct;
        String marks;
        String type;
        int total;
        double percentage;

        public Student1(String id,int week,String type,String answer,String correct,String marks,int total){
            this.answer=answer;
            this.correct=correct;
            this.id=id;
            this.marks=marks;
            this.week=week;
            this.total=total;
            this.type=type;
        }
    }


