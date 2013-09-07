# -*- coding: utf-8 -*- 
from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import threading
import avro.ipc as ipc
import avro.protocol as protocol
import avro.schema as schema
import thread

PROTOCOL = protocol.parse(open("../../avro/pingpong.avpr").read())
server_addr = ('localhost', 9090)


class PingpongResponder(ipc.Responder):
    def __init__(self):
        ipc.Responder.__init__(self, PROTOCOL)

    def invoke(self, msg, req):
        if msg.name == 'send':
            return "pong"
        else:
            raise schema.AvroException("unexpected message:", msg.getname())


class PingpongHandler(BaseHTTPRequestHandler):
    def do_POST(self):
        self.responder = PingpongResponder()
        call_request_reader = ipc.FramedReader(self.rfile)
        call_request = call_request_reader.read_framed_message()

        resp_body = self.responder.respond(call_request)

        print resp_body
        self.send_response(200)
        self.send_header('Content-Type', 'avro/binary')
        self.end_headers()

        resp_writer = ipc.FramedWriter(self.wfile)
        resp_writer.write_framed_message(resp_body)




def start_server():
    server = HTTPServer(server_addr, PingpongHandler)
    server.allow_reuse_address = True
    server.serve_forever()

if __name__ == '__main__':
    thread.start_new_thread(start_server(), ())
