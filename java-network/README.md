# Java Networking
Java Networking is a concept of connecting two or more computing devices together so that we can share resources.
It has all resources on `java.net` package.

Client and server are two separate components that will be communicating with each other.
They use sockets for communication. 
Sockets use IP address and port number to identify the communication end points. 
For data transfer, TCP and UDP are mostly used protocols. This is level 4 of OSI model.

TCP connection is a connection-oriented protocol. It is slower than UDP but it is reliable.
UDP is a connection-less protocol. It is faster than TCP, but it is not reliable.

TCP/IP is a protocol stack that is used to communicate over the internet. It is a combination of TCP and IP protocols.

# Networking Packages
- `java.net` package provides classes to communicate over the network.
  - `Low Level Networking Apis`: Socket, ServerSocket, DatagramSocket, MulticastSocket
  - `High Level Networking (Older) Apis`: URL, URI, URLConnection, HttpURLConnection, URLEncoder, URLDecoder, InetAddress
- `java.nio.channels` package provides classes to communicate over the network.
  - `Non-blocking IO (Include Networking IO Channels)`: Channels, Buffers, Selectors, ServerSocketChannel, SocketChannel, DatagramChannel
- `java.net.http` package provides classes to communicate over the network.
  - `HTTP Client`: HttpClient, HttpRequest, HttpResponse, WebSocket, WebSocket.Builder

# Socket Programming
Socket is an endpoint for communication between two machines.
Socket is a combination of IP address and port number.
Client and server will have own sockets for communication.
Multiple clients can connect to a server using sockets. All client will have separate sockets.

![Sockets](/resources/sockets.png)

## ServerSocket
ServerSocket is used to create a server. It listens to the client request.
This is a blocking call. It will wait until a client connects to it. 
It has `accept()` method to accept the client request. 
If a server can't call the `accept()` method, the clients will wait for the server to accept the request.
When a server call the `accept()` method, it will return a socket object to communicate with the client.
Also, it will wait for a client to connect to it. (Blocking call)
`close` method is used to close the server socket.

Server sockets only support TCP connections.

# NIO (New IO) Package
NIO package provides a new way to communicate over the network.
It provides a non-blocking way to communicate over the network.
It uses channels and buffers rather than streams.

## Channels
Channels an alternative to input and output streams.
- Read Write
  - Normally inputstream uses for receiving data and outputstream uses for sending data. 
  - Channels are used to read and write data.
- Blocking
  - Streams blocks the thread until the data is read or written. 
  - Channels are non-blocking. It doesn't block the thread.
- `FileChannel`: It is used to read and write data to a file.
- `SocketChannel`: It is used to read and write data to a TCP/IP connection. (Client)
- `ServerSocketChannel`: It is used to listen to the client TCP/IP connection. (Server)
- `DatagramChannel`: It is used to read and write data to a UDP connection.
- `Pipe`: It is used to read and write data between two threads. 

Channels use buffers to read and write data. Buffer is a block of memory that stores data.

## Buffer Characteristics
- Designed to provide a more general wat to handle data, whether it's bytes, characters, or any other primitive data type.
- It provides methods for accessing, manipulating, and tracking buffer state.
- Buffer states are `Ready to Read`, `Ready to Write`, `Empty`, `Full`.
  - `Ready to Read`: Buffer has data to read.
  - `Ready to Write`: Buffer has space to write.
  - `Empty`: Buffer has no data.
  - `Full`: Buffer has no space to write.

Capacity, position, limit, and mark are the important properties of the buffer.
- `Capacity`: It is the maximum number of elements that a buffer can hold. (Mutability is not allowed)
- `Position`: It is the index of the next element to read or write. (Position <= Limit)
- `Limit`: The maximum number of elements that can be read or written. (Limit <= Capacity)

### ByteBuffer vs CharBuffer
- `ByteBuffer`: It is used to read and write bytes.
- `CharBuffer`: It is used to read and write characters.

The best choice of buffer depends on the type of data you are working with.
For socket channels, `ByteBuffer` is the best choice. Because socket channels operate at the byte level.

### Selectable Channels
Selectable channels are used to create non-blocking IO. It allows a single thread to manage multiple channels.
These channels are used with selectors. Selectors are used to select the channels that are ready for IO operations.

Selectable Channels useful for servers that need to handle multiple connections simultaneously.
It works event-driven architecture. We have to polling in default channel to check the status of the channel.


## DatagramSocket (UDP)
DatagramSocket is used to send and receive data over the UDP protocol.
It is connection-less protocol. It is faster than TCP, but it is not reliable.
It is used for broadcasting and multicasting.
`DatagramPacket` is used to send and receive data over the network.
`DatagramSocket` is used to send and receive `DatagramPacket`.

# Additional Resources
- `InetAddress`: This class represents an IP address. It provides methods to get the IP address of any host.
- `InetSocketAddress`: This class represents a socket address. It has IP address and port number.