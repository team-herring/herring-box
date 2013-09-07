package example.E07asf;

import example.proto.PingPong;
import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.apache.avro.util.Utf8;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public class PingPongExecutor {
    static class PingPongImpl implements PingPong {
        public CharSequence send(CharSequence msg) throws AvroRemoteException {
            return new Utf8("Pong");
        }
    }

    private static Server server;
    private static final int CALC_SERVER_PORT = 65123;

    private static void startServer() throws IOException {
        server = new NettyServer(new SpecificResponder(PingPong.class, new PingPongImpl()),
                new InetSocketAddress(CALC_SERVER_PORT));
    }

    public static void main(String[] args) throws IOException {
        NettyTransceiver client = null;
        try {
            System.out.println("Starting server...");
            startServer();
            System.out.println("Server started");

            client = new NettyTransceiver(new InetSocketAddress(CALC_SERVER_PORT));
            PingPong proxy = (PingPong) SpecificRequestor.getClient(PingPong.class, client);

            Utf8 result = (Utf8) proxy.send(new Utf8("ping"));
            System.out.println(result);
        } finally {
            client.close();
            server.close();
        }

    }
}
