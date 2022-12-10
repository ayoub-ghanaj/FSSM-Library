package src;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application  {
    private double xOffset = 0;
    private double yOffset = 0;
    Stage window ;
    Scene login , books, users, history ;
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        SQLiteTools sql = new SQLiteTools();
        window = stage;

        // Create a GridPane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add a label for the username field
        Label usernameLabel = new Label("Login :");
        grid.add(usernameLabel, 0, 1);

        // Add a text field for the username
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        // Add a label for the password field
        Label passwordLabel = new Label("Password :");
        grid.add(passwordLabel, 0, 2);

        // Add a password field for the password
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        // Add a login button
        Button loginButton = new Button("Submit");
        loginButton.setAlignment(Pos.CENTER);
        grid.add(loginButton, 1, 3);

        // Add a label for the error field
        Label errorLabel = new Label(" Invalid Login/Password ");
        grid.add(errorLabel, 0, 3);
        errorLabel.setVisible(false);

        loginButton.setId("loginbtn");






        ResultSet rs = sql.displayStudents();

//        button.setStyle("-fx-focus-color: transparent;");
        loginButton.setOnAction(e->{
            errorLabel.setVisible(false);
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            if(username.length()>0 && password.length()>0 ){
                try {
                    int[] arr = sql.login(username,password);
                    if(arr[0]==0){
                        System.out.println("makainch");
                        errorLabel.setVisible(true);
                    }else if(arr[0]==1){
                        System.out.println("admin w  id dialo :" + arr[1]);
                    }else{
                        System.out.println("staff w  id dialo :" + arr[1]);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

//        Button ext

        BorderPane home_pan = new BorderPane();
        home_pan.setCenter(grid);

        // Create the scene and add the home_pan
        Scene scene = new Scene(home_pan, 420, 220);
        window.initStyle(StageStyle.UNDECORATED);

        //resizable/movable
        scene.setOnMousePressed(e-> {
                xOffset = e.getSceneX();
                yOffset = e.getSceneY();

        });
        scene.setOnMouseDragged(e->{
                stage.setX(e.getScreenX() - xOffset);
                stage.setY(e.getScreenY() - yOffset);

        });

        // top bar buttons
        final int height = 25;
        HBox top_items = new HBox();
        Button close = new Button("x");
        close.setId("exit-btn");
        close.setOnAction(e ->{
            Platform.exit();
        });
//        Button fullscreen = new Button("■");
        Button minimze = new Button("-");
        minimze.setId("minimize-btn");
        minimze.setOnAction(e ->{
            window.setIconified(true);
        });
        
        top_items.getChildren().addAll(minimze,close);
        // Align the child nodes on the right side of the HBox
        top_items.setPrefWidth(window.getWidth());
        top_items.setPrefHeight(height);
        top_items.setMinHeight(height);
        top_items.setMaxHeight(height);
        top_items.setSpacing(1.5);
        top_items.setAlignment(Pos.BASELINE_RIGHT);

        home_pan.setTop(top_items);

        window.setMaxWidth(470);
        window.setMaxHeight(240);
        window.setMinWidth(420);
        window.setMinHeight(220);
        window.setScene(scene);
        window.show();
        scene.getStylesheets().add(getClass().getResource("FSLib.css").toString());
        errorLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: red;");
        window.setTitle("Login");
        window.show();

    }


}
