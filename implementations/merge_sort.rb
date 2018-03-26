def merge_sort(a)
    return a if a.length == 1
    mid = a.length/2
    left = merge_sort(a[0...mid])
    right = merge_sort(a[mid..a.length])

    sorted = []
    loop do
        if left.first < right.first
            sorted << left.shift
        else
            sorted << right.shift
        end
        break if left.empty? || right.empty?
    end
    sorted.concat(left).concat(right)
end

merge_sort([4,5,1,3,2])
merge_sort([20,16,1,4,19,11,8,2,3,6,15,18,5,7,12,10,9,14,13,17])

#The dividing of the array takes O(1), since we know the length of the array, we cat get its 
#midpoint in the same time no matter how large the array is.

#The combining of the left and right arrays take O(N). The left and right arrays combine
#make up the original array. Since we loop until either of left or right is empty, we are
#essentially looping N times.

#So, the time it takes to divide and combine is O(cN), where c is the constant time it takes
#to divide. 

#Lets say there are l levels, so the total time spent in the resursive calls is l*cN. 
#And l = lgn+1. So the total time merge sort takes is cn*(lgn+1). We discard the 1 and
#constants, and we are left with O(nlgn)


