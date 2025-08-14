CREATE SCHEMA UninaBioGarden;

CREATE TABLE coltivatore (
  username    dom_username   PRIMARY KEY, 
  password    dom_password   NOT NULL,
  nome        dom_name       NOT NULL, 
  cognome     dom_name       NOT NULL,
  bday        DATE            NOT NULL,
  email       dom_email      NOT NULL UNIQUE,
  nazionalita VARCHAR(30)     NOT NULL,
  num_tel     VARCHAR(10),
  residenza   VARCHAR(50)
);

CREATE TABLE Coltura (
  id_coltura        INT             GENERATED ALWAYS AS IDENTITY,
  nome              dom_name       NOT NULL,
  data_semina       DATE            NOT NULL, -- deve essere posteriore al progetto relativo all'orto
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

CREATE DOMAIN dom_name 

CREATE TABLE Attivita (
  id_attivita     INT               GENERATED ALWAYS AS IDENTITY,
  nome            dom_name         NOT NULL,
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

CREATE TABLE SvoltaDa (
  username_col    dom_username     REFERENCES Coltivatore,
  id_attivita     INT               REFERENCES Attivita,

  PRIMARY KEY (username_col, id_attivita)
);

/*
Uno username può contenere
- lettere [a-z] e [A-Z]
- numeri [0-9]
- underscore [_]
- deve iniziare con una lettera
- ha una lunghezza minima di 6 caratteri e una massima di 20
*/
CREATE DOMAIN dom_username AS VARCHAR(20) CHECK (VALUE ~ '^[a-zA-z][a-zA-Z0-9_]{5,19}$');


/*
Caratteristiche di una password.
- min length: 8
- max length: 60 -- per permettere in futuro di poter usare dei metodi di hashing
Caratteri permessi:
- lettere [a-zA-Z], case sensitive
- numeri [0-9]
- caratteri speciali: [!@#$%^&*()_+-=[]{}|;':",.<>/?\]
*/
CREATE DOMAIN dom_password AS VARCHAR(60);
--max 50
-- the username should not be empty 
-- consider enforcing a min length

CREATE DOMAIN dom_name AS VARCHAR(30);
-- change to max 50 chars
-- the username should not be empty 
-- consider enforcing a min length

CREATE DOMAIN dom_email AS VARCHAR(50);
-- max 80 chars
-- the username should not be empty 
-- consider enforcing a min length

CREATE TABLE proprietario (
  username    dom_username   PRIMARY KEY, 
  password    dom_password   NOT NULL,
  nome        dom_name       NOT NULL, 
  cognome     dom_name       NOT NULL,
  bday        DATE            NOT NULL, -- consider enforcing a minimum age
  email       dom_email      NOT NULL UNIQUE,
  nazionalita VARCHAR(30)     NOT NULL,
  num_tel     VARCHAR(10),
  residenza   VARCHAR(50)
);

CREATE TABLE lotto (
  id_lotto    INT           GENERATED ALWAYS AS IDENTITY,
  indirizzo   VARCHAR(80)   NOT NULL  CHECK (indirizzo <> ''),
  codice_lotto VARCHAR(10)  NOT NULL  CHECK (codice_lotto ~ '^[0-9]+$'),
  estensione  DOUBLE PRECISION         NOT NULL  CHECK (estensione >= 0),
  nome_orto   VARCHAR(80)   NOT NULL  CHECK (indirizzo <> ''),
  username_prop dom_username NOT NULL REFERENCES Proprietario, 

  UNIQUE (indirizzo, codice_lotto),

  PRIMARY KEY (id_lotto)
);

CREATE TABLE progetto (
  id_progetto     INT               GENERATED ALWAYS AS IDENTITY,
  nome            VARCHAR(30)       NOT NULL CHECK (nome <> ''),
  data_inizio     DATE              NOT NULL CHECK (data_inizio <= data_fine),
  data_fine       DATE,             
  descrizione     VARCHAR(200),
  username_prop   dom_username     NOT NULL REFERENCES proprietario(username) ON DELETE CASCADE,
  id_lotto        INT               NOT NULL REFERENCES lotto(id_lotto) ON DELETE CASCADE,

  -- un proprietario non puo avere progetti con lo stesso nome che iniziano nella stessa data
  UNIQUE (nome, data_inizio, username_prop),

  PRIMARY KEY (id_progetto)
);

CREATE OR REPLACE FUNCTION check_progetto_validity()
RETURNS TRIGGER AS $$
DECLARE 
  v_lotto_username_prop VARCHAR;
BEGIN 

  IF OLD IS NULL THEN -- solo per inserimento
    IF NEW.data_fine IS NOT NULL THEN
      -- non puoi inserire un progetto terminato
      RAISE EXCEPTION 'La data di fine per un nuovo progetto non può essere inserita';
    END IF;
  ELSE                -- solo per update
    /*
    Se la data di fine è stata settata, questa può solo essere posticipata
    in particolare non può essere settata nuovamente a NULL 
    (il progetto non può riprendere dopo che è finito, questo comporterebbe riassegnargli un lotto e non è supportato) 
    */
    IF OLD.data_fine IS NOT NULL AND NEW.data_fine <= OLD.data_fine THEN
      RAISE EXCEPTION 'La data fine di un progetto può solo essere posticipata';
    ELSE IF OLD.data_fine IS NOT NULL AND NEW.data_fine IS NULL THEN 
      RAISE EXCEPTION 'Un progetto terminato non può ricominciare';
    END IF;
  END IF;

  -- inserimento AND update

  IF EXISTS ( -- non può esistere un progetto sullo stesso lotto se il lotto ne ospita un altro che è in corso
    SELECT 1
    FROM progetto
    WHERE id_lotto=NEW.id_lotto
      AND data_fine IS NULL 
      AND id_progetto <> NEW.id_progetto -- per gli update
  ) THEN 
    RAISE EXCEPTION 'Esiste già un progetto in corso sul lotto selezionato';
  END IF;

  -- il lotto referenziato deve appartenere allo stesso proprietario del progetto

  SELECT username_prop INTO v_lotto_username_prop
  FROM lotto 
  WHERE lotto.id_lotto = NEW.id_lotto;

  IF v_lotto_username_prop <> NEW.username_prop THEN 
    RAISE EXCEPTION 'Il lotto selezionato non appartiene al proprietario del progetto';
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER before_progetto_insert_update 
BEFORE INSERT OR UPDATE ON progetto
FOR EACH ROW 
EXECUTE FUNCTION check_progetto_validity();



CREATE FUNCTION get_available_lotti(p_username_prop VARCHAR)
RETURNS SETOF lotto AS $$
BEGIN
  RETURN QUERY
  (
    SELECT *
    FROM lotto AS lotti_utente
    WHERE username_prop = p_username_prop
  )

  EXCEPT 

  (
    SELECT lotti_utente.*
    FROM (SELECT * FROM progetto WHERE username_prop=p_username_prop AND data_fine IS NULL) AS progetti_utente
    JOIN (SELECT * FROM lotto WHERE username_prop=p_username_prop) AS lotti_utente ON progetti_utente.id_lotto = lotti_utente.id_lotto
  );
END;
$$ LANGUAGE plpgsql;