package com.example.asus.klasseandroid;

import android.content.DialogInterface;
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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;


public class ChatRoomInstructor extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseListAdapter<ChatMessage> adapter;
    private EditText input;
    ListView listOfMessages;
    int type = 0;
    String q="";
    boolean val=false;
    ChatMessage mod;

    @Override
    protected void onStart() {
        super.onStart();
        displayChatMessages();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        listOfMessages = (ListView) findViewById(R.id.list_of_messages);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = (EditText) findViewById(R.id.input);
                if (type == 1) {
                    ChatMessage cm=new ChatMessage(input.getText().toString(),
                            FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getDisplayName(), "reply");
                    cm.setQuestion(q);

                    FirebaseDatabase.getInstance()
                            .getReference()
                            .push()
                            .setValue(cm);
                    type=0;



                } else {
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .push()
                            .setValue(new ChatMessage(input.getText().toString(),
                                    FirebaseAuth.getInstance()
                                            .getCurrentUser()
                                            .getDisplayName(), "question")
                            );

                }


                displayChatMessages();
                // Clear the input
                input.setText("");
            }
        });

    }

    public void displayChatMessages() {

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, final ChatMessage model, final int position)
            {
                // Get references to the views of message.xml
                Log.i("anwesha","val="+val);
                if(mod!=null)
                { Log.i("anwesha",mod.getMessageText()+" mod text");
                mod.setVerified();}

                TextView messageText = (TextView) v.findViewById(R.id.message_text);
                TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                TextView messageTime = (TextView) v.findViewById(R.id.message_time);

                // Set their text



                Log.i("anwesha",model.getMessageText()+" "+model.getVerified()+"");

                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
                messageUser.setText(model.getMessageUser());

                if (model.getMessageType().equals("reply"))
                {
                    messageText.setText(model.getQuestion()+model.getMessageText());

                    if((mod!=null)&&(model.getMessageText().equals(mod.getMessageText())))
                    {

                        Log.i("anwesha","verified");
                        model.setVerified();

                        v.setBackgroundColor(Color.parseColor("#f7f26c"));
                      String k=FirebaseDatabase.getInstance().getReference().child("verified").getKey();



                    }
                    else
                        v.setBackgroundColor(Color.parseColor("#88f7a7"));


                }
                else

                    messageText.setText(model.getMessageText());

                v.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new AlertDialog.Builder(ChatRoomInstructor.this)
                                .setMessage(
                                        "Verify?")
                                .setPositiveButton(
                                        "Okay",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                if(model.getMessageType().equals("reply"))
                                                {
                                                val=true;
                                                mod=model;}
                                                else
                                                    Toast.makeText(ChatRoomInstructor.this, "Can onlyverify replies", Toast.LENGTH_LONG).show();
                                                dialog.cancel();
                                                displayChatMessages();

                                            }
                                        }).setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
                                dialog.cancel();

                            }
                        }).show();
                        return false;
                    }
                });



            }


        };
        listOfMessages.setAdapter(adapter);
    }

    public void verify(View v)
    {
       mod.setVerified();
       v.setBackgroundColor(Color.parseColor("#f7f26c"));
    }
}
