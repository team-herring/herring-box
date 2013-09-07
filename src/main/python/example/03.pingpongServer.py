from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import avro.ipc as ipc
import avro.protocol as protocol

PROTOCOL = protocol.parse(open("../../avro/pingpong.avpr").read())


class FileResponder(ipc.Responder):
    def __init__(self):
        ipc.Responder.__init__(self, PROTOCOL)

    def invoke(self, msg, req):
        print msg
        return "Pong"

class FileHandler(BaseHTTPRequestHandler):
    def do_POST(self):
        self.responder = FileResponder()

        call_request_reader = ipc.FramedReader(self.rfile)
        call_request = call_request_reader.read_framed_message()
        resp_body = self.responder.respond(call_request)

        self.send_response(200)
        self.send_header('Content-Type', 'avro/binary')
        self.end_headers()

        resp_writer = ipc.FramedWriter(self.wfile)
        resp_writer.write_framed_message(resp_body)


server_addr = ('localhost', 9090)

if __name__ == '__main__':
    server = HTTPServer(server_addr, FileHandler)
    server.allow_reuse_address = True
    server.serve_forever()
