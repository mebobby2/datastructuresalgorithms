# Task: Longest increasing subsequence

Given a list of N integers find the longest increasing subsequence in this list.

## Example
If the list is [16, 3, 5, 19, 10, 14, 12, 0, 15] one possible answer is the subsequence [3, 5, 10, 12, 15], another is [3, 5, 10, 14, 15].

If the list has only one integer, for example: [14], the correct answer is [14].

One more example: [10, 8, 6, 4, 2, 0], a possible correct answer is [8].

## Test cases
Your solution will be graded against a number of test cases. All test cases contain at least one integer. Half of them will have no more than 1,000 integers in the input sequence. The other half will contain sequences with up to 10,000 integers.

You can design a solution, which works fast enough for N <= 1,000 but is slow for bigger inputs. Try and see how good of a solution you can create. Of course, aim to get the maximum possible points!


## Current Solution
```
def longest_increasing_subsequence(sequence)
    return sequence if sequence.length == 1

    if sequence.length == 2
        return [sequence.last] if sequence.first >= sequence.last
        return sequence
    end

    cache = {}
    (2...sequence.length).each do |i|
        puts "index is #{i}"
        previous = cache[i-1] || longest_increasing_subsequence(sequence[0...i])
        puts "previous is #{previous.inspect}"
        if previous.last < sequence[i]
            puts '1'
            cache[i] = previous + [sequence[i]]
        else

            puts '2'
            cache[i] = previous[0...-1] + [sequence[i]]
        end
    end
    puts cache.inspect
    cache[sequence.length-1]
end
```
