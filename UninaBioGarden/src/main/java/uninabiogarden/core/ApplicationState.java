package uninabiogarden.core;

import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;

/**
 * Lo scopo di questa classe è quello di rappresentare 
 * l'attuale stato del sistema:
 * la situazione attuale del sistema (pensa ai runtime data)
 * 
 * Quindi mentre l'application context rappresenta le componenti
 * fisse del sistema, questa classe rappresenta i dati di runtime.
 * 
 * The SessionManager and ApplicationState are just data containers; 
 * they should not contain logic for updating each other.
 * The SessionManager's job is to manage the login status,
 * and the ApplicationState's job is to hold the current user's data.
 * 
 * Questa classe non dovrebbe contenere logica come un logout etc.
 * E semplicemente un contenitore di dati, con setters e getters
 * niente di piu.
 * 
 */

public class ApplicationState {
  private Proprietario loggedInProprietario;
  private Coltivatore loggedInColtivatore;

  public ApplicationState() {
    
  }

  public void clear() {
    loggedInProprietario = null;
    loggedInColtivatore = null;
  }

  public Proprietario getLoggedInProprietario() {
    return loggedInProprietario;
  }

  public void setLoggedInProprietario(Proprietario loggedInProprietario) {
    this.loggedInProprietario = loggedInProprietario;
  }

  public Coltivatore getLoggedInColtivatore() {
    return loggedInColtivatore;
  }

  public void setLoggedInColtivatore(Coltivatore loggedInColtivatore) {
    this.loggedInColtivatore = loggedInColtivatore;
  }
}
