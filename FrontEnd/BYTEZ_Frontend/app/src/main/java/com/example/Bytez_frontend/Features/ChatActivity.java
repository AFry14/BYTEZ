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

import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class ChatActivity extends AppCompatActivity {

    private User currentUser;
    private User friend;
    private WebSocketClient webSocketClient;
    private TextView chatWindow;
    private EditText input;
    private Button connectButton, disconnectButton;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent i = getIntent();
        friend = i.getParcelableExtra("user");
        currentUser = i.getParcelableExtra("currentUser");
        setTitle("Message " + friend.getUsername());

        connectButton = findViewById(R.id.connectButton);
        disconnectButton = findViewById(R.id.disconnectButton);
        sendButton = findViewById(R.id.sendButton);

        chatWindow = findViewById(R.id.chatWindowView);
        chatWindow.setMovementMethod(new ScrollingMovementMethod());

        input = findViewById(R.id.messageEditText);


        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webSocketClient == null) {
                    connectWebSocket();
                } else {
                    Boolean websocketOpen = webSocketClient.isOpen();
                    // if the websocket client is not open, open one when the connect button is clicked
                    if (websocketOpen == false) {
                        connectWebSocket();
                    }
                }
            }
        });

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webSocketClient != null) {
                    Boolean websocketOpen = webSocketClient.isOpen();
                    // if the websocket client is open, then close it when the disconnect button is clicked
                    if (websocketOpen == true) {
                        webSocketClient.close();
                        finish();
                        //output.setText("");
                        //connectButton.setText("Connect");
                    }
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webSocketClient != null) {
                    Boolean websocketOpen = webSocketClient.isOpen();
                    // If websocket is open, send message
                    if (websocketOpen == true) {
                        String messageToSend = input.getText().toString();

                        // If the message is not null and its length is greater than 0, send the message
                        if (messageToSend != null && messageToSend.length() > 0) {
                            webSocketClient.send(messageToSend);
                            input.setText("");
                        }
                    }
                }
            }
        });
    }


    /**
     * Method for connecting to web socket server
     */
    private void connectWebSocket() {
        URI uri;
        try {
            //uri = new URI("ws://echo.websocket.org");
            uri = new URI(URLs.URL_WEBSOCKET + currentUser.getUsername());
            connectButton.setText("Connection Successful!");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String msg) {
                Log.i("Websocket", "Message Received");
                // Appends the message received to the previous messages
                chatWindow.append("\n" + msg);
            }

            @Override
            public void onClose(int errorCode, String reason, boolean remote) {
                Log.i("Websocket", "Closed " + reason);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        webSocketClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}