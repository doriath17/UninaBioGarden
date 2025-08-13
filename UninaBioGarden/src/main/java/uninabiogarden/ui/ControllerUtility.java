package uninabiogarden.ui;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.TextInputControl;

public class ControllerUtility {

  public static void addCodiceLottoFilter(TextField textField) {
    UnaryOperator<Change> filter = change -> {
      String newText = change.getControlNewText();
      if (newText.isEmpty()) {
        return change;
      }
      if (newText.matches("^[0-9]{1,10}$")) { 
        return change;
      }
      return null;
    };
    textField.setTextFormatter(new TextFormatter<>(filter));
  }

  public static void addEstensioneLottoFilter(TextField textField) {
    UnaryOperator<Change> filter = change -> {
      String newText = change.getControlNewText();
      if (newText.isEmpty()) {
        return change;
      }
      if (newText.matches("^([1-9][0-9]{0,10})(\\.[0-9]{0,3})?$")) {
        return change;
      }
      return null;
    };
    textField.setTextFormatter(new TextFormatter<>(filter));
  }


  public static void addPositiveIntegerFilter(TextField textField) {
    UnaryOperator<Change> filter = change -> {
      String newText = change.getControlNewText();
      if (newText.isEmpty()) {
        return change;
      }
      if (newText.matches("^0|[1-9][0-9]$")) { 
        return change;
      }
      return null;
    };
    textField.setTextFormatter(new TextFormatter<>(filter));
  }

  public static void addTextLimiter(TextInputControl textInputControl, int maxLength) {
    UnaryOperator<Change> filter = change -> {
      String newText = change.getControlNewText();
      if (newText.isEmpty()) {
        return change;
      }
      if (newText.length() <= maxLength) {
        return change;
      }
      return null;
    };
    textInputControl.setTextFormatter(new TextFormatter<>(filter));
  }
}
