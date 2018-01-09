package demo.jsr330.helloworld;

import act.Act;
import act.inject.DefaultValue;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@SuppressWarnings("unused")
@Path("/plaintext")
public class AppEntry {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String echo(@DefaultValue("Hello Act") String msg) {
        return msg;
    }

    @GET
    @Path("/echo")
    @Produces(MediaType.APPLICATION_JSON)
    public String echoInJson(String msg) {
        return msg;
    }

    public static void main(String[] args) throws Exception {
        Act.start();
    }

}
