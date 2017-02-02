-- Feeds that the user is subscribed to
INSERT INTO feeds (id, copyright, description, language, link, pub_date, rss_path, title) VALUES (5, "Michael Bertolacci, licensed under a Creative Commons Attribution 3.0 Unported License." , "This is a constantly updating lorem ipsum feed", NULL, "http://example.com/", "2017-02-02 12:35:00", "http://lorem-rss.herokuapp.com/feed?unit=second&interval=30","Lorem ipsum feed for an interval of 30 seconds" );
INSERT INTO feeds (id, copyright, description, language, link, pub_date, rss_path, title) VALUES (1, NULL , NULL, "HU-hu", "http://index.hu/24ora/", "2017-02-02 12:30:00", "http://index.hu/24ora/rss/","Index - 24óra" );
-- Feed to what the user is NOT subscribed to
INSERT INTO feeds (id, copyright, description, language, link, pub_date, rss_path, title) VALUES (3, "hvg@hvg.hu" , "hvg.hu RSS", "HU-hu", "http://hvg.hu/", "2017-02-02 12:33:39", "http://hvg.hu/rss","hvg.hu RSS" );

-- user
INSERT INTO users (id, access_token) VALUES (1,1234);

-- these feeditems should appear
INSERT INTO feed_tems (id, author, description, link, pub_date, title, feed_id) VALUES (1, NULL, "A Fülöp-szigetek elnöke még több "rohadék" drogost akar megöletni, csak a korrupt rend?rség helyett a katonákkal.", "http://index.hu/kulfold/2017/02/02/duterte_a_hadsereget_is_beveti_a_droghaboruban/", "2017-02-02 12:30:00", "Duterte a hadsereget is beveti a drogháborúban", 1 )
INSERT INTO feed_tems (id, author, description, link, pub_date, title, feed_id) VALUES (122, "John Smith", "Anim fugiat consectetur ea ut labore consectetur pariatur commodo ut est exercitation incididunt laboris.", "http://example.com/test/1486035270", "2017-02-02 12:34:30", "Lorem ipsum 2017-02-02T11:34:30+00:00", 5 )
-- feeditem that should not appear
INSERT INTO feed_tems (id, author, description, link, pub_date, title, feed_id) VALUES (91, "hvg@hvg.hu", " Hajdu János már akkor kiemelt személyek védelemével foglalkozott, amikor Orbán Viktor még farmerban koptatta parlament padjait, épphogy visszatérve a Soros-ösztöndíjon folytatott tanulmányairól. Vessen egy pillantást a 25 évvel ezel?tti terrorelhárítóra!  ", "http://hvg.hu/itthon/20170202_Igy_meg_sosem_latta_a_TEK_foigazgatojat__Hajdu_Janos_akcioban#rss", "2017-02-02 12:31:00", " Így még sosem látta a TEK f?igazgatóját - Hajdu János akcióban, miel?tt Putyint védte  ", 3 );

-- Subscribed users
INSERT INTO subscribed_users (subscribed_users_id, subscribed_feed_id) VALUES (1, 1);
INSERT INTO subscribed_users (subscribed_users_id, subscribed_feed_id) VALUES (1, 5);

-- Feeds for users
INSERT INTO feeds_for_users (id, read_by_user, starred, feed_item_id, user_id) VALUES (1, 0, 0, 1, 1);
INSERT INTO feeds_for_users (id, read_by_user, starred, feed_item_id, user_id) VALUES (82, 0, 0, 122, 1);
