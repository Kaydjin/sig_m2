#!/usr/bin/env python
# coding: utf-8

import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(("localhost", 1111))

print("Le nom du fichier que vous voulez récupérer:")
r = s.recv(9999999)
print r
print("Le fichier a été correctement copié")