

CREATE OR REPLACE FUNCTION check_progetto_insert_validity()
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