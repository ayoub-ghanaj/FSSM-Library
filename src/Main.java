package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application  {

    Stage window ;
    Scene login , books, users, history ;
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        SQLiteTools sql = new SQLiteTools();
        ResultSet rs = sql.displayStudents();

        GridPane log_gp = new GridPane();
        log_gp.setPadding(new Insets(12,12,12,12) );
        log_gp.setVgap(9);
        log_gp.setHgap(11);


        Label login_label = new Label("cni :");
        log_gp.setConstraints(login_label,0,0);

        TextField cni_input = new TextField();
        log_gp.setConstraints(cni_input,1,0);

        Label pass_label = new Label("password :");
        log_gp.setConstraints(pass_label,0,1);

        TextField pass_input = new TextField();
        log_gp.setConstraints(pass_input,1,1);

        button = new Button("login");
        log_gp.setConstraints(button,1,2);

        button.setOnAction(e->{
            String username = cni_input.getText().trim();
            String password = pass_input.getText().trim();
            if(username.length()>0 && password.length()>0 ){
                try {
                    int[] arr = sql.login(username,password);
                    if(arr[0]==0){
                        System.out.println("makainch");
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
        window.setTitle("Login");
        log_gp.getChildren().addAll(login_label,cni_input,pass_label,pass_input,button);
        Scene scene = new Scene(log_gp,300,200);
        scene.getStylesheets().add(getClass().getResource("FSLib.css").toString());

        window.setScene(scene);

        window.show();



    }


}
