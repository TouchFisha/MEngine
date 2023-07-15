package touchfish.unit.util;

import touchfish.unit.math.float3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream outputStream;
    private BufferedReader inputReader;

    public TCP() {}

    /**
     * 开启客户端
     */
    public void startClient(String host, int port) {
        // Connect to server
        try {
            clientSocket = new Socket(host, port);
            System.out.println("# Connected to server: " + clientSocket.getInetAddress().getHostName());
            // Get input and output streams to communicate with server
            inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 开启服务端
     */
    public void startServer(int port) {

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("# Server listening on port " + port);

        // Wait for incoming connections
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("# Client connected: " + clientSocket.getInetAddress().getHostName());

        // Get output stream to send data to client
        try {
            outputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送消息
     */
    public void send(String data) {
        try {
            outputStream.write(data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("# Data sent to client: " + data);
    }

    /**
     * 发送消息
     */
    public void send(Object data) {
        send(JsonUtility.toJson(data));
    }

    /**
     * 接收消息（线程等待）
     */
    public String receive() {
        String inputData = null;
        try {
            inputData = inputReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("# Data received from server: " + inputData);
        return inputData;
    }

    /**
     * 关闭链接
     */
    public void dispose() {

        if (clientSocket != null) {
            // Close the connection
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("# Client connection closed.");
        }

        if (serverSocket != null) {
            // Close the server socket
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("# Server socket closed.");
        }

    }

    public static void main(String[] args) throws Exception {
        TCP server = new TCP();
        TCP client = new TCP();

        Thread serverThread = new Thread(()-> {
            server.startServer(56789);
            server.send(new float3(1,2,3));
        });
        Thread clientThread = new Thread(()-> {
            client.startClient("localhost",56789);
            client.receive();
        });

        serverThread.start();
        clientThread.start();

        Thread.sleep(1000);

        server.dispose();
        client.dispose();
    }
}
