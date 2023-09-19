package DAO;

import Model.Painting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object
 * meaning : a style of object intended to contain methods that interact with a database, and, it manages the
 * conversion from database records to/from java objects
 *
 * A DAO should not contain programming logic that isn't database-related. The methods also shouldn't make more than
 * one database statement/query. That should be left to service classes.
 */
public class PaintingDAO {

    private Connection conn;

    public PaintingDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * method that uses JDBC to send a insert statement to my database
     * @param p
     */
    public void insertPainting(Painting p){
        try{
//            using preparedstatement's ? ? syntax prevents SQL injection
            PreparedStatement ps = conn.prepareStatement("insert into painting (title, author) values (?, ?)");
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getAuthor());
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * method that uses JDBC to parse the resultset of a query that selects paintings with a matching author
     * into a java arraylist, and returns it
     * @param author
     * @return
     */
    public List<Painting> queryPaintingsByAuthor(String author){
        List<Painting> paintingList = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from painting where author = ?");
            ps.setString(1, author);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String dbAuthor = rs.getString("author");
                String dbTitle = rs.getString("title");
                Painting dbPainting = new Painting(dbTitle, dbAuthor);
                paintingList.add(dbPainting);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return paintingList;
    }

    /**
     * return a painting based off of a match in title and author, if no such match occurs in the database,
     * return null.
     * @param p
     * @return
     */
    public Painting queryPaintingsByTitleAndAuthor(Painting p){
        try{
            PreparedStatement ps = conn.prepareStatement("select * from painting where title = ? and author = ?");
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getAuthor());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String dbTitle = rs.getString("title");
                String dbAuthor = rs.getString("author");
                Painting dbPainting = new Painting(dbTitle, dbAuthor);
                return dbPainting;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void updatePainting(Painting p){
        try{
            PreparedStatement ps = conn.prepareStatement("update painting set author = ? where title = ?");
            ps.setString(1, p.getAuthor());
            ps.setString(2, p.getTitle());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deletePainting(String title){
        try{
            PreparedStatement ps = conn.prepareStatement("delete painting where title = ?");
            ps.setString(1, title);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
