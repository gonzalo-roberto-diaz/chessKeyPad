# chessKeyPad
A generic solution to the "chess pieces dialing numbers in a telephone keypad" quiz

This is a program, written is Scala, that solves a quiz that goes like this:

_"given a phone keypad, find out how many possible numbers of length 7 can be dialed using the movement of a chess piece"_

![alt tag](https://raw.githubusercontent.com/gonzalo-roberto-diaz/chessKeyPad/master/Phone_keypad_layout,_grayscale.png)

For example, a possible 7-digit number for the *knight*  would be 381-6729

Additional rules are: 
* The numbers can't start with 0 or 1
* Keys * and # can't be used

Quiz enunciations sometimes don't specify it, but it is assumed that repetitions of digits are allowed, if the come form valid piece moves. So, 381-8181 is also a valid number.  

Also notice that the quiz asks only for the _amount_ of numbers dialed by each piece, not to output the numbers themselves.

Additional features

I want to propose a sulution with these additional features:

* It has to be able to output the actual numbers if necessary
* It can calculate the numbers with or without repetitions

My solution not only does that, but is also generic enough to be easily applicable to other board shapes, or to pieces with movement different from those of chess.

##Installation and requirements

This program is written in Scala version 2.11.8. Its only non-Scala dependencies are: is a simple JUnit library for scala called "scalatest" (used only for testing, not for the program itself), and SBT, which is a building tool for Scala.

If you are using IntelliJ or Eclipse, just import the chesKeyPad directory "as an SBT project"

##Running the program

The program has no executable object, just the test, ChessKeyPadSuite. The last few tests compare my calculations agains known, correct quiz solutions.

##Ideas and strategy used

A traditional solution to the original quiz uses a computing concept variously called "dynamic programming", "memorization" or "associativity". The basic concept is, to somehow reuse the knowledge of how to build shorter numbers when building longer numbers, for example:

  * a valid 6-digit number is 43-8167
  * possible knight moves that end in 4 start in numbers 3 and 9
  * therefore, when calculating phone numbers that start with 3 or 9, I should be able to reuse the information that 43-8167 is a valid suffix.
  * this concept can be extrapolated to "numbers of size 2 should reuse the information from numbers size 1", "numbers size 3 should reuse numbers size 2", and so on until the lenght desired.
  
My requirement of storing the actual number (not just the count) makes things a little more complicated. I don't known if this concept can be efficiently employed in a structure containing the actual digits.

I utilize a class called _Hydra_, able to store anything, which is basically a List with many, optional heads instead of just one as in a normal list. This provides some level of re-utilization of tails, akin to the reutilization of phone numbers mentioned above. 

I also find useful the concept of start calculating the phone numbers backwards, starting from the last number.

Another original idea, is to conceive the _Board_ as an assortment of geometric _Points_, and apply common vector operations to those _Points_ in order to represent _Piece_ movements. I also make a difference between the "moving ability" of a piece versus an actual _Move_, equivalent to an actual new _Point_

Finally, I use functions as values (passing them as parameters, etc) in order to keep my data structures as generic as possible.

