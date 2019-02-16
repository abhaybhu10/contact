aws dynamodb create-table --table-name Contacts --attribute-definitions AttributeName=email,AttributeType=S  --key-schema AttributeName=email,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 --endpoint-url http://localhost:8000

aws dynamodb update-table  --table-name Contacts \
  --attribute-definitions AttributeName=contact_name,AttributeType=S \
  --global-secondary-index-updates \
  "Create={"IndexName"="contact-name-index", "KeySchema"=[ {"AttributeName"="contact_name", "KeyType"="HASH" }], "Projection"={"ProjectionType"="ALL"}, "ProvisionedThroughput"= {"ReadCapacityUnits"=1, "WriteCapacityUnits"=1}   }" --endpoint-url http://localhost:8000

  java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb