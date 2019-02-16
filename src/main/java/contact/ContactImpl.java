package contact;

import contants.ContactConstants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import persister.Persister;
import pojo.PersisterResponse;
import pojo.ContactData;
import pojo.ContactResponse;

/**
 * Created by kuabhay on 01/02/19
 */
@RequiredArgsConstructor
public class ContactImpl implements Contact {
    private final Persister persister;

    @Override
    public ContactResponse findContactByName(String name, String nextToken) {

        PersisterResponse persisterResponse = persister.getBatchData(name, nextToken);
        Validate.isTrue(persisterResponse.getContactDataList().size() <= ContactConstants.PAGE_LIMIT,
            "result should be exceed defined limit");

        return ContactResponse.fromContactData(persisterResponse.getContactDataList(),
            persisterResponse.getNextToken());
    }

    @Override
    public ContactData findContactByEmail(String email) {
        return persister.fetchData(email);
    }

    @Override
    public boolean createContact(ContactData contactData) {
        return persister.persistData(contactData);
    }

    @Override
    public boolean updateContact(String email, ContactData contactData) {
        return persister.updateData(email, contactData);
    }

    @Override
    public void deleteContact(String emailAddress) {
         persister.deleteData(emailAddress);
    }

}
