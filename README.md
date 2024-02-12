### mysql 계정 만들기
```
create database board;
create user yunhee@localhost identified by '1234';
grant all privileges on board.* to yunhee@localhost;
```

### tables
```
drop table if exists board_table;
create table board_table(
    id bigint primary key auto_increment,
    boardWriter varchar(50),
    boardPass varchar(20),
    boardTitle varchar(50),
    boardContents varchar(500),
    boardCreatedTime datetime default now(),
    boardHits int default 0,
    fileAttached int default 0
);
```