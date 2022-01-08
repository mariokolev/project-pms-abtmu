package util;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class ClientTest {
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) throws IOException
    {
        startConnection("localhost", 8818);
//        sendMessage(new JSONObject()
//                .put("endpoint", "message")
//                .put("action", "add")
//                .put("parameters", new JSONObject()
//                        .put("sender_id", 1)
//                        .put("conversation_id", 1)
//                        .put("body", "Hello friend")
//                        .put("is_media", false)
//                        .put("created_at", "11/12/2021")
//                        .put("updated_at", ""))
//                .toString());

        System.out.println(sendMessage(new JSONObject()
                .put("endpoint", "login")
                .put("action", "get")
                .put("parameters", new JSONObject()
                        .put("username", "first_user")
                        .put("password", "test"))
                .toString()));
    }

    public static void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public static String sendMessage(String msg) throws IOException {
        out.println(msg);
        System.out.println("will read in");

        String resp = in.readLine();
        System.out.println("the response: " + resp);
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
