
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













