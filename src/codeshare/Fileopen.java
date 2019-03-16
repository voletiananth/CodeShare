/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeshare;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTextArea;

/**
 *
 * @author voleti
 */
public class Fileopen extends JTextArea{
    Fileopen(String path){
        super();
         BufferedReader d;
        
               try
                {
                  d = new BufferedReader(new FileReader(path));
                  String line = d.readLine();
                  while(line!=null){
                           append(line +"\n");
                           line = d.readLine();
                  }
                  d.close();
                } catch (IOException ex) {
          
       }
    }
}
