package entities.DB;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class main {
	
	public static void main(String[] args) {
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/websclassified","root","admin");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Statement s0=null;
		try {
			s0=con.createStatement();
			s0.executeUpdate(" insert into urls (url) select distinct url from urls_classified;");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Statement s=null;
		Statement s2=null;
		Statement s3=null;
		Statement s4=null;
		Statement s5=null;
		Statement s6=null;
		
		try {
			s = con.createStatement();
			s2=con.createStatement();
			s3=con.createStatement();
			s4=con.createStatement();
			s5=con.createStatement();
			s6=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs=null;
		try {
			rs = s.executeQuery("select distinct id from urls");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next()){
				int id_aux=rs.getInt(1);
				ResultSet rs2=s2.executeQuery("select category, score from urls_classified where url in (select url from urls where id="+id_aux+")");
				while(rs2.next()){
					int cat_aux=rs2.getInt(1);
					float score_aux=rs2.getFloat(2);
					s3.executeUpdate("insert into cats (id,cat_id,score) values ("+id_aux+","+cat_aux+","+score_aux+")");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ResultSet rs4=null;
		try {
			rs4 = s4.executeQuery("select id, url from urls");
			while(rs4.next()){
				int id_aux=rs4.getInt(1);
				String url_aux=rs4.getString(2);
				ResultSet rs5=s5.executeQuery("select cat_id,score from cats where id="+id_aux+" order by score desc limit 5");
				if(rs5.next()){
					int cat_aux1=rs5.getInt(1);
					float score_aux1=rs5.getFloat(2);
					s6.executeUpdate("insert into list (id,url,cat1,score1) values ("+id_aux+","+'"'+url_aux+'"'+","+cat_aux1+","+score_aux1+")");
					if(rs5.next()){
						int cat_aux2=rs5.getInt(1);
						float score_aux2=rs5.getFloat(2);
						s6.executeUpdate("update list set cat2="+cat_aux2 +" ,score2="+score_aux2+" where id="+id_aux);
						if(rs5.next()){
							int cat_aux3=rs5.getInt(1);
							float score_aux3=rs5.getFloat(2);
							s6.executeUpdate("update list set cat3="+cat_aux3 +" ,score3="+score_aux3+" where id="+id_aux);
							if(rs5.next()){
								int cat_aux4=rs5.getInt(1);
								float score_aux4=rs5.getFloat(2);
								s6.executeUpdate("update list set cat4="+cat_aux4 +" ,score4="+score_aux4+" where id="+id_aux);
								if(rs5.next()){
									int cat_aux5=rs5.getInt(1);
									float score_aux5=rs5.getFloat(2);
									s6.executeUpdate("update list set cat5="+cat_aux5 +" ,score5="+score_aux5+" where id="+id_aux);
								}
							}
						}
					}
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
