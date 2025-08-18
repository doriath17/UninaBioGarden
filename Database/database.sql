CREATE SCHEMA uninabiogarden;

CREATE TYPE utente_type AS ENUM ('proprietario', 'coltivatore');

  -- username    VARCHAR(20)     NOT NULL CHECK (username ~ '^[a-zA-Z][a-zA-Z0-9_-]{5,19}$'), 
  -- password    VARCHAR(60)     NOT NULL CHECK (password ~ '^[a-zA-Z0-9_!@#$%^&*+=\\?-]{1,60}$'),
  -- email       VARCHAR(254)    NOT NULL CHECK (email ~ '^[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$'),

CREATE TABLE utente (
  id_utente   INT             GENERATED ALWAYS AS IDENTITY,
  username    VARCHAR(20)     NOT NULL CHECK (username <> ''), 
  password    VARCHAR(60)     NOT NULL CHECK (username <> ''),
  email       VARCHAR(254)    NOT NULL CHECK (username <> ''),
  nome        VARCHAR(30)     NOT NULL CHECK (nome <> ''),
  cognome     VARCHAR(30)     NOT NULL CHECK (cognome <> ''),
  bday        DATE            NOT NULL,
  nazionalita VARCHAR(30)     NOT NULL CHECK (nazionalita <> ''),
  num_tel     VARCHAR(10)     CHECK (num_tel IS NULL OR  num_tel ~ '^[0-9]{10}$'),
  residenza   VARCHAR(50)     CHECK (residenza IS NULL OR residenza <> ''),
  u_type      utente_type     NOT NULL,

  UNIQUE (username),
  UNIQUE (email),

  PRIMARY KEY (id_utente)
);

CREATE TABLE lotto (
  id_lotto      INT                 GENERATED ALWAYS AS IDENTITY,
  indirizzo     VARCHAR(80)         NOT NULL  CHECK (indirizzo <> ''),
  codice_lotto  VARCHAR(10)         NOT NULL  CHECK (codice_lotto ~ '^[0-9]+$'),
  estensione    DOUBLE PRECISION    NOT NULL  CHECK (estensione >= 0),
  nome_orto     VARCHAR(80)         NOT NULL  CHECK (nome_orto <> ''),
  id_prop       INT                 NOT NULL REFERENCES utente(id_utente), 

  UNIQUE (indirizzo, codice_lotto),

  PRIMARY KEY (id_lotto)
);

CREATE TABLE progetto (
  id_progetto     INT               GENERATED ALWAYS AS IDENTITY,
  nome            VARCHAR(30)       NOT NULL CHECK (nome <> ''),
  data_inizio     DATE              NOT NULL,
  data_fine       DATE,             
  descrizione     VARCHAR(200),
  id_prop         INT               NOT NULL REFERENCES utente(id_utente) ON DELETE CASCADE,
  id_lotto        INT               NOT NULL REFERENCES lotto(id_lotto) ON DELETE CASCADE,

  -- un proprietario non puo avere progetti con lo stesso nome che iniziano nella stessa data
  UNIQUE (nome, data_inizio, id_prop),

  PRIMARY KEY (id_progetto)
);

/**********************************************************************************************************************************************/

-- TRIGGERS and FUNCTIONS

/**********************************************************************************************************************************************/



CREATE OR REPLACE FUNCTION check_progetto_validity()
RETURNS TRIGGER AS $$
DECLARE 
  v_lotto_id_prop INT;
BEGIN 
  IF OLD IS NULL THEN -- solo per inserimento
    IF NEW.data_fine IS NOT NULL THEN
      -- non puoi inserire un progetto terminato
      RAISE EXCEPTION 'La data di fine per un nuovo progetto non può essere inserita';
    END IF;
  ELSE                -- solo per update

    IF NEW.data_fine IS NOT NULL AND NEW.data_inizio > NEW.data_fine THEN
      RAISE EXCEPTION 'Data inizio posteriore alla data di fine';
    END IF;

    /*
    Se la data di fine è stata settata, questa può solo essere posticipata
    in particolare non può essere settata nuovamente a NULL 
    (il progetto non può riprendere dopo che è finito, questo comporterebbe riassegnargli un lotto e non è supportato) 
    */
    IF OLD.data_fine IS NOT NULL AND NEW.data_fine <= OLD.data_fine THEN
      RAISE EXCEPTION 'La data fine di un progetto può solo essere posticipata';
    ELSIF OLD.data_fine IS NOT NULL AND NEW.data_fine IS NULL THEN 
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

  SELECT id_prop INTO v_lotto_id_prop
  FROM lotto 
  WHERE lotto.id_lotto = NEW.id_lotto;

  IF v_lotto_id_prop <> NEW.id_prop THEN 
    RAISE EXCEPTION 'Il lotto selezionato non appartiene al proprietario del progetto';
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER before_progetto_insert_update 
BEFORE INSERT OR UPDATE ON progetto
FOR EACH ROW 
EXECUTE FUNCTION check_progetto_validity();

/***********************************************************************/

CREATE FUNCTION get_available_lotti(p_id_prop INT)
RETURNS SETOF lotto AS $$
BEGIN
  RETURN QUERY
  (
    SELECT *
    FROM lotto AS lotti_utente
    WHERE id_prop = p_id_prop
  )

  EXCEPT 

  (
    SELECT lotti_utente.*
    FROM (SELECT * FROM progetto WHERE id_prop=p_id_prop AND data_fine IS NULL) AS progetti_utente
    JOIN (SELECT * FROM lotto WHERE id_prop=p_id_prop) AS lotti_utente ON progetti_utente.id_lotto = lotti_utente.id_lotto
  );
END;
$$ LANGUAGE plpgsql;