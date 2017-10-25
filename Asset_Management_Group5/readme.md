TABLE-USER_MASTER
_____________________________________________________________________________________

CREATE TABLE USER_MASTER(USERID VARCHAR2(20) PRIMARY KEY,USERNAME VARCHAR2(50),USERPASSWORD VARCHAR2(30),USERTYPE VARCHAR2(30),EMPNO NUMBER, FOREIGN KEY (EMPNO) REFERENCES EMPLOYEE(EMPNO));

insert into user_master values ('CG101','SHWETA TRIPATHI','CapG@123','USER',9001);
insert into user_master values ('CG102','ROHIT KHANDAGALE','CapG@123','USER',9002);
insert into user_master values ('CG103','SNEHAL BAGADE','CapG@123','USER',9003);
insert into user_master values ('CG104','SAYANTANI','CapG@123','USER',9004);
insert into user_master values ('CG105','VINAYAK KEDIA','CapG@123','USER',9005);
insert into user_master values ('CG106','PRANJALI GUPTA','CapG@123','USER',9006);
insert into user_master values ('CG107','RAHUL MUKHERJEE','CapG@123','USER',9007);
_____________________________________________________________________________________



TABLE-EMPLOYEE
_____________________________________________________________________________________
CREATE TABLE EMPLOYEE(EMPNO NUMBER PRIMARY KEY,ENAME VARCHAR2(50),JOB VARCHAR2(50),MGR NUMBER,HIREDATE DATE,DEPT_ID NUMBER,FOREIGN KEY (DEPT_ID) REFERENCES DEPARTMENT(DEPT_ID));

insert into employee values (9001, 'SHWETA TRIPATHI', 'SR MANAGER', NULL, TO_DATE ('02/12/2013','dd/mm/yyyy'), 114) ;

insert into employee values (9002, 'ROHIT KHANDAGALE', 'BU MANAGER', 9001, TO_DATE ('03/11/2015','dd/mm/yyyy'), 114) ;

insert into employee values (9003, 'SNEHAL BAGADE', 'SR ANALYST', 9002, TO_DATE ('10/06/2016','dd/mm/yyyy'), 111) ;

insert into employee values (9004, 'SAYANTANI', 'TESTER', 9002, TO_DATE ('19/09/2016','dd/mm/yyyy'), 112) ;

insert into employee values (9005, 'VINAYAK KEDIA', 'CO-ORDINATOR', 9002, TO_DATE ('09/05/2015','dd/mm/yyyy'), 113) ;

insert into employee values (9006, 'PRANJALI GUPTA', 'SUPERVISOR', 9005, TO_DATE ('03/04/2016','dd/mm/yyyy'), 113) ;

insert into employee values (9007, 'RAHUL MUKHERJEE', 'ANALYST', 9003, TO_DATE ('03/04/2016','dd/mm/yyyy'), 111) ;
_____________________________________________________________________________________

TABLE-DEPARTMENT

CREATE TABLE DEPARTMENT(DEPT_ID INT PRIMARY KEY, DEPT_NAME VARCHAR2(50));


insert into department values (111,'DEVELOPMENT');
insert into department values (112,'TESTING');
insert into department values (113,'MAINTENANCE');
insert into department values (114,'BUSINESS UNIT');
_____________________________________________________________________________________

TABLE-ASSET

CREATE TABLE ASSET( ASSETID NUMBER PRIMARY KEY, ASSETNAME VARCHAR2(25), ASSETDESC VARCHAR2(25), QUANTITY NUMBER, ALLOCATEDQUANTITY NUMBER);

insert into asset (1,'LAPTOP','DELL',50,0);
insert into asset (2,'ANTIVIRUS','BITDEFENDER',50,0);
insert into asset (3,'HARD DISK','SEAGATE',30,0);
insert into asset (4,'VCLOUD SUITE','VMWARE',20,0);
insert into asset (5,'PRINTER','HP',10,0);

CREATE SEQUENCE SEQ_ASSET_ID START WITH 6 INCREMENT BY 1 NOCACHE NOCYCLE;
_____________________________________________________________________________________

TABLE-ASSET_ALLOCATION

CREATE TABLE ASSET_ALLOCATION(ALLOCATIONID NUMBER PRIMARY KEY, ASSETID NUMBER, EMPNO NUMBER, ALLOCATION_DATE DATE, RELEASE_DATE DATE, STATUS VARCHAR2(50),MGRID NUMBER,
FOREIGN KEY (ASSETID) REFERENCES ASSET(ASSETID),FOREIGN KEY (EMPNO) REFERENCES EMPLOYEE(EMPNO));

CREATE SEQUENCE SEQ_ALLOCID START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
______________________________________________________________________________________
