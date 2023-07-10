import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.bytedeco.tesseract.PAGE_RES;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class cipherBox extends Application {
    private String Name;
    private File selectedFile;
    private  TextField userInputField;
    private String passWord;
    private String encryptedText;
    private String decryptedText;


    public cipherBox(String name, File file){
        this.Name = name;
        this.selectedFile = file;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(Name);

        Label titleLabel = new Label("decrypt or encrypt");

        // Set the font weight to bold
        titleLabel.setStyle("-fx-font-weight: bold;");

        BorderPane title = new BorderPane();
        title.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        HBox enterPassword = new HBox();
        enterPassword.setAlignment(Pos.CENTER);
        enterPassword.setSpacing(10);
        //enterPassword.setPadding(new Insets((10)));

        userInputField = new TextField();

        Button submit = new Button("submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                // the key must be 16 digits
                if(userInputField.getText().trim().length() == 16) {
                    passWord = userInputField.getText().trim();
                    System.out.println(passWord);
                    userInputField.setStyle("-fx-border-color: green;");
                }else {
                    userInputField.setStyle("-fx-border-color: red;");
                }
            }
        });

        enterPassword.getChildren().addAll(submit, userInputField);


        Button leftButton = new Button("encrypt");
        leftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    encryptedText = AESEncryption.encryption(selectedFile, passWord);
                }catch (Exception E){
                    throw new RuntimeException(E);
                }
                replace(selectedFile.getAbsolutePath(),encryptedText);
            }
            });


        Button rightButton = new Button("decrypt");
        rightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    decryptedText = AESEncryption.decrypt(selectedFile, passWord);
                }catch (Exception E){
                    throw new RuntimeException(E);
                }
                replace(selectedFile.getAbsolutePath(),decryptedText);
            }
        });

        HBox buttonsEnDe = new HBox();
        buttonsEnDe.setAlignment(Pos.CENTER);

        // space between the buttons
        buttonsEnDe.setSpacing(50);
        //buttonsEnDe.setPadding(new Insets(10));
        buttonsEnDe.getChildren().addAll(leftButton, rightButton);




        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        vbox.getChildren().addAll(title,new Label("enter security key (16 digits)"), enterPassword, buttonsEnDe);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void replace(String filePath, String newContent){

        try {
            // Write the new content to the file, overwriting the existing content
            Files.writeString(Path.of(filePath), newContent, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("File content has been replaced successfully.");
        } catch (IOException e) {
            System.err.println("Failed to replace file content: " + e.getMessage());
        }
    }
}
