DROP DATABASE FinalProject;
CREATE DATABASE FinalProject;
USE  FinalProject;

create table customer ( 
    customerID int primary key, 
    customerName varchar(100) not null 
); 
create table article ( 
    articleID int primary key, 
    articleTitle varchar(100) not null, 
    author varchar(100), 
    aDateTime datetime, 
    topic varchar(100), 
    url varchar(100) 
); 
create table billed ( 
    billID int primary key, 
    articleID int, 
    customerID int, 
    billDateTime datetime not null, 
    amount decimal(13,2) not null, 
    billDue datetime, 
    constraint bill_date_chk check(billDue>billDateTime), 
    foreign key (customerID) references customer(customerID) on delete cascade, 
    foreign key (articleID) references article(articleID) on delete cascade 
); 
create table user_type ( 
    typeID int primary key, 
    typeName varchar(100) not null, 
    permission int not null 
); 
create table user ( 
    userID varchar(100) primary key, 
    password varchar(100) not null, 
    fName varchar(100) not null, 
    lName varchar(100) not null, 
    typeID int not null, 
    email varchar(100) not null, 
    country varchar(100), 
    state varchar(100), 
    avatarURL varchar(100), 
    offenceNo int default 0, 
    isSuspended boolean default false, 
    foreign key (typeID) references user_type(typeID)  
); 
create table comment ( 
    commentID int primary key, 
    commentDate datetime, 
    commentType varchar(45) not null default "comment", 
    userID varchar(100) not null, 
    articleID int not null, 
    parentCommentID int, 
    content longtext, 
    foreign key (userID) references user(userID), 
    foreign key (articleID) references article(articleID), 
    constraint type_chk check (commentType in ('comment', 'like', 'dislike', 'reply')) 
); 
create table derogatory_comment ( 
    commentDate datetime, 
    userID varchar(100), 
    articleID int, 
    parentCommentID int, 
    content longtext, 
    reviewerID varchar(100), 
    foreign key (userID) references user(userID), 
    /*foreign key (parentCommentID) references comment(commentID), */
    foreign key (articleID) references article(articleID), 
    foreign key (reviewerID) references user(userID) 
); 
create table suspended_user ( 
    userID varchar(100), 
    reviewerID varchar(100), 
    suspendDate datetime, 
    suspendUntil datetime, 
    constraint sus_date_chk check(suspendUntil > suspendDate), 
    foreign key (userID) references user(userID), 
    foreign key (reviewerID) references user(userID) 
); 