# titan-dynamodb-lambda-seed

This project contains an example of running TitanDB in completely serverless way. Titan is run in memory of
AWS Lambda container and DynamoDB is used as a backend.

## Quickstart

```
sbt assembly
./create-lambda.sh
```
Above steps should be enough to create lambda functions allowing you to modify and query Titan graph.

Additionaly `create-api-gateway.sh` script is provided to help you create API Gateway that will allow you
to call lambda functions as a rest endpoints. Sadly, I was not able to create APIGateway->Lambda integration
with CLI, so if it is possible pull request would be welcome.


