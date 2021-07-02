create or replace procedure p_procdemo_query_act(tcCode        in varchar2,
                                                 tcName        in varchar2,
                                                 page          in number,
                                                 pagesize      in number,
                                                 v_out_data    out varchar2,
                                                 v_out_success out varchar2,
                                                 v_out_msg     out varchar2,
                                                 v_out_list1   out sys_refcursor,
                                                 v_out_list2   out sys_refcursor) is
  /*
  -- jun 20200514
  -- 模块功能：demo演示_翻页查询
  */
  --定义sql语句变量
  v_sql           varchar2(10000);
  v_sql_select    varchar2(3000);
  v_sql_fromWhere varchar2(5000);
  v_sql_order     varchar2(1000);
begin

  v_out_success := 'true';
  v_out_msg     := '';
  v_out_data    := tcCode || '|' || tcName || '|' || page || '|' ||
                   pagesize;

  v_sql := 'select t.tc_rowid, t.tc_code, t.tc_name from tb_demo t';
  open v_out_list1 for v_sql;
  v_sql := 'select t.tc_rowid, t.tc_code from tb_demo t';
  open v_out_list2 for v_sql;
end;
