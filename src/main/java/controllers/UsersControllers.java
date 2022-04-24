package controllers;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import models.User;
import repository.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/users")
@Produces(APPLICATION_JSON)
public class UsersControllers {
    private final UserRepository repository;

    public UsersControllers(UserRepository userRepository){
        this.repository = userRepository;
    }

    @GET
    @Path("/{id}")
    @Timed
    public Response get (@PathParam("username") String username, @Auth User us){
          User user = repository.findByUsername(username);
        return Response.status(OK).entity(user).build();
    }

    @POST
    @Path("/")
    @Timed
    public Response post(User userP,@Auth User us) {
        User user = repository.create(userP);
        return Response.status(CREATED).entity(user).build();
    }
}
