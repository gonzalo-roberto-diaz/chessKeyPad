# chessKeyPad
A generic solution to the "chess pieces dialing numbers in a telephone keypad" quiz

This is a program, written is Scala, that solves a quiz that goes like this:

_"given a phone keypad, find out how many possible numbers of length 7 can be dialed using the movement of a chess piece"_

![alt tag](https://raw.githubusercontent.com/gonzalo-roberto-diaz/chessKeyPad/master/Phone_keypad_layout,_grayscale.png)

For example, a possible 7-digit number for the *knight*  would be 381-6729

Additional rules are: 
* The numbers can't start with 0 or 1
* Keys * and # can't be used

It is assumed that digit repetitions **are allowed**, provided that they come form valid piece movements. So, 381-8181 would also be a valid number. Sometimes this requirement is not clearly enuntiated.

Also notice that the quiz asks only to calculate the _amount_ of numbers that can't be dialed by each piece, it does not ask to output the actual numbers themselves.

##Additional features

Given those basic requirements, I want to propose a solution with these additional features:

* It has to be able to output the actual numbers if necessary
* It can calculate the numbers with or without repetitions

My solution not only does that, but is also generic enough to be easily applicable to other board shapes, or to pieces from games other than chess.

##Installation and requirements

This program is written in Scala version 2.11.8. Its only non-Scala dependencies are: is a simple JUnit library for scala called "scalatest" (used only for testing, not for the program itself), and SBT, which is a building tool for Scala.

If you are using IntelliJ or Eclipse, just import the chesKeyPad directory "as an SBT project"

##Running the program

The program has an executable object called `Solution` that outputs the quiz solutions for all chess pieces, with and without allowing repetitions. There is also a test suite class in `src/test/scala/ChessKeyPadSuite` with those and other simpler tests.

##Ideas and strategy used

A traditional solution to the original quiz uses a computing concept variously called "dynamic programming", "memorization" or "associativity". The basic concept is, to somehow reuse the knowledge of how to build shorter numbers when building longer numbers, for example:

  * a valid 6-digit number is 43-8167
  * possible knight moves ending in 4 could start in 3 or 9 (and also 0, but that is not allowed in our quiz).
  * therefore, when calculating phone numbers that start with 3 or 9, I should be able to reuse the information that 43-8167 is a valid suffix.
  * this concept can be extrapolated to "numbers of size 2 should reuse the information from numbers size 1", "numbers size 3 should reuse numbers size 2", and so on until the lenght desired.
  
My additional requirement of storing the actual numbers (not just the count of possible numbers) makes things a little more complicated. But the ideas of starting the calculation backwards (starting from the last number), and somehow reuse previously existing, shorter paths, still apply.

I utilize a class called _Hydra_, which is basically a List with many, optional heads instead of just one. This provides some level of re-utilization of tails, akin to the reutilization of shorter phone numbers mentioned above. 

Another original idea, is to conceive the _Board_ as an assortment of geometric _Points_, and apply common vector operations to those _Points_ in order to represent _Piece_ movements. I also make a difference between the "moving ability" of a piece versus an actual _Move_, equivalent to an actual new _Point_

Finally, I use functions as values (passing them as parameters, etc) in order to keep my data structures as generic as possible. For example: 
* a Hydra doesn't need to know what game function we are going to use in order to calculate possible moves
* a Point doesn't know how what board-specific String function we will use to represent its value. 

