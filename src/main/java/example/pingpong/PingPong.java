/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package example.pingpong;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public interface PingPong {
  public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"PingPong\",\"namespace\":\"example.pingpong\",\"types\":[],\"messages\":{\"send\":{\"request\":[{\"name\":\"msg\",\"type\":\"string\"}],\"response\":\"string\"}}}");
  java.lang.CharSequence send(java.lang.CharSequence msg) throws org.apache.avro.AvroRemoteException;

  @SuppressWarnings("all")
  public interface Callback extends PingPong {
    public static final org.apache.avro.Protocol PROTOCOL = example.pingpong.PingPong.PROTOCOL;
    void send(java.lang.CharSequence msg, org.apache.avro.ipc.Callback<java.lang.CharSequence> callback) throws java.io.IOException;
  }
}