package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Car;

@Path("cars")
public class CarsRest {
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser parser = new JsonParser();
    MainController ctrl = new MainController(Persistence.createEntityManagerFactory("carsPU"));

    @Context
    private UriInfo context;

    public CarsRest() {
    }

    @POST
    @Consumes("application/json")
    public String addCar(String content) {
        Car car = gson.fromJson(content, Car.class);
        JsonObject response = new JsonObject();
        
        try {
            car = ctrl.addCar(car);
        } catch (Exception e) {
        }

        response.addProperty("id", car.getId());
        response.addProperty("year", car.getmodel_Year());
        response.addProperty("registered", car.getRegistered());
        response.addProperty("make", car.getMake());
        response.addProperty("model", car.getModel());
        response.addProperty("description", car.getDescription());
        response.addProperty("price", car.getPrice());
        
        return gson.toJson(response);
    }
    
    @GET
    @Produces("application/json")
    public Response getCars() {
        return Response.ok(gson.toJson(ctrl.getCars()), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public void editCar(Car car) {        
        ctrl.editCar(car);
    }
    
    @DELETE
    @Consumes("application/json")
    @Path("/{id}")
    public void deleteCar(@PathParam("id") String id) {
        ctrl.deleteCar(new Long(id));
    }
}
