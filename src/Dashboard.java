package src;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @FXML
    private Button ADD;

    @FXML
    private Text fullname_labelle;
    @FXML
    private Text tot_mat;
    @FXML
    private Text tot_etu;
    @FXML
    private Text tot_emp;
    @FXML
    private ComboBox<?> types;

    @FXML
    private Button refresh;


    @FXML
    private GridPane EtudsDisplay;

    @FXML
    private GridPane booksDisplay;

    @FXML
    private ComboBox<?> categories;

    @FXML
    private HBox staff_btn;
    @FXML
    private Button Home;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_text;

    private String id;

    final  private  SQLiteTools  sql= new SQLiteTools();
    private Stage father ;
    private boolean staff_is_admin;

    public void setFather(Stage stg) throws SQLException, ClassNotFoundException {
        staff_btn.setVisible(Main.isAdmin);
        this.father = stg;
        EtudsDisplay.getChildren().clear();

        CategoryAxis newxaxis= new CategoryAxis();
        NumberAxis yaxis = new NumberAxis(0,15,5);
        newxaxis.setLabel("Etudiants");
        yaxis.setLabel("Les étudiants les plus empruntés");

        BarChart<String,Float> newbar = new BarChart(newxaxis,yaxis);
        //newbar.setTitle("Fruits Choices of people");

        XYChart.Series<String,Float> series = new XYChart.Series<>();
        ResultSet rs = sql.stat_user();
        while (rs.next()){
            series.getData().add(new XYChart.Data(rs.getString("cne"),rs.getInt("cnecount")));
        }
        rs.close();
        newbar.getData().add(series);
        EtudsDisplay.getChildren().add(newbar);

        booksDisplay.getChildren().clear();

        PieChart piechart = new PieChart();
        ResultSet rs2 = sql.stat_books();
        while (rs2.next()){
            piechart.getData().add(new PieChart.Data(rs2.getString("doc_libelle"), rs2.getInt("doccount")));

        }
        rs2.close();

        ResultSet rs3 = sql.tot_mat();
        while (rs3.next()){
            tot_mat.setText( String.valueOf(rs3.getInt("data")));
        }
        rs3.close();
        ResultSet rs4 = sql.tot_emp();
        while (rs4.next()){
            tot_emp.setText( String.valueOf(rs4.getInt("data")));
        }
        rs4.close();
        ResultSet rs5 = sql.tot_etu();
        while (rs5.next()){
            tot_etu.setText( String.valueOf(rs5.getInt("data")));
        }
        rs5.close();

        piechart.setTitle("Les métiers les plus emprunt");

        booksDisplay.getChildren().add(piechart);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fullname_labelle.setText(Main.staff_name);
    }
    private int staff_id ;
    private String  staff_name ;
    public void setStaffId(int staffid){
        this.staff_id = staffid;
    }
    public void setStaffName( String staff_nam){
        this.staff_name =  staff_nam;
    }

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    void switch_to_documents(ActionEvent event) throws IOException {
        switch_docs();
    }
    @FXML
    void switch_to_documents_txt(ActionEvent event) throws IOException {
        switch_docs();
    }
    @FXML
    void switch_to_documents_ico(ActionEvent event) throws IOException {
        switch_docs();
    }

    private  void  switch_docs() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/books.fxml")));
        Parent books_fxml  = loader.load();
        Books_page_controller controller = loader.getController();
        controller.setFather(father);
        controller.setStaffId(this.staff_id);
        controller.setStaffName(this.staff_name);
        controller.setData(this.staff_id,Main.isAdmin);
        Scene scene = new Scene(books_fxml);
        scene.setOnMousePressed(e-> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();

        });
        scene.setOnMouseDragged(e->{
            father.setX(e.getScreenX() - xOffset);
            father.setY(e.getScreenY() - yOffset);

        });
        father.setScene(scene);
    }
    public void setData(int staf_id , boolean  admin){
        this.staff_id =staf_id ;
        this.staff_is_admin = admin;
        fullname_labelle.setText(Main.staff_name);
        if(!admin){
            staff_btn.setVisible(false);
        }
    }
    @FXML
    public void handleExitClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void handleMiniClick(ActionEvent event) {
        father.setIconified(true);
    }

    @FXML
    public void logout_controller(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        sql.addlog(this.staff_id,"Logged out");
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/login.fxml")));
        Parent books_fxml  = loader.load();
        Login_controller controller = loader.getController();
        controller.setFather(father);
        Main.staff_id = 0;
        Main.staff_name = "";
        Main.isAdmin = false;
        Scene scene = new Scene(books_fxml);
        scene.setOnMousePressed(e-> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();

        });
        scene.setOnMouseDragged(e->{
            father.setX(e.getScreenX() - xOffset);
            father.setY(e.getScreenY() - yOffset);

        });
        father.setScene(scene);
    }

}
