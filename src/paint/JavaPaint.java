package paint;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * ペイントツール
 *
 * @author ishibashi
 * @version 1.0
 */
public class JavaPaint extends Application {

	/**
	 * @param args
	 *            コマンドライン引数
	 *
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		StackPane root = new StackPane();
		Screen screen = Screen.getPrimary();
		Rectangle2D rect = screen.getVisualBounds();
		Canvas canvas = new Canvas(rect.getWidth() / 2 + 50, rect.getHeight() / 2 + 300);
		canvas.setStyle("-fx-background-color: white");
		final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillRect(0, 0, rect.getWidth() / 2 + 50, rect.getHeight() / 2 + 300);

		// ペンの色変更
		final ColorPicker colorPicker = new ColorPicker();
		colorPicker.setOnAction(event -> {
			Color newColor = colorPicker.getValue();
			graphicsContext.setStroke(newColor);
		});

		// ペンの太さ変更
		ChoiceBox<String> sizeChooser = new ChoiceBox<String>(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
		sizeChooser.getSelectionModel().selectFirst();
		sizeChooser.getSelectionModel().selectedIndexProperty().addListener((ChangeListener)(ov, old, newval) -> {
			Number idx = (Number) newval;

			switch (idx.intValue()) {
			case 0:
				graphicsContext.setLineWidth(1);
				break;
			case 1:
				graphicsContext.setLineWidth(2);
				break;
			case 2:
				graphicsContext.setLineWidth(3);
				break;
			case 3:
				graphicsContext.setLineWidth(4);
				break;
			case 4:
				graphicsContext.setLineWidth(5);
				break;
			default:
				graphicsContext.setLineWidth(1);
				break;
			}
		});
		sizeChooser.setTranslateX(5);

		// 消しゴム
		final Button eraseButton = new Button("消しゴム");
		eraseButton.setOnAction(actionEvent -> {
			graphicsContext.setStroke(Color.WHITE);
		});
		eraseButton.setTranslateX(10);

		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			graphicsContext.beginPath();
			graphicsContext.moveTo(event.getX(), event.getY());
			graphicsContext.stroke();
		});

		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
			graphicsContext.lineTo(event.getX(), event.getY());
			graphicsContext.stroke();
		});

		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(colorPicker, sizeChooser, eraseButton);

		initDraw(graphicsContext, canvas.getLayoutX(), canvas.getLayoutY());

		BorderPane container = new BorderPane();
		container.setTop(buttonBox);
		container.setCenter(canvas);

		root.getChildren().add(container);
		Scene scene = new Scene(root, rect.getHeight(), rect.getWidth(), Color.BLUE);
		primaryStage.setTitle("ペイント");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void initDraw(GraphicsContext graphics, double x, double y) {
		double canvasWidth = graphics.getCanvas().getWidth();
		double canvasHeight = graphics.getCanvas().getHeight();

		graphics.fill();
		graphics.strokeRect(x, y, canvasWidth, canvasHeight);

	}
}