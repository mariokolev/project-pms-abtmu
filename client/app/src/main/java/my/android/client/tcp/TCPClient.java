package my.android.client.tcp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
    public static final String SERVER_IP = "10.0.2.2";
    public static final int SERVER_PORT = 8818;
    private Socket socket;
    private static TCPClient singleInstance = null;

    private TCPClient() {

    }

    public static TCPClient getInstance() {
        if (singleInstance == null) {
            singleInstance = new TCPClient();
        }
        return singleInstance;
    }

    public void sendMessage(String message) {
        try {
            OutputStream out = socket.getOutputStream();
            out.write(message.getBytes());
            Log.i("Sending ", message);
            out.flush();
        } catch (IOException e) {
            Log.e("Send Error ", "Something went wrong.", e);
        }
    }


    public void connect() {
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Log.i("TCP ", "Client Connecting...");
            socket = new Socket(serverAddr, SERVER_PORT);
        } catch (UnknownHostException e) {
            Log.e("HOST ", "unknown host ", e);
        } catch (IOException e) {
            Log.e("TCP ", "C: Error", e);
        }
    }

    public String receive() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";
            line = in.readLine();

            Log.i("Received ", line);

            return line;
        } catch (Exception e) {
            Log.e("TCP ERROR ", "error on receive ", e);
        }
        return null;
    }
}
