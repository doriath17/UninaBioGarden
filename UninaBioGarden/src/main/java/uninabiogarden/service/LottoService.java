package uninabiogarden.service;

import uninabiogarden.dao.LottoDao;
import uninabiogarden.entities.Lotto;
import uninabiogarden.exceptions.FormatException;
import uninabiogarden.exceptions.MissingFieldException;

public class LottoService {
  private final LottoDao lottoDao;

  private static final int INDIRIZZO_MAX_LENGTH = 80;
  private static final int CODICE_MAX_LENGTH = 10;
  private static final int ORTO_MAX_LENGTH = 80;

  public void basicCheck(Lotto toCheck) throws MissingFieldException, FormatException {
    if (Constraint.checkEmptyString(toCheck.getIndirizzo())){
      throw new MissingFieldException("Indirizzo lotto non inserito");
    } else if (toCheck.getIndirizzo().length() > INDIRIZZO_MAX_LENGTH) {
      throw new FormatException("Indirizzo lotto contiene più di "+INDIRIZZO_MAX_LENGTH+" caratteri");
    }
    if (Constraint.checkEmptyString(toCheck.getCodice())) {
      throw new MissingFieldException("Codice lotto non inserito");
    } else if (toCheck.getCodice().length() > CODICE_MAX_LENGTH) {
      throw new FormatException("Codice lotto contiene più di "+CODICE_MAX_LENGTH+" caratteri");
    }
    if (toCheck.getEstensione() == null) {
      throw new MissingFieldException("Estensione lotto non inserita");
    } else if (toCheck.getEstensione() < 0) {
      throw new FormatException("Estensione del lotto non può essere negativa");
    }
    if (Constraint.checkEmptyString(toCheck.getOrto())) {
      throw new MissingFieldException("Nome orto non inserito");
    } else if (toCheck.getOrto().length() > ORTO_MAX_LENGTH) {
      throw new FormatException("Nome orto contiene più di "+ORTO_MAX_LENGTH+" caratteri");
    }
  }

  public LottoService(LottoDao lottoDao) {
    this.lottoDao = lottoDao;
  }

  public void update(Lotto toUpdate) throws MissingFieldException, FormatException {
    basicCheck(toUpdate);
  }

  public void insert(Lotto lotto) {

  }

  public void delete(Lotto lotto) {

  }


}
