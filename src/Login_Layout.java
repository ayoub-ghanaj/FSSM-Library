package src;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login_Layout extends BorderPane {
    public Login_Layout(Stage window , SQLiteTools sql) throws SQLException, ClassNotFoundException {
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
        loginButton.setId("loginbtn");

        // Add a label for the error field
        Label errorLabel = new Label(" Invalid Login/Password ");
        grid.add(errorLabel, 0, 3);
        errorLabel.setVisible(false);
        errorLabel.setId("errlab");




        ResultSet rs = sql.displayStudents();

        loginButton.setOnAction(e->{
            window.setMaximized(true);
            loginButton.setStyle("-fx-shape: \"m12 .5c-6.63 0-12 5.28-12 11.792 0 5.211 3.438 9.63 8.205 11.188.6.111.82-.254.82-.567 0-.28-.01-1.022-.015-2.005-3.338.711-4.042-1.582-4.042-1.582-.546-1.361-1.335-1.725-1.335-1.725-1.087-.731.084-.716.084-.716 1.205.082 1.838 1.215 1.838 1.215 1.07 1.803 2.809 1.282 3.495.981.108-.763.417-1.282.76-1.577-2.665-.295-5.466-1.309-5.466-5.827 0-1.287.465-2.339 1.235-3.164-.135-.298-.54-1.497.105-3.121 0 0 1.005-.316 3.3 1.209.96-.262 1.98-.392 3-.398 1.02.006 2.04.136 3 .398 2.28-1.525 3.285-1.209 3.285-1.209.645 1.624.24 2.823.12 3.121.765.825 1.23 1.877 1.23 3.164 0 4.53-2.805 5.527-5.475 5.817.42.354.81 1.077.81 2.182 0 1.578-.015 2.846-.015 3.229 0 .309.21.678.825.56 4.801-1.548 8.236-5.97 8.236-11.173 0-6.512-5.373-11.792-12-11.792z\";");
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

        this.setCenter(grid);


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

        this.setTop(top_items);
    }
}
