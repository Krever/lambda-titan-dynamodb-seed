#!/bin/bash
set -x

role_name='lambda_to_dynamodb_full'

assume_policy='{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}'

role_arn=$(aws iam create-role \
  --role-name "$role_name" \
  --assume-role-policy-document "$assume_policy" \
  --output text \
  --query Role.Arn)

role_policy='{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "Stmt1428341300017",
            "Action": [
                "dynamodb:DescribeTable",
                "dynamodb:DeleteItem",
                "dynamodb:GetItem",
                "dynamodb:PutItem",
                "dynamodb:Query",
                "dynamodb:Scan",
                "dynamodb:UpdateItem"
            ],
            "Effect": "Allow",
            "Resource": "*"
        },
        {
            "Sid": "",
            "Resource": "*",
            "Action": [
                "logs:CreateLogGroup",
                "logs:CreateLogStream",
                "logs:PutLogEvents"
            ],
            "Effect": "Allow"
        }
    ]
}'

policy_arn=$(aws iam create-policy \
  --policy-name "dynamodb_access_policy" \
  --policy-document "$role_policy" \
  --output text \
  --query Policy.Arn
  )

aws iam attach-role-policy \
  --role-name "$role_name" \
  --policy-arn $policy_arn


aws lambda create-function \
  --function-name AddTitanNode \
  --runtime java8 \
  --role "$role_arn" \
  --handler "example.AddNodeHandler" \
  --region eu-west-1 \
  --timeout 15 \
  --memory-size 512 \
  --zip-file fileb://target/scala-2.11/titan-dynamodb-lambda-seed-assembly-1.0.jar

aws lambda create-function \
  --function-name GetTitanNode \
  --runtime java8 \
  --role "$role_arn" \
  --handler "example.GetNodeHandler" \
  --region eu-west-1 \
  --timeout 15 \
  --memory-size 512 \
  --zip-file fileb://target/scala-2.11/titan-dynamodb-lambda-seed-assembly-1.0.jar
