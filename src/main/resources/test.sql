
drop table if exists numbers;
create table numbers as select generate_series(1, 10000) n;
select distinct dvd.n
from numbers as dvd left join numbers as dvs on
 dvd.n % dvs.n = 0 and dvs.n > 1 and sqrt(dvd.n) > dvs.n
where dvs.n is null and dvd.n > 1