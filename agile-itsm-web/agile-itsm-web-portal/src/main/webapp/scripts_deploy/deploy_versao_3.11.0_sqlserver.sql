
-- INICIO - RODRIGO PECCI ACORSE - 10/09/2014

declare @table_name nvarchar(256)
declare @col_name nvarchar(256)
declare @Command  nvarchar(1000)

set @table_name = N'ocorrenciaproblema'
set @col_name = N'tempogasto'

select @Command = 'ALTER TABLE ' + @table_name + ' drop constraint ' + d.name
 from sys.tables t   
  join    sys.default_constraints d       
   on d.parent_object_id = t.object_id  
  join    sys.columns c      
   on c.object_id = t.object_id      
    and c.column_id = d.parent_column_id
 where t.name = @table_name
  and c.name = @col_name

execute (@Command);

alter table ocorrenciaproblema alter column tempogasto integer;

declare @table_name nvarchar(256)
declare @col_name nvarchar(256)
declare @Command  nvarchar(1000)

set @table_name = N'ocorrencialiberacao'
set @col_name = N'tempogasto'

select @Command = 'ALTER TABLE ' + @table_name + ' drop constraint ' + d.name
 from sys.tables t   
  join    sys.default_constraints d       
   on d.parent_object_id = t.object_id  
  join    sys.columns c      
   on c.object_id = t.object_id      
    and c.column_id = d.parent_column_id
 where t.name = @table_name
  and c.name = @col_name

execute (@Command);

alter table ocorrencialiberacao alter column tempogasto integer;

declare @table_name nvarchar(256)
declare @col_name nvarchar(256)
declare @Command  nvarchar(1000)

set @table_name = N'ocorrenciamudanca'
set @col_name = N'tempogasto'

select @Command = 'ALTER TABLE ' + @table_name + ' drop constraint ' + d.name
 from sys.tables t   
  join    sys.default_constraints d       
   on d.parent_object_id = t.object_id  
  join    sys.columns c      
   on c.object_id = t.object_id      
    and c.column_id = d.parent_column_id
 where t.name = @table_name
  and c.name = @col_name

execute (@Command);

alter table ocorrenciamudanca alter column tempogasto integer;

declare @table_name nvarchar(256)
declare @col_name nvarchar(256)
declare @Command  nvarchar(1000)

set @table_name = N'ocorrenciasolicitacao'
set @col_name = N'tempogasto'

select @Command = 'ALTER TABLE ' + @table_name + ' drop constraint ' + d.name
 from sys.tables t   
  join    sys.default_constraints d       
   on d.parent_object_id = t.object_id  
  join    sys.columns c      
   on c.object_id = t.object_id      
    and c.column_id = d.parent_column_id
 where t.name = @table_name
  and c.name = @col_name

execute (@Command);

alter table ocorrenciasolicitacao alter column tempogasto integer;

alter table justificativaparecer   add column datafim date default null;

alter table justificativaparecer add column datainicio date default null;

-- FIM - RODRIGO PECCI ACORSE - 10/09/2014