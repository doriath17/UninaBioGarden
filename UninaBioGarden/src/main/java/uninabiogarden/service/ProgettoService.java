package uninabiogarden.service;

import java.sql.SQLException;

import uninabiogarden.dao.ProgettoDao;
import uninabiogarden.dto.ProgettoDto;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.EmptyValueException;
import uninabiogarden.exceptions.NoDataFoundException;
import uninabiogarden.exceptions.WrongDataFineException;

public class ProgettoService {
  ProgettoDao progettoDao;

  public ProgettoService(ProgettoDao progettoDao) {
    this.progettoDao = progettoDao;
  }

  private void check(ProgettoDto dto) throws WrongDataFineException, EmptyValueException  {
    Constraint.checkNotEmptyValue(dto.nome());
    Constraint.checkNotEmptyValue(dto.dataInizio());
    Constraint.checkDataFine(dto.dataInizio(), dto.dataFine());
    return;
  }

  public void update(ProgettoDto dto) throws ConnectionFailedException, WrongDataFineException, EmptyValueException, SQLException {
    check(dto);
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

}
