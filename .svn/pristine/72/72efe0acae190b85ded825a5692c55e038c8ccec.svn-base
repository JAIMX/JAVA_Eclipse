# BendersExample #

### What is this repository for? ###

The code here provides a small example of how to implement Benders decomposition using the Java API to CPLEX. The application is a fixed charge transportation problem.

The code actually contains two different Benders approaches, both using callbacks.

* The basic approach adds cuts only at nodes where integer feasible solutions are found.
* The "aggressive" approach adds cuts at all nodes (by rounding fractional solutions).
To run the aggressive version, you need to uncomment some code near the end of BendersExample.java.

### Version ###
The current version of the example code (in the "master" branch) is 2.5, which works with CPLEX 12.7 and *should* work with earlier versions.

If you encounter a problem with version 2.5 and an earlier version of CPLEX, try the branch labeled "pre-12.7".

### License ###
The code here is copyrighted by Paul A. Rubin (yours truly) and licensed under a [Creative Commons Attribution 3.0 Unported License](http://creativecommons.org/licenses/by/3.0/deed.en_US). 

### Setup ###

* If you don't already have it, install CPLEX Studio. (CPLEX is the only dependency.)
* Download the code from the repository (five Java classes) and park it somewhere.
* To run the code from a command line, open a terminal/DOS window, change to the directory where you parked the code, and execute the following:

<pre style="margin: 1em 10em;">
java -Djava.library.path=<path to CPLEX binary> -jar BendersExample.jar
</pre>

You can omit the library path argument if the CPLEX binary is on the system library path (given by the LD_LIBRARY_PATH in Linux).

### Problems? ###

I've set up an issue tracker in case anyone runs into problems.
