# WebSockets
WebSockets are used to create a two-way communication channel between a client and a server.

It works over a TCP connection. But everything starts with an HTTP request.
When a client wants to establish a WebSocket connection, it sends an HTTP GET request to the server.
This request contains a `Upgrade` header with the value `websocket`. This tells the server that the client wants to establish a WebSocket connection.
Server responds with a `101 Switching Protocols` status code. This tells the client that the server is switching to the WebSocket protocol.
This is the handshake process.

[Java Websocket](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_a_WebSocket_server_in_Java)

## WebSocket.Listener Interface
This interface is used to create a WebSocket client listener. It has 5 methods:
- `onOpen(WebSocket webSocket)`: This method is called when the WebSocket connection is established.
- `onClose(WebSocket webSocket, int statusCode, String reason)`: This method is called when the WebSocket connection is closed.
- `onError(WebSocket webSocket, Throwable error)`: This method is called when an error occurs.
- `onText(WebSocket webSocket, CharSequence data, boolean last)`: This method is called when a text message is received.
- `onBinary(WebSocket webSocket, ByteBuffer data, boolean last)`: This method is called when a binary message is received.
- `onPing(WebSocket webSocket, ByteBuffer message)`: This method is called when a ping message is received. Ping message is used to check if the connection is still alive.
- `onPong(WebSocket webSocket, ByteBuffer message)`: This method is called when a pong message is received. Pong message is used to respond to a ping message.