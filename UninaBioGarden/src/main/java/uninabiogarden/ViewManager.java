package uninabiogarden;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class ViewManager {

  Stage stage;
  HashMap<String, Parent> cache = new HashMap<>();

  public ViewManager() {
    loadViews();
  }

  public void show(Stage stage) {
    this.stage = stage;
    Scene scene = new Scene(cache.get("MainView"));
    stage.setScene(scene);
    stage.show();
  }

  public void loadViews() {
      cacheView("MainView");
//      cacheView("LoginView");
  }

  private Parent cacheView(String viewName) {
    return cache.put(viewName, this.loadView(viewName + ".fxml"));
  }

  private Parent loadView(String fxmlFile) {
    System.out.println(getClass().getResource("/com/uninabiogarden/boundaries/ui/"+fxmlFile));
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      return loader.load();
    } catch (Exception e) {
      System.err.println(e.getMessage());
      throw new RuntimeException("Failed to load " + fxmlFile);
    }
  }
  
}
