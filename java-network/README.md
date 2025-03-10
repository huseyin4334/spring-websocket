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

## DatagramSocket (UDP)
DatagramSocket is used to send and receive data over the UDP protocol.
It is connection-less protocol. It is faster than TCP, but it is not reliable.
It is used for broadcasting and multicasting.
`DatagramPacket` is used to send and receive data over the network.
`DatagramSocket` is used to send and receive `DatagramPacket`.

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

# Additional Resources
- `InetAddress`: It has IP address and host name only. It uses to get the IP address of the host.
  - https://learn.microsoft.com/en-us/dotnet/api/java.net.inetaddress?view=net-android-34.0
- `InetSocketAddress`: Creates a socket address from an IP address and a port number.
- `SocketAddress`: This represents an ip and port number. This uses to create a socket address.


# Java High Level Networking APIs
Java high-level networking APIs are used to communicate over the network with high-level abstractions.
These APIs are built on top of low-level networking APIs.

These types abstract networking concepts even further, starting with URLs and URIs, which represent resources on the internet.

- `URI`: It represents a **Uniform Resource Identifier**. It is used to identify a resource on the internet.
  - `URL` and `URN` are the subsets of URI.
  - URI defines the syntax for identifying resources. This can be a URL or URN.
- `URL`: It represents a **Uniform Resource Locator**. It is used to locate a resource on the internet.
  - It is a subtype of URI.
  - It has a protocol, host, port, path, query, and fragment.
  - `http://www.example.com:8080/index.html?name=java#section1`
  - `scheme://host:port/path?query#fragment`
- `URN`: It represents a **Uniform Resource Name**. It is used to name a resource on the internet.
  - It is a subtype of URI.
  - It is used to identify a resource by name in a particular namespace.
  - `urn:isbn:0451450523`
  - `scheme:namespace:identifier`

- `Relative URI`: It is a URI that doesn't contain the scheme and host. It is used to locate a resource relative to the current resource.
  - `../images/logo.png`
  - When we call `toURL()` method on a relative URI, it will give an `IllegalArgumentException`. Because URL don't have relative URI.
- `Absolute URI`: It is a URI that contains the scheme and host. It is used to locate a resource from the root.
  - `http://www.example.com/images/logo.png`

```java
import java.net.URI;

class Test {
  public static void main(String[] args) {
    URI uri = URI.create("http://www.example.com:8080");
    URI relativeUri = URI.create("/images/logo.png");
    
    URI absoluteUri = uri.resolve(relativeUri);
  }
}
```

## HttpURLConnection vs HttpClient
- `URLConnection`: It is used to connect to a resource on the internet. It is an abstract class. It uses for client-side networking.
  - `HttpURLConnection`: It is used to connect to an HTTP resource on the internet.
  - `JarURLConnection`: It is used to connect to a JAR resource on the internet.

- `HttpClient`: It is used to connect to a resource on the internet. It is a class. It uses for client-side networking.

But the main difference between `URLConnection` and `HttpClient` is that `HttpClient` is more flexible, non-blocking, and supports HTTP/2.

```java