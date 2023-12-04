use tccacai;
show tables;

describe atendimento;
describe cliente;
describe menu;
describe perfil;
describe perfil_menu;
describe produto;
describe usuario;
describe venda;
describe venda_produto;

insert into perfil (nome, dataCadastro, status) values ("Administrador", "2023-11-21", 1);
insert into perfil (nome, dataCadastro, status) values ("Gerente", "2023-11-21", 1);
insert into perfil (nome, dataCadastro, status) values ("Atendente", "2023-11-21", 1);
select * from perfil;
update perfil set nome = "Funcionário", dataCadastro = "2023-11-21", status = 1 where idPerfil = 4;


insert into usuario (nome, login, senha, status, idPerfil) values ("Administrador", "admin", "admin", 1, 1);
select * from usuario;

insert into menu (nome, link, exibir, status) values ("Home", "index.jsp", 1, 1);
insert into menu (nome, link, exibir, status) values ("Menus", "gerenciarMenu?acao=listar", 1, 1);
insert into menu (nome, link, exibir, status) values ("Perfis", "gerenciarPerfil?acao=listar", 1, 1);
insert into menu (nome, link, exibir, status) values ("Usuários", "gerenciarUsuario?acao=listar", 1, 1);
insert into menu (nome, link, exibir, status) values ("Vendas", "gerenciarVenda?acao=listar", 1, 1);
insert into menu (nome, link, exibir, status) values ("Clientes", "gerenciarCliente?acao=listar", 1, 1);
insert into menu (nome, link, exibir, status) values ("Produtos", "gerenciarProduto?acao=listar", 1, 1);
insert into menu (nome, link, exibir, status) values ("Atendimentos", "gerenciarAtendimento?acao=listar", 1, 1);
select * from menu;

select * from menu_perfil;
insert into menu_perfil (idMenu, idPerfil) values (1, 1);
select m.idMenu, m.nome, m.link, m.exibir, m.status from menu_perfil as mp, menu as m where mp.idMenu = m.idMenu and mp.idPerfil = 1;

SELECT c.idCliente, a.idAtendimento, u.idUsuario, v.idVenda, v.dataVenda, v.precoTotal, v.status FROM venda v INNER JOIN 
cliente c ON c.idCliente = v.idCliente INNER JOIN 
atendimento a ON a.idAtendimento = v.idAtendimento INNER JOIN 
usuario u ON u.idUsuario = v.idUsuario;
