DELETE FROM musica_tags;
DELETE FROM playlists_musicas;
DELETE FROM participantes_playlist;
DELETE FROM preferencias_musicais;
DELETE FROM historico_acao;
DELETE FROM historico_estado_emocional_usuario;
DELETE FROM recomendacao_musica;
DELETE FROM relacionamento_usuario;
DELETE FROM usuario_preferencias_notificacoes;
DELETE FROM usuario_configuracoes_privacidade;
DELETE FROM musica;
DELETE FROM playlist;
DELETE FROM tag;
DELETE FROM artista;
DELETE FROM genero_musical;
DELETE FROM usuario;

ALTER SEQUENCE usuario_id_seq RESTART WITH 1;

INSERT INTO usuario (email, senha, nome, data_nascimento, data_ultimo_login, foto_perfil_url, genero_usuario, plano)
VALUES (
    'vitor.luiz@gmail.com', '123', 'Vitor Luiz', '1995-08-15', CURRENT_TIMESTAMP,
    'https://encrypted-tbn3.gstatic.com/licensed-image?q=tbn:ANd9GcQsbOlRto0TLromWWlNIHKcbl3ggbBfGVa7pEnnSKQryd31xiKYF6BPbhbwJJJo2lc0b7e_qZmtXphMEZw',
    'MASCULINO', 'PREMIUM'
);
