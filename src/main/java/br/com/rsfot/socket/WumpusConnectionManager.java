package br.com.rsfot.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WumpusConnectionManager {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private List<String> messagesFromWumpusServer = new ArrayList<>();
    private final Logger LOGGER = Logger.getLogger(WumpusConnectionManager.class.getName());

    public WumpusConnectionManager(String serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        //TODO: validações se tem mensagens
        this.messagesFromWumpusServer.add(in.readLine());
    }

    public void sendCommand(String command) {
        LOGGER.info("Sending command: " + command);
        this.out.println(command);
        this.messagesFromWumpusServer.add(receiveResponse());
        LOGGER.info("Receiving response: " + this.messagesFromWumpusServer.getLast());
    }

    public String retrieveInfoWumpusWorld() {
        return messagesFromWumpusServer.getLast();
    }

    public String receiveResponse() {
        try {
            return in.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void close() throws Exception {
        socket.close();
        in.close();
        out.close();
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

}