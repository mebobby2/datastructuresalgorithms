Most basic and general explanation: cryptography is all about number theory, and all integer numbers (except 0 and 1) are made up of primes, so you deal with primes a lot in number theory.

More specifically, some important cryptographic algorithms such as RSA critically depend on the fact that prime factorization of large numbers takes a long time. Basically you have a "public key" consisting of a product of two large primes used to encrypt a message, and a "secret key" consisting of those two primes used to decrypt the message. You can make the public key public, and everyone can use it to encrypt messages to you, but only you know the prime factors and can decrypt the messages. Everyone else would have to factor the number, which takes too long to be practical, given the current state of the art of number theory.

## Theory
A prime number that is divisible only by itself and 1 (e.g. 2, 3, 5, 7, 11). They have unique properties for factorization. One of these properties is that when it is relatively easy to find a larger prime number, it is tough to factor large numbers back into primes.
