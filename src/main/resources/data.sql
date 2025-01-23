-- 기존 Article 더미 데이터
INSERT INTO article(title, content) VALUES('가가가가','1111')
INSERT INTO article(title, content) VALUES('나나나나','2222')
INSERT INTO article(title, content) VALUES('다다다다','3333')
-- article 테이블에 데이터 추가
INSERT INTO article(title, content) VALUES('당신의 인생 영화는?','댓글 적어보기~')
INSERT INTO article(title, content) VALUES('당신의 소울 푸드는?','댓글 고고링~')
INSERT INTO article(title, content) VALUES('당신의 취미는?','댓글 고고고')

-- 4,5,6 게시글에 댓글 달기
-- 4
INSERT INTO comment(article_id, nickname, body) VALUES(4, 'park', '비긴 어게인')
INSERT INTO comment(article_id, nickname, body) VALUES(4, 'choi', '인턴')
INSERT INTO comment(article_id, nickname, body) VALUES(4, 'kim', '어바웃 타임')
-- 5
INSERT INTO comment(article_id, nickname, body) VALUES(5, 'park', '비빔밥')
INSERT INTO comment(article_id, nickname, body) VALUES(5, 'choi', '햄버거')
INSERT INTO comment(article_id, nickname, body) VALUES(5, 'kim', '치킨')
-- 6
INSERT INTO comment(article_id, nickname, body) VALUES(6, 'park', '헬스')
INSERT INTO comment(article_id, nickname, body) VALUES(6, 'choi', '러닝')
INSERT INTO comment(article_id, nickname, body) VALUES(6, 'kim', '산책')

INSERT INTO coffee(name, price) VALUES('아이스 아메리카노','4500')
INSERT INTO coffee(name, price) VALUES('카페 라떼','5000')
INSERT INTO coffee(name, price) VALUES('바닐라 라떼','6000')