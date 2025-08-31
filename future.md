# Using this file to register in AWS ECR
- Future Thining about Calculator How many peaople will access
````
name: Build and Push to AWS ECR

# This workflow runs on pushes to the main branch
on:
  push:
    branches:
      - main
  workflow_dispatch: # Allows manual triggering from the GitHub UI

# These permissions are needed to interact with AWS ECR using OIDC
permissions:
  id-token: write
  contents: read

env:
  # The name of your ECR repository
  ECR_REPOSITORY: carconfig-api
  # The Dockerfile is located in the carconfig-api subdirectory
  DOCKER_CONTEXT: ./carconfig-api

jobs:
  build-and-push-to-ecr:
    name: Build and Push to ECR
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v4

      - name: Configure AWS credentials
        uses: aws-actions/configure-aws-credentials@v4
        with:
          # The AWS region where your ECR repository is
          aws-region: ${{ secrets.AWS_REGION }}
          # The ARN of the IAM role to assume
          role-to-assume: ${{ secrets.AWS_IAM_ROLE_ARN }}
          role-session-name: GitHubActionsECR-Session

      - name: Log in to Amazon ECR
        id: login-ecr
        uses: aws-actions/amazon-ecr-login@v2

      - name: Build, tag, and push image to Amazon ECR
        id: build-image
        env:
          ECR_REGISTRY: ${{ steps.login-ecr.outputs.registry }}
          IMAGE_TAG: ${{ github.sha }}
        run: |
          docker build -t $ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG -t $ECR_REGISTRY/$ECR_REPOSITORY:latest $DOCKER_CONTEXT
          docker push --all-tags $ECR_REGISTRY/$ECR_REPOSITORY
````