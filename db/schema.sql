# ==db정보 저장==

# 계정생성 아이디/비밀번호
GRANT ALL PRIVILEGES ON *.* TO sbsst@`%` IDENTIFIED BY 'sbs123414';

# DB 생성
DROP DATABASE IF EXISTS sb_app_2022_12-03;
CREATE DATABASE sb_app_2022_12-03;
USE sb_app_2022_12-03;

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

