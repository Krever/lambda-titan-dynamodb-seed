#!/bin/bash

api_name=TitanAPI
api_description="Invoke sample Lamba functions interacting with TitanDB on DybamoDB"
stage_name=test
region=eu-west-1

api_id="9cl2cll787"
#api_id=$(aws apigateway create-rest-api \
#  --region "$region" \
#  --name "$api_name" \
#  --description "$api_description" \
#  --output text \
#  --query 'id')
echo api_id=$api_id

root_res_id=$(aws apigateway get-resources \
  --region "$region" \
  --rest-api-id "$api_id" \
  --output text \
  --query "items[?path == '/'].[id]")
echo root_res_id=$root_res_id

nodes_res_id=$(aws apigateway create-resource \
  --region "$region" \
  --rest-api-id "$api_id" \
  --parent-id "$root_res_id" \
  --path-part 'nodes' \
  --output text \
  --query 'id')
echo nodes_res_id=$nodes_res_id

node_res_id=$(aws apigateway create-resource \
  --region "$region" \
  --rest-api-id "$api_id" \
  --parent-id "$nodes_res_id" \
  --path-part '{nodeId}' \
  --output text \
  --query 'id')
echo node_res_id=$node_res_id

nodes_post_meth_id=$(aws apigateway put-method \
  --region "$region" \
  --rest-api-id "$api_id" \
  --resource-id "$nodes_res_id" \
  --http-method "POST" \
  --authorization-type "NONE")
echo nodes_post_meth_id=$nodes_post_meth_id

nodes_get_meth_id=$(aws apigateway put-method \
  --region "$region" \
  --rest-api-id "$api_id" \
  --resource-id "$nodes_res_id" \
  --http-method "GET" \
  --authorization-type "NONE")
echo nodes_get_meth_id=$nodes_get_meth_id

node_get_meth_id=$(aws apigateway put-method \
  --region "$region" \
  --rest-api-id "$api_id" \
  --resource-id "$node_res_id" \
  --http-method "GET" \
  --authorization-type "NONE")
echo node_get_meth_id=$node_get_meth_id


