#!/usr/bin/env python
# coding: utf-8 

import sys
import simplejson
import psycopg2
from psycopg2.extras import RealDictCursor
import socket
import threading

class ClientThread(threading.Thread):

    def __init__(self, ip, port, clientsocket):

        threading.Thread.__init__(self)
        self.ip = ip
        self.port = port
        self.clientsocket = clientsocket
        print("[+] Nouveau thread pour %s %s\n" % (self.ip, self.port, ))

    def run(self): 
   
        print("Connection de %s %s" % (self.ip, self.port, ))
        try:
            connect = psycopg2.connect(dbname='postgres', user='postgres', password='thibault')
            sys.stdout.write('Connected... creating your account\n')
        except psycopg2.Error:
            sys.stdout.write('connection failed...\n')
            sys.exit()
        cur = connect.cursor(cursor_factory=RealDictCursor)
        #execution requete
        cur.execute("select * from table_sig")
        print("envoie de la bdd\n")
        bdd = simplejson.dumps(cur.fetchall(), indent=2)
        #print bdd
        self.clientsocket.send(bdd)

        print("Client déconnecté...\n")

tcpsock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcpsock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
tcpsock.bind(("",1111))

while True:
    tcpsock.listen(10)
    print( "En écoute...")
    (clientsocket, (ip, port)) = tcpsock.accept()
    newthread = ClientThread(ip, port, clientsocket)
    newthread.start()
