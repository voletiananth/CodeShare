/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeshare;

import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import codeshare.connection.ServerConnection;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
/**
 *
 * @author voleti
 */
public class jCheckBoxMenuItem extends JCheckBoxMenuItem implements KeyListener,CaretListener{
    private int position = 0;
    public jCheckBoxMenuItem(String title,JTabbedPane jTabbedPane,int selectedTab) {
        super(title);
        JTextArea textArea=(JTextArea)(((JScrollPane)jTabbedPane.getComponentAt(selectedTab)).getViewport()).getComponent(0);
        addItemListener((ItemEvent e) -> {
            JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getItem();
            DataOutputStream output;
            if(e.getStateChange()==1){
                for(Socket clientSocket: ServerConnection.activeClients.values()) {
                    try {
                        output =  new DataOutputStream(clientSocket.getOutputStream());
                        output.writeInt(2);
                        output.writeUTF(item.getText());
                    } catch (IOException ex) {
                       
                    }
                }
                textArea.addKeyListener(this);
                textArea.addCaretListener(this);
            }else{
                
              
                for(Socket clientSocket: ServerConnection.activeClients.values()) {
                    try {
                        output =  new DataOutputStream(clientSocket.getOutputStream());
                        output.writeInt(3);
                        output.writeUTF(item.getText());
                    }
                    catch(IOException ex){
                        
                    }
                }
                textArea.removeKeyListener(this);
                textArea.removeCaretListener(this);
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if((keyChar>='a' && keyChar<='z') ||(keyChar>='A' && keyChar<='Z') )
        sendText(keyChar);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            sendText(e.getKeyChar());
        else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
            removeCharacter(5);
        }
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        position = e.getDot();
    }
    
    private void sendText(char keyChar){
         for(Socket clientSocket: ServerConnection.activeClients.values()) {
                    try {
                        DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                        String text =String.valueOf(keyChar); 
                        outputStream.writeInt(1);
                        outputStream.write(position);
                        outputStream.writeUTF(text);
                    } catch (IOException ex) {
                        ServerConnection.activeClients.remove(clientSocket.getInetAddress().getHostAddress());
                    }
                }
    }
    
    private void removeCharacter(int a){
         for(Socket clientSocket: ServerConnection.activeClients.values()) {
                    try {
                        DataOutputStream output =  new DataOutputStream(clientSocket.getOutputStream());
                        output.writeInt(a);
                        output.writeInt(position);
                    } catch (IOException ex) {
                       
                    }
        }
    }
    
}
