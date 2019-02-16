# Time Complexity

Let's look at some examples in order to get a better understanding of time complexity of an algorithm. Later we will also look at memory complexity as this is another limited resource that we have to deal with.

First of all, time complexity will be measured in terms of the input size. As you saw in the example above, N was the number of latin letters to use for building palindromes. We said that all permutations of these N letters are N!. So to generate them we would perform a number of steps that is proportional to N!. Of course, it is also important how many steps it takes to generate each separate permutation. It is possible to do that with a constant amount of steps. We will not go into details how this can be done but trust us.

This means that the number of steps to generate each next permutation does not depend on the size of the input. For each generated permutation we would need to check if it is a palindrome. One way to do it is to compare the first and last letters, then the second and the last but one and so on. This will require a number of steps that is proportional to the number of letters - N. So, for each permutation we will perform that many steps.

With all that in mind we will have to perform a number of steps that is proportional to N * N! in order to execute our brute force solution. This number will be multiplied by some constant but usually when this constant is not too high we don't take it into account. Now that we have quickly analysed the number of steps required, we can clearly see that the number of steps will grow very quickly with increasing values of N. If your interviewer tells you that N can be as high as 100, then there is no use in even considering such a solution. And being able to describe to the interviewer why such a solution is not feasible is also a useful skill.

Let's look at another example. Imagine a block of code, which sorts an array of integers:

```
// An array `arr` with `len` integers in it is sorted.
for (int i = 0; i < len - 1; i++) {
  for (int j = i + 1; j < len; j++) {
    if (arr[i] > arr[j]) {
      int tmp = arr[i];
      arr[i] = arr[j];
      arr[j] = tmp;
    }
  }
}
```

This algorithm has two nested loops. The outer one goes through the numbers from left to right and finds the number that must be in each position. For the number at position 0 it finds the minimum of all numbers. Then, for the number at position 1 it finds the minimum of all remaining numbers and so on. The question we will answer here is: what is the time complexity of this algorithm?

The outer loop will perform N-1 iterations where N is the number of the numbers to sort. This is our parameter indicating the size of the input. For each iteration of the outer loop the inner loop will perform a different number of steps. In the first interation it will perform N-1 steps, next it will perform N-2 steps and so on.

Inside the loops there is a comparison and in some cases there will be three operations used for swapping two values. These operations inside the loops take constant time regardless of N. That is why we will be more interested in computing the total number of interations that the two loops will perform. To compute that we just need to sum up the number of iterations of the inner loop: (N-1) + (N-2) + ... + 2 + 1 = N * (N-1) / 2. This is a number proportional to N^2 because if we expand it we will get N^2 / 2 - N / 2. We are always interested in the term with the highest degree and here this is N^2. It is multiplied by a constant - 1/2 - but this does not change the fact that the total number of steps will be proportional to N^2 and as N grows linearly our algorithm's speed will slow down quadratically.

Another important thing to mention is that you are usually interested in finding out the slowest part of your algorithm. Maybe for some task you have a solution that does some preprocessing first taking roughly N*M steps, with N and M being some values identifying the size of your input. But then if your core algorithm performs N*M^2 steps to run, then you can say that this is your actual time complexity becuase it's of higher order than N*M.

There are several formal definitions for how we define computational complexity and they can be used depending on the case. For most tech interviews and real-time examples you will need to use the so called big-O notation. Below we have included links to a few useful resources that will tell you more about big-O and other notations using more or less formal language.

# Memory Complexity
To measure memory complexity of a solution you can use a lot of the things that we already described in this section. The difference is that here you need to measure the maximum amount of memory that is used by your solution at one point in time. Why is that important? Mainly because every time you run a piece of software on a given machine you will only have a limited amount of memory. Go over this amount and you are in trouble. The first most likely effect will be that the OS will start to swap memory to hard disk, which will make the execution much slower and practically useless.

Hopefully, you agree that memory usage is as important as the running speed of an algorithm. You can measure it in similar ways to how you measure the time complexity. For example, let's look again at the example with the permutations of latin letters. First, to hold all the letters you will need memory proportional to N (the number of letters). Then, to generate all permutations you could use the same array and just generate subsequent permutations in it. Checking if a sequence of letters is a palindrome does not require additional memory. We could say that this brute force solution requires memory proportional to N. In terms of memory usage this solution is not bad then.

Let's look at another example. Consider a task in which you need to store information about a network of some kind. The network has nodes and edges that connect them. We will look at more tasks like this one in the section covering graphs but in this lesson let's just focus on how we could represent the network in memory.

One way would be to store a square matrix M with size N x N where N is the number of nodes in the network. In each cell M[i][j] we will have 0 or 1 indicating if there is an edge between nodes i and j. This representation of the network requires that we use memory proportional to N^2.

An alternative approach would be to store for each node a list of the nodes that it is connected to. In this case we can allocate memory that is proportional to the number of edges in the network. If nothing else is specified the number of edges could vary from 0 to N * (N-1). This means that in the worst case the memory used will also be proportional to N^2, like for the previous representation. But if for example we know that the network is quite sparse and doesn't have many edges, the memory used will be less that what we would consume with the first approach.

When designing a solution for a problem at a tech interview you will need to be able to compute the memory complexity, so that you can explain to the interviewer why a solution is good or bad given the input constraints.

# Time vs Memory
practice shows that usually time complexity is somewhat more discussed. However, you must remember that very often there is a tradeoff between time and memory. A solution can be tweaked to use more memory and become faster and vice versa. Because of that, both complexities are important. You may even be in a situation where your solution can be pushed in any of the two directions. In such cases, it's wise to ask the interviewer which resource is more valuable. The answer will hopefully allow you to decide how to shape your solution.
