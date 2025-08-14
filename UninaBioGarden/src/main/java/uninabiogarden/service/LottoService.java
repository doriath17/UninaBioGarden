package uninabiogarden.service;

import java.sql.SQLException;

import uninabiogarden.dao.LottoDao;
import uninabiogarden.entities.Lotto;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.FormatException;
import uninabiogarden.exceptions.MissingFieldException;

public class LottoService {

  private static final LottoService instance = new LottoService();
  private final LoginService loginService = LoginService.getInstance();
  private final LottoDao lottoDao = LottoDao.getInstance();

  private LottoService() {}

  public static LottoService getInstance() {
    return instance;
  }

  private static final int INDIRIZZO_MAX_LENGTH = 80;
  private static final int CODICE_MAX_LENGTH = 10;
  private static final int ORTO_MAX_LENGTH = 80;

  public void basicCheck(Lotto lottoToCheck) throws MissingFieldException, FormatException {
    if (Constraint.checkEmptyString(lottoToCheck.getIndirizzo())){
      throw new MissingFieldException("Indirizzo lotto non inserito");
    } else if (lottoToCheck.getIndirizzo().length() > INDIRIZZO_MAX_LENGTH) {
      throw new FormatException("Indirizzo lotto contiene più di "+INDIRIZZO_MAX_LENGTH+" caratteri");
    }
    if (Constraint.checkEmptyString(lottoToCheck.getCodice())) {
      throw new MissingFieldException("Codice lotto non inserito");
    } else if (lottoToCheck.getCodice().length() > CODICE_MAX_LENGTH) {
      throw new FormatException("Codice lotto contiene più di "+CODICE_MAX_LENGTH+" caratteri");
    }
    if (lottoToCheck.getEstensione() == null) {
      throw new MissingFieldException("Estensione lotto non inserita");
    } else if (lottoToCheck.getEstensione() < 0) {
      throw new FormatException("Estensione del lotto non può essere negativa");
    }
    if (Constraint.checkEmptyString(lottoToCheck.getOrto())) {
      throw new MissingFieldException("Nome orto non inserito");
    } else if (lottoToCheck.getOrto().length() > ORTO_MAX_LENGTH) {
      throw new FormatException("Nome orto contiene più di "+ORTO_MAX_LENGTH+" caratteri");
    }
  }

  public void update(Lotto lottoToUpdate) throws MissingFieldException, FormatException, SQLException, ConnectionFailedException {
    basicCheck(lottoToUpdate);
    lottoDao.update(lottoToUpdate);
  }

  public void delete(Lotto lotto) throws SQLException, ConnectionFailedException {
    lottoDao.delete(lotto);
  }

  public Long insert(Lotto lottoToInsert) throws MissingFieldException, FormatException, SQLException, ConnectionFailedException {
    basicCheck(lottoToInsert);
    return lottoDao.insert(lottoToInsert);
  }

}
