/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeshare;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author voleti
 */
public class FileChooserWorkspace extends JFileChooser {
        private String path ;
        private String filename;
     FileChooserWorkspace(String folderPath) throws IOException {
        setCurrentDirectory(new File(folderPath));
        setDialogTitle("Select WorkSpace");
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setAcceptAllFileFilterUsed(false);
         int status = showOpenDialog(this);
         if(status == JFileChooser.APPROVE_OPTION){
       
            path =  (getSelectedFile().getCanonicalPath());
         }
         else if ( status == JFileChooser.CANCEL_OPTION){
             
         }
    }

    FileChooserWorkspace(String filepath, String file)  {
        setCurrentDirectory(new File(filepath));
        setDialogTitle("Selet File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("java","java");
        setFileFilter(filter);
        int status = showOpenDialog(this);
        if(status == JFileChooser.APPROVE_OPTION){
       
            try {
                path =  (getSelectedFile().getCanonicalPath());
                filename = getSelectedFile().getName();
        } catch (IOException ex) {
            
        }
        }
        else if ( status == JFileChooser.CANCEL_OPTION){
             
         }
    }
    
    public String getPath(){
       
        return path;
    }
    public String Name(){
        return filename;
    }
}


