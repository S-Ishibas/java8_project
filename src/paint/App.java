package paint;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * ペイントツール
 *
 * @author 石橋 宗一郎
 * @version 1.0
 */
public class App extends Application {

	public static void main(
			String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		StackPane root;
		try {
			root = (StackPane) FXMLLoader
					.load(getClass().getResource("paint.fxml"));
			Scene scene = new Scene(root, 500, 350);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
