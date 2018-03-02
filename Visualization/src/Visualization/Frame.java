package Visualization;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
final class Frame extends Canvas {
	
	private BufferedImage myBuffer;
	private Graphics graphic;
	public volatile int adaptor;
	
	public Frame(int size) {
		this(size, size);
	}
	
	
	public Frame(int width, int height) {
		if (width <= 0 || height <= 0)
			throw new IllegalArgumentException();
		this.setSize(width, height);
		myBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		graphic = myBuffer.getGraphics();
	}
	public Graphics getBufferGraphics() {
		return graphic;
	}
	public void update(Graphics g) {
		paint(g);
	}
	public void paint(Graphics g) {
		adaptor = adaptor;
		g.drawImage(myBuffer, 0, 0, this);
	}
	
}
