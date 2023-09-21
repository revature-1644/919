import DAO.AuthorDAO;
import DAO.PaintingDAO;
import Exceptions.PaintingAlreadyExistsException;
import Model.Painting;
import Service.AuthorService;
import Service.PaintingService;
import Util.ConnectionSingleton;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;

public class PaintingServiceTest {
    Connection conn;
    PaintingDAO mockPaintingDAO;
    AuthorService mockAuthorService;
    PaintingDAO realPaintingDAO;
    AuthorService realAuthorService;
    PaintingService paintingService;
    PaintingService paintingService2;
    @Before
    public void setUp(){
        mockPaintingDAO = Mockito.mock(PaintingDAO.class);
        mockAuthorService = Mockito.mock(AuthorService.class);
        paintingService = new PaintingService(mockPaintingDAO, mockAuthorService);
        conn = ConnectionSingleton.getConnection();
        ConnectionSingleton.resetTestDatabase();
        realPaintingDAO = new PaintingDAO(conn);
        realAuthorService = new AuthorService(new AuthorDAO(conn));
        paintingService2 = new PaintingService(realPaintingDAO, realAuthorService);
    }

    /**
     * the paintingservice SHOULD allow us to save a painting when the paintingDAO can not find the painting
     * already existing, and CAN find the author id based off of a name.
     */
    @Test
    public void savePaintingSuccessfulTest() throws PaintingAlreadyExistsException {
        String expectedName = "van gogh";
        String expectedTitle = "starry night";
        Painting testPainting = new Painting(1, expectedTitle, 0, 1880);
        int expectedId = 5;
        Mockito.when(mockAuthorService.getIdFromName(expectedName)).thenReturn(5);
        Mockito.when(mockPaintingDAO.queryPaintingsByTitleAndAuthor(expectedTitle, expectedId)).thenReturn(null);
        paintingService.savePainting(testPainting, expectedName);
//        verify that we did actually attempt an insert!
        Mockito.verify(mockPaintingDAO).insertPainting(Mockito.any());
    }
    /**
     * the paintingService should NOT attempt an insert when the querypaintingsbytitleandauthor reveals that the
     * painting already existed!
     */
    @Test
    public void savePaintingUnsuccessfulTest(){
        String expectedName = "van gogh";
        String expectedTitle = "starry night";
        Painting testPainting = new Painting(1, expectedTitle, 0, 1880);
        int expectedId = 5;
        Mockito.when(mockAuthorService.getIdFromName(expectedName)).thenReturn(5);
        Mockito.when(mockPaintingDAO.queryPaintingsByTitleAndAuthor(expectedTitle, expectedId)).thenReturn(testPainting);
        try {
            paintingService.savePainting(testPainting, expectedName);
//            prepare for a paintingalreadyexistsexception
        }catch(Exception e){
            //        verify that we never attempt an insert!
            Mockito.verify( mockPaintingDAO, Mockito.never()).insertPainting(Mockito.any());
        }

    }

    /**
     * the paintingservice SHOULD allow us to save a painting when the paintingDAO can not find the painting
     * already existing, and CAN find the author id based off of a name.
     */
    @Test
    public void savePaintingSuccessfulTestUnmocked() throws PaintingAlreadyExistsException {
        String expectedName = "van gogh";
        String expectedTitle = "test painting";
        Painting testPainting = new Painting(2225, expectedTitle, 0, 1881);
        int expectedId = 5;
        paintingService2.savePainting(testPainting, expectedName);
//        verify that we did actually attempt an insert!
    }
    /**
     * the paintingService should NOT attempt an insert when the querypaintingsbytitleandauthor reveals that the
     * painting already existed!
     */
    @Test
    public void savePaintingUnsuccessfulTestUnmocked(){
        String expectedName = "van gogh";
        String expectedTitle = "starry night";
        Painting testPainting = new Painting(2222, expectedTitle, 0, 1880);
        int expectedId = 5;
        try {
            paintingService2.savePainting(testPainting, expectedName);
//            prepare for a paintingalreadyexistsexception
        }catch(PaintingAlreadyExistsException e){
            //        verify that we never attempt an insert!

        }
//        assert statements
    }
}
