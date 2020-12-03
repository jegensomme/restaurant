select * from users u
    left join user_roles ur on u.id = ur.user_id
    where key='1234';

grant select on table users to manager;