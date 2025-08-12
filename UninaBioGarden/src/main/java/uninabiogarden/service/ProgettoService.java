package uninabiogarden.service;

import java.sql.SQLException;

import uninabiogarden.dao.ProgettoDao;
import uninabiogarden.dto.ProgettoDto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.MissingFieldException;
import uninabiogarden.exceptions.WrongDataFineException;

public class ProgettoService {
  ProgettoDao progettoDao;

  public ProgettoService(ProgettoDao progettoDao) {
    this.progettoDao = progettoDao;
  }

  private void basicCheck(ProgettoDto dto) throws MissingFieldException, WrongDataFineException {
    if (Constraint.checkNotEmptyValue(dto.nome())) {
      throw new MissingFieldException("Nome progetto mancante");
    }
    if (Constraint.checkNotEmptyValue(dto.dataInizio())) {
      throw new MissingFieldException("Data inizio del progetto mancante");
    }
    if (Constraint.checkDataFine(dto.dataInizio(), dto.dataFine())) {
      throw new WrongDataFineException(
        "Data fine posteriore alla data di inizio ("+dto.dataFine()+")");
    }
  }

  private void checkInsert(ProgettoDto dto) throws WrongDataFineException, MissingFieldException {
    basicCheck(dto);
    if (Constraint.checkNotEmptyValue(dto.id_lotto())) {
      throw new MissingFieldException("Lotto del progetto non selezionato");
    }
  }

  public void update(ProgettoDto dto) throws ConnectionFailedException, WrongDataFineException, MissingFieldException, SQLException {
    basicCheck(dto);
    progettoDao.update(
      dto.id_progetto(),
      dto.nome(),
      dto.dataInizio(),
      dto.dataFine(),
      dto.descrizione()
    );
  }

  public void delete(Long id_progetto) throws ConnectionFailedException, SQLException {
    progettoDao.delete(id_progetto);
  }

  public Progetto create(ProgettoDto dto, Proprietario prop) throws WrongDataFineException, MissingFieldException, SQLException, ConnectionFailedException {
    checkInsert(dto);
    var newProgetto = Progetto.createFromDto(dto, prop);
    progettoDao.insert(newProgetto);
    return newProgetto;
  }

}
