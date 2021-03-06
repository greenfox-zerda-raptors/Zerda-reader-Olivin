-- Feeds that the user is subscribed to
INSERT INTO feeds (id, copyright, description, language, link, pub_date, rss_path, title) VALUES (2, NULL , NULL, 'HU-hu', 'http://index.hu/24ora/', '2017-02-02 12:30:00', 'http://index.hu/24ora/rss/','Index - 24óra');
INSERT INTO feeds (id, copyright, description, language, link, pub_date, rss_path, title) VALUES (3, 'Michael Bertolacci, licensed under a Creative Commons Attribution 3.0 Unported License.' , 'This is a constantly updating lorem ipsum feed', NULL, 'http://example.com/', '2017-02-02 12:35:00', 'http://lorem-rss.herokuapp.com/feed?unit=second&interval=30','Lorem ipsum feed for an interval of 30 seconds');
-- Feed to what the user is NOT subscribed to
INSERT INTO feeds (id, copyright, description, language, link, pub_date, rss_path, title) VALUES (4, 'hvg@hvg.hu' , 'hvg.hu RSS', 'HU-hu', 'http://hvg.hu/', '2017-02-02 12:33:39', 'http://hvg.hu/rss','hvg.hu RSS' );

-- user
INSERT INTO users (id, email, password, token) VALUES (2, 'name@example.com', '1234', 'ABCD1234');
INSERT INTO users (id, email, password, token) VALUES (3, 'anothername@example.com','5678', 'QWERTY9876');
-- these feeditems should appear
INSERT INTO feed_items (id, author, description, link, pub_date, title, feed_id) VALUES (11, NULL, 'A Fülöp-szigetek elnöke még több rohadék drogost akar megöletni, csak a korrupt rendorség helyett a katonákkal.', 'http://index.hu/kulfold/2017/02/02/duterte_a_hadsereget_is_beveti_a_droghaboruban/', '2017-02-02 12:30:00', 'Duterte a hadsereget is beveti a drogháborúban', 2 );
INSERT INTO feed_items (id, author, description, link, pub_date, title, feed_id) VALUES (12, 'John Smith', 'Anim fugiat consectetur ea ut labore consectetur pariatur commodo ut est exercitation incididunt laboris.', 'http://example.com/test/1486035270', '2017-02-02 12:34:30', 'Lorem ipsum 2017-02-02T11:34:30+00:00', 3 );
-- feeditem that should not appear
INSERT INTO feed_items (id, author, description, link, pub_date, title, feed_id) VALUES (13, 'hvg@hvg.hu', ' Hajdu János már akkor kiemelt személyek védelemével foglalkozott, amikor Orbán Viktor még farmerban koptatta parlament padjait, épphogy visszatérve a Soros-ösztöndíjon folytatott tanulmányairól. Vessen egy pillantást a 25 évvel ezelotti terrorelhárítóra!  ', 'http://hvg.hu/itthon/20170202_Igy_meg_sosem_latta_a_TEK_foigazgatojat__Hajdu_Janos_akcioban#rss', '2017-02-02 12:31:00', ' Így még sosem látta a TEK f?igazgatóját - Hajdu János akcióban, mielott Putyint védte  ', 4 );

-- Subscribed users
INSERT INTO subscribed_users (subscribed_users_id, subscribed_feeds_id) VALUES (2, 2);
INSERT INTO subscribed_users (subscribed_users_id, subscribed_feeds_id) VALUES (2, 3);

-- Feeds for users
INSERT INTO feed_items_for_users (id, read_by_user, starred, feed_item_id, user_id) VALUES (2, 0, 0, 11, 2);
INSERT INTO feed_items_for_users (id, read_by_user, starred, feed_item_id, user_id) VALUES (3, 0, 0, 12, 2);
