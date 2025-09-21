package uninabiogarden.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.FormatException;
import uninabiogarden.exceptions.InvalidUtenteFieldException;
import uninabiogarden.exceptions.MissingFieldException;
import uninabiogarden.exceptions.NoDataFoundException;
import uninabiogarden.exceptions.WrongDataFineException;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;
import uninabiogarden.service.LoginService;
import uninabiogarden.service.LoginService.UserType;
import uninabiogarden.service.LottoService;
import uninabiogarden.service.ProgettoService;
import uninabiogarden.service.ProprietarioService;
import uninabiogarden.service.RegistrationService;

/*
 * Using lazy evaluation for controllers loading
*/
public class MainController extends ContentController {

  @FXML VBox root;
  @FXML VBox contentRoot;

  @Override
  VBox getRoot(){
    return root;
  }

  @Override 
  VBox getContentRoot() {
    return contentRoot;
  }

  // Boundary classes
  LoginController         loginController;
  HomeController          homeController;
  RegistrationController  registrationController;

  ProprietarioHomeController  propHomeController;
  ColtivatoreHomeController   coltHomeController;
  LottiController             lottiController;
  ProgettiViewController      progettiViewController;
  AvailableLottiController    availableLottiController;

  public void simulate() {
    try {
      loginProprietario("alessandro", "alessandro");
      openProgettiView();
      progettiViewController.toggleAddProgettoView();
    } catch (ConnectionFailedException | WrongUsernameException | WrongPasswordException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void show(Stage stage) {
    Scene scene = new Scene(root);
    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

    openLoginView();

    stage.setScene(scene);
    stage.show();
  }

  LoginController getLoginController() {
    if (loginController == null){
      loginController = (LoginController) ControllerBase.loadController("LoginView.fxml");
      loginController.mainController = this;
    }
    return loginController; 
  }

  HomeController getHomeController() {
    if (homeController == null) {
      homeController = (HomeController) ControllerBase.loadController("HomeView.fxml");
      homeController.mainController = this;
    }
    return homeController;
  }

  RegistrationController getRegistrationController() {
    if (registrationController == null) {
      registrationController = (RegistrationController) ControllerBase.loadController("RegistrationView.fxml");
      registrationController.mainController = this;
    }
    return registrationController;
  }

  ///
  /// 
  /// 
  /// LOGIN
  /// 
  /// 
  /// 

  void openLoginView() {
    setActiveContent(getLoginController());
  }

  void loginProprietario(String username, String password) throws ConnectionFailedException, WrongUsernameException, WrongPasswordException, SQLException {
    LoginService.getInstance().authenticateProprietario(username, password);
    openHomeView();
  }

  void loginColtivatore(String username, String password) throws ConnectionFailedException, WrongUsernameException, WrongPasswordException, SQLException {
    LoginService.getInstance().authenticateColtivatore(username, password);
    openHomeView();
  }

  Proprietario getLoggedInProprietario() {
    return LoginService.getInstance().getLoggedInProprietario();
  }

  Coltivatore getLoggedInColtivatore() {
    return LoginService.getInstance().getLoggedInColtivatre();
  }

  ///
  /// 
  /// 
  /// REGISTRATION
  /// 
  /// 
  /// 

  void openRegistrationView() {
    getRegistrationController().clearUIContent();
    setActiveContent(getRegistrationController());
  }

  void registerProprietario(Proprietario newUtente) throws ConnectionFailedException, WrongUsernameException, WrongPasswordException, SQLException, InvalidUtenteFieldException {
    var newId = RegistrationService.getInstance().registerProprietario(newUtente);
    newUtente.setId(newId);
    LoginService.getInstance().authenticateProprietario(newUtente.getUsername(), newUtente.getPassword());
    openHomeView();
  }

  void registerColtivatore(Coltivatore newUtente) throws ConnectionFailedException, WrongUsernameException, WrongPasswordException, SQLException, InvalidUtenteFieldException {
    var newId = RegistrationService.getInstance().registerColtivatore(newUtente);
    newUtente.setId(newId);
    LoginService.getInstance().authenticateColtivatore(newUtente.getUsername(), newUtente.getPassword());
    openHomeView();
  }

  ///
  /// 
  /// 
  /// HOME
  /// 
  /// 
  /// 

  void openHomeView() {
    getHomeController().openHomeContent();
    setActiveContent(homeController);
  }

  String getUsername() {
    return LoginService.getInstance().getUsername();
  }

  @SuppressWarnings("incomplete-switch")
  ControllerBase getHomeContent() {
    ControllerBase homeContent = null;
    switch (LoginService.getInstance().getLoggedInType()) {
      case UserType.PROPRIETARIO: 
        homeContent = getProprietarioHomeController();
        break;
      case UserType.COLTIVATORE: 
        homeContent = getColtivatoreHomeController();
        break;
    }
    return homeContent;
  }

  void clearPropUIContent() {
    getLottiController().clearUIContent();
    getProgettiController().clearUIContent();
  }

  void clearColtUIContent() {

  }

  void logout() {
    if (LoginService.getInstance().isProprietarioSession()){
      clearPropUIContent();
    } else {
      clearColtUIContent();
    }
    homeController.clearUIContent();
    LoginService.getInstance().logout();
    openLoginView();
  }

  ProprietarioHomeController getProprietarioHomeController() {
    if (propHomeController == null) {
      propHomeController = (ProprietarioHomeController) ControllerBase.loadController("ProprietarioHomeView.fxml");
      propHomeController.mainController = this;
    }
    return propHomeController;
  }

  ColtivatoreHomeController getColtivatoreHomeController() {
    if (coltHomeController == null) {
      coltHomeController = (ColtivatoreHomeController) ControllerBase.loadController("ColtivatoreHomeView.fxml");
      coltHomeController.mainController = this;
    }
    return coltHomeController;
  }

  void openLottiView() {
    getLottiController().init();
    homeController.setActiveContent(getLottiController());
  }

  void openProgettiView() {
    getProgettiController().init();
    homeController.setActiveContent(getProgettiController());
  }

  ///
  /// 
  /// 
  /// LOTTI
  /// 
  /// 
  /// 

  LottiController getLottiController() {
    if (lottiController == null) {
      lottiController = (LottiController) ControllerBase.loadController("LottiView.fxml");
      lottiController.mainController = this;
    }
    return lottiController;
  }

  void updateLotto(Lotto lottoToUpdate) throws MissingFieldException, FormatException, SQLException, ConnectionFailedException {
    LottoService.getInstance().update(lottoToUpdate);
  }

  void deleteLotto(Lotto lottoToDelete) throws SQLException, ConnectionFailedException {
    LottoService.getInstance().delete(lottoToDelete);
  }

  Long addLotto(Lotto lottoToAdd) throws MissingFieldException, FormatException, SQLException, ConnectionFailedException {
    lottoToAdd.setProprietario(LoginService.getInstance().getLoggedInProprietario());
    return LottoService.getInstance().insert(lottoToAdd);
  }

  List<Lotto> requestLotti() throws ConnectionFailedException, NoDataFoundException {
    return ProprietarioService.getInstance().requestLotti();
  }

  ///
  /// 
  /// 
  /// PROGETTI
  /// 
  /// 
  /// 

  ProgettiViewController getProgettiController() {
    if (progettiViewController == null) {
      progettiViewController = (ProgettiViewController) ControllerBase.loadController("ProgettiView.fxml");
      progettiViewController.mainController = this;
    }
    return progettiViewController;
  }

  List<Progetto> requestProgetti() throws ConnectionFailedException, NoDataFoundException {
    return ProprietarioService.getInstance().requestProgetti();
  }

  void updateProgetto(Progetto progettoToUpdate) throws ConnectionFailedException, WrongDataFineException, MissingFieldException, SQLException {
    ProgettoService.getInstance().update(progettoToUpdate);
  }

  void deleteProgetto(Progetto progettoToDelete) throws ConnectionFailedException, SQLException {
    ProgettoService.getInstance().delete(progettoToDelete.getId());
  }

  Long addProgetto(Progetto newProgetto) throws WrongDataFineException, MissingFieldException, SQLException, ConnectionFailedException {
    return ProgettoService.getInstance().create(newProgetto);
  }

  void showProgettiTable() {
    getProgettiController().init();
    progettiViewController.tableViewRoot.getChildren().remove(
      getAvailableLottiController().getRoot()
    );
    progettiViewController.tableViewRoot.getChildren().add(
      progettiViewController.progettiTableView
    );
  }

  void showAvailableLottiTable() {
    getAvailableLottiController().init();
    progettiViewController.tableViewRoot.getChildren().remove(
      progettiViewController.progettiTableView
    );
    progettiViewController.tableViewRoot.getChildren().add(
      getAvailableLottiController().getRoot()
    );
  }

  void fillLottoForm(Lotto lotto) {
    progettiViewController.indirizzoLottoField.setText(lotto.getIndirizzo());
    progettiViewController.codiceLottoField.setText(lotto.getCodice());
  }

  void showErrorMessageProgetto(String msg) {
    progettiViewController.showErrorMessage(msg);
  }

  ///
  /// 
  /// 
  /// AVAILABLE LOTTI
  /// 
  /// 
  /// 
  /// 

  AvailableLottiController getAvailableLottiController() {
    if (availableLottiController == null) {
      availableLottiController = (AvailableLottiController) ControllerBase.loadController("AvailableLotti.fxml");
      availableLottiController.mainController = this;
    }
    return availableLottiController;
  }

  List<Lotto> requestAvailableLotti() throws SQLException, ConnectionFailedException, NoDataFoundException {
    return ProprietarioService.getInstance().requestAvailableLotti();
  }

  Lotto getSelectedLotto() throws MissingFieldException {
    return availableLottiController.getSelectedLotto();
  }

}
