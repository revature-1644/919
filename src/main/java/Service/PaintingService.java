package Service;

import DAO.PaintingDAO;
import Model.Painting;

import java.util.List;

public class PaintingService {
    private PaintingDAO paintingDAO;

    public PaintingService(PaintingDAO paintingDAO){
        this.paintingDAO = paintingDAO;
    }

    public void savePainting(Painting p){

    }

    public List<Painting> getPaintingsByAuthor(String author){
        return null;
    }

    public void updatePainting(Painting p){

    }

    public void deletePainting(String title){

    }

}
