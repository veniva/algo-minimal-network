# Minimal network
This is the problem 107 from the [Project Euler](https://projecteuler.net/problem=107).
Initially I did this algorithm as part of a HackerRank challenge.
It is an implementation of the [Prim's algorithm](https://en.wikipedia.org/wiki/Prim%27s_algorithm) for finding the minimum weight
spanning tree on an un-directed graph that connects the
vertices via weighted edges:

![Network graph](http://vivanov.me/images/euler107/network-graph.png)
![Minimal network](http://vivanov.me/images/euler107/minimal-network.png)  
Because the implementation is not very well explained in Wikipedia (difficult to follow), for the second version of the algorithm I followed as an example the C++ implementation of this algorithm 
at [Stephan Brumme's page](https://euler.stephan-brumme.com/107/)
and did it in Java aiming to improve my Java skills

## Input & Output
The input & output are read from a file, and format is as described in HackerRank:
First line, consists of two numbers - the number of nodes and the number of edges respectively.
The next N number of lines consists of three numbers - the edge represented as start and finish node and the weight:  
![Input data](http://vivanov.me/images/euler107/input.png)  
The output is a single number - the sum of the total weight of the resulting graph:  
  
15  

## Program execution
There is a compiled file called `minimal-network.jar` which you can run using the following command:
`java -jar minimal-network.jar data.txt`