insert into usuario ( email, registro, senha) values ( 'email@email.br' ,'2021-02-26T03:55:15', '$2a$10$0T.lLhVYihj25.YsaqLzfeJjBgzkmqlRQbhbtV5flHpeOXCjkzPG6' );
insert into usuario ( email, registro, senha) values ( 'kaiofprates@gmail.com' ,'2021-02-26T03:55:15', '$2a$10$0T.lLhVYihj25.YsaqLzfeJjBgzkmqlRQbhbtV5flHpeOXCjkzPG6' );
insert into usuario ( email, registro, senha) values ( 'joao@gmail.com' ,'2021-02-26T03:55:15', '$2a$10$0T.lLhVYihj25.YsaqLzfeJjBgzkmqlRQbhbtV5flHpeOXCjkzPG6' );
insert into usuario ( email, registro, senha) values ( 'antonio@gmail.com' ,'2021-02-26T03:55:15', '$2a$10$0T.lLhVYihj25.YsaqLzfeJjBgzkmqlRQbhbtV5flHpeOXCjkzPG6' );
insert into usuario ( email, registro, senha) values ( 'lidia@gmail.com' ,'2021-02-26T03:55:15', '$2a$10$0T.lLhVYihj25.YsaqLzfeJjBgzkmqlRQbhbtV5flHpeOXCjkzPG6' );

insert into categoria (nome) values ( 'tecnologia');
insert into produto ( descricao, nome, quantidade, valor, categoria_id, usuario_id)  values (' produto legal' , 'tenis', 3, 10.89, 1, 1);
insert into produto ( descricao, nome, quantidade, valor, categoria_id, usuario_id)  values (' produto legal' , 'tenis', 3, 10.89, 1, 2);
insert into caracteristica (descricao, nome, produto_id) values ( 'adidas' ,'marca', 1);
insert into caracteristica (descricao, nome, produto_id) values ( 'medio' ,'Tamanho', 1);
insert into caracteristica (descricao, nome, produto_id) values ( 'Branca' ,'Cor', 1);
insert into imagem_produto (link, produto_id ) values ( 'http://firebase.io/imagem.jgp' , 1);
insert into imagem_produto (link, produto_id ) values ( 'http://firebase.io/imagem1.jgp' , 1);
insert into imagem_produto (link, produto_id ) values ( 'http://firebase.io/imagem2.jgp' , 1);
insert into pergunta (criacao, titulo, perguntador_id, produto_id) values ('2021-03-01T19:40:07.063604' , 'Qual o preço?' , 2, 1);
insert into pergunta (criacao, titulo, perguntador_id, produto_id) values ('2021-03-01T19:40:07.063604' , 'é semi novo?' , 3, 1);
insert into pergunta (criacao, titulo, perguntador_id, produto_id) values ('2021-03-01T19:40:07.063604' , 'entrega por sedex até sexta?' , 4, 1);
insert into pergunta (criacao, titulo, perguntador_id, produto_id) values ('2021-03-01T19:40:07.063604' , 'tem em um tamanho menor?' , 5, 1);