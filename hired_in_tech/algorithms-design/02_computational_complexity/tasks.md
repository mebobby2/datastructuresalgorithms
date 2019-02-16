# 1
Let's say that you have to implement matrix multiplication. The matrices are M1 and M2, where M1 has m rows and n columns. M2 has n rows and p columns. The algorithm you will use is the one, which takes each row in M1 and multiplies it by each column in M2 (using the dot product operations on vectors). What is the time complexity of this algorithm in big-O notation?

## Explanation:
When multiplying the matrices we will have to traverse each row of M1 - this is m iterations. For each such iteration we will traverse each columns in M2 - this is p iterations. So far we have mp iterations. For each such iteration we will have to compute the dot product of two vectors with lengths n. This requires n operations, which means that in total we will perform mp*n operations. Hence, the correct answer is O(m * p * n).

# 2
You are given an array of integers A and one additional integer N. Design a solution, which will tell us if there are two numbers in A the sum of which is equal to N. The array A will contain no more than 1,000,000 integers, which will be in the range [0, 10^12]. Which of the solutions suggested below has the optimal trade-off between time and memory usage?

* For each number in A look through all other numbers and look for a match that sums up to N
* Sort the numbers in A using merge sort. Then, for each number in A use binary search to check if there is a matching number that sums up to N
* Use an array P in which you store how many times a given number is present in A. For example if there is one number 10 in A, P[10] = 1. If there are two numbers with value 99, P[99] = 2. Then, for each number in A compute what other number is needed to sum up to N and check in P if such a number exists.

## Explanation:
Let's say that A has M numbers in it. The solution, which tries every number in A with every other number has a time complexity of O(M^2). The memory complexity is O(M) to store the input array A. The solution that uses binary search is faster because it first sorts the numbers with time complexity O(M * logM) and then for each number in A runs a binary search over M numbers. This again has time complexity O(M * logM) and this is the overall time complexity of the algorithm. Memory complexity is O(M), like for the previous algorithm. The third approach iterates over the numbers in A and finds a matching number if such exists in constant time - just a look up in the array P. This means that the time complexity is O(M), better than the previous two solutions. However, the memory needed for P is proportional to the range in which the numbers in M fall. According to the problem statement, they could be in [0, 10^12]. This makes the third approach impractical although it is the fastest. That is why the solution using binary search is the fastest one that has a reasonable memory complexity. For 1,000,000 input numbers the complexity of O(M * logM) should run in reasonable time. It's worth mentioning that implementing this solution requires a bit more effort and testing than the solution with time complexity O(M^2), but this is probably worth it considering the size of the input. Note that it is also possible to solve this task using a hash table and this way saving on memory. However, for the purposes of this quiz we have suggested only these solutions in the possible answers.

# 3
Imagine that you have to store a graph in memory and have chosen to use the matrix representation for this. You are using a data structure which allows you to use 1 bit to describe if there is an edge between two nodes or not. The graph has 128 nodes. How many bytes will this data structure use to store the matrix?

## Explanation:
The matrix will be square with 128 rows and columns. The cell at postion (i, j) will tell us if there is an edge from node i to node j. This means that there will be 128 * 128 = 16384 cells in the matrix. Each cell takes up 1 bit. To compute the bytes used we need to divide 16384 by 8 and we get 2048, which is the correct answer.

# 4
A team of software engineers have built software for maintaining a wiki with textual pages much like wikipedia. In their database they store all wiki pages as lists of words. Now they want to add a search capability where users would be able to type a word and get a list of documents that contain this word. The search should be really quick and return results in under 1 second.

The algorithm that the engineers want to try simply iterates over all documents in the database and compares the search term to the words in each document (remember they have the documents represented as lists of words already). In which of the situations described below this approach will work reasonably well? Mark all that apply.

* The wiki can have no more than 1,000 pages each with no more than 1,000 words in it.
* The wiki can have up to 1,000 pages in it but many of them are really long and contain up to 10,000,000 words.
* The wiki is meant to be hosting many many articles, possibly as many as 100,000,000. The shortest ones will probably have at least 10,000 characters in them.
* The wiki is used to host very short articles containing no more than 1,000 characters. The number of pages can be up to 10,000.

## Explanation:
In the answers to this question you need to be careful about words and characters. For example, in the answer with 1,000 pages and up to 10,000,000 words in them, we know that the algorithm will have to process 1,000 x 10,000,000 = 10^10 words in the worst case. But for each word it will have to perform a letter by letter comparison until it finds a mismatch or a complete match. The exact number of characters to process is not so important because going through 10^10 words is already too much. The software engineers will need a better algorithm to manage this kind of load.

The answer with 100,000,000 articles and at least 10,000 characters in them doesn't tell us about the number of words in the articles but we could make an assumption. For example in the very worst case scenario we would have to compare 100,000,000 x 10,000 characters, which is 10^12. For sure in real life we won't have to go through all characters because when comparing words they have a mismatch in the first characters. But if we assume that the average length of a word in this wiki is 10 characters, we would still get a very high number. This is why in this situation we will also need a better algorithm.

The other two situations seem feasible to be solved with the simple algorithm proposed.
