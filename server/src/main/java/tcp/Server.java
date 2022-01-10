package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Server extends Thread{
    private final int serverPort;

    private ArrayList<ServerWorker> workers = new ArrayList<>();

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public List<ServerWorker> getWorkers() {
        return workers;
    }

    private Logger logger = Logger.getLogger(Server.class.getName());

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);

            while (true) {
                logger.info("Server is accepting connections.");
                Socket client = serverSocket.accept();

                logger.info("Created thread for current connection.");
                ServerWorker worker = new ServerWorker(this, client);
                workers.add(worker);
                worker.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeWorker(ServerWorker serverWorker) {
        this.workers.remove(serverWorker);
    }
}
