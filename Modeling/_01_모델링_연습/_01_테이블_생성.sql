
/* Drop Tables */

DROP TABLE EMP CASCADE CONSTRAINTS;




/* Create Tables */

CREATE TABLE EMP
(
	emp_no varchar2(4) NOT NULL,
	emp_name varchar2(100),
	emp_social_num varchar2(13),
	emp_addr varchar2(200),
	emp_post_num varchar2(12),
	emp_phone varchar2(20),
	PRIMARY KEY (emp_no)
);



