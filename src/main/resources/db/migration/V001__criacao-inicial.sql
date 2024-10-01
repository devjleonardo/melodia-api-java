CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    data_ultimo_login TIMESTAMPTZ,
    foto_perfil_url VARCHAR(255),
    genero_usuario VARCHAR(255) NOT NULL,
    plano VARCHAR(255) NOT NULL DEFAULT 'GRATUITO'
);

CREATE TABLE artista (
    id BIGSERIAL PRIMARY KEY,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    nome VARCHAR(255) NOT NULL,
    execucoes BIGINT DEFAULT 0 NOT NULL,
    foto_url VARCHAR(255)
);

CREATE TABLE genero_musical (
    id BIGSERIAL PRIMARY KEY,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE musica (
    id BIGSERIAL PRIMARY KEY,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    titulo TEXT,
    album VARCHAR(100),
    duracao INT,
    capa_url TEXT,
    audio_url TEXT,
    data_lancamento DATE,
    popularidade INT,
    artista_id BIGINT NOT NULL,
    genero_musical_id BIGINT,
    CONSTRAINT fk_artista FOREIGN KEY (artista_id) REFERENCES artista(id),
    CONSTRAINT fk_genero_musical FOREIGN KEY (genero_musical_id) REFERENCES genero_musical(id)
);

CREATE TABLE tag (
    id BIGSERIAL PRIMARY KEY,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE musica_tags (
    musica_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    CONSTRAINT fk_musica_tag FOREIGN KEY (musica_id) REFERENCES musica(id),
    CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tag(id),
    PRIMARY KEY (musica_id, tag_id)
);

CREATE TABLE playlist (
    id BIGSERIAL PRIMARY KEY,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(256),
    data_atualizacao DATE,
    publica BOOLEAN,
    imagem_url VARCHAR(255),
    numero_seguidores INT DEFAULT 0,
    numero_musicas INT,
    duracao_total INT,
    colaborativa BOOLEAN,
    genero_musical_id BIGINT,
    usuario_id BIGINT,
    CONSTRAINT fk_genero FOREIGN KEY (genero_musical_id) REFERENCES genero_musical(id),
    CONSTRAINT fk_usuario_playlist FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE playlists_musicas (
    playlist_id BIGINT NOT NULL,
    musica_id BIGINT NOT NULL,
    CONSTRAINT fk_playlist_musica FOREIGN KEY (playlist_id) REFERENCES playlist(id),
    CONSTRAINT fk_musica_playlist FOREIGN KEY (musica_id) REFERENCES musica(id),
    PRIMARY KEY (playlist_id, musica_id)
);

CREATE TABLE participantes_playlist (
    id BIGSERIAL PRIMARY KEY,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    playlist_id BIGINT,
    usuario_id BIGINT,
    nivel_participacao VARCHAR(255),
    CONSTRAINT fk_playlist_participantes FOREIGN KEY (playlist_id) REFERENCES playlist(id),
    CONSTRAINT fk_usuario_participantes FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE preferencias_musicais (
    id BIGSERIAL PRIMARY KEY,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    genero VARCHAR(100) NOT NULL,
    data_inicio DATE,
    ranking_popularidade INT DEFAULT 0 NOT NULL,
    usuario_id BIGINT,
    CONSTRAINT fk_usuario_preferencias FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE historico_acao (
    id BIGSERIAL PRIMARY KEY,
    estado_emocional VARCHAR(255),
    data_acao TIMESTAMPTZ,
    usuario_id BIGINT NOT NULL,
    tipo_acao VARCHAR(255),
    musica_id BIGINT,
    playlist_id BIGINT,
    CONSTRAINT fk_usuario_historico FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT fk_musica_historico FOREIGN KEY (musica_id) REFERENCES musica(id),
    CONSTRAINT fk_playlist_historico FOREIGN KEY (playlist_id) REFERENCES playlist(id)
);

CREATE TABLE historico_estado_emocional_usuario (
    id BIGSERIAL PRIMARY KEY,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    usuario_id BIGINT NOT NULL,
    estado_emocional VARCHAR(255) NOT NULL,
    data TIMESTAMPTZ,
    CONSTRAINT fk_usuario_estado_emocional FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE recomendacao_musica (
    id BIGSERIAL PRIMARY KEY,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    usuario_id BIGINT,
    musica_id BIGINT,
    base_recomendacao VARCHAR(255),
    data_recomendacao TIMESTAMPTZ,
    CONSTRAINT fk_usuario_recomendacao FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT fk_musica_recomendacao FOREIGN KEY (musica_id) REFERENCES musica(id)
);

CREATE TABLE relacionamento_usuario (
    id BIGSERIAL PRIMARY KEY,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    seguidor_id BIGINT,
    seguido_id BIGINT,
    data_inicio TIMESTAMPTZ,
    CONSTRAINT fk_seguidor FOREIGN KEY (seguidor_id) REFERENCES usuario(id),
    CONSTRAINT fk_seguido FOREIGN KEY (seguido_id) REFERENCES usuario(id)
);

CREATE TABLE usuario_preferencias_notificacoes (
    usuario_id BIGINT NOT NULL,
    tipo_notificacao VARCHAR(255),
    CONSTRAINT fk_usuario_notificacao FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE usuario_configuracoes_privacidade (
    usuario_id BIGINT NOT NULL,
    configuracao_privacidade VARCHAR(255),
    CONSTRAINT fk_usuario_privacidade FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE artista_ranking (
    id BIGSERIAL PRIMARY KEY,
    pontuacao DOUBLE PRECISION NOT NULL,
    artista_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_artista_ranking FOREIGN KEY (artista_id) REFERENCES artista(id)
);

CREATE TABLE usuario_artista_ranking (
    id BIGSERIAL PRIMARY KEY,
    pontuacao DOUBLE PRECISION NOT NULL,
    usuario_id BIGINT NOT NULL,
    artista_id BIGINT NOT NULL,
    CONSTRAINT fk_usuario_artista FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT fk_artista_usuario_artista FOREIGN KEY (artista_id) REFERENCES artista(id)
);
