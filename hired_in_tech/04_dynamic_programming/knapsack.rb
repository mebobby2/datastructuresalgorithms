def longest_increasing_subsequence(sequence)
  return sequence if sequence.length == 1

  if sequence.length == 2
    return sequence.first >= sequence.last ? [sequence.last] : sequence
  end

  cache = {}
  (2...sequence.length).each do |i|
    number = sequence[i]
    previous = cache[i - 1] || longest_increasing_subsequence(sequence[0...i])
    if previous.last < number
      cache[i] = previous + [number]
    elsif previous[-2] && previous[-2] < number
      cache[i] = previous[0...-1] + [number]
    else
      cache[i] = previous
    end
  end
  cache[sequence.length - 1]
end

puts "Answer: #{longest_increasing_subsequence [16, 3, 5, 19, 10, 14, 12, 0, 15]}"
puts "Answer: #{longest_increasing_subsequence [14]}"
puts "Answer: #{longest_increasing_subsequence [10, 8, 6, 4, 2, 0]}"
