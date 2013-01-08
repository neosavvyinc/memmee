select u.id as id, u.firstName as firstName, u.email as email, count(*) as memmeeCount, s.shareCount
    from memmee m join user u on m.userId = u.id
           JOIN (select count(*) as shareCount from memmee where shareKey IS NOT NULL) s on
            (m.userId = userId)
    group by u.id
    order by memmeeCount DESC, u.firstName, u.email;



select u.id as id,
    u.firstName as firstName,
    u.email as email,
    count(*) as memmeeCount,
    s.shareCount
from
  user u, memmee m, (
    select userId uid, count(*) as shareCount from memmee where shareKey is not null group by uid
  ) s
where
  u.id = m.userId and u.id = s.uid;


select
 t1.id as id
 ,t1.firstName as firstName
 ,t1.email as email
 ,t1.memmeeCount as memmeeCount
 ,t2.shareCount as shareCount
from
(select u.id as id,
    u.firstName as firstName,
    u.email as email,
    count(*) as memmeeCount
from
  user u, memmee m
where
  u.id = m.userId
group by u.id
  order by memmeeCount DESC, u.firstName, u.email
) t1 left outer join
(
  select userId uid, count(*) as shareCount from memmee where shareKey is not null group by uid
) t2 on
t1.id = t2.uid


select t1.id as id,t1.firstName as firstName,t1.email as email,t1.memmeeCount as memmeeCount,t2.shareCount as shareCount from (select u.id as id, u.firstName as firstName, u.email as email, count(*) as memmeeCount from user u, memmee m where u.id = m.userId group by u.id order by memmeeCount DESC, u.firstName, u.email) t1 left outer join (select userId uid, count(*) as shareCount from memmee where shareKey is not null group by uid) t2 on t1.id = t2.uid


