# What are System Questions
* Design a URL shortening service like bit.ly.
* How would you implement the Google search?
* Design a client-server application which allows people to play chess with one another.
* How would you store the relations in a social network like Facebook and implement a feature where one user receives notifications when their friends like the same things as they do?

# Don't Panic
The typical outcome of such a discussion is a high-level architecture addressing the goals and constraints in the question. Perhaps the interviewer will choose one or more areas where they will want to discuss bottlenecks and other common problems.

# The System Design Process
## 1. Constraints and Uses Cases
The very first thing you should do with any system design question is to clarify the system's constraints and to identify what use cases the system needs to satisfy. Spend a few minutes questioning your interviewer and agreeing on the scope of the system.

### Example (bit.ly):
#### Uses Cases:

Obvious ones:
1. Shortening: take url => return shorter url
2. Redirection: take short url => redirect to original

Not so obvious ones:

3. Custom url
4. Analytics
5. Automatic link expiration
6. Manual link removal

Let's assume we discussed with the interviewer and agreed on these uses case:

1. Shortening: take url => return shorter url
2. Redirection: take short url => redirect to original
3. Custom url
5. High availability of the system

#### Constraints
How much *traffic* our system should handle and what is the *data size*.

You need to ask the interviewer (or figure out these numbers) in about 2 mins.
1. New urls per month: 100MLN
2. 1BN request per month
3. 10% from shortening and 90% from redirection
4. Requests per second: 1,000,000,000/(30*24*60*60) ~= 400 (40: shortens, 360: redirects)
5. 6BN urls in 5 years (100MLN*12*5)
6. 500 bytes per url (assume 500 chars average, 1 char = 1 byte)
7. 6 bytes per hash (assume we use an alphabet set of numbers and upper and lower case letters. This gives 26+26+10=62, so 62^6 gives us rougly 60BLN unique combinations. Therefore 6 character combinations is enough => 6 bytes)
8. 3TB for all urls, 36GB for all hashes (over 5 years)
9. New data written per second: 40 * (500 + 6): 20k
10. Data read per second: 360 * 506 bytes: 180K

## 2. Abstract Design
Outline all the important components that your architecture will need. Sketch your main components and the connections between them.

1. Application service layer (serves requests)
* Shortening service
* Redirection service
2. Data storage layer (keeps track of the hash => url mappings)

hashed_url = convert_to_base62(md5(original_url + random_salt))[:6]

## 3. Understand Bottlenecks
Now that you have your high-level design, start thinking about what bottlenecks it has. Perhaps your system needs a load balancer and many machines behind it to handle the user requests. Or maybe the data is so huge that you need to distribute your database on multiple machines. What are some of the downsides that occur from doing that? Is the database too slow and does it need some in-memory caching?

These are just examples of questions that you may have to answer in order to make your solution complete. Remember, usually each solution is a trade-off of some kind. Changing something will worsen something else. However, the important thing is to be able to talk about these trade-offs, and to measure their impact on the system given the constraints and use cases defined.

**Example**
Continuing with the bit.ly example:
Traffic wise there will be no bottle neck:
The redirection service is just a wrapper around the db call
The shortening service is a wrapper around the db plus computing a simple hash. Computing 40 hashes per second is not going to be a problem.
The bottle will come from the data layer. Scanning the db on each call from the redirection service to look up a url might be slow because we have to scan through at least 3TB of data.

## 4. Scaling the Abstract Design
You can now dive into making the abstract design more detailed. Usually, this means making your system scale.

### Vertical Scalling
* Buying more computing resources.
* Has physical limits

### Horizontal Scaling
* Using cheaper machines to share the load
* More often than not, have to use a load balancer to distribute the load. However, load balancer can be a single point of failure. Can use multiple load balancers and some election algorithm to select an active load balancer.
* Round robin is the simpliest routing strategy. However, it does not take into account server load, so a machine could be over-worked while others are not doing much
* How to shared sessions? - sticky sessions
1. Have a shared storage for sessions. Then this becomes a failure point. Could use RAID hard drives to introduce redundancy if one of the drives fail
2. Store a server identifier in the user's cookie so that user's request is always routed to the same server

### Caching
* Invalidation strategy is important
#### Examples
* File based caching - cache the generated html pages so do not have to generate the html page on each request
* Database query caching
* memcached - memory cache. Expiration rules is important.

### Replication
* DB replication - master-slave topology.
* Increase in redundancy => reduce single point of failure.
* Load balance across the slave databases for read-heavy traffic. However, load balancer can be a single point of failure.
* For write-heavy traffic - can use master-master setup
* Generally - if have more than one db, it's always a good idea to use a load balancer between them.
1. If there is no load balancer, and your webservers can connect to every available db, then your webservers need to do the routing at the application level. Then your application is aware of your topology. We want to introduce a layer of abstraction so the application only knows to connect to one db
2. If there is no load balancer, then every time u add a new webserver, u need to configure it to connect to all the dbs - this quickly becomes a maintenance nightmare.

### Partition
* Partition database. E.g. users with last name from A-M go to db1 and others go to db2.

### High Availability
* AWS have multiple data-centers (called Availability Zones) in a single Region (e.g. region = Singapore)
* AWS also have multiple regions
* How do u load balance traffic across availability zones? Do it at the DNS level. Geo load balancer

# Other notes
## What is base 64 encoding used for?
When you have some binary data that you want to ship across a network, you generally don't do it by just streaming the bits and bytes over the wire in a raw format. Why? because some media are made for streaming text. You never know -- some protocols may interpret your binary data as control characters (like a modem), or your binary data could be screwed up because the underlying protocol might think that you've entered a special character combination (like how FTP translates line endings).

So to get around this, people encode the binary data into characters. Base64 is one of these types of encodings.

**Why 64?**

Because you can generally rely on the same 64 characters being present in many character sets, and you can be reasonably confident that your data's going to end up on the other side of the wire uncorrupted.

## What is the purpose of base64 encoding, why not just use binary?
The idea behind Base64 is very simple.

Consider sending a message in binary. This transmission can be considered as composed by 8 bit characters so it is base256: each byte is a symbol from 0 to 255.
*(1 character is represented as a byte. There is 8 bits in a byte. Binary system only has two digits, 0 and 1. So, the number of unique combinations that can be represented by 8 digits (that can be either 0 or 1) is 2^8 = 256)*

The problem with this coding is that it cannot be attached to emails and similar channels as some characters have special meaning.

Therefore instead of using 8 bit digits, the Base64 code uses 6 bit words, i.e. 64 combinations, but maps these 64 digits to ASCII characters from ‘A’ (meaning 0) to ‘/’ meaning 63. *(2^6=64)*

The advantage is that to use only legal ASCII characters, that are perfectly compatible with any channel, but to minimize the waste. In fact on 7 bit per byte channels, only one bit is lost, on 8 bit per byte only two bits are lost.

**Example**
Suppose we want to code Base64 a 16 bit word, in hex 0xAB34.

First of all let us transform this in binary: 0xA = 1010,  0xB= 1011, 0x3=0011 0x4=0100 so this number is 1010_1011_0011_0100.

Now reorganize these bits in 6 bits chunks, starting from the MSB (Most Significant Bit, the leftmost bit): you get 101010_110011_010000 (I have left aligned the last digit, so I had to add two 0 bits to the right).

Each of these 6 bit digits have the following decimal representation
```
101010 = 42
110011 = 51
010000 = 16
```

To get the Base64 code, we must use the base64 mapping table (can view on wikipedia) so:
```
42->q
52->z
16->Q
```

therefore the aforementioned hex word is transformed into the Base64 string 'qzQ'.

# Upto
https://www.hiredintech.com/classrooms/system-design/lesson/61
