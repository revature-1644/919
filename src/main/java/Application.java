import Controller.MuseumController;
import DAO.AuthorDAO;
import DAO.PaintingDAO;
import Service.AuthorService;
import Service.PaintingService;
import Util.ConnectionSingleton;
import io.javalin.Javalin;

import java.sql.Connection;

public class Application {
    public static void main(String[] args) {
        Connection conn = ConnectionSingleton.getConnection();
        PaintingDAO paintingDAO = new PaintingDAO(conn);
        AuthorDAO authorDAO = new AuthorDAO(conn);
        AuthorService authorService = new AuthorService(authorDAO);
        PaintingService paintingService = new PaintingService(paintingDAO, authorService);

//        Scanner sc = new Scanner(System.in);
        MuseumController museumController = new MuseumController(paintingService, authorService);
        Javalin server = museumController.getAPI();
        server.start();

    }
}