delimiter /
create function getAverage(category varchar(12))
returns decimal(5, 2)
deterministic
begin
	declare Avg_Price decimal(5, 2);
    select avg(price) into Avg_Price
    from book
    where book.category = category;
    return Avg_Price;
end /
delimiter ;

delimiter /
create procedure listCoauthors()
deterministic
begin
	select title, ISBN, group_concat(distinct concat(firstName, ' ', lastName) separator ' & ') as coauthors
	from book_author natural join author natural join book
	group by ISBN
	having count(authorID) > 1;
end /
delimiter ;