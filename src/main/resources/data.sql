--INSERT INTO users(name,password) VALUES(1,'田中太郎', 'test123'),(2,'鈴木一郎', 'test456');

--INSERT INTO tasks (user_id, category_id,title, closing_date, progress, memo,time, date)
--VALUES (1, 1, '見積もり', '2025-12-31', 0, '案件に適した見積もりを取る' ,'40' , '2025-12-30');


INSERT INTO categories (category_name)
VALUES('仕事'),('日常');
