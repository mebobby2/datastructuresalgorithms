def quick_sort(a, min = 0, max = a.length)
  puts "start #{min} and #{max}"
  puts a.inspect
  return a if max == 1
  pivot = max / 2
  swap(a, pivot, 0)
  swap_index = nil
  (1...max).each do |i|
    if a[i] < a[0]
      if swap_index
        swap(a, i, swap_index)
        swap_index += 1
      end
    else
      swap_index = i if swap_index == nil
    end
    puts a.inspect
  end
  if swap_index
    swap(a, 0, swap_index - 1)
  else
    swap(a, 0, max - 1)
  end
  puts a.inspect

  if swap_index
    quick_sort(a, min, swap_index - 1)
  else
    quick_sort(a, min, pivot - 1)
  end
  #quick_sort(a, pivot + 1, max)
  a
end

def swap(a, i, j)
  tmp = a[i]
  a[i] = a[j]
  a[j] = tmp
end

#puts quick_sort([5,4,3,2,1]).inspect
puts quick_sort([10, 8, 4, 9, 2, 7, 6, 3, 5, 1]).inspect

##### Best/Average case
# [5 9 1 0 8 3 2]

# pivot = 5
# list1 = 1 0 3 2
#   pivot = 2
#     list1 = 1 0
#       pivot = 1
#         list1 = 0
#     list2 = 3
# list2 = 9 8
#   pivot = 9
#     list1 = 8

# 4 recursive calls (logn) - height of a well balanced tree
# At each call, we need to loop through the sublists, which when combined is just the original list n
# => O(nlogn)

##### Worst case
# [5 9 1 0 8 3 2]

# pivot = 0
# list2 = 5 9 1 8 3 2
#   pivot = 1
#   list2= 5 9 8 3 2
#     pivot = 2
#       list2 = 5 9 8 3
#         pivot = 3
#           list2 = 5 9 8
#             pivot = 5
#               list2 = 9 8
#                 pivot = 8
#                   list2 = 9

# 6 recursive calls - which is just n
# At each call, we need to loop through the sublists, which when combined is just the original list n
# => O(n^2)

# Therefore, the worst case is when the pivot is chosen and it happens to be the smallest/biggest item in the
# list. This means the sublist length is (n-1), meaning the next recursive call will need to process
# a longer list as compared to the best case where the pivot is near the mid point. The longer the
# sublist, the more chances the same items will be compared again and again throughout the recursive
# calls

# Note: Usually, when an algorithm doesn't have to look at all its inputs to find the result, we say it has runtime
# complexity of O(logn). An example is binary search.
# However, quick sort has runtime O(nlogn), and the logn in the runtime
# complexity is not due the algorithm skipping some inputs (it looks at all items) but comes from the fact
# that the recursive calls are made logn times due to the halfing of the inputs.
