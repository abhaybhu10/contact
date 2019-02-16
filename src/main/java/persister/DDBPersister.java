package persister;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import pojo.ContactData;
import contants.ContactConstants;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import pojo.PersisterResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by kuabhay on 01/02/19
 */
@Slf4j
public class DDBPersister implements Persister{

    @Override
    public boolean persistData(@NonNull ContactData contact) {
        Preconditions.checkNotNull(contact.getEmail(), "Email address should not be null");
        Preconditions.checkNotNull(contact.getNumber(), "Number should not be null");
        Preconditions.checkNotNull(contact.getContactName(), "FirstName can't be null");
        DDBClient.getDdbMapper().save(contact);
        return true;
    }

    @Override
    public ContactData fetchData(String key) {
        Validate.isTrue(StringUtils.isNotEmpty(key), "Email should not be empty");
        DynamoDBQueryExpression<ContactData> queryExpression = new DynamoDBQueryExpression<>();
        ContactData contactData = new ContactData();
        contactData.setEmail(key);
        queryExpression.setHashKeyValues(contactData);

        List<ContactData> retrievedData = DDBClient.getDdbMapper().query(ContactData.class, queryExpression);

        if (retrievedData == null || retrievedData.size() == 0 ) {
            log.info("No entry is present in ddb for key {}", key);
            return null;
        } else {
            log.debug("found contact with key {} contact {}", key, retrievedData);
        }
        return retrievedData.get(0);
    }

    @Override
    public PersisterResponse getBatchData(String key, String lastEvaluatedKey) {
        Validate.isTrue(StringUtils.isNotEmpty(key), "Email should not be empty");
        DynamoDBQueryExpression<ContactData> queryExpression = new DynamoDBQueryExpression<>();
        ContactData contactData = new ContactData();
        contactData.setContactName(key);
        queryExpression.setHashKeyValues(contactData);
        queryExpression.setIndexName(ContactConstants.CONTACT_NAME_INDEX);
        queryExpression.setConsistentRead(false);
        Map<String, AttributeValue> attributeValueMap = Maps.newHashMap();

        if (StringUtils.isNotEmpty(lastEvaluatedKey)) {
            attributeValueMap.put(ContactConstants.EMAIL, new AttributeValue().withS(lastEvaluatedKey));
            attributeValueMap.put(ContactConstants.CONTACT_NAME, new AttributeValue().withS(key));
            queryExpression.setExclusiveStartKey(attributeValueMap);
        }

         queryExpression.setLimit(ContactConstants.PAGE_LIMIT);

        QueryResultPage<ContactData> retrievedData =
            DDBClient.getDdbMapper().queryPage(ContactData.class, queryExpression);


        PersisterResponse persisterResponse = new PersisterResponse();
        persisterResponse.setContactDataList(retrievedData.getResults());
        persisterResponse.setItemCount(retrievedData.getCount());
        log.info("retrieved data {}", retrievedData.getLastEvaluatedKey());

        if (retrievedData.getLastEvaluatedKey() != null)
            persisterResponse
                .setNextToken(retrievedData.getLastEvaluatedKey().get(ContactConstants.EMAIL).getS());
        else persisterResponse.setNextToken(null);

        return persisterResponse;
    }

    @Override
    public boolean updateData(@NonNull String key,
                              @NonNull ContactData contact) {
        Preconditions.checkNotNull(key, "Email address should not be null");
        DynamoDBMapperConfig dynamoDBMapperConfig =
            new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES);

        ContactData fetchedData = fetchData(key);
        if (fetchedData == null) {
            log.error("no contact found for the key {}", key);
            return false;
        }
        contact.setEmail(fetchedData.getEmail());
        DDBClient.getDdbMapper().save(contact, dynamoDBMapperConfig);
        return true;
    }

    @Override
    public void deleteData(String key) {
        ContactData contactData = new ContactData();
        contactData.setEmail(key);
        DDBClient.getDdbMapper().delete(contactData);
    }
}
