
select *
from user_shifts left join users u on u.id = user_shifts.user_id
order by date(start_date_time), user_id;