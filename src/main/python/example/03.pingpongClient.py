import avro.ipc as ipc
import avro.protocol as protocol

PROTOCOL = protocol.parse(open("../../avro/pingpong.avpr").read())

server_addr = ('localhost', 9090)


if __name__ == '__main__':
    client = ipc.HTTPTransceiver(server_addr[0], server_addr[1])
    requestor = ipc.Requestor(PROTOCOL, client)

    print("Result: " + requestor.request('send', {"msg": "ping"}))

    client.close()
