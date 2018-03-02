package Visualization;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Objects;
import Core.AbstractSortArray;

final class VisualSortArray extends AbstractSortArray {
	
	int[] condition;
	int volume;
	Frame Frame;
	Graphics graphics;
	volatile int changeNumber;
	volatile int swapNumber;
	double frameRate = 60;
	double steps;
	double allowedSteps;
	long nextPainting;
	boolean IncrementShow;
	
	
	public VisualSortArray(int size, int scale, double speed) {
		super(size);
		if (scale <= 0 || speed <= 0 || Double.isInfinite(speed) || Double.isNaN(speed))
			throw new IllegalArgumentException();
		shuffle();
		condition = new int[size];
		
		changeNumber = 0;
		swapNumber = 0;
		steps = speed / frameRate;
		allowedSteps = 0;
		nextPainting = System.nanoTime();
		IncrementShow = steps < size;
		
		this.volume = scale;
		Frame = new Frame(size * scale);
		graphics = Frame.getBufferGraphics();
		redraw(0, values.length);
	}
	
	
	public int compare(int i, int j) {
		if (Thread.interrupted())
			throw new StopException();
		
		setElement(i, ElementState.COMPARING);
		setElement(j, ElementState.COMPARING);
		beforeStep();
		changeNumber++;
		
		setElement(i, ElementState.ACTIVE);
		setElement(j, ElementState.ACTIVE);
		
		return super.compare(i, j);
	}
	
	
	public void swap(int i, int j) {
		if (Thread.interrupted())
			throw new StopException();
		super.swap(i, j);
		if (condition != null) {  // If outside the constructor
			beforeStep();
			swapNumber++;
			setElement(i, ElementState.ACTIVE);
			setElement(j, ElementState.ACTIVE);
		}
	}
	
	public void setElement(int index, ElementState state) {
		Objects.requireNonNull(state);
		this.condition[index] = state.ordinal();
		if (IncrementShow)
			redraw(index, index + 1);
	}
	
	
	public void setRange(int start, int end, ElementState state) {
		Objects.requireNonNull(state);
		Arrays.fill(this.condition, start, end, state.ordinal());
		if (IncrementShow)
			redraw(start, end);
	}
	
	
	public int getChangeNumber() {
		return changeNumber;
	}
	
	public int getSwapNumber() {
		return swapNumber;
	}
	
	
	public void assertSorted() {
		for (int i = 1; i < values.length; i++) {
			if (values[i - 1] > values[i])
				throw new AssertionError();
		}
		redraw(0, values.length);
		Frame.repaint();
	}
	
	private void redraw(int start, int end) {
		graphics.setColor(BACKGROUND_COLOR);
		graphics.fillRect(0, start * volume, values.length * volume, (end - start) * volume);
		for (int i = start; i < end; i++) {
			graphics.setColor(STATE_COLORS[condition[i]]);
			if (volume == 1)
				graphics.drawLine(0, i, values[i], i);
			else  // volume > 1
				graphics.fillRect(0, i * volume, (values[i] + 1) * volume, volume);
		}
	}
	
	private void beforeStep() {
		boolean first = true;
		while (allowedSteps < 1) {
			long currentTime;
			while (true) {
				currentTime = System.nanoTime();
				if (currentTime >= nextPainting)
					break;
				long delay = nextPainting - currentTime;
				try {
					Thread.sleep(delay / 1000000, (int)(delay % 1000000));
				} catch (InterruptedException e) {
					throw new StopException();
				}
			}
			if (first) {
				if (!IncrementShow)
					redraw(0, values.length);
				Frame.adaptor = Frame.adaptor;
				Frame.repaint();
				first = false;
			}
			nextPainting += Math.round(1e9 / frameRate);
			if (nextPainting <= currentTime)
				nextPainting = currentTime + Math.round(1e9 / frameRate);
			allowedSteps += steps;
		}
		allowedSteps--;
	}
	
	
	
	
	private static Color[] STATE_COLORS = {
		new Color(0x294031),  // Dark Green
		new Color(0x959EBF),  // Gray
		new Color(0xD4BA9D),  // Ligh Red
		new Color(0x23163D),  // Green
	};
	
	private static Color BACKGROUND_COLOR = new Color(0xFFFFFF);  // White
	
}
