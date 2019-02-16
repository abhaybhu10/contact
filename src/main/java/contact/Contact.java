package contact;

import pojo.ContactData;
import pojo.ContactResponse;

/**
 * Created by kuabhay on 01/02/19
 */
public interface Contact {
    ContactResponse findContactByName(String name, String nextToken);
    ContactData findContactByEmail(String email);
    boolean createContact(ContactData contactData);
    boolean updateContact(String email, ContactData contactData);
    void deleteContact(String emailAddress);
}
