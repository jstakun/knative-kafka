package com.redhat.developer.demos;

import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/")
public class MyKnativeSink {

  @ConfigProperty(name = "event.processing.time", defaultValue="10000") 
  String eventProcessingTime;
	
  private final String prefix = "Aloha";
    
  private final String HOSTNAME = System.getenv().getOrDefault("HOSTNAME", "unknown");

  private int count = 0;
  
  private Random random = new Random();

  @GET    
  @Produces(MediaType.TEXT_PLAIN)
  public String greet() {
      count++;
      return prefix + " " + HOSTNAME + ":" + count + "\n";
  }

  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public Response event(String event) {
    long start = System.currentTimeMillis();
    try { // adding some "processing time"
    	int interval = 10000;
    	if (eventProcessingTime.startsWith("random:")) {
    		int bound = Integer.valueOf(eventProcessingTime.substring(7)).intValue();
    		interval = random.nextInt(bound);
    	} else {
    		interval = Integer.valueOf(eventProcessingTime).intValue();
    	}
    	Thread.sleep(interval);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    final String response = "EVENT: " + event + " processed in " + (System.currentTimeMillis() - start) + "ms.";
    System.out.println(response);
    return Response.ok().build();
  }

  @GET
  @Path("/healthz")
  @Produces(MediaType.TEXT_PLAIN)
  public Response health() {
      return Response.ok().build();
  }

  @GET    
  @Path("/test")
  @Produces(MediaType.TEXT_PLAIN)
  public String test() {      
      return "test";
  }
 

}