package uninabiogarden.dao;

import uninabiogarden.core.ApplicationContext;

public abstract class DaoBase {
  protected final ApplicationContext context;

  public DaoBase(ApplicationContext context){
    this.context = context;
  }
}
