package persister;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * Created by kuabhay on 01/02/19
 */
public class DDBClient {
    private static String DDB_URL = "http://localhost:8000";
    private static String AWS_REGION = "us-west-2";
    private static DynamoDBMapper ddbMapper = null;

    public synchronized static DynamoDBMapper getDdbMapper() {
        if (ddbMapper == null) {
            ddbMapper = getMapper();
        }
        return ddbMapper;
    }

    private static DynamoDBMapper getMapper() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
            new AwsClientBuilder.EndpointConfiguration(DDB_URL, AWS_REGION))
            .build();
        return new DynamoDBMapper(client);
    }
}
