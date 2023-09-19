import Controller.Controller;
import DAO.PaintingDAO;
import Model.Painting;
import Service.PaintingService;
import Util.ConnectionSingleton;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Connection conn = ConnectionSingleton.getConnection();
        PaintingDAO paintingDAO = new PaintingDAO(conn);
        PaintingService paintingService = new PaintingService(paintingDAO);

        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("1: add new painting 2: get paintings by author 3: update the author of painting " +
                    "4: delete painting by title");
            int choice = Integer.parseInt(sc.nextLine());
            if(choice == 1){

                System.out.println("enter title");
                String title = sc.nextLine();
                System.out.println("enter author");
                String author = sc.nextLine();
                Painting p = new Painting(title, author);
                paintingService.savePainting(p);

            }else if(choice == 2){

                System.out.println("enter author");
                String author = sc.nextLine();
                List<Painting> paintingList = paintingService.getPaintingsByAuthor(author);

            }else if(choice ==3) {

                System.out.println("enter title");
                String title = sc.nextLine();
                System.out.println("enter author");
                String author = sc.nextLine();
                Painting p = new Painting(title, author);
                paintingService.updatePainting(p);

            }else if(choice == 4){

                System.out.println("enter title");
                String title = sc.nextLine();
                paintingService.deletePainting(title);

            }else{
                System.out.println("invalid choice");
            }
        }

    }
}