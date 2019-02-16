package pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by kuabhay on 13/02/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {
    List<ContactData> contactDataList;
    String nextToken;
    int size;
    public static ContactResponse fromContactData(List<ContactData> contactData,
                                                  String nextToken) {
        int responseLength = contactData != null ? contactData.size() : 0;
        return new ContactResponseBuilder()
            .nextToken(nextToken)
            .size(responseLength)
            .contactDataList(contactData)
            .build();
    }
}
