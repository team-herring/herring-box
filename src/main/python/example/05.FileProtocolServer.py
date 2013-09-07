# -*- coding: utf-8 -*-
import SocketServer
from StringIO import StringIO
from avro import schema
from avro.datafile import DataFileReader
from avro.io import DatumReader

FILE_SCHEMA = schema.parse(open("../../avro/herring-box-data.avpc").read())
OUT_DIRECTORY = "backup/"

fileDict = dict()


class MyTCPHandler(SocketServer.BaseRequestHandler):
    def handle(self):
        data = self.request.recv(8024).strip()
        data = StringIO(data)

        reader = DataFileReader(data, DatumReader())
        for fileData in reader:
            id = fileData['id']
            data = fileData['data']

            print fileData

            if not fileDict.has_key(id):
                fileDict[id] = open("./" + id, "w")

            f = fileDict[id]

            f.write(data)
            f.flush()
        reader.close()


if __name__ == "__main__":
    HOST, PORT = "localhost", 9999

    server = SocketServer.TCPServer((HOST, PORT), MyTCPHandler)
    server.serve_forever()
    print "server started"

