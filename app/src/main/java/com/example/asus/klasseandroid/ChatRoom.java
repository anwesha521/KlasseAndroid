package com.example.asus.klasseandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    SharedPreferences prefName;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorName;
    String t;
    String id;

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
        prefName=getApplicationContext().getSharedPreferences("UserDetails",MODE_PRIVATE);
        editorName=prefName.edit();
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
                String txt=input.getText().toString();
                if (TextUtils.isEmpty(txt)) {
                    Toast.makeText(ChatRoom.this, "Please enter message.", Toast.LENGTH_LONG).show();
                } else {

                    if (type == 1) {


                        id = FirebaseDatabase.getInstance()
                                .getReference()
                                .push().getKey();
                        ChatMessage cm = new ChatMessage(txt,
                                prefName.getString("name", "Anonymous"), "reply", room_id, id);
                        cm.setQuestion(q);

                        FirebaseDatabase.getInstance()
                                .getReference().child(id).setValue(cm);
                        type = 0;


                    } else {
                        id = FirebaseDatabase.getInstance()
                                .getReference()
                                .push().getKey();
                        ChatMessage cm = new ChatMessage(txt,
                                prefName.getString("name", "Anonymous"), "question", room_id, id);

                        FirebaseDatabase.getInstance()
                                .getReference().child(id).setValue(cm);


                    }
                }
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
                final int id = model.get_id();
                Log.i("anweshaid",id+"");

                if (id == room_id) {
                    if(model.getMessageType().equalsIgnoreCase("question"))
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

                    if (model.getMessageType().equals("reply"))
                    {
                        messageText.setText(model.getQuestion()+":" + message);

                        if ((model != null)&&(model.getVerified()))
                            v.setBackground(getResources().getDrawable(R.drawable.verified_bubble));
                        else
                            v.setBackground(getResources().getDrawable(R.drawable.reply_bubble));


                    } else {
                        v.setBackground(getResources().getDrawable(R.drawable.question_bubble));
                        messageText.setText(message);
                    }


                }
            }

        };
        listOfMessages.setAdapter(adapter);
    }
}