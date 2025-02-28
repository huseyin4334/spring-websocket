package org.example.javanetwork.server;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class BufferExample {
    public static void main(String[] args) {
        System.out.println("With Flip");
        withFlip();
        System.out.println("Without Flip");
        withoutFlip();
        System.out.println("Change Limit");
        changeLimit();
    }

    private static void withoutFlip() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        doOperation("Print: ", buffer, (b) -> System.out.print(b + " "));
        doOperation("Write: ", buffer, b -> b.put("This is a test string".getBytes()));
        doOperation("Flip: ", buffer, ByteBuffer::flip);
        doOperation("Write: ", buffer, b -> b.put("Character".getBytes()));
        doOperation("Print: ", buffer, (b) -> System.out.print(b + " "));
        //doOperation("Flip: ", buffer, ByteBuffer::flip);
        doOperation("Read And Print: ", buffer, b -> {
            byte[] responseObject = new byte[b.remaining()];
            b.get(responseObject);
            System.out.printf("\"%s\" ", new String(responseObject, StandardCharsets.UTF_8));
        });
        doOperation("Flip: ", buffer, ByteBuffer::flip);
        doOperation("Read And Print: ", buffer, b -> {
            byte[] responseObject = new byte[b.remaining()];
            b.get(responseObject);
            System.out.printf("\"%s\" ", new String(responseObject, StandardCharsets.UTF_8));
        });
        doOperation("Clear: ", buffer, ByteBuffer::clear);
    }

    private static void withFlip() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        doOperation("Print: ", buffer, (b) -> System.out.print(b + " "));
        doOperation("Write: ", buffer, b -> b.put("This is a test string".getBytes()));
        doOperation("Flip: ", buffer, ByteBuffer::flip);
        doOperation("Write: ", buffer, b -> b.put("This".getBytes()));
        doOperation("Print: ", buffer, (b) -> System.out.print(b + " "));
        doOperation("Flip: ", buffer, ByteBuffer::flip);
        doOperation("Read And Print: ", buffer, b -> {
            byte[] responseObject = new byte[b.limit()];
            b.get(responseObject);
            System.out.printf("\"%s\" ", new String(responseObject, StandardCharsets.UTF_8));
        });
        doOperation("Clear: ", buffer, ByteBuffer::clear);
    }

    private static void changeLimit() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        doOperation("Print: ", buffer, (b) -> System.out.print(b + " "));
        doOperation("Write: ", buffer, b -> b.put("This is a test string".getBytes()));
        doOperation("Flip: ", buffer, ByteBuffer::flip);
        doOperation("Change Position And Limit: ", buffer, b -> {
            b.position(b.limit());
            b.limit(b.capacity());
        });
        doOperation("Write: ", buffer, b -> b.put("yyyyyyy".getBytes()));
        doOperation("Flip: ", buffer, ByteBuffer::flip);
        doOperation("Read And Print: ", buffer, b -> {
            byte[] responseObject = new byte[b.remaining()];
            b.get(responseObject);
            System.out.printf("\"%s\" ", new String(responseObject, StandardCharsets.UTF_8));
        });
        doOperation("Clear: ", buffer, ByteBuffer::clear);
    }

    private static void doOperation(String operation, ByteBuffer buffer, Consumer<ByteBuffer> consumer) {
        System.out.printf("%-30s", operation);

        consumer.accept(buffer);

        System.out.printf(
                "Position: %d, Limit: %d, Capacity: %d, Remaining: %d\n",
                buffer.position(), buffer.limit(), buffer.capacity(), buffer.remaining()
        );

    }
}
