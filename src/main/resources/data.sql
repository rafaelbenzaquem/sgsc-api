insert into cliente(nome,email,cpf_ou_cnpj,tipo) values ('Maria Silva','maria@gmail.com','36378912377',0);
insert into cliente(nome,email,cpf_ou_cnpj,tipo) values ('Rafael Benzaquem Neto','rafael@gmail.com','88754181364',0);

insert into telefone(cliente_id,numero) values (1,'27363323');
insert into telefone(cliente_id,numero) values (1,'93838393');
insert into telefone(cliente_id,numero) values (2,'981166350');

insert into endereco(logradouro,numero,complemento,bairro,cep,cidade,cliente_id)
values('rua flores','300','apto 203','Jardim Santarém','36220634','Santarém',1);
insert into endereco(logradouro,numero,complemento,bairro,cep,cidade,cliente_id)
values('Avenida Otaviano de matos','105','sala 800','Centro','38777012','Santarém',1);

insert into endereco(logradouro,numero,complemento,bairro,cep,cidade,cliente_id)
values('Rua Vinte e Quatro de Outubro','2284',null,'Aldeia','68040010','Santarém',2);
