insert into usuario ( email, registro, senha) values ( 'email@email.br' ,'2021-02-26T03:55:15', '$2a$10$0T.lLhVYihj25.YsaqLzfeJjBgzkmqlRQbhbtV5flHpeOXCjkzPG6' );
insert into categoria (nome) values ( 'tecnologia');
insert into produto ( descricao, nome, quantidade, valor, categoria_id, usuario_id)  values (' produto legal' , 'tenis', 3, 10.89, 1, 1);
insert into caracteristica (descricao, nome, produto_id) values ( 'adidas' ,'marca', 1);