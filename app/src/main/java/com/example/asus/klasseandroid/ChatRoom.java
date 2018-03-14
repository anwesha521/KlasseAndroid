package com.example.asus.klasseandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ChatRoom extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseListAdapter<ChatMessage> adapter;
    private EditText input;
    ListView listOfMessages;
    int type = 0;
    String q="";
    int room_id;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String t;

    @Override
    protected void onStart()
    {
        super.onStart();
        displayChatMessages();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Intent intent = getIntent();
        pref=getApplicationContext().getSharedPreferences("Messages",MODE_PRIVATE);
        editor = pref.edit();
        room_id = intent.getIntExtra("id", 11);
        Log.i("anwesha",room_id+"=room id");

        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        listOfMessages = (ListView) findViewById(R.id.list_of_messages);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = (EditText) findViewById(R.id.input);
                if (type == 1) {
                    ChatMessage cm = new ChatMessage(input.getText().toString(),
                            FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getDisplayName(), "reply", room_id);
                    editor.putString(input.getText().toString()+"question",q);
                    editor.putInt(input.getText().toString()+"id",room_id);
                    t="reply";

                    FirebaseDatabase.getInstance()
                            .getReference()
                            .push()
                            .setValue(cm);
                    type = 0;



                } else {
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .push()
                            .setValue(new ChatMessage(input.getText().toString(),
                                    FirebaseAuth.getInstance()
                                            .getCurrentUser()
                                            .getDisplayName(), "question", room_id)
                            );
                    editor.putInt(input.getText().toString()+"id",room_id);
                    t="question";


                }
                editor.putString(input.getText().toString(), t);
                editor.commit();
                input.setText("");
                displayChatMessages();
            }
        });


    }

    public void displayChatMessages() {

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, final ChatMessage model, final int position) {

                TextView messageText = (TextView) v.findViewById(R.id.message_text);
                TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                TextView messageTime = (TextView) v.findViewById(R.id.message_time);

                // Set their text
                final String message = model.getMessageText();
                String msgtype = pref.getString(message, "question");
                int id = pref.getInt(message+"id",11);
                Log.i("anweshaid",id+"");

                if (id == room_id) {
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            new AlertDialog.Builder(ChatRoom.this)
                                    .setMessage(
                                            "Reply to Question?")
                                    .setPositiveButton(
                                            "Okay",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                    dialog.cancel();
                                                    type = 1;
                                                    q = model.getMessageText();


                                                }
                                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    dialog.cancel();

                                }
                            }).show();
                            Log.i("anwesha", "type=" + type);
                            return false;
                        }
                    });

                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                            model.getMessageTime()));
                    messageUser.setText(model.getMessageUser());

                    if (msgtype.equals("reply")) {
                        messageText.setText(pref.getString(message+"question","")+": " + message);
                        boolean ver = pref.getBoolean(message+"verified", false);
                        if ((model != null)&&(ver == true))
                            v.setBackgroundColor(Color.parseColor("#f7f26c"));
                        else
                            v.setBackgroundColor(Color.parseColor("#88f7a7"));


                    } else

                        messageText.setText(message);


                }
            }

        };
        listOfMessages.setAdapter(adapter);
    }
}
