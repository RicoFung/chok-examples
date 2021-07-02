-- Create table
create table TB_DEMO
(
  tc_rowid NUMBER(18),
  tc_code  VARCHAR2(100),
  tc_name  VARCHAR2(100)
);
-- Create/Recreate indexes 
create unique index PK_TB_DEMO on TB_DEMO (TC_ROWID);

-- Create sequence 
create sequence SEQ_TB_DEMO
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1;

-- 查询
select t.*, t.rowid from TB_DEMO t;
-- 清空
-- truncate table TB_DEMO;
-- 插入测试数据
begin
  for j in 1 .. 100 loop
    insert into tb_demo
    values
      (seq_tb_demo.nextval, 'code' || j, 'name' || j);
  end loop;
end;
