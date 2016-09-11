package it.cap4.lsystems;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// TODO: Auto-generated Javadoc
/**
 * Corso di Informatica III B 2015-2016 - Prof.ssa Patrizia Scandurra
 * Demo of Java GUI, through a program that show a L-System
 * 
 * @author marcoradavelli
 *
 */

public class LSystemSimulator extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The content pane. */
	private JSplitPane contentPane;
	
	/** The panel. */
	private Surface panel;
	
	/** The dtrpn lsystem. */
	private JEditorPane dtrpnLsystem;
	
	/** The lbl fattore y. */
	private JLabel lblSpeed, lblD, status, lblDelta, lblAlfa0, lblFattoreX, lblFattoreY;
	
	/** The check automatica. */
	private JCheckBox checkAutomatica;
	
	/** The labels. */
	private String[] labels = { "Tartaruga 1", "Tartaruga 2", "Triangolo di Kock", "Fiocco di neve di Koch", "Pianta 1", "Pianta 2" };
	
	/** The predefiniti. */
	private String[] predefiniti = { "FF-FFF-F-FF+F-F+ ff F+FFF+F+FFF", "FF-FFF-F-FF+F-F", "F {F+F--F+F}", "F--F--F {F+F--F+F}", "F {F[+F]F[-F[+F][-F]]F}", "F {F[+F[+F]-F][-F]F}" };
	
	/** The combo. */
	private JComboBox<String> combo;
	
	/** The img. */
	private BufferedImage img;
	
	/** The sl fattore y. */
	private JSlider slRit, slDim, slDelta, slAlfa0, slFattoreX, slFattoreY;
	
	/** The t fattore scala. */
	private JTextField tFattoreScala;
	
	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						// Set System L&F
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (Exception e) {
						e.printStackTrace();
					}
					LSystemSimulator frame = new LSystemSimulator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LSystemSimulator() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		//setBounds(100, 100, 600, 400);
		setTitle("L-System Simulator");
		
		URL url = LSystemSimulator.class.getResource("/resources/tartarugaVerdeQuadrata.png");
		try {
			img = ImageIO.read(url);
			setIconImage(img);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenuItem mntmInformazioni = new JMenuItem("Informazioni");
		mntmInformazioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(LSystemSimulator.this, "Programma di Simulazione di alcuni tipi di L-Systems (Lindenmayer-Systems).\n\nRealizzato per l'esposizione del capitolo 4 del libro \"Bio-Inspired Artificial Intelligence\",\n nell'ambito del corso di Informatica Teorica\nUniversità degli Studi di Bergamo, a.a. 2014-2015.\nDocente del corso: Mario Verdicchio\n\nMembri del gruppo:\n- Radavelli Marco\n- Ghisleni Stefano\n- Opreni Simone", "About", JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnFile.add(mntmInformazioni);
		JMenuItem mntmEsci = new JMenuItem("Esci");
		mntmEsci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmEsci);

		JLabel lbl1 = new JLabel("Predefiniti");
		combo = new JComboBox<String>(labels);
		JPanel p1 = new JPanel();
		p1.add(combo);
		combo.addActionListener(new ActionListener() {
			/** Initialize the parameters depending on the patter (Lindemayer-System, or LSystem) to draw */
			@Override
			public void actionPerformed(ActionEvent e) {
				dtrpnLsystem.setText(predefiniti[combo.getSelectedIndex()]);
				if (combo.getSelectedIndex()==2) {
					slAlfa0.setValue(0);
					slDelta.setValue(60);
					tFattoreScala.setText("3.0");
					slDim.setValue(120);
					slFattoreX.setValue(2);
					slFattoreY.setValue(2);
				} else if (combo.getSelectedIndex()==3) {
					slAlfa0.setValue(60);
					slDelta.setValue(60);
					tFattoreScala.setText("3.0");
					slDim.setValue(120);
					slFattoreX.setValue(2);
					slFattoreY.setValue(2);
				} else if (combo.getSelectedIndex()==4) {
					slAlfa0.setValue(90);
					slDelta.setValue(29);
					tFattoreScala.setText("2.0");
					slDim.setValue(100);
					slFattoreX.setValue(5);
					slFattoreY.setValue(5);
				} else if (combo.getSelectedIndex()==5) {
					slAlfa0.setValue(90);
					slDelta.setValue(21);
					tFattoreScala.setText("1.5");
					slDim.setValue(100);
					slFattoreX.setValue(5);
					slFattoreY.setValue(5);
				} else {
					slAlfa0.setValue(180);
					slDelta.setValue(90);
					slFattoreX.setValue(5);
					slFattoreY.setValue(5);
					slDim.setValue(60);
				}
			}
		});

		JLabel lblLsystem = new JLabel("L-System");
		dtrpnLsystem = new JEditorPane();
		dtrpnLsystem.setToolTipText("Descrizione dell'L-System");
		dtrpnLsystem.setContentType("text/html");
		dtrpnLsystem.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		dtrpnLsystem.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		dtrpnLsystem.setText(predefiniti[0]);
		dtrpnLsystem.setPreferredSize(new Dimension(500,50));
		JScrollPane scrollPane = new JScrollPane(dtrpnLsystem);

		status = new JLabel("");
		JScrollPane statusPane = new JScrollPane(status);
		
		JLabel lb1 = new JLabel("Angolo δ");
		slDelta = new JSlider();
		slDelta.setMaximum(360);
		slDelta.setValue(90);
		slDelta.setMinorTickSpacing(30);
		slDelta.setMajorTickSpacing(90);
		slDelta.setPaintTicks(true);
		slDelta.setPaintLabels(true);
		slDelta.setLabelTable(slDelta.createStandardLabels(90));
		slDelta.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				lblDelta.setText(""+value+"°");
				panel.setDelta(value);
			}
		});
		lblDelta = new JLabel(""+slDelta.getValue()+"°");
		
		JLabel lb2 = new JLabel("Angolo iniziale Alfa0");
		slAlfa0 = new JSlider();
		slAlfa0 = new JSlider();
		slAlfa0.setMaximum(360);
		slAlfa0.setValue(180);
		slAlfa0.setMinorTickSpacing(30);
		slAlfa0.setMajorTickSpacing(90);
		slAlfa0.setPaintTicks(true);
		slAlfa0.setPaintLabels(true);
		slAlfa0.setLabelTable(slAlfa0.createStandardLabels(90));
		slAlfa0.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				lblAlfa0.setText(""+value+"°");
				panel.setAlfa0(value);
			}
		});
		lblAlfa0 = new JLabel(""+slAlfa0.getValue()+"°");
		
		//JLabel lb3 = new JLabel("Posizione iniziale x0");
		slFattoreX = new JSlider();
		slFattoreX = new JSlider();
		slFattoreX.setMaximum(50);
		slFattoreX.setValue(5);
		slFattoreX.setMinorTickSpacing(5);
		slFattoreX.setMajorTickSpacing(10);
		slFattoreX.setPaintTicks(true);
		slFattoreX.setPaintLabels(true);
		slFattoreX.setLabelTable(slFattoreX.createStandardLabels(10));
		slFattoreX.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				lblFattoreX.setText(""+value+" * D");
				panel.setFattoreX(value);
			}
		});
		lblFattoreX = new JLabel(""+slFattoreX.getValue()+" * D");
		
		//JLabel lb4 = new JLabel("Posizione iniziale y0");
		slFattoreY = new JSlider();
		slFattoreY = new JSlider();
		slFattoreY.setMaximum(50);
		slFattoreY.setValue(5);
		slFattoreY.setMinorTickSpacing(5);
		slFattoreY.setMajorTickSpacing(10);
		slFattoreY.setPaintTicks(true);
		slFattoreY.setPaintLabels(true);
		slFattoreY.setLabelTable(slFattoreY.createStandardLabels(10));
		slFattoreY.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				lblFattoreY.setText(""+value+" * D");
				panel.setFattoreY(value);
			}
		});
		lblFattoreY = new JLabel(""+slFattoreY.getValue()+" * D");
		
		JLabel lb5 = new JLabel("Fattore di scala per Rewriting Systems: ");
		tFattoreScala = new JTextField("1.5");
		
		JLabel lblVelocitStep = new JLabel("Ritardo fra i passi (ms)");
		slRit = new JSlider();
		slRit.setMaximum(2000);
		slRit.setValue(500);
		slRit.setMinorTickSpacing(50);
		slRit.setMajorTickSpacing(500);
		slRit.setPaintTicks(true);
		slRit.setPaintLabels(true);
		// We'll just use the standard numeric labels for now...
		slRit.setLabelTable(slRit.createStandardLabels(500));
		slRit.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				lblSpeed.setText(""+value+" ms");
				panel.setDelay(value);
			}
		});
		lblSpeed = new JLabel(""+slRit.getValue()+" ms");

		JLabel lblDim = new JLabel("Dimensione della griglia");
		slDim = new JSlider();
		slDim.setMinimum(0);
		slDim.setMaximum(200);
		slDim.setValue(60);
		slDim.setMinorTickSpacing(10);
		slDim.setMajorTickSpacing(50);
		slDim.setPaintTicks(true);
		slDim.setPaintLabels(true);
		// We'll just use the standard numeric labels for now...
		slDim.setLabelTable(slDim.createStandardLabels(50));
		slDim.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				if (value==0) value++;
				lblD.setText(""+value+" px");
				panel.setD(value, value*slFattoreX.getValue(), value*slFattoreY.getValue());
			}
		});
		lblD = new JLabel(""+slDim.getValue()+" px");

		checkAutomatica = new JCheckBox("Esecuzione automatica");
		checkAutomatica.setSelected(true);
		checkAutomatica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!checkAutomatica.isSelected()) {
					if (panel.timer!=null && panel.timer.isRunning()) panel.timer.stop();
				} else {
					if (panel.timer!=null && !panel.timer.isRunning()) panel.timer.start();
				}
			}
		});

		JButton btnOk = new JButton("VAI");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkAutomatica.isSelected()) {
					panel.setParams(dtrpnLsystem.getText().replaceAll("\\<[^>]*>",""), slRit.getValue(), slDim.getValue(), slDim.getValue()*slFattoreX.getValue(), slDim.getValue()*slFattoreY.getValue(), slDelta.getValue(), slAlfa0.getValue(), Double.parseDouble(tFattoreScala.getText()));
					panel.startAnimation();
				} else {
					panel.avanti();
				}
			}
		});

		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dtrpnLsystem.setText(dtrpnLsystem.getText().replaceAll("\\<[^>]*>",""));
				panel.reset();
				panel.setParams(dtrpnLsystem.getText().replaceAll("\\<[^>]*>",""), slRit.getValue(), slDim.getValue(), slDim.getValue()*slFattoreX.getValue(), slDim.getValue()*slFattoreY.getValue(), slDelta.getValue(), slAlfa0.getValue(), Double.parseDouble(tFattoreScala.getText()));
			}
		});

		JPanel panel1 = new JPanel();
		GroupLayout layout = new GroupLayout(panel1);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(lblLsystem)
				.addComponent(scrollPane)
				.addComponent(statusPane)
				.addComponent(lbl1)
				.addComponent(p1)
				.addComponent(lb1)
				.addGroup(layout.createParallelGroup()
						.addComponent(slDelta)
						.addComponent(lblDelta))
				.addComponent(lb2)
				.addGroup(layout.createParallelGroup()
						.addComponent(slAlfa0)
						.addComponent(lblAlfa0))
				/*.addComponent(lb3)
				.addGroup(layout.createParallelGroup()
						.addComponent(slFattoreX)
						.addComponent(lblFattoreX))
				.addComponent(lb4)
				.addGroup(layout.createParallelGroup()
						.addComponent(slFattoreY)
						.addComponent(lblFattoreY))*/
				.addGroup(layout.createParallelGroup()
						.addComponent(lb5)
						.addComponent(tFattoreScala))
				.addComponent(lblVelocitStep)
				.addGroup(layout.createParallelGroup()
						.addComponent(slRit)
						.addComponent(lblSpeed))
				.addComponent(lblDim)
				.addGroup(layout.createParallelGroup()
						.addComponent(slDim)
						.addComponent(lblD))
				.addComponent(checkAutomatica)
				.addGroup(layout.createParallelGroup()
						.addComponent(btnOk)
						.addComponent(btnReset))
		);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(lblLsystem)
				.addComponent(scrollPane)
				.addComponent(statusPane)
				.addComponent(lbl1)
				.addComponent(p1)
				.addComponent(lb1)
				.addGroup(layout.createSequentialGroup()
						.addComponent(slDelta)
						.addComponent(lblDelta))
				.addComponent(lb2)
				.addGroup(layout.createSequentialGroup()
						.addComponent(slAlfa0)
						.addComponent(lblAlfa0))
				/*.addComponent(lb3)
				.addGroup(layout.createSequentialGroup()
						.addComponent(slFattoreX)
						.addComponent(lblFattoreX))
				.addComponent(lb4)
				.addGroup(layout.createSequentialGroup()
						.addComponent(slFattoreY)
						.addComponent(lblFattoreY))*/
				.addGroup(layout.createSequentialGroup()
						.addComponent(lb5)
						.addComponent(tFattoreScala))
				.addComponent(lblVelocitStep)
				.addGroup(layout.createSequentialGroup()
						.addComponent(slRit)
						.addComponent(lblSpeed))
				.addComponent(lblDim)
				.addGroup(layout.createSequentialGroup()
						.addComponent(slDim)
						.addComponent(lblD))
				.addComponent(checkAutomatica)
				.addGroup(layout.createSequentialGroup()
						.addComponent(btnOk)
						.addComponent(btnReset))
		);
		panel1.setLayout(layout);

		panel = new Surface();
		Dimension minimumSize = new Dimension(340, 200);
		panel1.setMinimumSize(minimumSize);

		contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, panel, panel1);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setResizeWeight(1);

		setContentPane(contentPane);
		contentPane.setOneTouchExpandable(true);
	}

	/**
	 * Evidenzia step.
	 *
	 * @param step the step
	 */
	public void evidenziaStep(int step) {
		String s = dtrpnLsystem.getText().replaceAll("\\<[^>]*>","");
		int count=0;
		for (int i=0; i<s.length(); i++) {
			if ("Ff+-[]".contains(s.substring(i, i+1))) count++;
			if (count==step) {
				String s1=s.substring(0,i);
				String s2=s.substring(i,i+1);
				String s3=s.substring(i+1);
				dtrpnLsystem.setText(s1+"<font size=\"6\"><b>"+s2+"</b></font>"+s3);
				break;
			}
		}
	}
	
	/**
	 * Gets the total steps.
	 *
	 * @param s the s
	 * @return the total steps
	 */
	public int getTotalSteps(String s) {
		s=s.replaceAll(" ", "").replaceAll("\n", "");
		int count=0;
		for (int i=0; i<s.length(); i++) {
			if (s.charAt(i)=='{') break;
			else if (s.charAt(i)!=' ') count++;
		}
		//System.out.println(s+" "+count);
		return count;
	}
	
	/**
	 * Sostituisci.
	 *
	 * @param s the s
	 * @return the string
	 */
	public String sostituisci(String s) {
		if (s.contains("{") && s.contains("}")) 
			return s.substring(0, s.indexOf('{')).replaceAll("F", s.substring(s.indexOf('{')+1).replace("}","").replaceAll(" ","")) + " " + s.substring(s.indexOf("{"));
		return s;
	}
	
	/**
	 * Gets the commands until step.
	 *
	 * @param lsystem the lsystem
	 * @param step the step
	 * @return the commands until step
	 */
	public String getCommandsUntilStep(String lsystem, int step) {
		String s="";
		int passo=0;
		for (int i=0; i<lsystem.length() && passo<step; i++) {
			if ("Ff+-[]".contains(lsystem.substring(i,i+1))) {
				s+=lsystem.substring(i, i+1);
				passo++;
			}
		}
		return s;
	}

	/**
	 *  This is the surface on which the tortoise animation is drawn.
	 */
	class Surface extends JPanel implements ActionListener {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		/** The delay. */
		private int delay;
		
		/** The d. */
		private int D=60;
		
		/** The y0. */
		private int x0=D*5, y0=D*5;
		
		/** The delta. */
		private double delta = 90;
		
		/** The alfa0. */
		private double alfa0 = 180;
		
		/** The step. */
		private int step;
		
		/** The total steps. */
		private int totalSteps;
		
		/** The lsystem. */
		private String lsystem="";
		
		/** The timer. */
		private Timer timer;
		
		/** The scala. */
		private double fattoreScala=3, scala=1;
		
		/** The stack alfa. */
		private Deque<Double> stackX = new ArrayDeque<Double>(), stackY=new ArrayDeque<Double>(), stackAlfa = new ArrayDeque<Double>();

		/**
		 *  Sets the params for initialization.
		 *
		 * @param lsystem the lsystem
		 * @param delay the delay
		 * @param D the d
		 * @param x0 the x0
		 * @param y0 the y0
		 * @param delta the delta
		 * @param alfa0 the alfa0
		 * @param fattoreScala the fattore scala
		 */
		public void setParams(String lsystem, int delay, int D, int x0, int y0, double delta, double alfa0, double fattoreScala) {
			this.lsystem=lsystem;
			this.delay=delay;
			this.D=D; 
			this.x0=x0; 
			this.y0=y0;
			this.delta=delta;
			this.alfa0=alfa0;
			this.fattoreScala=fattoreScala;
			step=0;
			totalSteps = getTotalSteps(lsystem);
		}
		
		/**
		 * Reset.
		 */
		public void reset() {
			if (timer!=null) timer.stop();
			step=0;
			scala=1;
			repaint();
		}

		/**
		 * Gets the current step.
		 *
		 * @return the current step
		 */
		public int getCurrentStep() {
			return step;
		}

		/**
		 * Start animation.
		 */
		public void startAnimation() {
			if (timer==null) timer = new Timer(delay, this);
			else timer.setDelay(delay);
			timer.start();
		}

		/**
		 *  Set the delay (interval) of the Timer (the background thread).
		 *
		 * @param delay the new delay
		 */
		public void setDelay(int delay) {
			this.delay = delay;
			if (timer!=null) timer.setDelay(delay);
		}
		
		/**
		 * Sets the d.
		 *
		 * @param D the d
		 * @param x0 the x0
		 * @param y0 the y0
		 */
		public void setD(int D, int x0, int y0) {
			this.D=D;
			this.x0=x0;
			this.y0=y0;
			if (timer==null || !timer.isRunning()) repaint();
		}
		
		/**
		 * Sets the alfa0.
		 *
		 * @param alfa0 the new alfa0
		 */
		public void setAlfa0(double alfa0) {
			this.alfa0=alfa0;
		}
		
		/**
		 * Sets the delta.
		 *
		 * @param delta the new delta
		 */
		public void setDelta(double delta) {
			this.delta=delta;
		}
		
		/**
		 * Sets the fattore x.
		 *
		 * @param fattoreX the new fattore x
		 */
		public void setFattoreX(int fattoreX) {
			x0=D*fattoreX;
		}
		
		/**
		 * Sets the fattore y.
		 *
		 * @param fattoreY the new fattore y
		 */
		public void setFattoreY(int fattoreY) {
			y0=D*fattoreY;
		}

		/**
		 * Gets the timer.
		 *
		 * @return the timer
		 */
		public Timer getTimer() {
			return timer;
		}

		/**
		 *  This method actually draws. Notice the "Graphics g" variable automatically passed
		 *
		 * @param g the g
		 */
		private void doDrawing(Graphics g) {
			Graphics2D g2d = (Graphics2D) g; // g2d extends g, adding more features
			g2d.setPaint(Color.gray);
			int w = getWidth();
			int h = getHeight();

			// disegna griglia
			for (int i = 0; i < w; i+=D) { // disegna linee verticali
				g2d.drawLine(i, 0, i, h);
			}
			for (int i=0; i < h; i+=D) { // disegna linee orizzontali
				g2d.drawLine(0, i, w, i);
			}
			
			int passo=0;
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.BLACK);
			double x=x0, y=y0;
			double alfa=alfa0;
			double x2,y2;
			for (int i=0; i<lsystem.length() && passo<step; i++) {
				switch (lsystem.charAt(i)) {
				case 'f':
					x2 = x + D*Math.cos(Math.toRadians(alfa))/scala;
					y2 = y - D*Math.sin(Math.toRadians(alfa))/scala;
					x=x2; y=y2; 
					passo++;
					break;
				case 'F': 
					x2 = x + D*Math.cos(Math.toRadians(alfa))/scala;
					y2 = y - D*Math.sin(Math.toRadians(alfa))/scala;
					g2d.drawLine((int)x, (int)y, (int)x2, (int)y2);
					x=x2; y=y2;
					passo++;
					break;
				case '+': alfa = (alfa + delta) % 360; passo++; break;
				case '-': alfa = (alfa - delta) % 360; passo++; break;
				case '[': stackX.push(x); stackY.push(y); stackAlfa.push(alfa); passo++; break;
				case ']': 
					x=stackX.pop(); 
					y=stackY.pop(); 
					alfa=stackAlfa.pop();
					passo++;
					break;
				}
				//System.out.println(x+" "+y+" "+alfa+" "+delta+" "+step+" "+passo);
			}
			double rotationRequired = Math.toRadians(360 - (alfa-180));
			double locationX = img.getWidth() / 2;
			double locationY = img.getHeight() / 2;
			AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

			// Drawing the rotated image at the required drawing locations
			g2d.drawImage(op.filter(img, null), (int)(x-(D/scala)/2), (int)(y-(D/scala)/2), (int)(D/scala), (int)(D/scala), null);
		}

		/* (non-Javadoc)
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			doDrawing(g);
		}

		/**
		 *  This method does the logic to calculate the next tortoise steps.
		 */
		public void avanti() {
			if (step < totalSteps) {
				step++;
				if (!lsystem.contains("{")) evidenziaStep(step);
				status.setText(getCommandsUntilStep(lsystem, step));
				repaint();
			}
			else if (lsystem.contains("{")) {
				step=0;
				lsystem = sostituisci(lsystem);
				totalSteps = getTotalSteps(lsystem);
				scala*=fattoreScala;
			}
			else if (timer!=null && timer.isRunning()) {
				timer.stop();
			}
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			avanti();
		}
	}
}
