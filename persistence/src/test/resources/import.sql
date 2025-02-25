INSERT INTO tag (id_tag, name_tag) VALUES
(1, 'gift'),
(2, 'sport'),
(3, 'jumping'),
(4, 'riding'),
(5, 'wonderful gift'),
(6, 'relax'),
(7, 'make you fun');

INSERT INTO gift_certificate (id_gift_certificate, name_gift_certificate,
description, price, duration, create_date, last_update_date) VALUES
(1, 'Skating', 'Ice skating is a sport in which people slide over a smooth ice surface on steel-bladed skates. Millions of people skate in those parts of the world where the winters are cold enough.', 10, 30, '2021-01-10 12:15:37', '2021-01-10 12:15:37'),
(2, 'Fitness', 'Physical fitness is a state of health and well-being and, more specifically, the ability to perform aspects of sports, occupations and daily activities. Physical fitness is generally achieved through proper nutrition, moderate-vigorous physical exercise, and sufficient rest.', 80, 30, '2021-01-11 10:30:01', '2021-01-11 10:30:01'),
(3, 'Horseback riding', 'Horseback riding is the activity of riding a horse, especially for enjoyment or as a form of exercise.', 100, 30, '2021-01-12 11:34:18', '2021-01-12 11:34:18'),
(4, 'Trampoline jumping', 'Trampoline jumping can be fun.', 20, 30, '2021-01-12 11:34:18', '2021-01-12 11:34:18');

INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag) VALUES
(1, 1),
(1, 2),
(1, 7),
(2, 2),
(2, 5),
(2, 7),
(3, 1),
(3, 4),
(3, 5),
(3, 6),
(4, 2),
(4, 3),
(4, 6),
(4, 7);

INSERT INTO user (id_user, surname, name) VALUES
(1, 'Ivanov', 'Ivan'),
(2, 'Petrov', 'Mihail'),
(3, 'Sokolov', 'Sergey');

INSERT INTO purchase (id_purchase, purchase_date,id_user,cost) VALUES
(1, '2021-01-10 10:29:18',1,110),
(2, '2021-01-11 11:34:10',3,80),
(3, '2021-01-11 14:38:17',2,20),
(4, '2021-01-12 18:54:28',1,10),
(5, '2021-01-12 19:05:18',2,100),
(6, '2021-01-12 20:14:18',1,100),
(7, '2021-01-14 11:10:07',3,30);

INSERT INTO purchase_gift_certificate (id_purchase, id_gift_certificate) VALUES
(1, 1),
(1, 3),
(2, 2),
(3, 4),
(4, 1),
(5, 2),
(5, 4),
(6, 3),
(7, 1),
(7, 4);

CREATE VIEW user_purchases_cost AS
 SELECT user.id_user, SUM(cost) AS full_sum
  FROM user
  JOIN purchase
  ON(user.id_user=purchase.id_user)
  GROUP BY user.id_user;

CREATE VIEW user_tags AS
 SELECT id_user, tag.id_tag, name_tag, COUNT(tag.id_tag) AS count_tag
  FROM tag
  JOIN gift_certificate_tag
  ON(tag.id_tag=gift_certificate_tag.id_tag)
  JOIN purchase_gift_certificate
  ON (gift_certificate_tag.id_gift_certificate=purchase_gift_certificate.id_gift_certificate)
  JOIN purchase
  ON(purchase_gift_certificate.id_purchase=purchase.id_purchase)
  GROUP BY tag.id_tag, id_user;