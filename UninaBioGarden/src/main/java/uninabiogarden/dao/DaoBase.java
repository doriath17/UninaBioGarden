package uninabiogarden.dao;

import uninabiogarden.core.Database;

public abstract class DaoBase {
  protected final Database database;

  public DaoBase(Database database){
    this.database = database;
  }
}
