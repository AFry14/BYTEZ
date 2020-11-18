package com.example.Bytez_frontend.Features;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class ChatActivity extends AppCompatActivity {

    // Current logged in user
    private User currentUser;

    // Friend that the logged in user is chatting with
    private User friend;

    // Websocket client used for communication
    private WebSocketClient webSocketClient;

    // TextView used as the chat window
    private TextView chatWindow;

    // EditText used to send message to friend from logged in user
    private EditText sendTextView;

    // Buttons used in the xml file for clearing message history, disconnecting from the websocket server, and sending a message
    private Button clearHistoryButton, disconnectButton, sendButton;

    // Volley request queue
    private RequestQueue reqQueue;

    /**
     * Chat activity allows 2 friends to communicate with each other in real time using websockets
     * Each user can leave the websocket and the messages will be preserved until a user deletes the
     * chat history
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Receive user information from previous activity
        Intent i = getIntent();
        friend = i.getParcelableExtra("user");
        currentUser = i.getParcelableExtra("currentUser");
        setTitle("Message " + friend.getUsername());

        // Set xml properties
        clearHistoryButton = findViewById(R.id.clearHistoryButton);
        disconnectButton = findViewById(R.id.disconnectButton);
        sendButton = findViewById(R.id.sendButton);
        chatWindow = findViewById(R.id.chatWindowView);
        chatWindow.setMovementMethod(new ScrollingMovementMethod());
        sendTextView = findViewById(R.id.messageEditText);

        // Set new request queue
        reqQueue = Volley.newRequestQueue(this);


        // OnClick functionality for all three buttons in the chat activity

        // All messages on the server and on the app will be erased when clear history is clicked
        clearHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear chatWindow of messages
                chatWindow.setText("");

                // Delete server history volley request
                String deleteChatHistoryURL = URLs.URL_DELETE_CHAT_HISTORY + currentUser.getUsername() + "/" + friend.getUsername();

                JsonObjectRequest deleteChatHistory = new JsonObjectRequest(Request.Method.DELETE, deleteChatHistoryURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("Response", response);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("Error", error);
                    }
                });
                reqQueue.add(deleteChatHistory);
            }
        });

        // Disconnects from the websocket server when the disconnect button is clicked, does nothing if not connected or null
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the client is not null
                if (webSocketClient != null) {
                    Boolean websocketOpen = webSocketClient.isOpen();
                    // if the websocket client is open, then close it when the disconnect button is clicked
                    if (websocketOpen == true) {
                        webSocketClient.close();
                        finish();
                    }
                }
            }
        });

        // Sends a message from the sendTextView when the send button is clicked, does nothing if not connected or null
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the client is not null
                if (webSocketClient != null) {
                    Boolean websocketOpen = webSocketClient.isOpen();

                    // If websocket is open, send message
                    if (websocketOpen == true) {
                        String messageToSend = sendTextView.getText().toString();

                        // If the message is not null and its length is greater than 0, send the message
                        if (messageToSend != null && messageToSend.length() > 0) {
                            webSocketClient.send(messageToSend);
                            sendTextView.setText("");
                        }
                    }
                }
            }
        });

        // On the creation of the activity, open the websocket client if the client is null or is not open
        if (webSocketClient == null) {
            connectWebSocket();
        } else {
            Boolean websocketOpen = webSocketClient.isOpen();
            // if the websocket client is not open, open one
            if (websocketOpen == false) {
                connectWebSocket();
            }
        }

    }


    /**
     * Method for connecting to web socket server
     */
    private void connectWebSocket() {
        URI uri;

        // Try connecting to websocket server
        try {
            uri = new URI(URLs.URL_WEBSOCKET + currentUser.getUsername() + "/" + friend.getUsername());
            //connectButton.setText("Connection Successful!");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        // New websocket client
        webSocketClient = new WebSocketClient(uri) {

            /**
             * Method when websocket client is opened
             * @param serverHandshake
             */
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
            }

            /**
             * Method when a message is received, adds message to chat window when the client receives it
             * @param msg
             */
            @Override
            public void onMessage(String msg) {
                Log.i("Websocket", "Message Received");
                // Appends the message received to the chat window
                chatWindow.append("\n" + msg);
            }

            /**
             * Method when the websocket client is closed
             * @param errorCode
             * @param reason
             * @param remote
             */
            @Override
            public void onClose(int errorCode, String reason, boolean remote) {
                Log.i("Websocket", "Closed " + reason);
            }

            /**
             * Method when the websocket client generates an error
             * @param e
             */
            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        webSocketClient.connect();
    }

    /**
     * When the activity is destroyed, close the websocket client
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}