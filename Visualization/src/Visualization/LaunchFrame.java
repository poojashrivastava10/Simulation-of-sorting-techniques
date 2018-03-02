package Visualization;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import Core.SortAlgorithm;

@SuppressWarnings("serial")
final class LaunchFrame extends Frame implements ActionListener {
	
	private final List<SortAlgorithm> algorithms;
	
	private final TextField arraySizeInput;
	private final TextField scaleInput;
	private final TextField speedInput;
	
	private final Choice algorithmInput;
	private final Button runButton;
	
	
	public LaunchFrame(List<SortAlgorithm> algos) {
		super("Sort Demo");
		Objects.requireNonNull(algos);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(gbl);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 0;
		gbc.ipady = 0;
		gbc.insets = new Insets(4, 4, 4, 4);
		gbc.weighty = 0;
		
		gbc.gridx = 0;
		gbc.weightx = 1;
		{
			Label label = new Label("Algorithm:");
			gbc.gridy = 0;
			gbl.setConstraints(label, gbc);
			this.add(label);
			
			label = new Label("Array size:");
			gbc.gridy = 1;
			gbl.setConstraints(label, gbc);
			this.add(label);
			
			label = new Label("Scale:");
			gbc.gridy = 2;
			gbl.setConstraints(label, gbc);
			this.add(label);
			
			label = new Label("Speed:");
			gbc.gridy = 3;
			gbl.setConstraints(label, gbc);
			this.add(label);
		}
		
		gbc.gridx = 1;
		gbc.weightx = 2;
		algorithms = new ArrayList<>(algos);
		algorithmInput = new Choice();
		for (SortAlgorithm algo : algos)
			algorithmInput.add(algo.getName());
		gbc.gridy = 0;
		gbl.setConstraints(algorithmInput, gbc);
		this.add(algorithmInput);
		
		arraySizeInput = new TextField("30");
		arraySizeInput.addActionListener(this);
		gbc.gridy = 1;
		gbl.setConstraints(arraySizeInput, gbc);
		this.add(arraySizeInput);
		
		scaleInput = new TextField("12");
		scaleInput.addActionListener(this);
		gbc.gridy = 2;
		gbl.setConstraints(scaleInput, gbc);
		this.add(scaleInput);
		
		speedInput = new TextField("10");
		speedInput.addActionListener(this);
		gbc.gridy = 3;
		gbl.setConstraints(speedInput, gbc);
		this.add(speedInput);
		
		runButton = new Button("Run");
		runButton.addActionListener(this);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.weighty = 1;
		gbl.setConstraints(runButton, gbc);
		this.add(runButton);
		
		this.pack();
		Rectangle rect = getGraphicsConfiguration().getBounds();
		this.setLocation(
			(rect.width - this.getWidth()) / 2,
			(rect.height - this.getHeight()) / 3);
		this.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent ev) {
		int size, scale;
		double speed;
		try {
			size = Integer.parseInt(arraySizeInput.getText());
			scale = Integer.parseInt(scaleInput.getText());
			speed = Double.parseDouble(speedInput.getText());
		} catch (NumberFormatException e) {
			return;
		}
		if (size <= 0 || scale <= 0 || speed <= 0 || Double.isInfinite(speed) || Double.isNaN(speed))
			return;
		
		final VisualSortArray array = new VisualSortArray(size, scale, speed);
		final SortAlgorithm algorithm = algorithms.get(algorithmInput.getSelectedIndex());
		final int startDelay = 1000;  // In milliseconds
		new Thread() {
			public Thread thread = this;
			
			public void run() {
				initFrame();
				doSort();
			}
			
			private void initFrame() {
				final Frame sortFrame = new Frame(algorithm.getName());
				sortFrame.add(array.Frame);
				sortFrame.setResizable(false);
				sortFrame.pack();
				sortFrame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						thread.interrupt();
						sortFrame.dispose();
					}
				});
				Rectangle rect = getGraphicsConfiguration().getBounds();
				sortFrame.setLocation(
					(rect.width - sortFrame.getWidth()) / 8,
					(rect.height - sortFrame.getHeight()) / 8);
				sortFrame.setVisible(true);
			}
			
			private void doSort() {
				try {
					Thread.sleep(startDelay);
					algorithm.sort(array);
				} catch (StopException|InterruptedException e) {
					return;
				}
				String msg;
				try {
					array.assertSorted();
					msg = String.format("%s: %d comparisons, %d swaps",
						algorithm.getName(), array.getChangeNumber(), array.getSwapNumber());
				} catch (AssertionError e) {
					msg = algorithm.getName() + ": Sorting failed";
				}
				synchronized (System.err) {
					System.err.println(msg);
				}
			}
		}.start();
	}
	
}
