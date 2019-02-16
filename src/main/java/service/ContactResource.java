package service;

import contact.Contact;
import pojo.ContactData;
import pojo.ContactResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by kuabhay on 01/02/19
 */
@Path("/contact")
@RequiredArgsConstructor
@Slf4j
public class ContactResource {

    private final Contact contact;

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContactData getContactByEmail(@PathParam("email") String email) {
        return contact.findContactByEmail(email);
    }

    @GET
    @Path("/name/{name}/")
    @Produces(MediaType.APPLICATION_JSON)
    public ContactResponse getContactByName(@PathParam("name") String name,
                                            @QueryParam("nextToken") String nextToken) {
        ContactResponse contactResponse = contact.findContactByName(name, nextToken);
        log.info("data received for request name {} token {} data {}", name, nextToken, contactResponse.toString());
        return contactResponse;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Boolean savePasteData(ContactData contactData) {
        log.info("input {}", contactData);
        Boolean id = contact.createContact(contactData);
        log.info("return id {}",  id);
        return id;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{emailID}")
    public void updateContact(ContactData contactData, @PathParam("emailID") String emailId) {
         contact.updateContact(emailId, contactData);
    }

    @DELETE
    @Path("/{emailId}")
    public void deleteContact(@PathParam("emailId") String emailId) {
        contact.deleteContact(emailId);
    }

}
