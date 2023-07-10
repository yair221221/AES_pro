
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileBrowser extends Application {
    private ListView<String> fileList;



    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Browser");


        fileList = new ListView<>();


        Button openFileButton = new Button("Open File");
        Button openFolderButton = new Button("Open Folder");


        openFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooseFile();
            }
        });

        openFolderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooseFolder();
            }
        });
        fileList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String selectedFile = fileList.getSelectionModel().getSelectedItem();
                File cipherFile = new File (selectedFile);
                cipherBox cipherBox = new cipherBox(selectedFile, cipherFile);
                Stage second = new Stage();
                cipherBox.start(second);
                handleMouseSelection();
            }
        });
        VBox vbox = new VBox( openFileButton, openFolderButton, fileList);
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleFileSelection(File file) {
        if(file== null){
            System.out.println("file not selected");
            return;
        }
        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid file path.");
        }
    }

    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {

                //fileList.getItems().clear();
                fileList.getItems().add(selectedFile.getAbsolutePath());
        }
        handleFileSelection(selectedFile);
    }

    private void chooseFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            fileList.getItems().clear();
            File[] files = selectedDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    fileList.getItems().add(file.getAbsolutePath());
                }
            }
        }
    }

    private void handleMouseSelection() {
        String selectedFile = fileList.getSelectionModel().getSelectedItem();
        handleFileSelection(new File(selectedFile));
    }

}



