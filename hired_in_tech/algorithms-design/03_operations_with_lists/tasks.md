# Find if a number is a Palindrome

Logic to find whether number is Palindrome or not is

* Need to reverse the number and check whether reverse of number and number are same or not.

To reverse the number we need to use below steps.

1. Assume input is stored in variable x, reverse of input is stored in variable y. Initialise y with 0. And one temp variable initialise with x
2. y = y*10 + (temp%10); temp = temp/10; - repeat this until (temp>0). Can do it in while loop
temp%10 gives the remainder and as we are doing division by 10 hence remainder will be the last digit of number.

temp/10 gives the quotient as division is by 10 hence quotient will be the rest of the number without last digit.

At the end compare x and y. If both are equal then it's Palindrome. Else it's not.

```
def is_numeric_palindrome number
    return false if number.nil?

    rev = 0
    n = number
    while n > 0 do
        dig = n % 10
        rev = rev * 10 + dig
        n = n / 10
    end
    number == rev
end
```
