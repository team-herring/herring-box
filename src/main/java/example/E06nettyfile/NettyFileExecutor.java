//package example.E06nettyfile;
//
//import org.apache.avro.ipc.NettyServer;
//import org.apache.avro.ipc.NettyTransceiver;
//import org.apache.avro.ipc.Server;
//import org.apache.avro.ipc.specific.SpecificRequestor;
//import org.apache.avro.ipc.specific.SpecificResponder;
//import org.apache.avro.util.Utf8;
//import org.herrig.proto.HerringBoxData;
//import org.herrig.proto.Message;
//
//import java.io.BufferedOutputStream;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.FileChannel;
//import java.util.HashMap;
//import java.util.Map;
//
///**
//* Description.
//*
//* @author Youngdeok Kim
//* @since 1.0
//*/
//public class NettyFileExecutor {
//    private static final Map<String, BufferedOutputStream> fileMap = new HashMap<String, BufferedOutputStream>();
//
//    static class HerringBoxDataImpl implements HerringBoxData {
//        @Override
//        public CharSequence send(Message message) throws IOException {
//            String name = message.getName().toString();
////            if ( !fileMap.containsKey(name) ) {
////                FileOutputStream fos = new FileOutputStream(name);
////                BufferedOutputStream bos = new BufferedOutputStream(fos);
////                fileMap.put(name, bos);
////            }
//
////            BufferedOutputStream bos = fileMap.get(name);
//
//            FileChannel channel = new FileOutputStream("out.xml", false).getChannel();
//
//            ByteBuffer buf = message.getData();
//            channel.write(buf);
//            channel.close();
//
//            return  new Utf8("Success");
//        }
//    }
//
//    private static Server server;
//    private static final int SERVER_PORT = 65123;
//
//    private static void startServer() throws IOException {
//        server = new NettyServer(new SpecificResponder(HerringBoxData.class, new HerringBoxDataImpl()),
//                new InetSocketAddress(SERVER_PORT));
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        NettyTransceiver client = null;
//        try {
//            System.out.println("Starting server...");
//            startServer();
//            System.out.println("Server started");
//
//            client = new NettyTransceiver(new InetSocketAddress(SERVER_PORT));
//            HerringBoxData proxy = (HerringBoxData) SpecificRequestor.getClient(HerringBoxData.class, client);
//
//
//            ByteBuffer buf = ByteBuffer.allocate(8024);
//            new FileInputStream("pom.xml").getChannel().read(buf);
//            buf.flip();
//            Message message = new Message("test", buf);
//
//            Utf8 result = (Utf8) proxy.send(message);
//
//            System.out.println(result);
//        } finally {
//            client.close();
//            server.close();
//        }
//
//    }
//}
