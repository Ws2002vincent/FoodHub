/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

/**
 *
 * @author ASUS
 */
public class ImageUploader {

    public static int uploadImage(String imgFilename) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "png", "jpg", "jpeg"));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            long fileSizeInBytes = selectedFile.length();
            long fileSizeInMB = fileSizeInBytes / (1024 * 1024);
            if (fileSizeInMB > 3) {
                System.out.println("The selected file is larger than 3MB.");
                return 0; //file size too large
            }
            try {
                Image image = ImageIO.read(selectedFile);
                if (image != null) {
                    saveImage(selectedFile, imgFilename);
                    return 1; //image was successfully uploaded
                } else {
                    System.out.println("Failed to read the selected image file.");
                }
            } catch (java.io.IOException ex) {
                ex.printStackTrace();
            }
        }
        return 2; //user cancelled the upload or an error occurred
    }

    private static void saveImage(File selectedFile, String newFileName) throws FileNotFoundException {
        String destinationFolderPath = "src/images/";

        File destinationFolder = new File(destinationFolderPath);
        //make directory if destination folder does not exist
        if (!destinationFolder.exists()) {
            if (!destinationFolder.mkdirs()) {
                //uncomment this for debugging purposes
//                System.err.println("Failed to create destination folder: " + destinationFolder.getAbsolutePath());
                return;
            } else {
                //uncomment this for debugging purposes
//                System.out.println("Destination folder created: " + destinationFolder.getAbsolutePath());
            }
        } else {
            //uncomment this for debugging purposes
//            System.out.println("Destination folder already exists: " + destinationFolder.getAbsolutePath());
        }

        File destinationFile = new File(destinationFolder, newFileName);
        try {
            //input & output stream to copy file (iamge) to destination folder
            InputStream in = new FileInputStream(selectedFile);
            OutputStream out = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
            
            //uncomment this for debugging purposes
//            System.out.println("Image saved to: " + destinationFile.getAbsolutePath());
//            System.out.println("Saved to: " + destinationFile.getPath());
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
    }
}
