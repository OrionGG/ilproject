package DBLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import CategoryGenerator.Categories;

public class DAOScoresIntermediate extends DAOWebsClassified{
	public static ResultSet selectUrlsFromCategory(Categories category) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		setUpCatego();
		Statement st = conexionCategorized.createStatement();
		String query="SELECT url,indextype,score FROM scores_intermediate WHERE category='"+category+"' ORDER BY value url,index";
		System.out.println(query);
		ResultSet rs = st.executeQuery(query);

		return rs;
	}
	public static void saveUrl(String domainUrl, int i, String stringValue,	float score)  {

		try {
			setUpCatego();
			Statement st = conexionCategorized.createStatement();
			String sql = "INSERT INTO scores_intermediate(url,indextype,category,score) VALUES ('"+domainUrl+"','"+i+"','"+stringValue+"','"+score+"')";
			st.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				conexionCategorized.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
