package Controller;

import Exceptions.PaintingAlreadyExistsException;
import Model.Painting;
import Service.AuthorService;
import Service.PaintingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * A Controller is a class that manages web interaction - it takes in requests and produces responses
 * The reason why it's called a 'controller' is that it need to route a request to the most appropriate method
 * to handle it
 */
public class MuseumController {
    PaintingService paintingService;
    AuthorService authorService;

    public MuseumController(PaintingService paintingService, AuthorService authorService){
        this.paintingService = paintingService;
        this.authorService = authorService;
    }

    public Javalin getAPI() {
        Javalin app = Javalin.create();
// lambda expression is the variable -> { do something with variable } syntax
// java is, in that lambda expression, implementing a class
// it does this using a functional interface - an interface with only a single method
// once this is done, we can generate an object with a single method based off of that interface
// and run that method - that's how we pass methods around as parameters

        app.get("painting", this::getAllPaintingsHandler);
        app.post("painting", this::postPaintingHandler);
        return app;
    }

    private void getAllPaintingsHandler(Context context){
        context.json(paintingService.getAllPaintings());
    }

    private void postPaintingHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Painting painting = om.readValue(context.body(), Painting.class);
        try{
            paintingService.savePainting(painting);
//            201 is the 'resource created' response
            context.status(201);
        }catch(PaintingAlreadyExistsException e){
//            400 is the 'user error' response
            context.status(400);
        }
    }
}
