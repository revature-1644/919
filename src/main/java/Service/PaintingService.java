package Service;

import DAO.PaintingDAO;
import Model.Painting;

import java.util.List;

/**
 * Service used for CRUD operations on paintings
 * Create Read Update Delete
 * (ie, an application that doesn't require any complicated programming logic - just a path from user input to data layer)
 */
public class PaintingService {
    private PaintingDAO paintingDAO;

    public PaintingService(PaintingDAO paintingDAO){
        this.paintingDAO = paintingDAO;
    }

    public void savePainting(Painting p){
        paintingDAO.insertPainting(p);
    }

    public List<Painting> getPaintingsByAuthor(String author){
        List<Painting> paintingList = paintingDAO.queryPaintingsByAuthor(author);
        return paintingList;
    }

    public void updatePainting(Painting p){

    }

    public void deletePainting(String title){

    }

}
