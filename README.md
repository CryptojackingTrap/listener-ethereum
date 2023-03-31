# cryptojacking-listener-Ethereum

This project is a part of cryptojacking solution. This solution include multiple listeners that each of the is
responsible to listening to a distinct cryptocurrency network and make log of the latest blocks of that network in
addition to block received tim and the block creation time. The format of logging is the same in all listeners. The
output files are used in the detector project in cryptojacking solution and detector expect the same data format to
parse the files. Each file line is recorded with the following format:

block hash hex value (block receive and log time in the executing host), (block creation time)


This project listens to Ethereum cryptocurrency peer to peer network and make a log file of
the latest blocks hash values.

for example:
0xdab5caa439e8b0eeeaf95c377ead187ac7ef6870d664cb3bdd750888a2de14dd (2022/05/17, 19:04:26) (2022/05/17, 19:03:06)