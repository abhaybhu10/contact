import pojo.ContactData;
import persister.DDBPersister;

/**
 * Created by kuabhay on 10/02/19
 */
public class Test {
    public static void main(String args[]) {
        ContactData contactData = new ContactData();
        contactData.setContactName("abhay");
        contactData.setEmail("abhaybhu10@gmail.com");
        contactData.setAddress("test");
        contactData.setNumber("919191");
        new DDBPersister().persistData(contactData);
        //System.out.println(new DDBPersister().getBatchData("abhay", null).get(0));
    }
}
