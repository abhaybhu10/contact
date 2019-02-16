package persister;

import pojo.ContactData;
import pojo.PersisterResponse;

/**
 * Created by kuabhay on 01/02/19
 */
public interface Persister {
    boolean persistData(ContactData contact);
    ContactData fetchData(String key);
    PersisterResponse getBatchData(String key, String lastEvaluatedKey);
    boolean updateData(String key, ContactData contact);
    void deleteData(String key);
}
