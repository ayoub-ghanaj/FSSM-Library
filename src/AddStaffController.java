package src;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddStaffController implements Initializable {

    @FXML
    private Label err_label;

    @FXML
    private Button Save_btn1;

    @FXML
    private TextField etu_cne;
    @FXML
    private PasswordField staff_pass;

    @FXML
    private TextField etu_nom;


    @FXML
    private TextField etu_phone;

    @FXML
    private ComboBox<TyCat> drop_role;


    @FXML
    private TextField etu_pre;

    @FXML
    private Button Save_btn;

    private Stage father;
    final private SQLiteTools sql = new SQLiteTools();


    private  Staff_page_controller home_controller;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    void saveHandler(ActionEvent event) {
        if(etu_cne.getText().trim().length() >=0 && etu_phone.getText().trim().length() >=0 && drop_role.getValue() !=null && etu_nom.getText().trim().length() >=0 && etu_pre.getText().trim().length() >=0 && staff_pass.getText().trim().length() >=0){
            err_label.setVisible(false);
            try{
                // Use a buffer to copy the file
                sql.addStaff(etu_cne.getText().trim(),etu_nom.getText().trim(),etu_pre.getText().trim(), String.valueOf(drop_role.getValue()),etu_phone.getText().trim(),staff_pass.getText().trim());
                home_controller.search_handler_func();
                father.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            err_label.setVisible(true);
        }
    }

    @FXML
    void deleteHandler(ActionEvent event) {
        father.close();
    }


    /**
     *  Takes Stage  and Controller of the mother Stage  as parameters and pass them the this  and launch the fill combos function that
     * @param father_in
     * @param home_con
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void setData(Stage father_in , Staff_page_controller  home_con) throws SQLException, ClassNotFoundException {
        this.home_controller = home_con;
        this.father = father_in;
        ObservableList<TyCat> ca_ls = drop_role.getItems();
        ca_ls.add(new TyCat("1","Admin"));
        ca_ls.add(new TyCat("2","Staff"));
    }
}
