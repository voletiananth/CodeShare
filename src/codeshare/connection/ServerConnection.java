/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeshare.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author voleti
 */
public class ServerConnection implements Runnable{
    private ServerSocket serverSocket;
    private int clients = 0;
    /**
     *
     */
    @SuppressWarnings("UseOfObsoleteCollectionType")
    public static volatile ConcurrentHashMap<String,Socket> activeClients;
    @SuppressWarnings("UseOfObsoleteCollectionType")
    public ServerConnection(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        activeClients = new ConcurrentHashMap<>();
    }
    

    @Override
    public void run() {
        while(true){
            try {
                Thread t1 = new Thread(new MultiServer(serverSocket.accept()));
                t1.start();
            } catch (IOException ex) {
            }
        }
    }
   
    private class MultiServer implements Runnable{
        private Socket clientAccept;
        
        public MultiServer(Socket clientAccept) {
            this.clientAccept = clientAccept;
            
        }

        @Override
        public void run() {
            activeClients.put(clientAccept.getInetAddress().getHostAddress(), clientAccept);
           
        }
        
  
    }
}
