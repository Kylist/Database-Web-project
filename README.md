1. Abstract
Nowdays, most websites allow users to comment on their content. Consider news websites, like Washington Post, Daily Mail, or CNN. Users are allowed to comment on the content of a news article. Suppose that you create a startup (come up with a neat name) that provides management services for user comments for such websites. Examples of such providers are: LiveFyre, Disuqus, and Spot.IM. Your task in this project is to create a database that tracks the user activity at each of these website and give aggregate statistics about the activity to your subscribers. Here are the main entities of your enterprise:

Customer: To keep things more contained, the customer is a news outlet or a blog.

Article (or Item/Artifact): Your customer regularly publishes content on its website. For example, a news outlet publishes news articles, while a blog publishes a story or opinion article. The article has several attributes. Here is non-exhaustive list: title, author, publication date, last updated, and topic.

User: User visit your customer's website and may decide to post a comment, reply to another comment, or indicate like/dislike on a comment. Some websites require the user to login before they are able to do those actions, while others do not require the user to login in. In the latter case, the user appears as a guest. You will need to collect some basic information about a user, like name, email, country, state (but not the full address), login, password, avatar, or picture. The list again is not exhaustive.

Comment: A comment is posted by a user to an article. Comments are of several types: direct (it comments on the article itself), reply (comments on someone else's comment), and like/dislike another comment. A comment has date and time, content. A comment may be flagged for its derogatory content.

Derogatory Comment: You need to create a process to manage such comments. If a comment is flagged as derogatory a senior user needs to read it and make a decision on it. (Imagine that a comment is flagged by a machine learning tool, which may have errors. See https://coralproject.net/ (Links to an external site.) or https://www.washingtonpost.com/pr/wp/2017/09/06/the-washington-post-launches-talk-commenting-platform/ (Links to an external site.)). Hence, you need to have a class of senior/trusted users whom the customer trusted to read the flagged comments and decide on their content. Each senior user has queue of such comments that she needs to read and decide. The decision is YES/NO. You can suspend a user's account if it is a repeated offender, say 5 times.

Billing: Your start up needs to make money. You need to envision a billing scheme. For example, you can bill by article. Hence, a customer places an order for an article to be opened for commenting. You can bill the client by the number of distinct users who comment, or by the number of comments. You may have different prices per client.

2. What included in this project
  a. ER diagram representing the conceptual design of the database.
  b. Relational schema based on your E-R diagram.
  c. Populate Relations and Queries for my data base.
  d. Code for my website and introduction to open my website.
 
3. Image of my website

![image](https://user-images.githubusercontent.com/28942562/84046335-f5e74c00-a977-11ea-9a7b-4a757d850231.png)

![image](https://user-images.githubusercontent.com/28942562/84046358-fda6f080-a977-11ea-931c-20fbd99d701d.png)

![image](https://user-images.githubusercontent.com/28942562/84046380-05669500-a978-11ea-877e-c64457cf2327.png)

![image](https://user-images.githubusercontent.com/28942562/84046611-54acc580-a978-11ea-9d74-02cfa4f4c02f.png)

![image](https://user-images.githubusercontent.com/28942562/84046630-5a0a1000-a978-11ea-9cc1-2c5d3c2f63de.png)

![image](https://user-images.githubusercontent.com/28942562/84046645-5e362d80-a978-11ea-8b44-2936f8b97b31.png)

![image](https://user-images.githubusercontent.com/28942562/84046653-61c9b480-a978-11ea-8bb8-53191b99258d.png)

![image](https://user-images.githubusercontent.com/28942562/84046672-65f5d200-a978-11ea-9ed0-39f447999cd8.png)

![image](https://user-images.githubusercontent.com/28942562/84046687-6a21ef80-a978-11ea-9f00-b6800b4a5b95.png)



