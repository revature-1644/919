package DAO;

import Model.Painting;

import java.sql.Connection;
import java.util.List;

public class PaintingDAO {

    private Connection conn;

    public PaintingDAO(Connection conn){
        this.conn = conn;
    }

    public void insertPainting(Painting p){

    }

    public List<Painting> queryPaintingsByAuthor(String author){
        return null;
    }

    public void updatePainting(Painting p){

    }

    public void deletePainting(String title){

    }

}
