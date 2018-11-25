/**
 *
 */
package paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author 石橋　宗一郎
 *
 */
public class ResizableCanvas extends Canvas {
	private static ResizableCanvas instance = getInstance();

	public static ResizableCanvas getInstance() {
		if(instance == null) {
			instance = new ResizableCanvas();
		}
		return instance;
	}

	public ResizableCanvas() {
		// Redraw canvas when size changes.
		widthProperty().addListener(evt -> draw());
		heightProperty().addListener(evt -> draw());
	}

	private void draw() {
		double width = getWidth();
		double height = getHeight();

		GraphicsContext graphicsContext = getGraphicsContext2D();
		setStyle("-fx-background-color: white");
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillRect(0, 0, width, height);
	}

	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public double prefWidth(double height) {
		return getWidth();
	}

	@Override
	public double prefHeight(double width) {
		return getHeight();
	}
}
