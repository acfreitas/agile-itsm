alter table 'projetos' add column 'idprojetopai' int(11) null,
  add constraint 'fk_idprojetopai'                                                   
  foreign key ('idprojetopai' )                                                      
  references 'citsmart_homologacao'.'projetos' ('idprojeto' )                        
  on delete no action                                                                
  on update no action                                                                
, add index 'fk_idprojetopai_idx' ('idprojetopai' asc) ;

alter table 'limitealcada' 
change column 'limitevaloritem' 'limiteitemusointerno' decimal(11,2) null default null ,
change column 'limitevalormensal' 'limitemensalusointerno' decimal(11,2) null default null  ,
add column 'limiteitematendcliente' decimal(11,2) null  default null, 
add column 'limitemensalatendcliente' decimal(11,2) null   default null;
