// Student.java
/*
Demonstrates the most basic features of a class.
A student is defined by their current number of units.
There are standard get/set accessors for units.
The student responds to getStress() to report
their current stress level which is a function
of their units.
NOTE A well documented class should include an introductory
comment like this. Don't get into all the details -- just
introduce the landscape.
*/
public class Student extends Object {
// NOTE this is an "instance variable" named "units"
// Every Student object will have its own units variable.
// "protected" and "private" mean that clients do not get access
protected int units;
/* NOTE
"public static final" declares a public readable constant that
is associated with the class -- it's full name is Student.MAX_UNITS.
It's a convention to put constants like that in upper case.
*/
public static final int MAX_UNITS = 20;
public static final int DEFAULT_UNITS = 15;
// Constructor for a new student
public Student(int initUnits) {
units = initUnits;
// NOTE this is example of "Receiver Relative" coding --
// "units" refers to the ivar of the receiver.
// OOP code is written relative to an implicitly present receiver.
}
// Constructor that that uses a default value of 15 units
// instead of taking an argument.
public Student() {
units = DEFAULT_UNITS;
}
// Standard accessors for units
public int getUnits() {
return(units);
}
public void setUnits(int units) {
if ((units < 0) || (units > MAX_UNITS)) {
return;
// Could use a number of strategies here: throw an
// exception, print to stderr, return false
}
this.units = units;
// NOTE: "this" trick to allow param and ivar to use same name
}
/*
Stress is units *10.
NOTE another example of "Receiver Relative" coding
*/
public int getStress() {
return(units*10);
}
/*
Tries to drop the given number of units.
Does not drop if would go below 9 units.
Returns true if the drop succeeds.
*/
public boolean dropClass(int drop) {
if (units-drop >= 9) {
setUnits(units - drop); // NOTE send self a message
return(true);
}
return(false);
}
/*
Here's a static test function with some simple
client-of-Student code.
NOTE Invoking "java Student" from the command line runs this.
It's handy to put test/demo/sample client code in the main() of a class.
*/
public static void main(String[] args) {
// Make two students
Student a = new Student(12); // new 12 unit student
Student b = new Student(); // new 15 unit student
// They respond to getUnits() and getStress()
System.out.println("a units:" + a.getUnits() +
" stress:" + a.getStress());
System.out.println("b units:" + b.getUnits() +
" stress:" + b.getStress());
a.dropClass(3); // a drops a class
System.out.println("a units:" + a.getUnits() +
" stress:" + a.getStress());
// Now "b" points to the same object as "a"
b = a;
b.setUnits(10);
// So the "a" units have been changed
System.out.println("a units:" + a.getUnits() +
" stress:" + a.getStress());
// NOTE: public vs. private
// A statement like "b.units = 10;" will not compile in a client
// of the Student class when units is declared protected or private
/*
OUTPUT...
a units:12 stress:120
b units:15 stress:150
a units:9 stress:90
a units:10 stress:100
*/
}
}
