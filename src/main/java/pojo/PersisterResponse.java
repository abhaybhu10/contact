package pojo;

import lombok.Data;

import java.util.List;

/**
 * Created by kuabhay on 16/02/19
 */
@Data
public class PersisterResponse {
    List<ContactData> contactDataList;
    String nextToken;
    int itemCount;
}
