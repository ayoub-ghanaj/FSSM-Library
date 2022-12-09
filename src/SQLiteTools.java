package src;

import java.sql.*;

public class SQLiteTools {

    private static Connection con;
    private static boolean hasData = false ;

    public int[] login(String username , String password) throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        int val = 0 ;
        int id = 0 ;
        PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM staff WHERE status =1 AND cin = ? AND pass = ? LIMIT 1;");
        prepStmt.setString(1,username);
        prepStmt.setString(2,password);
        ResultSet rs = prepStmt.executeQuery();
        while (rs.next()){
            id = rs.getInt("staff_id");
            val = rs.getInt("role");
        }
        return new int[]{val, id};
    }

    public ResultSet displayCategories() throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM categories WHERE status =1;");
        return rs;
    }


    public ResultSet displayTypes() throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM types WHERE status =1;");
        return rs;
    }

    public ResultSet displayStudents() throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM etudiant WHERE status =1;");
        return rs;
    }

    public ResultSet displayDocs() throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM docs WHERE status =1;");
        return rs;
    }

    public void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:database/test1.db");
        initialize();
    }

    public void initialize() throws SQLException {
        if(!hasData){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='types'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE types (\n" +
                        "type_id INTEGER Primary key AUTOINCREMENT,\n" +
                        "ty_libelle TEXT,\n" +
                        "status INTEGER DEFAULT 1\n" +
                        ");");
            }

            rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='categories'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE categories (\n" +
                        "cat_id INTEGER Primary key AUTOINCREMENT,\n" +
                        "cat_libelle TEXT\n" +
                        "status INTEGER DEFAULT 1\n" +
                        ");");
            }

            rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='docs'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE docs (\n" +
                        "doc_id INTEGER Primary key AUTOINCREMENT,\n" +
                        "doc_libelle TEXT,\n" +
                        "doc_image TEXT DEFAULT '../img/defaul_book.png',\n" +
                        "status INTEGER DEFAULT 1,\n" +
                        "cat_id INTEGER,\n" +
                        "type_id INTEGER,\n" +
                        "constraint fk_cat foreign key (cat_id) references categories(cat_id ) ON DELETE CASCADE,\n" +
                        "constraint fk_type foreign key (type_id) references types(type_id ) ON DELETE CASCADE\n" +
                        ");");
            }

            rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='etudiant'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE etudiant (\n" +
                        "etud_id INTEGER Primary key AUTOINCREMENT,\n" +
                        "cne TEXT NOT NULL UNIQUE,\n" +
                        "nom TEXT,\n" +
                        "prenom TEXT,\n" +
                        "phone TEXT NOT NULL UNIQUE,\n" +
                        "email TEXT UNIQUE,\n" +
                        "image TEXT '../img/default_user.png',\n" +
                        "status INTEGER DEFAULT 1\n" +
                        ");");
                PreparedStatement prepStmt = con.prepareStatement("INSERT INTO etudiant(cne,nom,prenom,phone,email) values(?,?,?,?,?);");
                prepStmt.setString(1,"hs123x123");
                prepStmt.setString(2,"ahmed");
                prepStmt.setString(3,"ali");
                prepStmt.setString(4,"0677090909");
                prepStmt.setString(5,"ali@gmail.com");
                prepStmt.execute();
            }

            rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='staff'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE staff (\n" +
                        "staff_id INTEGER primary key AUTOINCREMENT ,\n" +
                        "cin TEXT NOT NULL UNIQUE ,\n" +
                        "nom TEXT  ,\n" +
                        "prenom TEXT  ,\n" +
                        "phone TEXT  ,\n" +
                        "role INTEGER DEFAULT 2 ,\n" +
                        "pass TEXT NOT NULL ,\n" +
                        "status INTEGER DEFAULT 1\n" +
                        ");");

                PreparedStatement prepStmt = con.prepareStatement("INSERT INTO staff(cin,nom,prenom,phone,pass,role) values(?,?,?,?,?,?);");
                prepStmt.setString(1,"admin");
                prepStmt.setString(2,"admin");
                prepStmt.setString(3,"admin");
                prepStmt.setString(4,"admin");
                prepStmt.setString(5,"admin");
                prepStmt.setInt(6,1);
                prepStmt.execute();
            }
            rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='emprunts'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE emprunts (\n" +
                        "emp_id INTEGER primary key AUTOINCREMENT ,\n" +
                        "doc_id INTEGER,\n" +
                        "etud_id INTEGER ,\n" +
                        "staff_id INTEGER,\n" +
                        "emp_state INTEGER DEFAULT 1,\n" +
                        "emp_d TEXT,\n" +
                        "return_d TEXT,\n" +
                        "status INTEGER DEFAULT 1\n," +
                        "constraint fk_doc foreign key (doc_id) references docs(doc_id ) ON DELETE CASCADE,\n" +
                        "constraint fk_etudiant foreign key (etud_id) references etudiant(etud_id ) ON DELETE CASCADE,\n" +
                        "constraint fk_staff foreign key (staff_id) references staff(staff_id ) ON DELETE CASCADE" +
                        ");");
            }



            hasData = true;
        }
    }
    public void addEtudiant(String cne ,String nom ,String prenom ,String email , String phone ,String image ) throws SQLException, ClassNotFoundException {
        if(con == null){
            getConnection();
        }
        PreparedStatement prepStmt = con.prepareStatement("INSERT INTO etudiant(cne,nom,prenom,phone,email,image,status) values(?,?,?,?,?,?,1);");
        prepStmt.setString(1,cne);
        prepStmt.setString(2,nom);
        prepStmt.setString(3,prenom);
        prepStmt.setString(4,phone);
        prepStmt.setString(5,email);
        prepStmt.setString(6,image);
        prepStmt.execute();

    }
}
