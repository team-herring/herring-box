# -*- coding: utf-8 -*-
import SocketServer
from StringIO import StringIO
import socket
import threading
from avro import schema, ipc, protocol
from avro.datafile import DataFileWriter, DataFileReader
from avro.io import DatumWriter, DatumReader

FILE_SCHEMA = schema.parse(open("../../avro/herring-box-data.avpc").read())
IN_DIRECTORY = "backup/"

fileDict = dict()

HOST, PORT = "localhost", 9999
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    sock.connect((HOST, PORT))
    file = open("/home/yd/Downloads/jvm-폰트설정", "r")

    dict = {'id': "test", 'data': None}
    datum = DatumWriter()
    data = StringIO()

    writer = DataFileWriter(data, datum, FILE_SCHEMA)
    while True:
        byte = file.read(8024)
        dict['data'] = byte

        if not byte:
            break
        print dict
        writer.append(dict)

    writer.flush()
    sock.sendall(data.getvalue())

finally:
    sock.close()

