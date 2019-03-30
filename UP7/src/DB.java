/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Даша
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

class ShopItem {
	int number;
	String adress;
	ShopItem( int number, String adress) {
		this.number = number;
		this.adress = adress;
	}
}

class TechnikItem {
	int number;
	String producer;
	String kind;
	String model;
	TechnikItem(int number, String producer, String kind, String model) {
		this.kind = kind;
		this.producer = producer;
		this.number = number;
		this.model = model;
	}
}

class InfoItem {
	String model;
	double price;
	double warranty;
	InfoItem (String model, double price, double warranty) {
		this.warranty = warranty;
		this.model = model;
		this.price = price;
	}
}


public class DB {
    String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String connect = "jdbc:derby:ShopDB;create=true";
        
    public void CreateDB(){
        System.setProperty("derby.system.home", "C:\\Shop" );
		
		try {
			// Регистрируем драйвер JDBC
			Class.forName( driver );
			// Подключаемся к БД 
			Connection conn = DriverManager.getConnection(connect);
			// Выполняем запросы 
			Statement st = conn.createStatement();
			st.executeUpdate("CREATE TABLE Shop " + "(Number INT PRIMARY KEY, Adress VARCHAR(32))");
			st.executeUpdate("CREATE TABLE Technik " + "(Number INT REFERENCES Shop(Number) , Producer VARCHAR(32), Kind VARCHAR(32), Model VARCHAR(32) PRIMARY KEY)");
			st.executeUpdate("CREATE TABLE Information " + "(Model VARCHAR(32) REFERENCES Technik(Model), Price DOUBLE, Warranty DOUBLE)");
			st.close();
			
		} catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Database already exist");
		}
    }
    
    static String shopIns = "INSERT INTO Shop(Number, Adress) VALUES(?,?)";
    static String technikIns = "INSERT INTO Technik(Number, Producer, Kind, Model) VALUES(?,?,?,?)";
    static String infoIns = "INSERT INTO Information(Model, Price, Warranty) VALUES(?,?,?)";
	
    public void FillDB(int i, String mas[]){
		// Текущий каталог для Derby
	System.setProperty("derby.system.home", "C:\\Shop" );
		
	try {
            // Регистрируем драйвер JDBC
		Class.forName( driver );
		// Подключаемся к БД 
		Connection conn = DriverManager.getConnection(connect);
                // Выполняем запросы 
                PreparedStatement st;
                
                if(i == 1){
                    st = conn.prepareStatement(shopIns);
                    try {
                        st.setInt( 1,  Integer.parseInt(mas[0]));
                        st.setString( 2, mas[1]);
                        st.executeUpdate();
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, e);
                        }			
                    st.close();
                }
                if(i==2){
                    st = conn.prepareStatement(technikIns);
                    try {
                        st.setInt( 1, Integer.parseInt(mas[0]));
                        st.setString( 2, mas[1] );
                        st.setString( 3, mas[2] );
                        st.setString( 4, mas[3] );
                             
                        st.executeUpdate();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
		    }			
                    st.close();
                }

                if(i == 3){
                    st = conn.prepareStatement(infoIns);
                    try {
                        st.setString( 1, mas[0] );
                        st.setDouble( 2, Double.parseDouble(mas[1]));
                        st.setDouble( 3, Double.parseDouble(mas[2]) );
                        st.executeUpdate();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
		    }		
                    st.close();
                }

                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
    }
}
