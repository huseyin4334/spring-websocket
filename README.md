# Websocket
Websocket is a protocol that provides full-duplex communication channels over a single TCP connection. 
It is used in web applications to provide real-time communication between a client and a server. 
Websocket is a different protocol from HTTP, but it uses the same port (80) by default. 
It is designed to be implemented in web browsers and web servers, but it can be used by any client or server application.

## Websocket Server
A websocket server is a server that listens for incoming websocket connections.
It is responsible for handling the handshake request and establishing a connection with the client.
Once the connection is established, the server can send and receive messages to and from the client.

## Websocket Client
A websocket client is a client that initiates a websocket connection with a websocket server.
It is responsible for sending the handshake request and establishing a connection with the server.
Once the connection is established, the client can send and receive messages to and from the server.

## Websocket Endpoint
A websocket endpoint is a URL that the client uses to connect to the websocket server.
It is similar to a URL in HTTP, but it uses the `ws://` or `wss://` scheme instead of `http://` or `https://`.
The endpoint can be a relative path or an absolute URL, depending on the server configuration.

## Websocket Message
A websocket message is a unit of data that is sent between the client and the server.
It can be a text message or a binary message, depending on the application requirements.
The message can be sent in one or more frames, depending on the size of the message and the websocket implementation.

## Websocket Frame
A websocket frame is a unit of data that is sent between the client and the server.
It consists of a header and a payload, where the header contains information about the frame and the payload contains the actual data.
The frame can be a control frame or a data frame, depending on the type of data being sent.

## 1 Server In Multiple Instances
In a production environment, it is common to run multiple instances of a websocket server to handle a large number of clients.
When we work on kubernetes, we can have multiple instances of the same application running in different pods.
Each instance of the server can handle a subset of the clients, and the load can be distributed among the instances.

When we use the sockets synchronously, it is easy to send messages to the clients from the same instance of the server. Because session comes with the function.

But how will clients get messages asynchronously from different instances of the server?
The answer is that we need a message broker that can distribute messages among the instances of the server.
The message broker acts as a middleman between the clients and the server, and it ensures that messages are delivered to the correct instance of the server.

Scenarios:
- A client sends a message to the server, and the server send message to broker.
- The broker sends the message to all instances of the server.
- Each instance of the server receives the message and sends it to the connected clients.
- The clients receive the message and display it on the screen.

## Stomp
STOMP (Simple Text Oriented Messaging Protocol) is a messaging protocol that defines the format and rules for exchanging messages between clients and servers.
It is designed to be simple and easy to implement, making it ideal for use in web applications.
STOMP uses WebSocket as the underlying transport protocol, but it can also be used with other transport protocols such as TCP or UDP.

When we connect to a websocket server using STOMP;
- Stomp client only communicates with the connected server.
- We should message broker that supports STOMP protocol.
  - Or we should implement a simple broker inside the server.
  - After that, we should implement a listener in the server side to listen the messages from stateless broker (for example kafka).
  - After listening, we should send the message to the simple broker in all instances of the server.
  - The simple broker sends the message to the connected clients.