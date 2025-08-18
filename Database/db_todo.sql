
-- TODO


-- CREATE TABLE Coltura (
--   id_coltura        INT             GENERATED ALWAYS AS IDENTITY,
--   nome              dom_name       NOT NULL,
--   data_semina       DATE            NOT NULL, -- deve essere posteriore al progetto relativo all'orto
--   descrizione       VARCHAR(200),
--   tempo_maturazione INT             NOT NULL, -- numero giorni per la maturazione?
--   id_lotto          INT             NOT NULL REFERENCES Lotto,
--   UNIQUE (id_lotto, nome), -- colture sullo stesso orto non hanno lo stesso nome 

--   PRIMARY KEY (id_coltura),
-- );

-- CREATE TYPE StatoAttivita AS ENUM (
--   'PIANIFICATO',
--   'IN CORSO',
--   'COMPLETATO',
-- );

-- CREATE TYPE TipoAttivita AS ENUM (
--   'RACCOLTA',
--   'SEMINA',
--   'IRRIGAZIONE',
--   'PREPARAZIONE TERRENO',
--   'POTATURA',
--   'FERTILIZZAZIONE',
--   'PERSONALIZZATA'
-- );

-- CREATE DOMAIN dom_name 

-- CREATE TABLE Attivita (
--   id_attivita     INT               GENERATED ALWAYS AS IDENTITY,
--   nome            dom_name         NOT NULL,
--   data_inizio     DATE              NOT NULL,
--   data_fine       DATE,
--   descrizione     VARCHAR(200),
--   stato           StatoAttivita    NOT NULL,
--   tipo            TipoAttivita,
--   qty_effettiva   INT,
--   qty_prevista    INT,
--   id_progetto     INT               NOT NULL,
--   id_coltura      INT               NOT NULL,

--   PRIMARY KEY (id_attivita),
--   FOREIGN KEY (id_progetto) REFERENCES Progetto(id_progetto),
--   FOREIGN KEY (id_coltura)  REFERENCES Coltura(id_coltura)
-- );

-- CREATE TABLE SvoltaDa (
--   username_col    dom_username     REFERENCES Coltivatore,
--   id_attivita     INT               REFERENCES Attivita,

--   PRIMARY KEY (username_col, id_attivita)
-- );
