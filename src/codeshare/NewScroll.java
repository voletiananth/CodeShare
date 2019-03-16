/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeshare;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
        /**
 *
 * @author voleti
 */
public class NewScroll extends JScrollPane  {
    
    public NewScroll(){
        super();
       
        JTextArea textarea = new JTextArea();
        TextLineNumber tln = new TextLineNumber(textarea);
        textarea.setVisible(true);
        setRowHeaderView( tln );
        setViewportView(textarea);   
    }

    public NewScroll(JTextArea textarea) {
        super();
        TextLineNumber tln = new TextLineNumber(textarea);
        setRowHeaderView( tln );
        textarea.setVisible(true);
        setViewportView(textarea);
    }

    
   
}
