CREATE SCHEMA UninaBioGarden;

CREATE DOMAIN type_username AS VARCHAR(30);
CREATE DOMAIN type_password AS VARCHAR(30);
CREATE DOMAIN type_name AS VARCHAR(30);
CREATE DOMAIN type_email AS VARCHAR(50);

CREATE TABLE Proprietario (
  username    type_username   PRIMARY KEY, 
  password    type_password   NOT NULL,
  nome        type_name       NOT NULL, 
  cognome     type_name       NOT NULL,
  bday        DATE            NOT NULL,
  email       type_email      NOT NULL UNIQUE,
  nazionalita VARCHAR(30)     NOT NULL,
  num_tel     VARCHAR(10),
  residenza   VARCHAR(50)
);

CREATE TABLE Coltivatore (
  username    type_username   PRIMARY KEY, 
  password    type_password   NOT NULL,
  nome        type_name       NOT NULL, 
  cognome     type_name       NOT NULL,
  bday        DATE            NOT NULL,
  email       type_email      NOT NULL UNIQUE,
  nazionalita VARCHAR(30)     NOT NULL,
  num_tel     VARCHAR(10),
  residenza   VARCHAR(50)
);

CREATE TABLE Lotto (
  id_lotto    INT           GENERATED ALWAYS AS IDENTITY,
  indirizzo   VARCHAR(50)   NOT NULL,
  codice_lotto INT          NOT NULL,
  UNIQUE (indirizzo, codice_lotto),
  estensione  FLOAT         NOT NULL,
  nome_orto   VARCHAR(30)   NOT NULL,
  username_prop type_username NOT NULL REFERENCES Proprietario, 

  PRIMARY KEY (id_lotto),
);

CREATE TABLE Coltura (
  id_coltura        INT             GENERATED ALWAYS AS IDENTITY,
  nome              type_name       NOT NULL,
  data_semina       DATE            NOT NULL,
  descrizione       VARCHAR(200),
  tempo_maturazione INT             NOT NULL, -- numero giorni per la maturazione?
  id_lotto          INT             NOT NULL REFERENCES Lotto,
  UNIQUE (id_lotto, nome), -- colture sullo stesso orto non hanno lo stesso nome 

  PRIMARY KEY (id_coltura),
);

CREATE TYPE StatoAttivita AS ENUM (
  'PIANIFICATO',
  'IN CORSO',
  'COMPLETATO',
);

CREATE TYPE TipoAttivita AS ENUM (
  'RACCOLTA',
  'SEMINA',
  'IRRIGAZIONE',
  'PREPARAZIONE TERRENO',
  'POTATURA',
  'FERTILIZZAZIONE',
  'PERSONALIZZATA'
);

CREATE TABLE Attivita (
  id_attivita     INT               GENERATED ALWAYS AS IDENTITY,
  nome            type_name         NOT NULL,
  data_inizio     DATE              NOT NULL,
  data_fine       DATE,
  descrizione     VARCHAR(200),
  stato           StatoAttivita    NOT NULL,
  tipo            TipoAttivita,
  qty_effettiva   INT,
  qty_prevista    INT,
  id_progetto     INT               NOT NULL,
  id_coltura      INT               NOT NULL,

  PRIMARY KEY (id_attivita),
  FOREIGN KEY (id_progetto) REFERENCES Progetto(id_progetto),
  FOREIGN KEY (id_coltura)  REFERENCES Coltura(id_coltura)
);

CREATE TABLE Progetto (
  id_progetto     INT               GENERATED ALWAYS AS IDENTITY,
  nome            type_name         NOT NULL,
  data_inizio     DATE              NOT NULL,
  data_fine       DATE,
  descrizione     VARCHAR(200),
  username_prop   type_username     NOT NULL,

  PRIMARY KEY (id_progetto),
  FOREIGN KEY (username_prop) REFERENCES Proprietario(username)
);

CREATE TABLE SvoltaDa (
  username_col    type_username     REFERENCES Coltivatore,
  id_attivita     INT               REFERENCES Attivita,

  PRIMARY KEY (username_col, id_attivita)
);