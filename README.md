# Overview
# Chat API (Restful API)
Representational state transfer (REST) has now become the standard for abstracting request/response type web services into an API. When it is combined with Server Sent Events (otherwise known as Event Source), the result is a fresh new way of proving two-way real-time communication between web clients and a server using synchronous requests/responses (IQ) with REST and asynchronous evening (Message, Presence) with SSE.

# How to use
the chat api can be used server-side from a web application with an openfire admin username/password on most HTTP requests. This is most useful when there is a middleware proxy web-server between Openfire and the web client like nodejs. This is also required when admin type requsts are made. Otherwise, the web client can use the credentials for the openfire user to perform excluse requests for that user only. For security, avoid exposing the master password/secret to the web client. Use basic HTTP authentication.

# REST Endpoint Summary
## Add New User 
POST/signup

## Login
GET/login

## Get users list
GET/users

## Delete user by username
DELETE/user/username

## Get messages list
GET/messages

## Get messages by user name
GET/message/username

## Delete message by user name
DELETE/message/username


# User cases
## How to login and logout
In order to send and receive chat messages for a specific user using the realtime server, a webSocet needs to be created. To login, use the login chat api endpoint with the user password as payload.If this done server-side with the server will respond with a json object like this 
```
{
    "userId": "51da67eb-0e37-47b1-892a-2a85486f3ded",
    "userName": "username",
    "password": "password",
    "e_mail": "email",
    "mobileNum": "mob"
}
```


## How to send and receive one-to-one chat messages
 To send a chat message, first format a json object that looks like this:

```
{
   "body" : "desired message",
    "receiver":"your receiver"
}
```

To receive a one-to-one chat message, ensure you have an SSE connection created on the web client and a handler coded to handle the expected JSON messages. incoming message data will look like this

```
{
    "messageId": "e6f7db01-371e-4e3b-ae06-a70e887d8df4",
    "messageBody": "sended message",
    "sender": "sender",
    "receiver": "receiver",
    "created_at": "Dec 5, 2018 4:17:11 PM"
}
```

