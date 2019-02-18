# Tips

## Break the problem apart

### Traffic

E.g. Write a logging system for 500 million active monthly users

500 million is a big scary number that we cannot cannot really understand and know what to do with. We have to break it down into requests per second. But when doing that, its not a linear divide where u just divde 500million by 30*24*60*60 beecause traffic patterns are spikey. So, u need to find the peak traffic and find out the maximum number of requests per second your system must support.

1. Ask, how much of the 500 million monthly users are active everyday. Let's say its 70%.
2. So, everyday, we must support 350 million active users.
3. Let's say our user base is entirely in US, and the peak traffic is 70% of the daily active users. => 245 million users in a peak hour
4. So that is (245million/(60*60)) =~ 6,800 per second.
5. Let's say our server is written in NodeJS and is poorly optimized. Its baseline is 1000 concurrent users per second
6. Hence, we need about 68 servers to handle this peak load

#### Background
Understand a typical system traffic pattern. It is always most likely spikey. There are peaks and troughs.

### Data & Bandwidth
How much logging should we store?

500mb per minute? That is too much, because people in third world countries would suffer.

I better upper bound will be 10kb per minute.

## Cover breadth
* What protocol between the client and server
* How to store the logs
* Retention time for logs
* Operational costs
* etc

## Play to your strengths
Do not try to figure out what the interviewer expects of you. Use your own experience to give your version of the solution.

# Reference
Tips are taken from this video: https://www.youtube.com/watch?v=ZgdS0EUmn70
