# Problem Statement
Design a simplified version of Twitter where people can post tweets, follow other people and favorite tweets.

# Clarifying Questions

1. Many users do we expect this system to handle?
Let’s aim for 10 million users generating around 100 million requests per day

2. How connected will these users be?
Each user will be following 200 other users on average, but expect some extraordinary users with tens of thousands of followers

3. How many requests will be generated from tweets and favouriting them?
Expect that there will be a maximum of 10 million tweets per day and each tweet will probably be favorited twice on average but again, expect some big outliers.

Hence:

We will have around 10 million users. Average number of followed other users is 200. This means that the network of users will have about 200 * 10 million edges. This makes 2 billion edges. If the average number of tweets per day is 10 million the number of favorites will then be 20 million.

To summarize, here are some more things we now know:

* 10 million users
* 10 million tweets per day
* 20 million tweet favorites per day
* 100 million HTTP requests to the site
* 2 billion “follow” relations
* Some users and tweets could generate an extraordinary amount of traffic

# High Level Design
Now we begin with the first part. What should it take care of? The answer will come straight from the problem statement and the clarifying comments that the interviewer has made. At this point it has become obvious that our application will need to handle requests for:

* posting new tweets
* following a user
* favoriting a tweet
* displaying data about users and tweets

The first three operations require things to be written somewhere in our database, while the last is more about reading data and returning it back to the user. The last operation will also be the most common one as can be inferred from the numbers discussed in the previous section.

Let’s make it more concrete how the user will interact with our application and design something that will support such interactions. We can describe to the interviewer how we imagine this to work. For example, there will be a profile page for each user, which will show us their latest tweets and will allow for older tweets to be shown. Each such page will have a button for following the user. There will also be a button at the top of the page, which will allow logged in users to open a dialog box and write a message in it. After they click a button the message will be stored and will appear on their profile page. Once we have confirmed this very rough description of the UI we can begin with the design of the back-end supporting everything.

## Handling user requests
We know that the expected daily load is 100 million requests. This means that on average the app will receive around 1150 requests per second. Of course, this traffic is expected to be distributed unevenly throughout the day. Therefore our architecture should allow the app to handle at least a few thousand requests per second at times.

We’ll say more about load balancing when we start analysing the bottlenecks in our architecture. For the moment, at this high level, we’ll just mention that this is a needed component. Behind the load balancer we will be running a set of servers that are running our application and are capable of handling the different requests that arrive. The load balancer routes requests to the servers using some predefined logic and the application servers are able to understand the requests and return the proper data back to the user’s browser. There is one more major component for our high-level architecture to be complete - the storage.

## Storing the data
We need to store data about our users and their tweets to make the application complete. Let’s quickly look at what needs to be stored. First of all, users have profiles with some data fields attached to them. We’ll need to store that. Each user has a set of tweets that they have produced over time. Moreover, users can follow other users. We need to store these relationships in our database. Finally, users can mark tweets as favorite. This is another kind of relationship between users and tweets. The first one was recording users as authors of tweets. The second one will record users who favorited a tweet.

Obviously, there are some relations between our data objects - users and tweets. Let’s assess the approximate size of the data to be stored. We said that we expect around 10 million users. For each user we’ll have some profile data to store but overall that kind of data shouldn’t be an issue for us. Tweets will be generated at an average speed of 10 million per day. This makes about 115 per second. Also, for a single year there will be 3.65 billion tweets. So, let’s aim for a solution that can store efficiently at least 10 billion tweets for now and the incoming traffic mentioned above. We didn’t really ask how big a tweet can be. Maybe it’s safe to quickly ask that now and let’s assume our interviewer told us it’s the same as it is for the real Twitter - 140 characters. This means that for tweets we want to be able to store at least 140 * 10 bln = 1.4 trillion characters or around 2.8 terabytes if we assume 2 bytes per character and no compression of the data.

The favorites are expected to grow at a rate of 20 mln per day. So, for a year there will be 7.3 bln such actions. Let’s say we want to be able to store at least 20 bln such objects. They can probably just point to one user and one tweet through their IDs. The IDs for the tweets will probably need to be 8 byte fields while for the users we could use 4 bytes only. This is because our tweets will grow a lot in number. So, our very rough calculation gives us 12 * 20 bln = 240 bln bytes or 240 gigabytes.

After this quick analysis it is obvious that the tweets will take up the majority of our storage’s space. In general, you don’t need to make very detailed calculations especially if you don’t have much time. However, it is important to build a rough idea about the size of the data that you will need to handle. If you don’t figure that out any design decision at the higher or lower level may be inappropriate.

Let’s say we decide to use a relational database like MySql or Postgres for our design.

The data that will be stored and the rate of the queries it will receive are not going to be absurdly high but they are not going to be trivial either. In order to handle the incoming read requests we may need to use a caching solution, which stands in front of the database server. One such popular tool is memcached. It could save us a lot of reads directly from the database.

In an application like ours it is likely that a given tweet or a user’s profile becomes highly popular and causes many requests to be sent to our database server. The cache solution will alleviate such situations by storing the popular bits of data in memory and allowing for very quick access to them without the need to hit the database.

Going further, in order to make it possible to answer read queries fast we will definitely need to add the appropriate indexes. This will also be vital for executing quick queries joining tables. Considering the size of the data we may also think about partitioning the data in some way. This can improve the read and write speeds and also make administration tasks like backups faster.

# Low Level Design
## Database schema
Users >
* ID (id)
* username (username)
* full name (first_name & last_name)
* password related fields like hash and salt (password_hash & * password_salt)
* date of creation and last update (created_at & updated_at)
* description (description)
* and maybe some other fields...

Tweets >
* ID (id)
* content (content)
* date of creation (created_at)
* user ID of author (user_id)

These two entities have several types of relations between them:
1. users create tweets
2. users can follow users
3. users favorite tweets

Connections >
* ID of user that follows (follower_id)
* ID of user that is followed (followee_id)
* date of creation (created_at)

Favorites>
* ID of user that favorited (user_id)
* ID of favorited tweet (tweet_id)
* date of creation (created_at)

Now that we have this rough idea about the database tables we will need, our interviewer could ask us to think about what else is needed to serve the load of expected queries. We already discussed with some numbers the expected sizes of the data. There is also a pretty good idea about the types of pages that the application will need to serve. Knowing this we could think about the queries that will be sent to our database and to try to optimize things so that these queries are as fast as possible.

Starting with the basics there will be queries for retrieving the details of a given user. Our users’ table above has both id and username fields. We will want to enforce uniqueness on both because IDs are designed to be unique and will serve as a primary key on the table and usernames are also meant to be different for all registered users. Let’s assume that the queries to our data will be filtering users by their username. If that’s the case we will definitely want to build an index over this field to optimize the times for such queries.

The next popular query will fetch tweets for a given user. The query needed for doing that will filter tweets using user_id, which every tweet has. It makes a lot of sense to build an index over this field in the tweets table, so that such queries are performed quickly.

We will probably not want to fetch all tweets of a user at once. For example, if a given user has accumulated several thousand tweets over time, on their profile page we will start by showing the most recent 20 or something like that. This means that we could use a query, which not only filters by user_id but also orders by creation date (created_at) and limits the result. Based on that we may think about expanding our index to include the user_id column but to also include the created_at column. When we have an index over more than one column the order of the columns matters. If our index looks like that: <user_id, created_at>, making a query filtering by just user_id will take advantage of the index even though we are not filtering by the second column. So, such an index will allow us to filter either by just user_id, or by both columns. This will allow us to fetch all tweets authored by a given user or to isolate just the tweets created in a given time frame. Both will be useful queries for our application.

For each user we will want to show the users that they follow and the users that follow them. For this we will need the table connections. To get the users followed by someone we can simply filter the connections table by the column follower_id. To get the users following someone we can filter by followee_id. All this means that it will make a lot of sense to build two indexes in this table - one on follower_id and another one on followee_id. Voila, we are ready to show the connections that each user has in our application. Like for tweets you can figure out how to fetch the connections in a paginated manner.

What about favorited tweets by a user? We will definitely want to see something like that. For this we will need to use the table favorites. We will need to join favorites with tweets and to filter by user_id for the user whose favorites we want to fetch. The columns used for joining will be the tweet_id in favorites and id in tweets.

The above means that it makes sense to add two indexes - one on the user_id column and one on the tweet_id column.

It is also worth mentioning that after creating the indexes our write queries will become slightly slower but the benefits that we get for our read operations are so significant with the amounts of data we have that we have no other choice.

## Building a RESTful API
We will definitely need to fetch the profile details for a given user. So we could define an endpoint that looks like that:

```
GET /api/users/<username>
```

To get the tweets of a given user ordered by date, we can expose an endpoint like that:

```
GET /api/users/<username>/tweets?page=4
```
This tells the back-end to fetch the 4th page with 20 tweets, instead of the default behavior - 1st page with 20 tweets.

Our front-end will also be asking about the users following a given user and followed by that user. Here are two possible endpoints for that:

```
GET /api/users/<username>/followers
GET /api/users/<username>/followees
```

Let’s look at creating new data. For example we will need an endpoint for posting a new tweet:

```
POST /api/users/<username>/tweets
```

Or how about following a given user:

```
POST /api/users/<username>/followers
```

It will be useful to be able to see a list of all users that favorited a tweet:

```
GET /api/users/<username>/tweets/<tweet_id>/favorites
POST /api/users/<username>/tweets/<tweet_id>/favorites
```

## Increased number of read requests
Suddenly we got lucky and people started visiting our application 5 times more often generating 5 times more read requests caused by viewing posts and user profiles. What would be the first place that will most likely become a bottleneck?

One very natural answer is our database. It could become overwhelmed with all the read requests coming to it. One typical way to handle more read requests would be to use replication. This way we could increase the number of database instances that hold a copy of the data and allow the application to read this data. Of course, this will help if the write requests are not increased dramatically. An alternative approach could be to shard our database and spread the data across different machines. This will help us if we need to write more data than before and the database cannot handle it. Such an approach does not come for free and it involves other complications that we would need to take care of.

If we manage to stabilize our database, another point where we could expect problems is the web application itself. If we’ve been running it on a limited set of machines, which cannot handle all the load anymore this could lead to slow response times. One good thing about our high-level design is that it allows us to just add more machines running the application. If the database is ready to handle more incoming requests from additional application instances we can scale horizontally like that. We will just add more machines running our application and instruct our load balancer to send requests to these machines, too. Of course, this sounds simpler that it is in practice but that’s the general idea.

We mentioned in the high level design that it is a very good idea to use a load balancer, which would direct requests to different instances of our application. This way our load balancer could become a single point of failure and a bottleneck if the number of requests is really high. In such cases we could start thinking about doing additional load balancing using DNS and directing requests for our domain to different machines, which are acting as load balancers themselves.

## Unexpected traffic
Let’s look at one more possible issue. We already touched on this but it doesn’t hurt to go through it one more time. In the beginning the interviewer warned us that there will be outliers in the data. This means that some users will have many more followers than the average. Also, some tweets will attract a lot of attention during a short period of time. In most cases such outliers will generate peaks in the number of requests that our application receives.

As we mentioned earlier this could increase the load on our database. In such a situation using a caching solution could help a lot. The idea is that it will be able to answer the popular and repeating requests coming from the application and these requests will never touch the database, which will be busy replying to other queries.

We could also experience unusual peaks in the requests hitting our application servers. If they are not enough to respond quickly enough to all requests this could cause timeout to occur for some of the users. In such situations solutions that offer auto-scaling of the available computing nodes could save the day. Some companies offering such services are Amazon and Heroku and they were already mentioned in this example. You can take the time to investigate what is out there on the market, so that you can have a discussion about possible ways to handle peaks in traffic.
