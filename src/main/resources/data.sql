insert into usuario(nome,login,senha,tipo)
values('Administrador','admin','admin',2);

insert into servico(nome,preco) values('formatação de computador',100.00);
insert into servico(nome,preco) values('manutenção de impressora',200.00);
insert into servico(nome,preco) values('panfletagem',200.00);
insert into servico(nome,preco) values('declaração de imposto de renda',200.00);
insert into servico(nome,preco) values('Internet fibra 30Mb',150.00);

insert into categoria(nome) values ('telefonia');
insert into categoria(nome) values ('contabilidade');
insert into categoria(nome) values ('informática');
insert into categoria(nome) values ('marketing');
insert into categoria(nome) values ('decoração');
insert into categoria(nome) values ('estética');


insert into servico_categoria(servico_id,categoria_id) values(1,3);
insert into servico_categoria(servico_id,categoria_id) values(2,3);
insert into servico_categoria(servico_id,categoria_id) values(3,4);
insert into servico_categoria(servico_id,categoria_id) values(4,2);
insert into servico_categoria(servico_id,categoria_id) values(5,1);
insert into servico_categoria(servico_id,categoria_id) values(5,3);


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


insert into contrato(instante,cliente_id) values ('2019-09-30 10:58:00.000',1);
insert into pagamento(contrato_id,estado)values (1,2);
insert into pagamento_com_cartao(numero_parcelas,contrato_id)values (6,1);

insert into contrato(instante,cliente_id) values ('2019-10-10 15:35:00.000',1);
insert into pagamento(contrato_id,estado)values (2,1);
insert into pagamento_com_boleto(data_vencimento,contrato_id)values ('2019-12-20',2);

insert into item_contrato(desconto,preco,quantidade,servico_id,contrato_id) values (0.00   ,100.00 ,1, 1, 1);
insert into item_contrato(desconto,preco,quantidade,servico_id,contrato_id) values (0.00   ,400.00   ,2, 3, 1);
insert into item_contrato(desconto,preco,quantidade,servico_id,contrato_id) values (100.00 ,800.00  ,3, 2, 2);
