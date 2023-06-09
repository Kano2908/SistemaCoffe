package Views;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionSQL {
    String bd = "sistemaCoffee";
    String url = "jdbc:mysql://localhost:3306/";
    String user = "Kano";                           //Variables de tipo String que almacenaran datos para la conexion a la base
    String psw = "Royalzkano01";
    String driver = "com.mysql.cj.jdbc.Driver";
    
    Connection con; //con es de tipo Connection
    
    public Connection getConnection(){
        try{
            Class.forName(this.driver);
            con = DriverManager.getConnection(url+bd, user, psw); //Le pasamos los datos al driver y se almacena en la variable con
            con.setAutoCommit(false); //Deacativamos el autocommit = 0;
            System.out.println("Conexion Establecida En: "+bd);
        } catch(Exception e){
            System.out.println("No Se Conecto: "+e);
        }
        return con;
    }
}
