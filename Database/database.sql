CREATE SCHEMA UninaBioGarden;

CREATE DOMAIN dom_username AS VARCHAR(20) NOT NULL 
CHECK (VALUE ~ '^[a-zA-Z][a-zA-Z0-9_-]{5,19}$');

-- max length is 60 to allow hashing in the future
CREATE DOMAIN dom_password AS VARCHAR(60) NOT NULL 
CHECK (VALUE ~ '^[a-zA-Z0-9_!@#$%^&*+=\\?-]{1,60}$');

CREATE DOMAIN dom_email AS VARCHAR(254) NOT NULL UNIQUE
CHECK (VALUE ~ '^[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$');

CREATE DOMAIN dom_name AS VARCHAR(30) NOT NULL 
CHECK (VALUE <> '');

CREATE DOMAIN dom_num_tel AS VARCHAR(10)
CHECK (VALUE IS NULL OR VALUE ~ '^[0-9]{10}$');

CREATE TABLE proprietario (
  username    dom_username   PRIMARY KEY, 
  password    dom_password,
  email       dom_email,
  nome        dom_name,
  cognome     dom_name,
  bday        DATE            NOT NULL,
  nazionalita VARCHAR(30)     NOT NULL  CHECK (nazionalita <> ''),
  num_tel     dom_num_tel,
  residenza   VARCHAR(50)     CHECK (residenza IS NULL OR residenza <> '')
);

CREATE TABLE coltivatore (
  username    dom_username   PRIMARY KEY, 
  password    dom_password,
  email       dom_email,
  nome        dom_name,
  cognome     dom_name,
  bday        DATE            NOT NULL,
  nazionalita VARCHAR(30)     NOT NULL  CHECK (nazionalita <> ''),
  num_tel     dom_num_tel,
  residenza   VARCHAR(50)     CHECK (residenza IS NULL OR residenza <> '')
);

CREATE TABLE lotto (
  id_lotto      INT                       GENERATED ALWAYS AS IDENTITY,
  indirizzo     VARCHAR(80)               NOT NULL  CHECK (indirizzo <> ''),
  codice_lotto  VARCHAR(10)               NOT NULL  CHECK (codice_lotto ~ '^[0-9]+$'),
  estensione    DOUBLE PRECISION          NOT NULL  CHECK (estensione >= 0),
  nome_orto     VARCHAR(80)               NOT NULL  CHECK (indirizzo <> ''),
  username_prop dom_username              NOT NULL REFERENCES Proprietario(username), 

  UNIQUE (indirizzo, codice_lotto),

  PRIMARY KEY (id_lotto)
);

CREATE TABLE progetto (
  id_progetto     INT               GENERATED ALWAYS AS IDENTITY,
  nome            VARCHAR(30)       NOT NULL CHECK (nome <> ''),
  data_inizio     DATE              NOT NULL CHECK (data_inizio <= data_fine),
  data_fine       DATE,             
  descrizione     VARCHAR(200),
  username_prop   dom_username      NOT NULL REFERENCES proprietario(username) ON DELETE CASCADE,
  id_lotto        INT               NOT NULL REFERENCES lotto(id_lotto) ON DELETE CASCADE,

  -- un proprietario non puo avere progetti con lo stesso nome che iniziano nella stessa data
  UNIQUE (nome, data_inizio, username_prop),

  PRIMARY KEY (id_progetto)
);
