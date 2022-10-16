delete
from link;

insert into link (id, short_link, original_link, requests_count)
values (100, '/l/short_link1', 'http://original.link1', 1),
       (101, '/l/short_link2', 'http://original.link2', 2),
       (102, '/l/short_link3', 'http://original.link3', 3),
       (103, '/l/short_link4', 'http://original.link4', 4),
       (104, '/l/short_link5', 'http://original.link5', 4),
       (105, '/l/short_link6', 'http://original.link6', 5);
