--INSERT INTO users(name,password) VALUES(1,'田中太郎', 'test123'),(2,'鈴木一郎', 'test456');

INSERT INTO tasks (user_id, category_id,title, closing_date, progress, memo,time, date)
VALUES (1, 1, '見積もり', '2026-5-25', 1, '案件に適した見積もりを取る' ,'40' , '2026-5-25'),
(1, 2, '散髪', '2026-6-1', 3, '駅まで20分' ,'60 ','2026-6-1'),
(1, 2, '日用品の買い出し', '2026-6-2',3 , '歩いて5分' ,'20' , '2026-6-2'),
(1, 2, '荷物の発送', '2026-6-3',3 , '' ,'30' , '2026-6-3'),
(1, 1, '書類作成', '2026-6-3', 3, '半分ほど作成済み' ,'100' , '2026-6-3');


INSERT INTO categories (category_name)
VALUES('仕事'),('日常');
