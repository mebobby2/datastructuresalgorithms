# BigO Summary
## Datastructures
### Legend
* A-SID = optimized for access, not search, insertion, deletion
* ID-AS = optimized for insertion/delete, not access & search
* AID = optimized for access, insertion, deletion. Search not available
* ASID = optimized for access, search, insertion, deletion
### O(n)
#### A-SID
* array
  * homogeneous size (same element types) and stored in contiguous memory
  * arrays have fixed length, so access is O(1)
    * index of an element is head of array plus offset
  * search is slow because you need to traverse every element
  * insertion & deletion is slow because if you insert/delete the first element, all subsequent elements need to be moved to make room

#### ID-AS
* stack
  * insertion/deletion is fast because its always done to the most recently added element
  * access/search is slow because you need to traverse all elements to get to a specific element (unless its the most recently added)
* queue
  * insertion/deletion is fast because its always done to the oldest added element
  * access/search is slow because you need to traverse all elements to get to a specific element (unless its the oldest added)
* singly-linked list
  * can traverse in one direction, not possible to reach the previous node
* doubly-linked list
  * can traverse in both directions

#### AID
* hash table

### O(logn)
* Binary search trees are ASID. All four operations take o(logn)

## Sorting
* both quicksort and mergesort have O(nlogn) best and average time complexity
* mergesort have O(nlogn) worst time complexity while quicksort has O(n^2)
* however, quicksort has space complexity of O(logn) while mergesort has(n)
* so quicksort is optimized for space at the cost of worst case complexity
