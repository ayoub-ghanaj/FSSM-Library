package src;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddEtudiantController implements Initializable {

    @FXML
    private Label err_label;

    @FXML
    private Button Save_btn1;

    @FXML
    private TextField etu_cne;

    @FXML
    private TextField etu_nom;

    @FXML
    private ImageView Image_view;

    @FXML
    private TextField etu_phone;

    @FXML
    private TextField etu_mail;

    @FXML
    private TextField etu_pre;

    @FXML
    private Button Save_btn;

    private Stage father;
    final private SQLiteTools sql = new SQLiteTools();

    private File File_image =null ;
    private Image image = null;
    private  Etudiants_page_controller home_controller;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.Image_view.setOnMouseClicked(e->{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Image");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
                );
                File_image = fileChooser.showOpenDialog(father);
                if (File_image != null) {
                    image = new Image(File_image.toURI().toString());
                    Image_view.setImage(image);
                }
            });
        }catch (Exception e){

        }


    }

    @FXML
    void saveHandler(ActionEvent event) {
        if(File_image != null && image != null && etu_cne.getText().trim().length() >=0 && etu_phone.getText().trim().length() >=0 && etu_mail.getText().trim().length() >=0 && etu_nom.getText().trim().length() >=0 && etu_pre.getText().trim().length() >=0){
            err_label.setVisible(false);
            String path =  new File("../database/test1.db").getAbsolutePath();
            path = path.substring(0, path.length() - 20);
            String new_name = "IMG-"+String.valueOf(Math.random()).substring(2) +"."+getFileExtension(File_image).toLowerCase();
            try (FileInputStream in = new FileInputStream(File_image.getAbsolutePath());
                 FileOutputStream out = new FileOutputStream(path+"resources\\img\\"+new_name)) {

                // Use a buffer to copy the file
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                out.close();
                in.close();
                sql.addEtu(etu_cne.getText().trim(),etu_nom.getText().trim(),etu_pre.getText().trim(),etu_mail.getText().trim(),etu_phone.getText().trim(),path+"resources\\img\\"+new_name);
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
    public void setData(Stage father_in , Etudiants_page_controller  home_con) throws SQLException, ClassNotFoundException {
        this.home_controller = home_con;
        this.father = father_in;
        Image  img =new Image(getClass().getResourceAsStream("../resources/img/admin.jpg"));
        Image_view.setImage(img);
    }
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
}
