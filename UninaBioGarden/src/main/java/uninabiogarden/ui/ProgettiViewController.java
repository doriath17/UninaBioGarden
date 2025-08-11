package uninabiogarden.ui;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import uninabiogarden.core.ApplicationState.ChangeType;
import uninabiogarden.dto.ProgettoDto;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.EmptyValueException;
import uninabiogarden.exceptions.MissingFieldException;
import uninabiogarden.exceptions.NoDataFoundException;
import uninabiogarden.exceptions.WrongDataFineException;

public class ProgettiViewController extends ControllerBase {

  @FXML VBox root;
  
  @FXML GridPane grid;

  @FXML VBox tableViewRoot;
  @FXML VBox progettiTableView;
  AvailableLottiController availableLottiController;
  
  @FXML TableView<Progetto> tableView;
  @FXML TableColumn<String, String> nomeCol;
  @FXML TableColumn<LocalDate, String> dataInizioCol;
  @FXML TableColumn<LocalDate, String> dataFineCol;

  // FORM
  @FXML TextField nomeField;
  @FXML DatePicker dataInizioField;
  @FXML DatePicker dataFineField;
  @FXML TextField indirizzoLottoField;
  @FXML TextField codiceLottoField;
  @FXML TextArea descrizioneField;

  HomeController homeController;

  @FXML Label errorLabel;

  ObservableList<Progetto> progettiObsList = FXCollections.observableArrayList();

  @Override
  VBox getRoot() {
    return root;
  }

  void close() {
    clearForm();
  }

  int selectedIndex;

  @FXML void initialize() {
    tableView.setItems(progettiObsList);
    nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
    dataInizioCol.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
    dataFineCol.setCellValueFactory(new PropertyValueFactory<>("dataFine"));

    tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Progetto>() {
      @Override
      public void changed(ObservableValue<? extends Progetto> observable, Progetto oldValue, Progetto newValue) {
        if (newValue != null) {
          selectedIndex = tableView.getSelectionModel().getSelectedIndex();
          fillForm(newValue);
        }
      }
    });

    addTextLimiter(nomeField, 50);
    addTextLimiter(descrizioneField, 200);
    addTextLimiter(indirizzoLottoField, 80);
    UnaryOperator<Change> positiveIntegerFilter = change -> {
      String newText = change.getControlNewText();
      if (newText.isEmpty()) {
        return change;
      }
      if (newText.matches("0|[1-9][0-9]*")) { 
        return change;
      }
      return null;
    };
    codiceLottoField.setTextFormatter(new TextFormatter<>(positiveIntegerFilter));
  }

  @SuppressWarnings("exports")
  public static void addTextLimiter(TextInputControl textInputControl, int maxLength) {
    UnaryOperator<Change> maxLengthFilter = change -> {
      String newText = change.getControlNewText();
      if (newText.isEmpty()) {
        return change;
      }
      if (newText.length() <= maxLength) {
        return change;
      }
      return null;
    };
    textInputControl.setTextFormatter(new TextFormatter<>(maxLengthFilter));
  }

  @FXML void back() {
    homeController.openHomeContent();
  }

  void loadProgetti() throws ConnectionFailedException, NoDataFoundException{
    Proprietario p = context.getAppState().getLoggedInProprietario();
    if (p.getProgetti() == null) {
      p.setProgetti(
        context.getProprietarioService().requestProgettiFor(p)
      );
    }
    progettiObsList.setAll(p.getProgetti());
  }

  @FXML void update() {
    Progetto p = tableView.getSelectionModel().selectedItemProperty().get();
    if (p != null) {
      var dto = new ProgettoDto(
        p.getId(),
        nomeField.getText(),
        dataInizioField.getValue(),
        dataFineField.getValue(),
        descrizioneField.getText(),
        // id_lotto non mi interessa per l'update 
        // siccome non puo' essere modificato
        // idem per il proprietario
        null,
        null
      );

      try {
        context.getProgettoService().update(dto);

        // mantieni lo stato dell'applicazione consistente con il db
        p.setNome(nomeField.getText());
        p.setDataInizio(dataInizioField.getValue());
        p.setDataFine(dataFineField.getValue());
        p.setDescrizione(descrizioneField.getText());
        progettiObsList.set(selectedIndex, p);
      } catch (ConnectionFailedException e) {
        System.err.println(e.getMessage());
      } catch (WrongDataFineException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (EmptyValueException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  @FXML void delete() {
    Progetto p = tableView.getSelectionModel().selectedItemProperty().get();
    if (p != null) {
      try {
        context.getProgettoService().delete(p.getId());
        progettiObsList.remove(selectedIndex);
        context.getAppState().getLoggedInProprietario().getProgetti().remove(p);      
      } catch (ConnectionFailedException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
  }

  @FXML void selectLotto() {

  }

  @FXML Button newButton;

  void fillLottoForm(Lotto lotto) {
    indirizzoLottoField.setText(lotto.getIndirizzo());
    codiceLottoField.setText(""+lotto.getCodice());
  }

  void fillForm(Progetto value) {
    nomeField.setText(value.getNome());
    dataInizioField.setValue(value.getDataInizio());
    dataFineField.setValue(value.getDataFine());
    descrizioneField.setText(value.getDescrizione());
    indirizzoLottoField.setText(value.getLotto().getIndirizzo());
    codiceLottoField.setText(""+value.getLotto().getCodice());
  }

  void clearForm() {
    nomeField.setText("");
    dataInizioField.setValue(null);
    dataFineField.setValue(null);
    descrizioneField.setText("");
    indirizzoLottoField.setText("");
    codiceLottoField.setText("");
  }

  void clearSelection() {
    if ("New".equals(newButton.getText())) {
      tableView.getSelectionModel().clearSelection();
    } else {
      getAvailableLottiController().clearSelection();
    }
    clearForm();
  }

  @FXML void toggleAddProgettoView() {
    if ("New".equals(newButton.getText())){
      showAvailableLottiTable();
      newButton.setText("Seleziona");
    } else {
      showProgettiTable();
      newButton.setText("New");
    }
    clearSelection();
  }

  ProgettoDto getFormData(Long id_lotto) {
    return new ProgettoDto(
      null, // the id do not exist yet
      nomeField.getText(),
      dataInizioField.getValue(),
      dataFineField.getValue(),
      descrizioneField.getText(),
      id_lotto,
      context.getSession().getUsername()
    );
  }

  @FXML void add() {

    try {
      var lotto = availableLottiController.getSelectedLotto();
    } catch (MissingFieldException e) {
      errorLabel.setText(e.getMessage());
    }

    // var pDto = getFormData(lotto.getId());
    // Progetto p = null;
    // try {
    //   p = Progetto.createFromDto(pDto, null, null);
    //   progettiObsList.add(p);

    //   context.getAppState().getPendingChanges().put(p, ChangeType.INSERT);
    // } catch (WrongDataFineException e) {
    //   e.printStackTrace();
    // } catch (EmptyValueException e) {
    //   e.printStackTrace();
    // }

  }

  void clear() {
    clearForm();
    progettiObsList.clear();
  }

  AvailableLottiController getAvailableLottiController() {
    if (availableLottiController == null){
      availableLottiController = (AvailableLottiController) loadContent("AvailableLotti.fxml");
      availableLottiController.progettiViewController = this;
      availableLottiController.availableLotti.setAll(
        context.getAppState().getLoggedInProprietario().getAvailableLotti()
      );
    }
    return availableLottiController; 
  }

  void showProgettiTable() {
    tableViewRoot.getChildren().remove(
      getAvailableLottiController().getRoot()
    );
    tableViewRoot.getChildren().add(progettiTableView);
  }

  void showAvailableLottiTable() {
    tableViewRoot.getChildren().remove(progettiTableView);
    tableViewRoot.getChildren().add(
      getAvailableLottiController().getRoot()
    );
  }

}
