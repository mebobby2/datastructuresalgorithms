# Bitwise Operations
* and
  * 1 & 1 = 1

* or
  * 0 | 0 = 0

* xor
  * 0 ^ 1 = 1

* negation
  * 0 = 1
  * 1 = 0

* shifting
  * e.g. 00101011 << 4 = 10110000

## Tips
* using xor will cancel out two identifical numbers
  * e.g. 15 ^ 12 ^ 15 = 12
* a number is odd if the lowest order bit is set
  * e.g. for 5 = 0101
    * 0101 & 0001 = 0001 (so 5 is odd)
