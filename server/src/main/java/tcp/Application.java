package tcp;


import config.EntityManagerConfig;

public class Application {

    private static final int PORT = 8818;

    public static void main (String[] args) {
        EntityManagerConfig.getInstance();
        Server server = new Server(PORT);
        server.start();
    }
}
