package example.E04pingpong;

import example.proto.PingPong;
import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.HttpServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.apache.avro.util.Utf8;

import java.io.IOException;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public class PingPongExecutor {
    static class PingPongImpl implements PingPong {
        public CharSequence send(CharSequence msg) throws AvroRemoteException {
            System.out.println(msg);
            return new Utf8("Pong");
        }
    }

    private static Server server;
    private static final int SERVER_PORT = 9090;

    private static void startServer() throws IOException, InterruptedException {
        //Netty는 이기종과 통신이 안된다.
//        server = new NettyServer(new SpecificResponder(PingPong.class, new PingPongImpl()),
//                new InetSocketAddress(SERVER_PORT));

        server = new HttpServer(new SpecificResponder(PingPong.class, new PingPongImpl()), SERVER_PORT);
        server.start();
    }

    public static void main(String[] args) throws IOException {
        try {
            System.out.println("Starting server...");
            startServer();
            System.out.println("Server started");

            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            server.close();
        }

    }
}
