
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	
	@Override
    public void start(Stage primaryStage) {
        // Create and display the welcome page
        WelcomePage welcomePage = new WelcomePage(primaryStage);
        welcomePage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

	
    
}

