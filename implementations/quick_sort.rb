def quick_sort(a, min = 0, max = a.length)
  puts "start #{min} and #{max}"
  puts a.inspect
  return a if max == 1
  pivot = max/2
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
    swap(a, 0, swap_index-1) 
  else
    swap(a, 0, max-1) 
  end
  puts a.inspect

  if swap_index
    quick_sort(a, min, swap_index-1)
  else
    quick_sort(a, min, pivot-1)
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
puts quick_sort([10,8,4,9,2,7,6,3,5,1]).inspect