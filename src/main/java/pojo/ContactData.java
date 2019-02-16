package pojo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import lombok.Setter;

/**
 * Created by kuabhay on 01/02/19
 */
@Data
@Setter
@DynamoDBTable(tableName = "Contacts")
public class ContactData {
    @DynamoDBHashKey
    String email;
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "contact-name-index", attributeName = "contact_name")
    String contactName;
    @DynamoDBAttribute
    String address;
    @DynamoDBAttribute
    String age;
    @DynamoDBAttribute
    String number;
}
