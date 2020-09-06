USE  FinalProject;

select count(*) as commentCount, userID, commentType from comment 
where commentType = 'comment' 
group by userID, commentType 
order by commentCount desc 
limit 20; 
 
select count(*) as replyCount, userID, commentType from comment 
where commentType = 'reply' 
group by userID, commentType 
order by replyCount desc 
limit 20; 
 
select count(*) as likeDislikeCount, userID from comment 
where commentType = 'like' or commentType = 'dislike' 
group by userID 
order by likeDislikeCount desc 
limit 20; 
 
with distinct_customerarticle as (select customerID, articleID from billed 
group by customerID, articleID) 
select customerID, count(*) as requestCount from distinct_customerarticle 
group by customerID 
order by requestCount desc 
limit 10; 
 
select customerID, sum(amount) as totalSales from billed 
group by customerID 
order by totalSales desc 
limit 10;