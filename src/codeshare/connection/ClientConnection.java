/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeshare.connection;

import codeshare.NewScroll;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JScrollPane;
import javax.swing.*;

/**
 *
 * @author voleti
 */
public class ClientConnection implements Runnable{
  
    private DataInputStream dis;
    private JTabbedPane  jTabbedPane;
    JTextArea textArea;
    private int teacherSelectedTab = 0 ;
    private volatile Boolean student = true;
    public ClientConnection(JTabbedPane jTabbedPane,Socket s) {
        try {
            this.jTabbedPane = jTabbedPane;
            dis = new DataInputStream(s.getInputStream());
            
        } catch (Exception ex) {
        }
    }
    
   
        @Override
        public void run() {
            
            while(student){
                try {
                    switch(dis.readInt()){
                        case 1:
                            textAreaEvents();
                            break;
                        case 2:
                            String title = dis.readUTF();
                            addTextArea(title);
                            setTab(title);
                            break;
                        case 3:
                            String textAreatitle = dis.readUTF();
                            editableTextAreaTrue(textAreatitle);
                            
                            break;
                        case 4:
                            String selectTab = dis.readUTF();
                            setTab(selectTab);
                        case 5:
                            int position = dis.readInt();
                            if(position!=0)
                            removeCharacter(position);
                       
                    }
                } catch (IOException ex) {
                    
                }
            }
           
        
        }
        
        void textAreaEvents() throws IOException {
            int position = dis.read();
            String string = dis.readUTF();
            textArea=(JTextArea)(((JScrollPane)jTabbedPane.getComponentAt(teacherSelectedTab)).getViewport()).getComponent(0);
            textArea.insert(string, position);
            String title = jTabbedPane.getTitleAt(teacherSelectedTab);
             if(!title.contains("*"))
                jTabbedPane.setTitleAt(teacherSelectedTab,title+"*");
        }
        void addTextArea(String title){
          
          Boolean tabNotExisted = false;
          JTextArea currentTextArea;
          for(int i=0;i<jTabbedPane.getTabCount();i++){
              if(jTabbedPane.getTitleAt(i).equals(title)){
                  tabNotExisted = true;
                  currentTextArea=(JTextArea)(((JScrollPane)jTabbedPane.getComponentAt(i)).getViewport()).getComponent(0);
                  currentTextArea.setEditable(false);
                
                 
              }
              
           }
          if(!tabNotExisted){
             jTabbedPane.addTab(title,new NewScroll()); 
             currentTextArea=(JTextArea)(((JScrollPane)jTabbedPane.getComponentAt(jTabbedPane.getTabCount()-1)).getViewport()).getComponent(0);
             currentTextArea.setEditable(false);
          }
        }
        void editableTextAreaTrue(String title){
            JTextArea currentTextArea;
            for(int i=0;i<jTabbedPane.getTabCount();i++){
              if(jTabbedPane.getTitleAt(i).equals(title)){
                  currentTextArea=(JTextArea)(((JScrollPane)jTabbedPane.getComponentAt(i)).getViewport()).getComponent(0);
                  currentTextArea.setEditable(true);
              }
            }
        }
       void setTab(String tab){
            for(int i=0;i<jTabbedPane.getTabCount();i++){
              if(jTabbedPane.getTitleAt(i).equals(tab)){
                  teacherSelectedTab = i;
              }
            }
       }
       
       void removeCharacter(int position){
           textArea=(JTextArea)(((JScrollPane)jTabbedPane.getComponentAt(teacherSelectedTab)).getViewport()).getComponent(0);
           textArea.replaceRange(null, position-1, position);
       }
      
        public void  startThread(){
            Thread t1 = new Thread(this);
            t1.start();
            
        }
        
       
        
        public synchronized void stopThread(){
            student = false;
        }

   

   
        
        
   }
    

