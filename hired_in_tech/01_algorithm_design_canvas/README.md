# The Algorithm Design Canvas
The Canvas contains 5 major areas: Constraints, Ideas, Complexities, Code, and Tests. Taken together, they capture everything you need to worry about when it comes to algorithm design problems.

## Area 1: Constraints
The Constraints area is where you fill in all constraints of the problem. This includes things like how large the input array can be, can the input string contain unicode characters, is the robot allowed to make diagonal moves in the maze, can the graph have negative edges, etc.

## Area 2: Ideas
After you've identified all constraints, you go into idea generation. Typically during an interview you discuss 1 to 3 ideas. Often times you start with one, explain it to the interviewer, and then move on to a better idea.

## Area 3: Complexities
Each idea has two corresponding Complexity areas: Time and Memory. For every algorithm you describe, you will need to be able to estimate its time and memory complexity. Time vs Memory trade-off is key to any algorithm design problem.

## Area 4: Code
After you've identified the problem's constraints, discussed a few ideas, analyzed their complexities, and found one that both you and your interviewer think is worth being implemented, you finally go into writing code.

## Area 5: Tests
Finally, you move on to writing test cases and testing your code. Many people completely ignore this step. This is not smart at all.

# Common Constraints
## Strings, Arrays and Numbers
* How many elements can be in the array?
* How large can each element be? If it’s a string, how long? If it’s a number, what is the minimum and maximum value?
* What is in each element? If it’s a number, is it an integer or a floating point? If it’s a string, is it single-byte or multibyte (unicode)?
* If the problem involves finding a subsequence, does “subsequence” mean that the elements must be adjacent, or is there no such requirement?
* Does the array contain unique numbers or can they be repeated (this is sometimes relevant)?

## Grids/Mazes
* For problems where some actor (e.g. a robot) is moving in a grid or maze, what
moves are allowed? Can the robot move diagonally (hence 8 valid moves), or only
horizontally/vertically (hence only 4 valid moves)?
* Are all cells in the grid allowed, or can there be obstacles?
* If the actor is trying to get from cell A to cell B, are cells A and B guaranteed to be
different from each other?
* If the actor is trying to get from cell A to cell B, is it guaranteed that there’s a path
between the two cells?

## Graphs
* How many nodes can the graph have?
* How many edges can the graph have?
* If the edges have weights, what is the range for the weights?
* Can there be loops in the graph? Can there be negative-sum loops in the graph?
* Is the graph directed or undirected?
* Does the graph have multiple edges and/or self-loops?

## Return Values
* What should my method return? For example, if I’m trying to find the longest
subsequence of increasing numbers in an array, should I return the length, the
start index, or both?
* If there are multiple solutions to the problem, which one should be returned?
* If it should return multiple values, do you have any preference on what to return?
E.g. should it return an object, a tuple, an array, or pass the output parameters as
input references? (This may not be applicable in languages allowing you to return
multiple values, e.g. Python)
( What should I do/return if the input is invalid / does not match the constraints?
Options may be to do nothing (always assume the input is correct), raise an
exception, or return some specific value.
* In problems where you’re supposed to find something (e.g. a number in an array),
what should be returned if the element is not present?

# Testing your code
## Extensive testing
* Edge cases: Remember that "Constraints" section that we filled in? It's going to come in very handy. Design cases that make sure the code works when the min and/or max values of the constraints are hit. This includes negative numbers, empty arrays, empty strings, etc.
* Cases where there's no solution: To make sure the code does the right thing (hopefully you know what it is)
* Non-trivial functional tests: these depend very much on the problem. They would test the internal logic of the solution to make sure the algorithm works correctly.
* Randomized tests: this makes sure your code works well in the "average" case, as opposed to only working well on human-generated tests (where there's inherent bias).
* Load testing: Test your code with as much data as allowed by the constraints. These test your code against being very slow or taking up too much memory.

## Sample tests
Sample tests are small tests that you run your code on at the interview to make sure it is solid. Now that we've covered the major kinds of tests you may design, which ones should you use as sample tests?

Typically, we stay away from randomized tests and load tests during interviews, for obvious reasons. Instead, we like choosing a small-scale version of a non-trivial functional test, to make sure the code does the right thing. Then, we look at how the code would react to several edge cases, and finally think about whether the code would work well if no solution can be found.

This combination (non-trivial functional + edge + no solution) tends to be the most effective. For the amount of time it takes to design the tests and to run them on your code on a sheet of paper, it gives you the highest certainty in your code.
