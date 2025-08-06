package uninabiogarden.controllers;

import uninabiogarden.dao.ProprietarioDAO;
import uninabiogarden.entities.Utente;

public class ControllerDAO {

    public void addProprietario(Utente.Builder proprietarioBuilder) {
        var proprietario = proprietarioBuilder.buildProprietario();
        ProprietarioDAO.add(proprietario);
    }

}
