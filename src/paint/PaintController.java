
package paint;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * ペイントコントローラー
 *
 * @author 石橋 宗一郎
 * @version 1.0
 */
public class PaintController
		implements Initializable {
	@FXML
	Canvas canvas;
	@FXML
	ColorPicker color;
	@FXML
	ChoiceBox<String> sizeChooser;
	Screen screen = Screen.getPrimary();
	Rectangle2D rect = screen
			.getVisualBounds();
	GraphicsContext graphicsContext;

	//サイズ選択
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		graphicsContext = canvas.getGraphicsContext2D();
		canvas.setStyle("-fx-background-color: white");
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillRect(0, 0, rect.getWidth() / 2 + 50,
				rect.getHeight() / 2 + 300);
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
				(MouseEvent event) -> {
					graphicsContext.beginPath();
					graphicsContext.moveTo(event.getX(), event.getY());
					graphicsContext.stroke();
				});
		canvas.addEventHandler(
				MouseEvent.MOUSE_DRAGGED,
				(MouseEvent event) -> {
					graphicsContext.lineTo(event.getX(), event.getY());
					graphicsContext.stroke();
				});

		sizeChooser.getSelectionModel().selectedIndexProperty()
				.addListener((ChangeListener<Number>) (ov, old, newval) -> {
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
	}

	//保存
	@FXML
	protected void saveAction(ActionEvent event) {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();

		//拡張子フィルター
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Files", "*.*"),
				new FileChooser.ExtensionFilter("png files (*.png)", "*.png"),
				new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg"),
				new FileChooser.ExtensionFilter("gif files (*.gif)", "*.gif"));

		// 保存ファイルダイアログ
		File file = fileChooser.showSaveDialog(stage);
		if (file != null) {
			try {
				WritableImage writableImage = new WritableImage(500, 350);
				canvas.snapshot(null, writableImage);
				BufferedImage bufferedImage = SwingFXUtils
						.fromFXImage(writableImage, null);
				ImageIO.write(bufferedImage, "png", file);
			} catch (IOException ex) {
				System.out.println(ex);
			}
		}
	}

	//読込
	@FXML
	protected void loadAction(ActionEvent event) {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();

		//読込ファイルダイアログ
		File file = fileChooser.showOpenDialog(stage);
		if(file != null) {
			Image img = null;;
			try {
				img = new Image(file.toURI().toURL().toString());
			} catch (MalformedURLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			graphicsContext.drawImage(img, 500, 350);
			graphicsContext.drawImage(img, 500, 350, 500, 350);

		}

	}

	//消しゴムボタン
	@FXML
	protected void eraseAction(ActionEvent event) {
		graphicsContext.setStroke(Color.WHITE);
	}

	//色選択
	@FXML
	protected void colorAction(ActionEvent event) {
		Color newColor = color.getValue();
		graphicsContext.setStroke(newColor);
	}

}
