package Service;

import DAO.PaintingDAO;
import Exceptions.PaintingAlreadyExistsException;
import Model.Painting;

import java.util.List;

/**
 * Service used for CRUD operations on paintings
 * Create Read Update Delete
 * (ie, an application that doesn't require any complicated programming logic - just a path from user input to data layer)
 */
public class PaintingService {
    private PaintingDAO paintingDAO;
    private AuthorService authorService;

    public PaintingService(PaintingDAO paintingDAO, AuthorService authorService){
        this.paintingDAO = paintingDAO;
        this.authorService = authorService;
    }

    /**
     * check if painting match already exists in the database, if it does, throw an exception.
     * otherwise, save the painting.
     * @param p
     */
    public void savePainting(Painting p, String name) throws PaintingAlreadyExistsException {
        int authorId = authorService.getIdFromName(name);
        Painting dbPainting = paintingDAO.queryPaintingsByTitleAndAuthor(p.getTitle(), authorId);
        if (dbPainting == null) {
            p.setAuthorFkey(authorId);
            paintingDAO.insertPainting(p);
        }else{
            throw new PaintingAlreadyExistsException();
        }
    }

    public List<Painting> getPaintingsByAuthor(String author){
        List<Painting> paintingList = paintingDAO.queryPaintingsByAuthor(author);
        return paintingList;
    }

    public void updatePainting(Painting p){
        paintingDAO.updatePainting(p);
    }

    public void deletePainting(String title){
        paintingDAO.deletePainting(title);
    }

}
