package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
    private final int serverPort;

    private ArrayList<ServerWorker> workers = new ArrayList<>();

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public List<ServerWorker> getWorkers() {
        return workers;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);

            while (true) {
                System.out.println("Accepting connections");
                Socket client = serverSocket.accept(); // returns client socket for every connection
                System.out.println("Acceped connection");
                // once we have a connection spawn worker
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
