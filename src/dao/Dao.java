package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Vene;

public class Dao {
	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	private String db ="Venekanta.sqlite";
	
	private Connection yhdista(){
    	Connection con = null;    	
    	String path = System.getProperty("catalina.base");    	
    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); //Eclipsessa
    	System.out.println("Polku on: " + path);
    	//path += "/webapps/"; //Tuotannossa. Laita kanta webapps-kansioon.
    	String url = "jdbc:sqlite:"+path+db;    	
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	        System.out.println("Yhteys avattu.");
	     }catch (Exception e){	
	    	 System.out.println("Yhteyden avaus ep�onnistui.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public ArrayList<Vene> listaaKaikki(){
		ArrayList<Vene> veneet = new ArrayList<Vene>();
		sql = "SELECT * FROM veneet";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui									
					while(rs.next()){
						Vene vene = new Vene();
						
						vene.setTunnus(rs.getInt(1));
						vene.setNimi(rs.getString(2));
						vene.setMerkkimalli(rs.getString(3));	
						vene.setPituus(rs.getDouble(4));
						vene.setLeveys(rs.getDouble(5));
						vene.setHinta(rs.getInt(6));
						
						veneet.add(vene);
					}					
				}				
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return veneet;
	}
	
	public ArrayList<Vene> listaaKaikki(String hakusana){
		ArrayList<Vene> veneet = new ArrayList<Vene>();
		sql = "SELECT * FROM veneet WHERE tunnus LIKE ? or nimi LIKE ? or merkkimalli LIKE ? or pituus LIKE ? or leveys LIKE ? or hinta LIKE ?";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);  
				stmtPrep.setString(1, "%" + hakusana + "%");
				stmtPrep.setString(2, "%" + hakusana + "%");   
				stmtPrep.setString(3, "%" + hakusana + "%");  
				stmtPrep.setString(4, "%" + hakusana + "%");
				stmtPrep.setString(5, "%" + hakusana + "%");
				stmtPrep.setString(6, "%" + hakusana + "%");
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui							
					while(rs.next()){
						Vene vene = new Vene();
						
						vene.setTunnus(rs.getInt(1));
						vene.setNimi(rs.getString(2));
						vene.setMerkkimalli(rs.getString(3));	
						vene.setPituus(rs.getDouble(4));	
						vene.setLeveys(rs.getDouble(5));
						vene.setHinta(rs.getInt(6));
						
						veneet.add(vene);
					}						
				}
				con.close();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return veneet;
	}
	
	
	public boolean lisaaVene(Vene vene){
		boolean paluuArvo=true;
		sql="INSERT INTO veneet VALUES(?,?,?,?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setInt(1, vene.getTunnus()); //ei automaattista numerointia?, jos on niin pois t��
			stmtPrep.setString(2, vene.getNimi());
			stmtPrep.setString(3, vene.getMerkkimalli());
			stmtPrep.setDouble(4, vene.getPituus());
			stmtPrep.setDouble(5, vene.getLeveys());
			stmtPrep.setDouble(6, vene.getHinta());
			//stmtPrep.setInt(7, vene.getTunnus());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public boolean poistaVene(int tunnus){ //Oikeassa el�m�ss� tiedot ensisijaisesti merkit��n poistetuksi.
		boolean paluuArvo=true;
		sql="DELETE FROM veneet WHERE tunnus=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setInt(1, tunnus);			
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}	
	
	public Vene etsiVene(int tunnus){
		Vene vene = null;
		sql = "SELECT * FROM veneet WHERE tunnus=?";       
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setInt(1, tunnus);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli tunnus on k�yt�ss�
        			rs.next();
        			vene = new Vene();        			
        			vene.setTunnus(rs.getInt(1));
					vene.setNimi(rs.getString(2));
					vene.setMerkkimalli(rs.getString(3));	
					vene.setPituus(rs.getDouble(4));
					vene.setLeveys(rs.getDouble(5));
					vene.setHinta(rs.getInt(6));
				}	
        		con.close(); 
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return vene;		
	}
	
	public boolean muutaVene(Vene vene, int tunnus){
		System.out.println("dao.muutaVene");
		boolean paluuArvo=true;
		sql="UPDATE veneet SET tunnus=?, nimi=?, merkkimalli=?, pituus=?, leveys=?, hinta=? WHERE tunnus=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setInt(1, vene.getTunnus());
			stmtPrep.setString(2, vene.getNimi());
			stmtPrep.setString(3, vene.getMerkkimalli());
			stmtPrep.setDouble(4, vene.getPituus());
			stmtPrep.setDouble(5, vene.getLeveys());
			stmtPrep.setInt(6, vene.getHinta());
			stmtPrep.setInt(7, tunnus);
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	//Testataan onko veneen tunnus jo k�yt�ss� ennen uuden veneen lis��mist�
	public boolean onkoTunnusta(int tunnus) {
		sql = "SELECT * FROM veneet WHERE tunnus=?";    
		boolean vastaus = true;
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setInt(1, tunnus);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){
        			vastaus = false;
        		}	
    		con.close(); 
		}			
	} catch (Exception e) {
		e.printStackTrace();
	}		
	return vastaus;		
}
		
	}
	