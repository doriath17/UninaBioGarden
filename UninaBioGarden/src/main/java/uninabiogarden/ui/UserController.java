package uninabiogarden.ui;

import uninabiogarden.service.UtenteService;

public abstract class UserController extends Controller {
  UtenteService userService;

  Controller loaController(String fxmlFileName){
    var c = (UserController) loadController(fxmlFileName);
    c.userService = userService;
    return c;
  }
}
