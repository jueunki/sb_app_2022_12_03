
# DB 생성
DROP DATABASE IF EXISTS sb_app_2022_k;
CREATE DATABASE sb_app_2022_k;
USE sb_app_2022_k;

# 게시물 테이블 생성(기본셋팅!!)
CREATE TABLE article (
	id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	title CHAR(100) NOT NULL,		# varchar보다는 char가 데이터소모가 덜하기 때문에 사용한다.
	`body` TEXT NOT NULL	
);

#게시물, 테스트 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';


SELECT * FROM article;


# =========MemberTable생성========= # varchar보다는 char가 데이터소모가 덜하기 때문에 사용한다.
# 회원 테이블 생성(기본셋팅!!)
CREATE TABLE `member` (
	id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	loginId CHAR(20) NOT NULL,
	loginPw CHAR(60) NOT NULL,	#암호화 하기위해 char(60)으로 한다.
	`authLevel` SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한레벨 (3=일반, 7=관리자)', # 1,2,3정도만 사용할것이라서 스몰인트로 사용한다.
	`name` CHAR(20) NOT NULL,																								 # DEFAULT 3 COMMENT : 디폴트값으로 3개를 설정한다.
	`nickname` CHAR(20) NOT NULL,
	cellphoneNo CHAR(20) NOT NULL,
	email CHAR(50) NOT NULL,
	delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부(0=탈퇴전, 1=탈퇴)', # Tinyint : 실체로 회원가입을할때 회원이 추가되는데 회원이 탈퇴를 할때 회원정보를 숨기는것이다.(나중에 찾을수가 없기때문이다.)ex.싸이월드
	delDate DATETIME COMMENT '탈퇴날짜'																										 # delSratus : 삭제 상태를 의미한다.
);

#회원, 테스트 데이터 생성(관리자 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`authLevel` = 7,
`name` = 'admin',
`nickname`= 'admin',
cellphoneNo = '01011111111',
email = 'inyeong1233@naver.com';

#일반회원, 테스트 데이터 생성(관리자 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = 'user1',
`name` = 'user1',
`nickname`= 'user1',
cellphoneNo = '01011111111',
email = 'inyeong1233@naver.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = 'user2',
`name` = 'user2',
`nickname`= 'user2',
cellphoneNo = '01011111111',
email = 'inyeong1233@naver.com';

SELECT * FROM `member`;


# == 필요할때 꺼내쓰시게 ==
# TRUNCATE `member`;
# DROP TABLE MEMBER;
# TRUNCATE `article`;
# SELECT LAST_INSERT_ID();

# 게시물 테이블에 회원정보 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER `updateDate`;
DESC article;

SELECT * FROM article; #->select하고나면 memberId가 0으로 나오는데 기존에 없던건데 추가를 했기때문에 0이 나오는것이다.

# 기존 게시물의 작성자를 2번으로 지정
UPDATE article
SET memberId = 2
WHERE memberId = 0;

# 게시판 테이블 생성(멀티 게시판)
CREATE TABLE board (
	id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	`code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice=공지사항,free1=자유게시판1,free2=자유게시판2...',
	`name` CHAR(50) NOT NULL UNIQUE COMMENT '게시판 이름',	# code와 name은 겹치면 안되기 때문에 unique로 해준다.
	delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부(0=삭제전, 1=삭제)',
	delDate DATETIME COMMENT '삭제날짜'
);

SHOW TABLES;

# 기본 게시판 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free1',
`name` = '자유';

# 게시판 테이블에 boardId 칼럼 추가

ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER memberId;

DESC article;



# 기존 게시물에 강제로 게시판 정보 넣기

# 1, 2번 테이블에 boardId 칼럼 추가
UPDATE article
SET boardId = 1
WHERE id IN(1, 2); #아이디가 1이거나 2인 경우에는 board아이디를 1로 설정하겠다는 의미

# 3번 게시물을 자유게시판 게시물로 지정
UPDATE article
SET boardId = 2
WHERE id IN(3); #아이디가 3인 경우에는 board아이디를 2로 설정하겠다는 의미

SELECT * FROM article;



# 번호의 의미가 뭐였는지 확인해보는 sql쿼리문
SELECT * FROM board WHERE id = 1;
SELECT * FROM board WHERE id = 2;

# 게시물 개수 늘리기!
/*
insert into article
(
	regDate, updateDate, memberId, boardId, title, `body`
)
select now(), NOW(), FLOOR(RAND() * 2) + 1, FLOOR(RAND() * 2) + 1, concat('제목_', rand()), CONCAT('내용_', RAND())
from article;
*/
# 몇개 추가되었는지 확인 할 수 있는것!
# select count(*) from article;


# 위의 쿼리문을 실행 할 때 마다 배수만큼 실행이 되어 늘어난다.



# mysql rand 1 to 100 : 1~100까지 랜덤 숫자값을 의미!
# CONCAT : 내용을 붙여서 넣을수 있는것.

# 0,1,2가 랜덤으로 나오는것!
# select  floor(rand() * 3);

# 1,2,3이 순서대로 나오는것!
# SELECT  FLOOR(RAND() * 3) + 1;

#===회원수를 셀때는 관리자를 빼고 계산해야한다===

# 숫자를 랜덤으로 배치가 되는것!
# select rand();
















