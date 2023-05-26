/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2023/5/26 14:03:13                           */
/*==============================================================*/


drop table if exists paper;

drop table if exists paper_question;

drop table if exists papper_answer;

drop table if exists question;

drop table if exists student;

drop table if exists student_papper;

drop table if exists teacher;

/*==============================================================*/
/* Table: paper                                                 */
/*==============================================================*/
create table paper
(
   id                   int not null auto_increment comment '试卷自增主键',
   total_score          int not null comment '试卷总分',
   primary key (id)
);

alter table paper comment '试卷类';

/*==============================================================*/
/* Table: paper_question                                        */
/*==============================================================*/
create table paper_question
(
   id                   int not null auto_increment comment '试卷-题目表自增主键',
   paper_id             int not null comment '试卷编号',
   question_id          int not null comment '题目编号',
   score                int not null comment '分值',
   primary key (id)
);

alter table paper_question comment '试卷-题目表（组卷）';

/*==============================================================*/
/* Table: papper_answer                                         */
/*==============================================================*/
create table papper_answer
(
   id                   int not null auto_increment comment '答卷-答案表自增主键',
   student_papper_id    int not null comment '学生答卷id',
   question_id          int not null comment '题目id',
   answer               varchar(64) not null comment '试卷上填写的题目答案',
   score                int default 0 comment '题目得分',
   primary key (id)
);

alter table papper_answer comment '答卷-答案表';

/*==============================================================*/
/* Table: question                                              */
/*==============================================================*/
create table question
(
   id                   int not null auto_increment comment '题目表自增主键',
   content              varchar(64) not null comment '题目内容',
   type                 varchar(64) not null comment '题目类型',
   correct_answer       varchar(64) not null comment '正确答案（图片请填写路径）',
   knowledge_point      varchar(64) not null comment '知识点',
   chapter              varchar(64) not null comment '章节',
   error_rate           varchar(64) not null comment '易错点',
   primary key (id)
);

alter table question comment '题目表';

/*==============================================================*/
/* Table: student                                               */
/*==============================================================*/
create table student
(
   id                   int not null auto_increment comment '学生表自增主键（学号）',
   name                 varchar(64) not null comment '学生姓名',
   password             varchar(64) not null comment '学生密码',
   primary key (id)
);

alter table student comment '学生表';

/*==============================================================*/
/* Table: student_papper                                        */
/*==============================================================*/
create table student_papper
(
   id                   int not null auto_increment comment '学生答卷表自增主键',
   student_id           int not null comment '学生学号',
   score                int not null comment '答卷总分',
   answer_papper_id     int not null comment '答卷编号',
   primary key (id)
);

alter table student_papper comment '学生答卷表';

/*==============================================================*/
/* Table: teacher                                               */
/*==============================================================*/
create table teacher
(
   id                   int not null auto_increment comment '教师表自增主键（教师编号）',
   name                 varchar(64) not null comment '教师姓名',
   password             varchar(64) not null comment '教师密码',
   primary key (id)
);

alter table teacher comment '教师表';

