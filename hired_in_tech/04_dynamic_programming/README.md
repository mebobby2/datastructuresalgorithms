# 1-0 Knapsack Problem

* It's an optimization problem
* Brute force: Try all 2^n possible subsets

## Solution
1. Partition the problem into subproblems
2. Solve the subproblems
3. Combine the solutions to solve the original one

*Remark:* If the subproblems are not independent, i.e. subproblems share subsubproblems, then a divideand-conquer algorithm repeatedly solves the common subsubproblems.
Thus, it does more work than necessary!

*Question:* Any better solution? Yes–Dynamic programming (DP)!

Dynamic programming is a method for solving optimization problems.

Compute the solutions to the subsub-problems once and store the solutions in a table, so that they can be reused (repeatedly) later.
Remark: We trade space for time.
