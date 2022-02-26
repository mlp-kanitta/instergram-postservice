# **Instergram**

## ****Introduction****

This application is inspires by the article : 
Scaling Instagram Infra (https://www.infoq.com/presentations/instagram-scale-infrastructure).
Since, I'm a regular user of instagram app, and interesting on how an instagram able to support more than 600M users a day. 
With the objective of learning how to use Cassandra database with Astra Datastax, I have created a mini-version of instagram microservice named "Instergram".

## **Scope of work**

The release divides into each difference MVP as below : 

### **MVP1**
- User can create a photo feed and view the feed(s) they have uploaded.
๊๊- User can delete their own post.
- User can update the caption.
- User can view feeds containing posts from the users they follow.
๊

### **MVP2**
- User can register a new account.
- User can login to his account.
- User should be able to follow other users.
- User should be able to like a photo of user they are following.

### **MVP3** 
- User can view suggested post based on a post that they liked.

## **High-Level Design Architecture**

![alt text](https://github.com/mlp-kanitta/instergram-postservice/blob/main/.design/instergram-architect.jpg?raw=true)


## **API Design**

The endpoint of Post Service should be exposed as below :

- POST   /posts      - Create new post
- GET    /posts      - List user posts
- DELETE /posts/{id} - Delete post by ID
- PATCH  /posts/{id} - Update post by ID

## **Prerequisite**
- JAVA SDK 11
- Internet connection should be available to Cassandra database by Astra Datastax

### How to run a project
1. To check readiness for the first time execute : `mvn clean package`
2. To run the application : `mvn spring-boot:run`

## **Swagger **
After start application success fully, an api should be ready to test at
http://localhost:8081/swagger-ui/#/post-controller/



