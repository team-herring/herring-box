# -*- coding: utf-8 -*-
import string
import sys
from avro import datafile, io, schema
from avro.datafile import DataFileWriter
from avro.io import DatumWriter

__author__ = 'yd'

import avro.ipc as ipc
import avro.protocol as protocol

PROTOCOL = protocol.parse(open("../../../avro/herring-box.avpr").read())
server_addr = ('localhost', 9090)


def sendData(command, data):
    client = ipc.HTTPTransceiver(server_addr[0], server_addr[1])
    requestor = ipc.Requestor(PROTOCOL, client)

    message = dict()
    message['command'] = command
    message['data'] = data

    params = dict()
    params['message'] = message
    print("Result: " + requestor.request('send', params))

    client.close()


if __name__ == '__main__':
    avro_file = ""
    writer = open(avro_file, 'wb')
    datum_writer = io.DatumWriter()
    schema_object = schema.parse("""{ "type": "record",
      "name": "Pair",
      "doc": "A pair of strings.",
      "fields": [
        {"name": "file", "type": "{"type": "array", "items": "bytes"}"}
      ]
    }""")

    dfw = datafile.DataFileWriter(writer, datum_writer, schema_object)
    for line in sys.stdin.readlines():
        (left, right) = string.split(line.strip(), ',')
        dfw.append({'left': left, 'right': right})
    dfw.close()


class DataSend:
    SCHEMA = schema.parse(open("../avro/herring-box-data.avpc").read())

    def testWrite(self, id, filename, bufferSize=8024):
        fd = open(filename, 'wb')
        dict = {'id': id, 'data': None}

        datum = DatumWriter()
        with DataFileWriter(fd, datum, self.SCHEMA) as writer:
            with open("filename", "rw") as file:
                while True:
                    byte = file.read(bufferSize)
                    dict['data'] = byte
                    writer.append(dict)

                    if not bytes:
                        break