# Minimal Networks
Initially I did this algorithm as part of a HackerRank challenge.
It is an implementation of the Prim's algorithm for finding the minimum weight
spanning tree on an un-directed graph that connects the
vertices via weighted edges:

![Wikipedia image](https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Msp1.jpg/318px-Msp1.jpg)

The input & output are read from a file, and format is as described in HackerRank:
First line, consists of two numbers - the number of nodes and the number of edges respectively.
The next M number of lines consists of three numbers - the edge represented as start and finish node and the weight:
The output is a single number - the sum of the total weight of the resulting graph.

## Program execution
After compilation, you have to provide a single argument - the name of the file with input data. There is a file included called data.txt
So you can execute the program providing data.txt as a first cli argument.