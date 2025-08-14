package uninabiogarden.service;

import java.sql.SQLException;

import uninabiogarden.dao.ProgettoDao;
import uninabiogarden.entities.Progetto;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.MissingFieldException;
import uninabiogarden.exceptions.WrongDataFineException;

public class ProgettoService {

  private static final ProgettoService instance = new ProgettoService();
  private final LoginService loginService = LoginService.getInstance();
  private final ProgettoDao progettoDao = ProgettoDao.getInstance();

  private ProgettoService() {}

  public static ProgettoService getInstance() {return instance;}

  private void basicCheck(Progetto toCheck) throws MissingFieldException, WrongDataFineException {
    if (Constraint.checkNotEmptyValue(toCheck.getNome())) {
      throw new MissingFieldException("Nome progetto mancante");
    }
    if (Constraint.checkNotEmptyValue(toCheck.getDataInizio())) {
      throw new MissingFieldException("Data inizio del progetto mancante");
    }
    if (Constraint.checkDataFine(toCheck.getDataInizio(), toCheck.getDataFine())) {
      throw new WrongDataFineException(
        "Data fine posteriore alla data di inizio ("+toCheck.getDataFine()+")");
    }
  }

  private void checkInsert(Progetto toCheck) throws WrongDataFineException, MissingFieldException {
    basicCheck(toCheck);
    if (Constraint.checkNotEmptyValue(toCheck.getLotto().getId())) {
      throw new MissingFieldException("Lotto del progetto non selezionato");
    }
  }

  public void update(Progetto progettoToUpdate) throws ConnectionFailedException, WrongDataFineException, MissingFieldException, SQLException {
    basicCheck(progettoToUpdate);
    progettoDao.update(progettoToUpdate);
  }

  public void delete(Long id_progetto) throws ConnectionFailedException, SQLException {
    progettoDao.delete(id_progetto);
  }

  public Long create(Progetto newProgetto) throws WrongDataFineException, MissingFieldException, SQLException, ConnectionFailedException {
    checkInsert(newProgetto);
    return progettoDao.insert(newProgetto);
  }

}
