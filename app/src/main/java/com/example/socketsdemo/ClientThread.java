package com.example.socketsdemo;

import android.app.Activity;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by James Ooi on 13/8/2017.
 */

public class ClientThread extends Thread {
    private Activity activity;
    private double radius;

    private static final int SERVER_PORT = 8000;
    private static final String SERVER_IP = "10.0.2.2";
    private Socket socket;

    public ClientThread(Activity activity, double radius) {
        this.activity = activity;
        this.radius = radius;
    }

    @Override
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            socket = new Socket(serverAddr, SERVER_PORT);

            DataInputStream is = new DataInputStream(socket.getInputStream());
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());

            os.writeDouble(radius);
            os.flush();

            final double circumference = is.readDouble();
            final double area = is.readDouble();

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EditText etCircumference = (EditText)activity.findViewById(R.id.circumference);
                    EditText etArea = (EditText)activity.findViewById(R.id.area);

                    etCircumference.setText(Double.toString(circumference));
                    etArea.setText(Double.toString(area));
                }
            });
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        }
    }
}
