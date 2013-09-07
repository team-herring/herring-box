# -*- coding: utf-8 -*-
from avro import schema
from avro.datafile import DataFileWriter, DataFileReader
from avro.io import DatumWriter, DatumReader

FILE_SCHEMA = schema.parse(open("../../avro/herring-box-data.avpc").read())
OUT_FILE = "data.dat"
IN_FILE = "01.FileProtocolExample.py"


def write(id, bufferSize=8024):
    fd = open(OUT_FILE, 'wb')
    dict = {'id': id, 'data': None}

    datum = DatumWriter()
    with DataFileWriter(fd, datum, FILE_SCHEMA) as writer:
        with open(IN_FILE, "r") as file:
            while True:
                byte = file.read(bufferSize)
                dict['data'] = byte

                if not byte:
                    break

                writer.append(dict)


def read():
    with open(OUT_FILE, 'rb') as file:
        with DataFileReader(file, DatumReader()) as reader:
            reader
            for file in reader:
                print file["id"], file['data']


if __name__ == '__main__':
    write("test")
    read()