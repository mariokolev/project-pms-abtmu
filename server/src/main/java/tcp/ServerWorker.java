package tcp;

import common.StatusCodes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private OutputStream outputstream;
    private HashSet<String> topicSet = new HashSet<>();
    private Logger logger = Logger.getLogger(ServerWorker.class.getName());

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

    private void handle() throws IOException, InterruptedException {
        outputstream = clientSocket.getOutputStream();
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {

            if (userId == null) {
                userId = Long.parseLong(JSONStringer.valueToString(new JSONObject(line).get("senderId")));
                logger.info(String.format("Thread handles user with id[%d] ", userId));
            }

            ArrayList<Long> receivers = new ArrayList<>();
            JSONArray json = (JSONArray) new JSONObject(line).get("receivers");

            for (Object id : json) {
                logger.info(String.format("parsing long, %s", String.valueOf(id)));
                receivers.add(Long.parseLong(String.valueOf(id)));
            }

            logger.info(String.format("Receivers: %s", receivers.toString()));
            Dispatcher dispatcher = new Dispatcher(line);
            JSONObject result = dispatcher.dispatch();
            logger.info(String.format("Dispatcher result: %s", result.toString()));

            if (result == null) {
                handleReceivers(receivers, String.format(new JSONObject().put("status", StatusCodes.NOT_FOUND.getValue()).toString() + "\n").getBytes());
            } else {
                handleReceivers(receivers, String.format(result.put("status", StatusCodes.SUCCESS.getValue()).toString() + "\n").getBytes());
            }
        }
    }

    public void handleReceivers(List<Long> receivers, byte[] response) throws IOException {
        for (ServerWorker worker : server.getWorkers()) {
            if (receivers.contains(worker.getUserId())) {
                logger.info(String.format("Sending response to userId: %d", worker.getUserId()));
                worker.send(response);
            }
        }
    }

    private void send(byte[] response) throws IOException {
        outputstream.write(response);
    }

    // LOG OUT remove worker from list
}
