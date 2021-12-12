package tcp;



import antlr.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private String login = null;
    private OutputStream outputstream;
    private HashSet<String> topicSet = new HashSet<>();

    public ServerWorker(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return login;
    }

    private void handleClientSocket() throws IOException, InterruptedException {
        // send msg
        this.outputstream = clientSocket.getOutputStream();  // every socket has stream

        // read input
        InputStream inputStream = clientSocket.getInputStream(); // every socket has stream

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(" ");

            if (tokens != null && tokens.length > 0) {
                String cmd = tokens[0];

                if ("logoff".equalsIgnoreCase(line) || "quit".equalsIgnoreCase(line)) {
                    handleLoggoff();
                    break;
                } else if ("login".equalsIgnoreCase(cmd)) {
                    String[] tokensMsg = line.split(line, 3);
                    handleLogin(outputstream, tokens);
                } else if ("msg".equalsIgnoreCase(cmd)) {
                    handleMessage(tokens);
                } else if ("join".equalsIgnoreCase(cmd)) {
                    handleJoin(tokens);
                }
//                String message = "You typed " + line + "\n";
//                outputstream.write(message.getBytes());
            }

        }

//        clientSocket.close();

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
