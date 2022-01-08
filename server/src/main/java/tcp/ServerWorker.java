package tcp;

import common.StatusCodes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private String login = null;
    private OutputStream outputstream;
    private HashSet<String> topicSet = new HashSet<>();

    /**
     * Identifies which user this thread handles.
     */
    private Long userId;

    public ServerWorker(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handle();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Long getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    private void handle() throws IOException, InterruptedException {
        System.out.println("Client is being handled");

        outputstream = clientSocket.getOutputStream();
        outputstream.write("did you get it".getBytes());

        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println("The line: " + line);
//            line = new JSONObject()
//                .put("endpoint", "message")
//                .put("action", "add")
//                .put("parameters", new JSONObject()
//                        .put("sender_id", 1)
//                        .put("conversation_id", 1)
//                        .put("body", "Hello friend")
//                        .put("is_media", false)
//                        .put("created_at", "11/12/2021")
//                        .put("updated_at", ""))
//                .toString();
//            line = new JSONObject()
//                    .put("endpoint", "login")
//                    .put("action", "get")
//                    .put("parameters", new JSONObject()
//                            .put("username", "first_user")
//                            .put("password", "test"))
//                    .toString();

//            line = new JSONObject()
//                .put("endpoint", "conversation")
//                .put("action", "get")
//                .put("parameters", new JSONObject()
//                        .put("user_id", 1))
//                .toString();

//            line = new JSONObject()
//                    .put("endpoint", "message")
//                    .put("action", "get")
//                    .put("parameters", new JSONObject()
//                            .put("conversation_id", 1)
//                            .put("user_id", 1))
//                    .toString();
            Long[] faker = new Long[2];
            faker[0] = 1L;
            faker[1] = 2L;


            line = new JSONObject()
                    .put("endpoint", "message")
                    .put("action", "add")
                    .put("senderId", 1L)
                    .put("receivers", faker)
                    .put("parameters", new JSONObject()
                            .put("sender_id", 2)
                            .put("conversation_id", 1)
                            .put("body", "Hi first_user from hardcoded json!")
                            .put("is_media", false)
                            .put("created_at", "11/12/2021")
                            .put("updated_at", ""))
                    .toString();

//            "endpoint": "message",
//                    "action": "add",
//                    "parameters": {
//                "sender_id": 1,
//                        "conversation_id": 1,
//                        "body": "Hello friend",
//                        "is_media": false,
//                        "created_at": "11/12/2021",
//                        "updated_at": null
            System.out.println("Dispatcher dispatches");

            ArrayList<Long> receivers = new ArrayList<>();
            JSONArray json = (JSONArray) new JSONObject(line).get("receivers");

            for (Object id : json) {
                System.out.println("Found receiver id: " + id);
                receivers.add(Long.parseLong(String.valueOf(id)));
            }

            Dispatcher dispatcher = new Dispatcher(line);
            JSONObject result = dispatcher.dispatch();

            if (result == null) {
                outputstream.write(new JSONObject().put("status", StatusCodes.NOT_FOUND.getValue()).toString().getBytes());
            } else {
                outputstream.write(result.put("status", StatusCodes.SUCCESS.getValue()).toString().getBytes());
            }
        }
    }

    public void handleTest() {

    }

    public boolean isMemberOfTopic(String topic) {
        return topicSet.contains(topic);
    }

    private void handleJoin(String[] tokens) {
        if (tokens.length > 1) {
            String topic = tokens[1];
            topicSet.add(topic);
        }
    }


    // format: "msg" "login" body..
    // format "msg" #topic body..
    private void handleMessage(String[] tokens) throws IOException {
        String sendTo = tokens[1];
        String body = tokens[2];

        boolean isTopic = sendTo.charAt(0) == '#';

        List<ServerWorker> workers = server.getWorkers();
        for (ServerWorker worker : workers) {
            if (isTopic) {
                if (worker.isMemberOfTopic(sendTo)) {
                    String outMsg = "msg " + sendTo + ":" + login + " " + body + " \n";
                    worker.send(outMsg);
                }
            } else {
                if (sendTo.equalsIgnoreCase(worker.getLogin())) {
                    String outMsg = "msg + " + login + " " + body + " \n";
                    worker.send(outMsg);
                }
            }


        }

    }

    private void handleLoggoff() throws IOException {
        this.server.removeWorker(this);

        // Send other online user current user's status
        String onlineMsg = "offline " + login + "\n";
        List<ServerWorker> workers = server.getWorkers();

        for (ServerWorker worker : workers) {
            if (worker.getLogin() != null &&!worker.getLogin().equalsIgnoreCase(login)) {
                worker.send(onlineMsg);
            }
        }
        clientSocket.close();
    }



    private void handleLogin(OutputStream outputstream, String[] tokens) throws IOException {
        if (tokens.length == 3) {
            String login = tokens[1];
            String password = tokens[2];

            if ((login.equals("guest") && password.equals("guest")) || (
                    login.equals("kel") && password.equals("kel")) ||
                            (login.equals("lel") && password.equals("lel")
                            )) {

                String msg = "ok login\n";
                this.login = login;
                System.out.println("User logged in successfuly: " + login);
                outputstream.write(msg.getBytes());

                List<ServerWorker> workers = server.getWorkers();

                // current user all other online logins
                for (ServerWorker worker : workers) {
                    if (worker.getLogin() != null && !worker.getLogin().equalsIgnoreCase(login)) {
                        String msg2 = "online " + worker.getLogin();
                        send(msg2);
                    }
                }

                // Send other online user current user's status
                String onlineMsg = "online " + login + "\n";
                for (ServerWorker worker : workers) {
                    if (worker.getLogin() != null &&!worker.getLogin().equalsIgnoreCase(login)) {
                        worker.send(onlineMsg);
                    }
                }
            } else {
                String msg = "error login\n";
                outputstream.write(msg.getBytes());
            }
        }
    }

    private void send(String msg) throws IOException {
        if (login != null) {
            outputstream.write(msg.getBytes());
        }
    }
}
