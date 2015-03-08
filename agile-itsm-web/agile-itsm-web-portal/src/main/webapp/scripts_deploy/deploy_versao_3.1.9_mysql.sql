set sql_safe_updates = 0;

-- INÍCIO - BRUNO CARVALHO DE AQUINO 26/12/2013

ALTER TABLE grupo ADD permitesuspensaoreativacao char(1);

-- FIM - BRUNO CARVALHO DE AQUINO

set sql_safe_updates = 1;
