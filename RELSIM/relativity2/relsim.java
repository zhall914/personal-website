package relativity2;

import java.lang.*;
import java.util.*;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.TickUnit;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.annotations.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.Range;
import org.jfree.util.ShapeUtilities;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
* This source file, when compiled and run, creates an applet for visualizing the relativity of simultaneity consequence of Einstein's postulate's as presented in Hermann Bondi's "Relativity and Common Sense." The user can specify the x-magnitude of two simulataneous events in frame S behind and in front of the world-line x=0, the relativity velocity v (with c set as unity) from -0.999 to 0.999 of a frame S' w.r.t. frame S, and whether or not to show a gridded outline of the region of S' displayed in S and gridded outline of region S displayed in S'. The user can also specify independently the half-widths of space-time diagrams of S and S'. The user, having specified |x| for each simultaneous S event and v, can then choose to start, stop, and slide through animation showing light pulses propogating through the space-time diagrams corresponding to both S and S' making distance and time measurements of the two events via radar signals, with subplots below each space-time diagram following the light pulses to help with the visualization of time as a dimension. The user can specify which frame is taken to have proper (real/System) time, S or S'. When S is taken to have proper time, time dilation gives "simulataneous" crossings of a given light pulse and the t' axis in both frames, and when S' is taken to have proper time, time dilation gives "simultaneous" crossings of a given light pulse and the t axis in both frames. In both cases, the light pulses cross the x, x' axes "simultaneously" accounting for time dilation in the S, and S' respectively. Written using Java Swing components and jfreechart tools.

Written 8/28/15 by Zachary Hall, Boston University

*/


public class relsim {
	
	public static final int speed = 10; // update interval in ms for timer
	public static final double pOffset = 0.2; // fraction of L to extend x' beyond L
	public static double offset; // value to extend x' beyond L

	public static XYSeriesCollection datin; // public datasets to be modified for time or parameter changes
	public static XYSeriesCollection datin2;

	public static XYSeriesCollection sub1;

	public static XYSeriesCollection sub2;

	public static XYLineAndShapeRenderer rin; // public renderers for controlling visibility of spacetime borders
	public static XYLineAndShapeRenderer rin2;

	public static XYLineAndShapeRenderer r3;
	public static XYLineAndShapeRenderer r4;

	public static NumberAxis xlim3; //public axes and plots to be updated for time or parameter changes
	public static NumberAxis tlim3;
	public static NumberAxis xlim4;
	public static NumberAxis tlim4;
	public static NumberAxis xlim1;
	public static NumberAxis tlim1;
	public static NumberAxis xlim2;
	public static NumberAxis tlim2;
	public static XYPlot plot1;
	public static XYPlot plot2;
	public static XYPlot plot3;
	public static XYPlot plot4;
		
	public static Shape shape0;

	public static ChartPanel chartpanel1;
	public static ChartPanel chartpanel2;



	public static double k; //fundamental parameters
	public static double v;
	public static double gamma;

	public static double tfactor; // 1/gamma or gamma depending on proper frame
	public static boolean tswitch = false; //boolean for changing time updates



	public static double xmag;

	public static double x1;
	public static double x2;
	public static double t1 = 0;
	public static double t2 = 0;
	public static double xp1;
	public static double xp2;
	public static double tp1;
	public static double tp2;

	public static XYSeriesCollection pts;
	public static XYSeriesCollection ptsp;

	public static int grid1index;
	public static int grid2index;
	public static int hyp1index;
	public static int hyp2index;

	public static int iS = 0;
	public static int moveCountS = 0;
	public static int iSP = 0;
	public static int n = 0;
	public static int moveCountSP = 0;
	
	public static int nref1;
	public static int nref2;
	public static int nref3;
	public static int nref4;


	public static double ts;
	public static double tf;
	public static double tsp;
	public static double tfp;
	public static double tfp1;
	public static double tfp2;
	public static double tsp1;
	public static double tsp2;
	public static double ts1;
	public static double ts2;
	public static double tf1;
	public static double tf2;


	public static double tmin;
	public static double tmax;
	public static double xmin;
	public static double xmax;
	public static double tpmin;
	public static double tpmax;
	public static double xpmin;
	public static double xpmax;

	public static double t;
	public static double tref;
	public static double halfwidth;

	public static double Sphalfwidth;
	public static double Shalfwidth;

	public static Timer timer;
	public static long startTime;
	public static long stopTime;
	public static long incr;
	public static int startcount = 0; //when 0, disables parameter sliders when timer started

	public static JButton startButton; // public for cross-class enabling, disabling
	public static JButton stopButton;
	public static JButton resetButton;
	

	public static JRadioButton tSelect;
	public static JRadioButton tpSelect;

	public static JSlider vslide;
	public static JSlider xslide;

	public static JSlider tslide;
	public static JSlider hwSslide;
	public static JSlider hwSpslide;

	public static JLabel kvgamLab; //public for updating
	public static JLabel ShwLab;
	public static JLabel SphwLab;
	public static JLabel timeLab;
	public static JLabel xLab;

	public static boolean treflock = false; // boolean switches for controlling slider behaviors
	public static boolean slidelock = false;
	public static boolean stgridon = true; //for space-time grids
	public static boolean hypon = false;
	

	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				createAndShowGUI();
				timer = new Timer(speed,new movePulse());
				
		
				
			}
		});
	}

	private static void createAndShowGUI() {
	
	
		
			
		JFrame f = new JFrame("Relativity of Simultaneity");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initializeConstants1(0); // initialize v = 0.5, L = 1.3, tr = 5, halfwidth as 1.5*tr-ts, time as emitting time, S as proper frame
		
		
	
		setSphalfwidth(5);
		setShalfwidth(5);
		setXmag(4);
		initializeConstants2();	
		tfactor = 1/gamma;
		t = ts;
		tref = ts;
		
	

	

		

		

		
		
		spaceTimeInitialize st = new spaceTimeInitialize(); // initialize spacetime diagrams
		datin = st.dataset1;
		rin = st.r1;
	
		int nseries1 = datin.getSeriesCount();
	
		
		spaceTimePInitialize stp = new spaceTimePInitialize();
		datin2 = stp.dataset2;
		rin2 = stp.r2;
		
		
		

		

		JFreeChart chart1 =
 		ChartFactory.createXYLineChart("S Space-Time Diagram","x (ls)","t (s)",datin); // make charts, axes
		plot1 = chart1.getXYPlot();
		plot1.setRenderer(rin);
		plot1.setBackgroundPaint(null);

		xlim1 = (NumberAxis) plot1.getDomainAxis();
		tlim1 = (NumberAxis) plot1.getRangeAxis();
		xlim1.setRange(xmin,xmax);
		tlim1.setRange(tmin,tmax);


		Marker origin = new ValueMarker(0,Color.BLACK,new BasicStroke(2.0f));
		plot1.addDomainMarker(origin);  //t, x axes
		plot1.addRangeMarker(origin);
	
	


		chartpanel1 = new ChartPanel(chart1);
		chartpanel1.setPreferredSize(new Dimension(300,300));
		

		
		JFreeChart chart2 = ChartFactory.createXYLineChart("S' Space-Time Diagram","x' (ls)","t' (s)",datin2); // make charts, axes
		plot2 = chart2.getXYPlot();
		plot2.setRenderer(rin2);
		plot2.setBackgroundPaint(null);

		xlim2 = (NumberAxis) plot2.getDomainAxis();
		tlim2 = (NumberAxis) plot2.getRangeAxis();
		xlim2.setRange(xpmin,xpmax);
		tlim2.setRange(tpmin,tpmax);
		

		Marker origin2 = new ValueMarker(0,Color.BLUE,new BasicStroke(2.0f));
		plot2.addDomainMarker(origin2);
		plot2.addRangeMarker(origin2);
	
	

		chartpanel2 = new ChartPanel(chart2);
		chartpanel2.setPreferredSize(new Dimension(300,300));
	

	
		

		AxisSpace rodax = new AxisSpace(); // for buffering rod visualization charts
		rodax.setLeft(70);
		rodax.setRight(1);
		rodax.setTop(1);
		rodax.setBottom(40);

		XYSeries tax = new XYSeries("Time axis");
		tax.add(0,ts-1.5);
		tax.add(0,ts+1.5);
		XYSeries tpaxs = new XYSeries("t' in s");
		tpaxs.add(v*(ts-1.5),ts-1.5);	
		tpaxs.add(v*(ts+1.5),ts+1.5);
		sub1 = new XYSeriesCollection(tax);
		pulses p = new pulses();
		XYSeries events = new XYSeries("events");
		events.add(x1,t1);
		events.add(x2,t2);
		sub1.addSeries(tpaxs);
		sub1.addSeries(events);
		sub1.addSeries(p.data);	
		datin.addSeries(p.data);
		rin.setSeriesLinesVisible(datin.getSeriesCount()-1,false);
		rin.setSeriesShapesVisible(datin.getSeriesCount()-1,true);
		
		
		
		

		JFreeChart chart3 = ChartFactory.createXYLineChart(null,null,null,sub1);
		plot3 = chart3.getXYPlot();
		r3 = new XYLineAndShapeRenderer(true,false);
		r3.setBaseSeriesVisibleInLegend(false);
		r3.setSeriesStroke(0,new BasicStroke(2.0f));
		r3.setSeriesPaint(0,Color.BLACK);
		r3.setSeriesStroke(1,new BasicStroke(2.0f));
		r3.setSeriesPaint(1,Color.BLACK);
		Shape cross = ShapeUtilities.createDiagonalCross(3, 1);;
		r3.setSeriesLinesVisible(2,false);
		r3.setSeriesShapesVisible(2,true);
		r3.setSeriesShape(2,cross);
		r3.setSeriesPaint(2,Color.CYAN);
		r3.setSeriesLinesVisible(3,false);
		r3.setSeriesShapesVisible(3,true);
		shape0 = rin.getSeriesShape(3);

		
		plot3.setRenderer(r3);
		plot3.setFixedDomainAxisSpace(rodax);
		plot3.setBackgroundPaint(Color.GRAY);

		xlim3 = (NumberAxis) plot3.getDomainAxis();
		tlim3 = (NumberAxis) plot3.getRangeAxis();
		xlim3.setRange(xmin,xmax);	
		tlim3.setRange(ts-1.5,ts+1.5);
		tlim3.setTickLabelsVisible(false);


		ChartPanel chartpanel3 = new ChartPanel(chart3); // rod visualization in S' visualization
		chartpanel3.setPreferredSize(new Dimension(250,60));


	
		XYSeries taxp = new XYSeries("t axis in S'");
		taxp.add(0,ts*tfactor-1.5);
		taxp.add(0,ts*tfactor+1.5);
		sub2 = new XYSeriesCollection(taxp);
		XYSeries taxsp = new XYSeries("t in S'");
		taxsp.add(-v*(ts*tfactor-1.5),ts*tfactor-1.5);
		taxsp.add(-v*(ts*tfactor+1.5),ts*tfactor+1.5);	
		pulsesp pp = new pulsesp();
		XYSeries eventsp = new XYSeries("events '");
		eventsp.add(xp1,tp1);
		eventsp.add(xp2,tp2);
		sub2.addSeries(taxsp);
		sub2.addSeries(eventsp);
		sub2.addSeries(pp.data);	
		datin2.addSeries(pp.data);
		rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,false);
		rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
		

		JFreeChart chart4 = ChartFactory.createXYLineChart(null,null,null,sub2);
		plot4 = chart4.getXYPlot();
		r4 = new XYLineAndShapeRenderer(true,false);
		r4.setBaseSeriesVisibleInLegend(false);
		r4.setSeriesStroke(0,new BasicStroke(2.0f));
		r4.setSeriesPaint(0,Color.BLACK);
		r4.setSeriesStroke(1,new BasicStroke(2.0f));
		r4.setSeriesPaint(1,Color.BLACK);
		r4.setSeriesLinesVisible(2,false);
		r4.setSeriesShapesVisible(2,true);
		r4.setSeriesShape(2,cross);
		r4.setSeriesPaint(2,Color.CYAN);
		r4.setSeriesLinesVisible(3,false);
		r4.setSeriesShapesVisible(3,true);

		plot4.setRenderer(r4);
		plot4.setFixedDomainAxisSpace(rodax);
		plot4.setBackgroundPaint(Color.GRAY);
		xlim4 = (NumberAxis) plot4.getDomainAxis();
		tlim4 = (NumberAxis) plot4.getRangeAxis();
		xlim4.setRange(xpmin,xpmax);
		tlim4.setRange(ts*tfactor-1.5,ts*tfactor+1.5);
		tlim4.setTickLabelsVisible(false);
		ChartPanel chartpanel4 = new ChartPanel(chart4);
		chartpanel4.setPreferredSize(new Dimension(250,60));
		
	
	

		
		Checkbox hyp = new Checkbox("Display hyperbolas?");
		hyp.addItemListener(new hypCheck());
		hyp.setState(false);
		
	
		JPanel container = new JPanel(new GridBagLayout()); // container to add to frame

		JPanel spaceTime = new JPanel();
		JPanel spaceTimeP = new JPanel();
		JPanel rodSIll = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel rodSPIll = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel holdButtons = new JPanel();
	
	
		
		spaceTime.add(chartpanel1);
		spaceTimeP.add(chartpanel2);
		rodSIll.add(chartpanel3);
		rodSPIll.add(chartpanel4);
		

		JPanel holdSliders = new JPanel(new GridBagLayout()); // holds v, L, tr sliders
		JPanel holdtslide = new JPanel(new GridBagLayout()); // holds time slider, time buttons, time label
		JPanel holdhwslide = new JPanel(new GridBagLayout());
		JPanel holdcheck = new JPanel();
		JPanel holdtlab = new JPanel();
		JPanel holdkvgamLab = new JPanel();	
		JPanel holdhwSLab = new JPanel();
		JPanel holdhwSpLab = new JPanel();

		GridBagConstraints sc = new GridBagConstraints();
		GridBagConstraints sc1 = new GridBagConstraints();
		GridBagConstraints sc2 = new GridBagConstraints();
		GridBagConstraints sc3 = new GridBagConstraints();
		GridBagConstraints sc4 = new GridBagConstraints();
		sc.gridy = 0; //vertical position within slider holder
		sc1.gridy = 1;
		sc2.gridy = 2;
		sc3.gridy = 3;
		sc4.gridy = 4;

		vslide = new JSlider(-999,999,0); // sliders
		vslide.addChangeListener(new vChange());
		vslide.setMajorTickSpacing(100);
		vslide.setMinorTickSpacing(10);
		vslide.setPaintTicks(true);
		vslide.setName("v");
		
		vLabel();

		xslide = new JSlider(10,1000,40);
		xslide.addChangeListener(new xChange());
		xslide.setName("x");
		


		int Shw0 = (int) Math.round(100*Shalfwidth)/10;
		hwSslide = new JSlider(5,1000,Shw0);
		hwSslide.addChangeListener(new ShwChange());
		hwSslide.setMajorTickSpacing(100);
		hwSslide.setMinorTickSpacing(10);
		hwSslide.setPaintTicks(true);
		hwSslide.setName("S Space-Time Half Width");
		

		int Sphw0 = (int) Math.round(100*Sphalfwidth)/10;
		hwSpslide = new JSlider(5,1000,Sphw0);
		hwSpslide.addChangeListener(new SphwChange());
		hwSpslide.setMajorTickSpacing(100);
		hwSpslide.setMinorTickSpacing(10);
		hwSpslide.setPaintTicks(true);
		hwSpslide.setName("S' Space-Time Half Width");
		ShwLabel();


		int t0sl = (int) Math.round(1000*ts);
		int tfsl = (int) Math.round(1000*tf);
		tslide = new JSlider(t0sl,tfsl,t0sl);
		tslide.addChangeListener(new tChange());
	
		

		ActionListener sp = new startPressed(); //buttons
		startButton = new JButton("Start");
		startButton.addActionListener(sp);
		startButton.setEnabled(true);
	
		ActionListener stpp = new stopPressed();
		stopButton = new JButton("Stop");
		stopButton.addActionListener(stpp);
		stopButton.setEnabled(false);

		ActionListener rp = new resetPressed();
		resetButton = new JButton("Reset");
		resetButton.addActionListener(rp);
		resetButton.setEnabled(false);	

	

		tSelect = new JRadioButton("Proper time in S");
		
		tSelect.setSelected(true);
		tSelect.addItemListener(new tSelected());

		tpSelect = new JRadioButton("Proper time in S'");	
		
		tpSelect.addItemListener(new tpSelected());

		ButtonGroup group = new ButtonGroup();
		JPanel holdRadio = new JPanel();		
		group.add(tSelect);
		group.add(tpSelect);
		holdRadio.add(tSelect);
		holdRadio.add(tpSelect);
		
		kvgamLab = new JLabel();
		ShwLab = new JLabel();
		SphwLab = new JLabel();
		timeLab = new JLabel();
		xLab = new JLabel();
		setkvgamLab(); //external labelling methods so they can be updated
		setXLab();
		setShwLab();
		setSphwLab();
		settimeLab();

		Checkbox stgrid = new Checkbox("Display space-time grids?");
		stgrid.addItemListener(new stCheck());
		stgrid.setState(true);

		holdButtons.add(startButton);
		holdButtons.add(stopButton);
		holdButtons.add(resetButton);
	

		
		holdkvgamLab.add(kvgamLab);
		Dimension kvd = holdkvgamLab.getSize();
		Dimension labsize = new Dimension((int) Math.round(1.0*kvd.width),(int) Math.round(kvd.height));
		
		holdhwSLab.add(ShwLab);
		holdhwSpLab.add(SphwLab);
		holdkvgamLab.setMinimumSize(labsize);
	
		holdhwSLab.setMinimumSize(labsize);
		holdhwSpLab.setMinimumSize(labsize);
		
		holdSliders.add(vslide,sc);
		holdSliders.add(holdkvgamLab,sc);
		holdSliders.add(xslide,sc1);
		holdSliders.add(xLab,sc1);
		holdSliders.add(hwSslide,sc2);
		holdSliders.add(holdhwSLab,sc2);
		holdSliders.add(hwSpslide,sc3);
		holdSliders.add(holdhwSpLab,sc3);
		
	
		holdtslide.add(holdButtons,sc);
		holdtslide.add(tslide,sc1);
		holdtlab.add(timeLab);

		


		holdtlab.setPreferredSize(new Dimension(400,20));

		holdtslide.add(holdtlab,sc2);
		
		holdcheck.add(stgrid);
		holdcheck.add(hyp);
		
		
	   	spaceTime.setPreferredSize(new Dimension(300,300));
	   	spaceTimeP.setPreferredSize(new Dimension(300,300));
		rodSIll.setPreferredSize(new Dimension(300,60));
		rodSPIll.setPreferredSize(new Dimension(300,60));
		
		GridBagConstraints c = new GridBagConstraints();
	 	GridBagConstraints c2 = new GridBagConstraints();
		GridBagConstraints c3 = new GridBagConstraints();
		GridBagConstraints c4 = new GridBagConstraints();
		GridBagConstraints c5 = new GridBagConstraints();
		GridBagConstraints c6 = new GridBagConstraints();
		GridBagConstraints c7 = new GridBagConstraints();
		c2.gridy = 1; // for relative positions of applet components
		c3.gridy = 2;
		c4.gridy = 3;
		c5.gridy = 4;
		c6.gridy = 5;
		c6.gridx = 1;
		c7.gridy = 6;
		c7.gridx = 1;

		JLabel author = new JLabel("Source code written 8/28/15 - Zachary Hall, Boston University");
		author.setFont( new Font("Ariel",Font.BOLD,10));
		container.add(spaceTime,c);
		container.add(spaceTimeP,c);

		

		container.add(rodSIll,c2);
		container.add(rodSPIll,c2);

		JPanel holdhws = new JPanel(new GridBagLayout());
		JPanel holdhwsp = new JPanel(new GridBagLayout());
		holdhws.add(hwSslide,sc1);
		holdhws.add(ShwLab,sc2);
		holdhwsp.add(hwSpslide,sc1);
		holdhwsp.add(SphwLab,sc2);

		container.add(holdhws,c3);
		container.add(holdhwsp,c3);

		JPanel container2 = new JPanel();
		container2.setLayout(new BoxLayout(container2,BoxLayout.PAGE_AXIS));

	

		container2.add(container);
		
		container2.add(vslide);
		container2.add(kvgamLab);

		container2.add(xslide);
		container2.add(xLab);
		
		JPanel containerT = new JPanel(new GridBagLayout());
		
		containerT.add(holdtslide,c);
		containerT.add(holdRadio,c2);

		container2.add(holdcheck);
		container2.add(containerT);
		container2.add(author);

	

		f.add(container2);
		f.pack();
		Dimension fd = f.getSize();
		f.setSize(new Dimension((int) Math.round(1.1*fd.width),(int) Math.round(1.1*fd.height))); // buffer for when velocity, time values take on more digits
		f.setVisible(true);
	
	}

	public static class vChange implements ChangeListener{
		public void vChange(){
		}
		
		public void stateChanged(ChangeEvent e){
			JSlider source = (JSlider)e.getSource();
		      
			if(source.getValueIsAdjusting()){
			slidelock = true; //don't disable sliders due to initial time value change from initialization
			} else {
			slidelock = false; // turn switch off when done
			}
		

			int thouV = (int)source.getValue();
             
			double vdum = (double)thouV/1000;
			initializeConstants1(vdum);
			setkvgamLab();
			initializeConstants2(); //update

		
			double constant; // if S' proper, scale slider, reference time so they control t' in real time
			if(!tswitch) { constant = 1;
			} else { constant = tfactor;
			}
			int t0sl = (int) Math.round(1000*ts*constant);
			int tfsl = (int) Math.round(1000*tf*constant);
	
			
			tslide.setMinimum(t0sl);
			tslide.setMaximum(tfsl);
			tslide.setValue(t0sl);
		
			tref = ts*constant;
			t = ts;

			
		
			
			
			updatePlots();
			//moveCountS = 0;
			//moveCountSP = 0;
		
		
		}
	}

	public static class xChange implements ChangeListener{
		public void xChange(){
		}

		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();

			int tenx = (int)source.getValue();

			double xdum = (double)tenx/10;
			setXmag(xdum);
			initializeConstants2();
			setXLab();
			

			
			double constant;
			if(!tswitch) { constant = 1; // scaling for proper frame switch
			} else { constant = tfactor;
			}
			int t0sl = (int) Math.round(1000*ts*constant); // update slider, keep time as current value
			int tfsl = (int) Math.round(1000*tf*constant); 
		
			tslide.setMinimum(t0sl);
			tslide.setMaximum(tfsl);
			tslide.setValue(t0sl);
			t = ts;
			tref = ts*constant;
			
			
			
			updatePlots();
		}

	}

	

	public static class ShwChange implements ChangeListener{
		public void ShwChange(){
		}
		
		public void stateChanged(ChangeEvent e){
			JSlider source = (JSlider)e.getSource();

		     	
			
			//slidelock = false;
			

			int tenhw = (int)source.getValue();

			double hwdum = (double)tenhw/10;
			setShalfwidth(hwdum);
			initializeConstants2();
			setShwLab();
			

			double tsave = t; //avoid precision loss
			double constant;
			if(!tswitch) { constant = 1; // scaling for proper frame switch
			} else { constant = tfactor;
			}
			int t0sl = (int) Math.round(1000*ts*constant); // update slider, keep time as current value
			int tfsl = (int) Math.round(1000*tf*constant); 
			int tsl = (int) Math.round(1000*t*constant);
			tslide.setMinimum(t0sl);
			tslide.setMaximum(tfsl);
			tslide.setValue(tsl);
			t = tsave;
			tref = t*constant;
			
			
			
			updatePlots();
			//moveCountS = 0;
			//moveCountSP = 0;
		
		
		}
	}
	
		public static class SphwChange implements ChangeListener{
		public void SphwChange(){
		}
		
		public void stateChanged(ChangeEvent e){
			JSlider source = (JSlider)e.getSource();

		     	
			
			//slidelock = false;
			

			int tenhw = (int)source.getValue();

			double hwdum = (double)tenhw/10;
			setSphalfwidth(hwdum);
			initializeConstants2();
			setSphwLab();
			

			double tsave = t; //avoid precision loss
			double constant;
			if(!tswitch) { constant = 1; // scaling for proper frame switch
			} else { constant = tfactor;
			}
			int t0sl = (int) Math.round(1000*ts*constant); // update slider, keep time as current value
			int tfsl = (int) Math.round(1000*tf*constant); 
			int tsl = (int) Math.round(1000*t*constant);
			tslide.setMinimum(t0sl);
			tslide.setMaximum(tfsl);
			tslide.setValue(tsl);
			t = tsave;
			tref = t*constant;
			
			
			
			updatePlots();
			//moveCountS = 0;
			//moveCountSP = 0;
		
		
		}
	}


	public static class tChange implements ChangeListener{
		public void tChange(){
		}
		
		
		public void stateChanged(ChangeEvent e){
			

			JSlider source = (JSlider)e.getSource();
			
			resetButton.setEnabled(true);
			int thout = (int)source.getValue();

			double tdum = (double)thout/1000;

			if(!slidelock){
			
			//vslide.setEnabled(false);
			
			}
			if (!tswitch){t = tdum;
			
				if(!treflock){
				tref = tdum;
				}
			} else {t = tdum/gamma; // time is now scaled down by gamma since time slider effectively controls t'

				if(!treflock){
				tref = tdum; // reference time is scaled in movePulse
				}
			}
			settimeLab();

			updatePlots(); //update


		}

	}

	public static class stCheck implements ItemListener{ //controls visibility of space-time border datasets through public renderers
			public void stCheck(){
			}
			
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					stgridon = true;
					for (int i = 10; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = 10; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,true);
					}
				} else {
					stgridon = false;
					for (int i = 10; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = 10; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,false);
					}		
				}
			}
				
	}

//Custom label tables for sliders

	public static void vLabel() {
		   Hashtable<Integer, JLabel> vlables = 
            new Hashtable<Integer, JLabel>();
		for (int i = -9; i <= 9; i++) {
		String s = Double.toString((double)i/10);
		JLabel lab = new JLabel(s);
		lab.setFont(new Font("Serif",Font.PLAIN,6));
		vlables.put(new Integer( (int)100*i ), lab);
		}
		
		vslide.setLabelTable(vlables);
		vslide.setPaintLabels(true);


	}



	public static void ShwLabel() {
		Hashtable<Integer, JLabel> hwlabels =
		new Hashtable<Integer, JLabel>();
		for (int i = 0; i <= 10; i++) {
		String s = Double.toString((double) 0.5 + i*(1000-5)/100);
		JLabel lab = new JLabel(s);
		lab.setFont(new Font("Serif",Font.PLAIN,7));
		hwlabels.put(new Integer( (int) 5 + i*(1000-5)/10),lab);
		}
		hwSslide.setLabelTable(hwlabels);
		hwSslide.setPaintLabels(true);
		hwSpslide.setLabelTable(hwlabels);
		hwSpslide.setPaintLabels(true);
		
	}


		




	public static void initializeConstants1(double vdum){ //initialize fundamental constants
		v = vdum;
		k = Math.sqrt((1+Math.abs(v))/(1-Math.abs(v)));
	
		gamma = 1/(Math.sqrt(1-Math.pow(v,2)));
		if(tswitch) { tfactor = gamma;
		} else { tfactor = 1/gamma;
		}
	}

	public static void setXmag(double xdum){
		xmag = xdum;
	
		
	}

	



	public static void setShalfwidth (double dummyw) {
		Shalfwidth = dummyw;
		tmin = -Shalfwidth;
		xmin = -Shalfwidth;
		tmax = Shalfwidth;
		xmax = Shalfwidth;
	}

	public static void setSphalfwidth (double dummyw) {
		Sphalfwidth = dummyw;
		tpmin = -Sphalfwidth;
		xpmin = -Sphalfwidth;
		tpmax = Sphalfwidth;
		xpmax = Sphalfwidth;
	}

	public static void initializeConstants2() { //derived values for making spacetime diagrams
		x1 = -xmag;
		x2 = xmag;
		
		xp1 = gamma*(x1-v*t1);
		xp2 = gamma*(x2-v*t2);
		
		tp1 = gamma*(t1-v*x1);
		tp2 = gamma*(t2 - v*x2);
		
		ts1 = t1 + Math.abs(t1+x1-t1)*Math.max(Math.abs(t1+x1-t1),Math.abs((t1+x1)/(1+v)-t1))/(t1+x1-t1);
	
		
		ts2 = t2 + Math.abs(-x2+t2-t2)*Math.max(Math.abs(-x2+t2-t2),Math.abs((t2-x2)/(1-v)-t2))/(-x2+t2-t2);
	//	System.out.println("ts1 = " + ts1);
	//	System.out.println("ts2 = " + ts2);
	
	

		tf1 = t1 + Math.abs(t1-x1-t1)*Math.max(Math.abs(t1-x1-t1),Math.abs((t1-x1)/(1-v)-t1))/(t1-x1-t1);
		tf2 = t2 + Math.abs(t2+x2-t2)*Math.max(Math.abs(t2+x2-t2),Math.abs((t2+x2)/(1+v)-t2))/(t2+x2-t2);
	//	System.out.println("tf1 = " + tf1);
	//	System.out.println("tf2 = " + tf2);
		

		tsp1 = tp1 + Math.abs(tp1+xp1-tp1)*Math.max(Math.abs(tp1+xp1-tp1),Math.abs((tp1+xp1)/(1-v)-tp1))/(tp1+xp1-tp1);
		tsp2 = tp2 + Math.abs(-xp2+tp2-tp2)*Math.max(Math.abs(-xp2+tp2-tp2),Math.abs((tp2-xp2)/(1+v)-tp2))/(-xp2+tp2-tp2);
	//	System.out.println("tsp1 = " + tsp1);
	//	System.out.println("tsp2 = " +tsp2);


		tfp1 = tp1 + Math.abs(tp1-xp1-tp1)*Math.max(Math.abs(tp1-xp1-tp1),Math.abs((tp1-xp1)/(1+v)-tp1))/(tp1-xp1-tp1);
		tfp2 = tp2 + Math.abs(tp2+xp2-tp2)*Math.max(Math.abs(tp2+xp2-tp2),Math.abs((tp2+xp2)/(1-v)-tp2))/(tp2+xp2-tp2);
	//	System.out.println("tfp1 = " + tfp1);
	//	System.out.println("tfp2 = " + tfp2);

		ts = Math.min(ts1,ts2);

		tf = Math.max(tf1,tf2);

		tsp = Math.min(tsp1,tsp2);
		tfp = Math.min(tfp1,tfp2);


		
	
	}


		
	public static class startPressed implements ActionListener {
	
		public startPressed(){
		}

		public void actionPerformed(ActionEvent e) {
			
			vslide.setEnabled(false);
			
			startTime = System.currentTimeMillis();
			
			
			timer.start();
			stopButton.setEnabled(true);
			resetButton.setEnabled(false);
			startButton.setEnabled(false);
			tslide.setEnabled(false);
			hwSslide.setEnabled(false);
			hwSpslide.setEnabled(false);
			tSelect.setEnabled(false);
			tpSelect.setEnabled(false);
			

	
			startcount++;
		}
	}

	public static class stopPressed implements ActionListener {
		public stopPressed() {
		}

		public void actionPerformed(ActionEvent e) {
			treflock = false;
			timer.stop();
			stopTime = System.currentTimeMillis();
			incr = stopTime-startTime; //system time in ms the timer had been running
			tref = tref + 0.001*incr; //update reference time for timer
			stopButton.setEnabled(false);
			resetButton.setEnabled(true);
			startButton.setEnabled(true);
			tslide.setEnabled(true);
			hwSslide.setEnabled(true);
			hwSpslide.setEnabled(true);
			tSelect.setEnabled(true);
			tpSelect.setEnabled(true);
			vslide.setEnabled(true);
		}
	}

	public static class resetPressed implements ActionListener {
		public resetPressed() {
		}

		public void actionPerformed(ActionEvent e) {
			timer.stop();
			double constant;

			if(!tswitch){constant = 1;
			} else { constant = 1/gamma;
			}
			
			tslide.setEnabled(true);
			int t0sl = (int) Math.round(1000*ts/constant); //reset time
			tslide.setValue(t0sl);
			t = ts;
			tref = ts/constant;

			vslide.setEnabled(true); //reset button, slider functionality
			hwSslide.setEnabled(true);
			hwSpslide.setEnabled(true);
			

			startButton.setEnabled(true);
			tSelect.setEnabled(true);
			tpSelect.setEnabled(true);
			stopButton.setEnabled(false);
			resetButton.setEnabled(false);
		
		
			
			updatePlots(); //redraw
			
			startcount = 0; //reset startcount

		
		}
	}


	public static class movePulse implements ActionListener {
		 
		public movePulse() {
		}
		
		public void actionPerformed(ActionEvent e) {
			treflock = true;
			long currentTime = System.currentTimeMillis();
			double constant;
			if(tswitch) { constant = 1/gamma;
			} else { constant = 1;
			}
			t = constant*(tref + 0.001*(currentTime-startTime)); //if S' proper, scale so t' runs at real time

			double tsave = t;
			settimeLab(); 
			int tsl = (int) Math.round(1000*t/constant);
			tslide.setValue(tsl);
			tslide.setEnabled(false);
			t = tsave;

			updatePlots();

			if(t >= tf){
				timer.stop();
				//tref = t/constant;
				
				//startButton.setEnabled(true); // re-enable for greater functionality, but performance can be buggy (code needs to be more robust for this)
				stopButton.setEnabled(false);
				resetButton.setEnabled(true);
				hwSslide.setEnabled(true);
				hwSpslide.setEnabled(true);
				tslide.setEnabled(true);
			}
	
		}
	}
		

	public static class pulses {
		private XYSeries data;
		
		public pulses() {
		data = new XYSeries("pulse data");
		if (t >= ts1 && t < t1) {
		data.add(t1-t+x1,t);
		} else if ( t >= t1 && t <= tf1) {
		data.add(t-t1+x1,t);
		}

		if (t >= ts2 && t < t2) {
		data.add(t-t2+x2,t);
		} else if (t >= t2 && t <= tf2) {
		data.add(t2-t+x2,t);
		}
		}
		
	}

	public static class pulsesp {
		private XYSeries data;
		double tp = t*tfactor;
		public pulsesp() {
		data = new XYSeries("pulse ' data");
		if (tp >= tsp1 && tp < tp1) {
		data.add(tp1-tp+xp1,tp);
		} else if (tp >= tp1 && tp < tfp1) {
		data.add(tp-tp1+xp1,tp);
		}

		if (tp >= tsp2 && tp < tp2) {
		data.add(tp-tp2+xp2,tp);
		} else if (tp >= tp2 && tp <= tfp2) {
		data.add(tp2-tp+xp2,tp);
		}
		}
	}
		

		
		//makes space-time diagram dataset and renderer
	public static class spaceTimeInitialize {

		
		
		private XYSeriesCollection dataset1;
		private XYLineAndShapeRenderer r1;
		
		public spaceTimeInitialize() {
		XYSeries tp = new XYSeries("t' axis");
		tp.add(tmin*v,tmin);
		tp.add(tmax*v,tmax);
		XYSeries xp = new XYSeries("x' axis");
		xp.add(tmin/v,tmin);
		xp.add(tmax/v,tmax);
		XYSeries lc1 = new XYSeries("light cone seg 1");
		lc1.add(xmin,xmin);
		lc1.add(xmax,xmax);
		XYSeries lc2 = new XYSeries("light cone seg 2");
		lc2.add(-xmin,xmin);
		lc2.add(-xmax,xmax);
		XYSeries spts = new XYSeries("events");
		spts.add(x1,t1);
		spts.add(x2,t2);
		XYSeries p1s1 = new XYSeries("pulse 1 seg 1");
		p1s1.add(t1-ts1+x1,ts1);
		p1s1.add(x1,t1);
		XYSeries p1s2 = new XYSeries("pulse 1 seg 2");
		p1s2.add(x1,t1);
		p1s2.add(tf1-t1+x1,tf1);
		XYSeries p2s1 = new XYSeries("pulse 2 seg 1");
		p2s1.add(ts2-t2+x2,ts2);
		p2s1.add(x2,t2);
		XYSeries p2s2 = new XYSeries("pulse 2 seg 2");
		p2s2.add(x2,t2);
		p2s2.add(t2-tf2+x2,tf2);
		

	
			
		XYSeries tpminl = new XYSeries("min edge of t' frame");
		XYSeries tpmaxl = new XYSeries("max edge of t' frame");
		XYSeries xpminl = new XYSeries("min edge of x'frame");
		XYSeries xpmaxl = new XYSeries("max edge of x' frame");
		
		if (v != 0) {
		double ti1 = (xpmin/gamma + tpmax/(gamma*v))/(1/v-v);
		double ti2 = (xpmax/gamma + tpmax/(gamma*v))/(1/v-v);
		double ti3 = (xpmin/gamma + tpmin/(gamma*v))/(1/v-v);
		double ti4 = (xpmax/gamma + tpmin/(gamma*v))/(1/v-v);
		tpminl.add((ti3-tpmin/gamma)/v,ti3);
		tpminl.add((ti4-tpmin/gamma)/v,ti4);
		
		tpmaxl.add((ti1-tpmax/gamma)/v,ti1);
		tpmaxl.add((ti2-tpmax/gamma)/v,ti2);

		xpminl.add(v*ti3 + xpmin/gamma,ti3);
		xpminl.add(v*ti1 + xpmin/gamma,ti1);
	
		xpmaxl.add(v*ti4 + xpmax/gamma,ti4);
		xpmaxl.add(v*ti2 + xpmax/gamma,ti2);
		} else {
		tpminl.add(xpmin,tpmin);
		tpminl.add(xpmax,tpmin);
		
		tpmaxl.add(xpmin,tpmax);
		tpmaxl.add(xpmax,tpmax);

		xpminl.add(xpmin,tpmin);
		xpminl.add(xpmin,tpmax);

		xpmaxl.add(xpmax,tpmin);
		xpmaxl.add(xpmax,tpmax);
		}

		dataset1 = new XYSeriesCollection(tp);
		dataset1.addSeries(xp);
		dataset1.addSeries(lc1);
		dataset1.addSeries(lc2);
		dataset1.addSeries(spts);
		dataset1.addSeries(p1s1);
		dataset1.addSeries(p1s2);
		dataset1.addSeries(p2s1);
		dataset1.addSeries(p2s2);
		dataset1.addSeries(tpmaxl);
		dataset1.addSeries(tpminl);
		dataset1.addSeries(xpmaxl);
		dataset1.addSeries(xpminl);
		

		int tpind1 = (int) Math.ceil(tpmin);
		int tpind2 = (int) Math.floor(tpmax);
		int tind1 = (int) Math.ceil(tmin);
		int tind2 = (int) Math.floor(tmax);

		XYSeries[] tpgridlines = new XYSeries[tpind2- tpind1+1];

		for(int i = tpind1; i <= tpind2; i++) {
		String nametp = "linetp-" + (i-tpind1);
		tpgridlines[i-tpind1] = new XYSeries(nametp);
		if (v != 0) {
		double x1 = (tpmin/gamma + i/(gamma*v))/(1/v-v);
		double x2 = (tpmax/gamma + i/(gamma*v))/(1/v-v);
		tpgridlines[i-tpind1].add(x1,(x1-i/gamma)/v);
		tpgridlines[i-tpind1].add(x2,(x2-i/gamma)/v);
		
		} else {
		tpgridlines[i-tpind1].add(i,tpmin);
		tpgridlines[i-tpind1].add(i,tpmax);
		}

		dataset1.addSeries(tpgridlines[i-tpind1]);
		}



		int xpind1 = (int) Math.ceil(xpmin);
		int xpind2 = (int) Math.floor(xpmax);
		int xind1 = (int) Math.ceil(xmin);
		int xind2 = (int) Math.floor(xmax);
		XYSeries[] xpgridlines = new XYSeries[xpind2- xpind1+1];

		for(int i = xpind1; i <= xpind2; i++) {
		String namexp = "linexp-" + (i-xpind1);
		xpgridlines[i-xpind1] = new XYSeries(namexp);
		if ( v != 0) {
		double t1 = (xpmin/gamma + i/(gamma*v))/(1/v-v);
		double t2 = (xpmax/gamma + i/(gamma*v))/(1/v-v);
		xpgridlines[i-xpind1].add((t1 - i/gamma)/v,t1);
		xpgridlines[i-xpind1].add((t2 -i/gamma)/v,t2);
		
		} else {
		xpgridlines[i-xpind1].add(xpmin,i);
		xpgridlines[i-xpind1].add(xpmax,i);
		}

		dataset1.addSeries(xpgridlines[i-xpind1]);
		}

		grid1index = dataset1.getSeriesCount();

		double xval = 0;
		XYSeries[] xhyp = new XYSeries[xind2-xind1+1];
		for(int i = xind1; i <= xind2; i++) {
			String namexhyp = "xhyp-" + (i-xind1);
			xhyp[i-xind1] = new XYSeries(namexhyp,false);
			
			

			double constx =  (double) i;

			

			for (int j = 0; j<= 20; j++) {
			double tval = j*tmax/20;
			if ( constx < 0) {
			xval = xmin + j*(constx-xmin)/20;
			} else {xval = xmax + j*(constx-xmax)/20;
			}
			double tvalneg = -Math.sqrt(xval*xval - constx*constx);
			
			xhyp[i-xind1].add(xval,tvalneg);
			}

			for (int j = 0; j<= 10; j++) {
			double tval = j*tmax/10;
			if ( constx < 0) {
			xval = constx + j*(xmin-constx)/10;
			} else {xval = constx + j*(xmax-constx)/10;
			}
			double tvalpos = Math.sqrt(xval*xval - constx*constx);
			
			xhyp[i-xind1].add(xval,tvalpos);
			
			
			}
			if(i != 0){
			dataset1.addSeries(xhyp[i-xind1]);
			}
			
		}


		XYSeries[] thyp = new XYSeries[tind2-tind1+1];
		for(int it = tind1; it <= tind2; it++) {
			String namethyp = "thyp-" + (it-tind1);
			thyp[it-tind1] = new XYSeries(namethyp);

			double constt =  (double) it;

			

			for (int j = -10; j<= 10; j++) {
			double xvalnew = j*xmax/10;
			double tval = Math.abs(constt)*Math.sqrt(constt*constt + xvalnew*xvalnew)/constt;
			thyp[it-tind1].add(xvalnew,tval);
			}
			dataset1.addSeries(thyp[it-tind1]);
			
		}

		hyp1index = dataset1.getSeriesCount();

			
		r1 = new XYLineAndShapeRenderer(true,true);
		
		
		float dasharr[] = {6.0f,6.0f};
		float dotarr[] = {2.0f,6.0f};
		float linearr[] = {1.0f};
		Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
		Stroke grid = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1.0f,linearr,0.0f);
		Stroke bdot = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1.0f,dotarr,0.0f);
		Stroke dashedthk = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,
		BasicStroke.JOIN_ROUND,1.0f,dasharr,0.0f);
		Stroke dashed = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1.0f,dasharr,0.0f);
		
		
		for(int i=1;i<=hyp1index;i++){
		
			if(i < 3){ r1.setSeriesStroke(i-1,new BasicStroke(2.0f));
			r1.setSeriesPaint(i-1,Color.BLUE); //black lines
			r1.setSeriesShapesVisible(i-1,false);
			} else if(i < 5) { r1.setSeriesStroke(i-1,dashedthk);
			r1.setSeriesPaint(i-1,Color.BLACK);
			r1.setSeriesShapesVisible(i-1,false);
			} else if (i < 6) {
				r1.setSeriesLinesVisible(i-1,false);
				r1.setSeriesShapesVisible(i-1,true);
				r1.setSeriesShape(i-1,cross);	
				r1.setSeriesPaint(i-1,Color.CYAN);

			} else if(i < 10) {r1.setSeriesShapesVisible(i-1,false);
			r1.setSeriesPaint(i-1,Color.BLACK);
			r1.setSeriesStroke(i-1,dashed);

			} else if (i < 14) { r1.setSeriesStroke(i-1,bdot);
			r1.setSeriesPaint(i-1,Color.BLUE); //black lines
			r1.setSeriesShapesVisible(i-1,false);
			} else if (i <= grid1index) {r1.setSeriesStroke(i-1,grid);
			r1.setSeriesPaint(i-1,Color.BLUE);
			r1.setSeriesShapesVisible(i-1,false);
			} else {r1.setSeriesStroke(i-1,new BasicStroke(1.5f));
			r1.setSeriesPaint(i-1,Color.RED);
			r1.setSeriesShapesVisible(i-1,false);
			r1.setSeriesVisible(i-1,false);
			}
		}	
              /*  Stroke dashedthk = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,
		BasicStroke.JOIN_ROUND,1.0f,dasharr,0.0f);
		r1.setSeriesStroke(2,dashedthk);
		
		r1.setSeriesStroke(nseries-1,sdashed);*/
		r1.setBaseSeriesVisibleInLegend(false);
		
	
	
		
		
	
		}
		
	
	}



	//makes S' space-time diagram dataset and renderer
	public static class spaceTimePInitialize {
		private XYSeriesCollection dataset2;
		private XYLineAndShapeRenderer r2;

		public spaceTimePInitialize() {
		
	        XYSeries tt = new XYSeries("t axis");
                tt.add(-v*tpmin,tpmin);
                tt.add(-v*tpmax,tpmax);
                XYSeries xx = new XYSeries("x axis");
                xx.add(-(tpmin)/v,tpmin);
                xx.add(-tpmax/v,tpmax);    
		XYSeries lc1 = new XYSeries("light cone seg 1");
		lc1.add(xpmin,xpmin);
		lc1.add(xpmax,xpmax);
		XYSeries lc2 = new XYSeries("light cone seg 2");
		lc2.add(-xpmin,xpmin);
		lc2.add(-xpmax,xpmax);
		XYSeries sptsp = new XYSeries("events '");
		sptsp.add(xp1,tp1);
		sptsp.add(xp2,tp2);
		XYSeries p1s1 = new XYSeries("pulse 1 seg 1");
		p1s1.add(tp1-tsp1+xp1,tsp1);
		p1s1.add(xp1,tp1);
		XYSeries p1s2 = new XYSeries("pulse 1 seg 2");
		p1s2.add(xp1,tp1);
		p1s2.add(tfp1-tp1+xp1,tfp1);
		XYSeries p2s1 = new XYSeries("pulse 2 seg 1");
		p2s1.add(tsp2-tp2+xp2,tsp2);
		p2s1.add(xp2,tp2);
		XYSeries p2s2 = new XYSeries("pulse 2 seg 2");
		p2s2.add(xp2,tp2);
		p2s2.add(tp2-tfp2+xp2,tfp2);
		

		XYSeries xmaxl = new XYSeries("max xp");
		XYSeries xminl = new XYSeries("min xp");
		XYSeries tmaxl = new XYSeries("max tp");
		XYSeries tminl = new XYSeries("min tp");
		
	
		
		if (v != 0){	
		double ti1 = (tmax/(gamma*v) - xmin/gamma)/(1/v-v);
		double ti2 = (tmax/(gamma*v) - xmax/gamma)/(1/v-v);
		double ti3 = (tmin/(gamma*v) - xmin/gamma)/(1/v-v);
		double ti4 = (tmin/(gamma*v) - xmax/gamma)/(1/v-v);
		tminl.add(-(ti4-tmin/gamma)/v,ti4);
		tminl.add(-(ti3-tmin/gamma)/v,ti3);
		tmaxl.add(-(ti2-tmax/gamma)/v,ti2);
		tmaxl.add(-(ti1-tmax/gamma)/v,ti1);
		xminl.add(-v*ti3+xmin/gamma,ti3);
		xminl.add(-v*ti1+xmin/gamma,ti1);
		xmaxl.add(-v*ti4+xmax/gamma,ti4);
		xmaxl.add(-v*ti2+xmax/gamma,ti2);
		}else{
		tminl.add(xmin,tmin);
		tminl.add(xmax,tmin);
		
		tmaxl.add(xmin,tmax);
		tmaxl.add(xmax,tmax);
		
		xminl.add(xmin,tmin);
		xminl.add(xmin,tmax);
	
		xmaxl.add(xmax,tmin);
		xmaxl.add(xmax,tmax);
		}
	



		dataset2 = new XYSeriesCollection(tt);
                dataset2.addSeries(xx);
		dataset2.addSeries(lc1);
		dataset2.addSeries(lc2);
		dataset2.addSeries(sptsp);
		dataset2.addSeries(p1s1);
		dataset2.addSeries(p1s2);
		dataset2.addSeries(p2s1);
		dataset2.addSeries(p2s2);
		dataset2.addSeries(tmaxl);
		dataset2.addSeries(tminl);
		dataset2.addSeries(xmaxl);
		dataset2.addSeries(xminl);
		//dataset2.addSeries(lsegp);

		int tind1 = (int) Math.ceil(tmin);
		int tind2 = (int) Math.floor(tmax);
		int tpind1 = (int) Math.ceil(tpmin);
		int tpind2 = (int) Math.floor(tpmax);

		XYSeries[] tgridlines; 
		tgridlines = new XYSeries[tind2- tind1+1];

		for(int i = tind1; i <= tind2; i++) {
		String namet = "linet-" + (i-tind1);
		tgridlines[i-tind1] = new XYSeries(namet);
		if (v != 0) {
		double x1 = (i/(gamma*v) - tmin/gamma)/(1/v-v);
		double x2 = (i/(gamma*v) - tmax/gamma)/(1/v-v);
		tgridlines[i-tind1].add(x1,-(x1-i/gamma)/v);
		tgridlines[i-tind1].add(x2,-(x2-i/gamma)/v);
		} else {
		tgridlines[i-tind1].add(i,tmin);
		tgridlines[i-tind1].add(i,tmax);
		}

		dataset2.addSeries(tgridlines[i-tind1]);
		}

		int xind1 = (int) Math.ceil(xmin);
		int xind2 = (int) Math.floor(xmax);
		int xpind1 = (int) Math.ceil(xpmin);
		int xpind2 = (int) Math.floor(xpmax);

		XYSeries[] xgridlines = new XYSeries[xind2- xind1+1];

		for(int i = xind1; i <= xind2; i++) {
		String namex = "linex-" + (i-xind1);
		
		xgridlines[i-xind1] = new XYSeries(namex);
		if (v != 0) {
		double t1 = (i/(gamma*v) - xmin/gamma)/(1/v-v);
		double t2 = (i/(gamma*v) - xmax/gamma)/(1/v-v);
		xgridlines[i-xind1].add(-(t1 -i/gamma)/v,t1);
		xgridlines[i-xind1].add(-(t2-i/gamma)/v,t2);
		} else {
		xgridlines[i-xind1].add(xmin,i);
		xgridlines[i-xind1].add(xmax,i);
		}

		dataset2.addSeries(xgridlines[i-xind1]);
		}

		grid2index = dataset2.getSeriesCount();


		double xpval = 0;
		XYSeries[] xphyp = new XYSeries[xpind2-xpind1+1];
		for(int i = xpind1; i <= xpind2; i++) {
			String namexphyp = "xphyp-" + (i-xpind1);
			xphyp[i-xpind1] = new XYSeries(namexphyp,false);
			
			

			double constxp =  (double) i;

			

			for (int j = 0; j<= 20; j++) {
			double tpval = j*tpmax/20;
			if ( constxp < 0) {
			xpval = xpmin + j*(constxp-xpmin)/20;
			} else {xpval = xpmax + j*(constxp-xpmax)/20;
			}
			double tpvalneg = -Math.sqrt(xpval*xpval - constxp*constxp);
			
			xphyp[i-xpind1].add(xpval,tpvalneg);
			}

			for (int j = 0; j<= 10; j++) {
			double tpval = j*tpmax/10;
			if ( constxp < 0) {
			xpval = constxp + j*(xpmin-constxp)/10;
			} else {xpval = constxp + j*(xpmax-constxp)/10;
			}
			double tpvalpos = Math.sqrt(xpval*xpval - constxp*constxp);
			
			xphyp[i-xpind1].add(xpval,tpvalpos);
			
			
			}
			if(i != 0){
			dataset2.addSeries(xphyp[i-xpind1]);
			}
			
		}


		XYSeries[] tphyp = new XYSeries[tpind2-tpind1+1];
		for(int it = tpind1; it <= tpind2; it++) {
			String nametphyp = "thyp-" + (it-tpind1);
			tphyp[it-tpind1] = new XYSeries(nametphyp);

			double consttp =  (double) it;

			

			for (int j = -10; j<= 10; j++) {
			double xpvalnew = j*xpmax/10;
			double tpval = Math.abs(consttp)*Math.sqrt(consttp*consttp + xpvalnew*xpvalnew)/consttp;
			tphyp[it-tpind1].add(xpvalnew,tpval);
			}
			dataset2.addSeries(tphyp[it-tpind1]);
			
		}

		hyp2index = dataset2.getSeriesCount();


                r2 = new XYLineAndShapeRenderer(true,true);
		Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
		float dasharr[] = {6.0f,6.0f};
	 	Stroke dashedthk = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,
		BasicStroke.JOIN_ROUND,1.0f,dasharr,0.0f);
		Stroke dashed = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,
		BasicStroke.JOIN_ROUND,1.0f,dasharr,0.0f);
		r2.setSeriesStroke(2,dashedthk);
		float dotarr[] = {2.0f,6.0f};
		float linearr[] = {1.0f};
		Stroke grid = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1.0f,linearr,0.0f);
		Stroke bdot = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1.0f,dotarr,0.0f);
                
		for(int i=1;i<=hyp2index;i++){
			
                        if(i < 3){ r2.setSeriesStroke(i-1,new BasicStroke(2.0f));
			  r2.setSeriesPaint(i-1,Color.BLACK); //black lines
			r2.setSeriesShapesVisible(i-1,false);
                        } else if (i < 5) { r2.setSeriesStroke(i-1,dashedthk);
			r2.setSeriesPaint(i-1,Color.BLACK);
			r2.setSeriesShapesVisible(i-1,false);
			}else if (i < 6) { r2.setSeriesLinesVisible(i-1,false);
				r2.setSeriesShapesVisible(i-1,true);
				r2.setSeriesShape(i-1,cross);	
				r2.setSeriesPaint(i-1,Color.CYAN);
			} else if (i < 10) { r2.setSeriesShapesVisible(i-1,false);
			r2.setSeriesStroke(i-1,dashed);
			r2.setSeriesPaint(i-1,Color.BLACK);

			} else if (i < 14) {r2.setSeriesStroke(i-1,bdot);
			 r2.setSeriesPaint(i-1,Color.BLACK); //black lines
			r2.setSeriesShapesVisible(i-1,false);
			} else if (i <= grid2index) { r2.setSeriesStroke(i-1,grid);
			r2.setSeriesPaint(i-1,Color.DARK_GRAY);
			r2.setSeriesShapesVisible(i-1,false);
                	} else { r2.setSeriesStroke(i-1,new BasicStroke(1.5f));
			r2.setSeriesPaint(i-1,Color.RED);
			r2.setSeriesShapesVisible(i-1,false);
			r2.setSeriesVisible(i-1,false);
			}
                }
		
		

	

		r2.setBaseSeriesVisibleInLegend(false);
             	}
	}	



//labels
	public static void setkvgamLab(){
		String vs = String.format("%.3f",v);
		String ks = String.format("%.2f",(double)Math.round(100*k)/100);
		String gammas = String.format("%.3f",(double) Math.round(1000*gamma)/1000);
		String s = String.format("v = %6s, k = %5s, \u03B3 = %6s", vs, ks, gammas);
		kvgamLab.setText(s);
	}
	


	public static void setShwLab() {
		String s = "S half-width = " + String.format("%1$3s",(double)Math.round(10*Shalfwidth)/10) + " s (/ls)";
		ShwLab.setText(s);
	}

	public static void setSphwLab() {
		String s = "S' half-width = " + String.format("%1$3s",(double)Math.round(10*Sphalfwidth)/10) + " s (/ls)";
		SphwLab.setText(s);
	}

	public static void settimeLab() {
		String s = "t = " + String.format("%.3f",(double)Math.round(1000*t)/1000) + " s (ts = " + String.format("%.3f",(double)Math.round(1000*ts)/1000) + " s , tf = " + String.format("%.3f",(double) Math.round(1000*tf)/1000) + " s ) t' = " + String.format("%.3f",(double)Math.round(1000*t*tfactor)/1000) + " s";
		timeLab.setText(s);
	}

	public static void setXLab() {
		String s = "x magnitude of events: " + String.format("%3.1f",(double)Math.round(10*xmag)/10) + " ls";
		xLab.setText(s);
	}

	public static class tSelected implements ItemListener {
		public static void tSelected(){
		}

		public void itemStateChanged(ItemEvent e) {
			
			if(tSelect.isSelected()){
				tfactor = 1/gamma; // restore default proper time
				tswitch = false;

				

				updatePlots();
				
				double tsave = t;
				settimeLab();
				int t0sl = (int) Math.round(1000*ts);
				int tfsl = (int) Math.round(1000*tf); 
				int tsl = (int) Math.round(1000*t);
				tslide.setMinimum(t0sl);
				tslide.setMaximum(tfsl);
				tslide.setValue(tsl);
				t = tsave;
				tref = tsave;
		
			}
		}
	}

	public static class tpSelected implements ItemListener {
		public static void tpSelected(){
		}

		public void itemStateChanged(ItemEvent e) {
				
			if(tpSelect.isSelected()){				
				tfactor = gamma; // switch time
				tswitch = true;

				updatePlots();

				double tsave = t;

				settimeLab();
				int t0sl = (int) Math.round(1000*ts*tfactor);
				int tfsl = (int) Math.round(1000*tf*tfactor); 
				int tsl = (int) Math.round(1000*t*tfactor);
				tslide.setMinimum(t0sl);
				tslide.setMaximum(tfsl);
				tslide.setValue(tsl);
				tref = t*tfactor;
				t = tsave;
				//tref = tsave;
			
			
		
			}
		}
	}

	public static void updatePlots() {

	

		
			xlim2.setRange(xpmin,xpmax);
			tlim2.setRange(tpmin,tpmax);

			xlim1.setRange(xmin,xmax);
			tlim1.setRange(tmin,tmax);

			xlim4.setRange(xpmin,xpmax);
			tlim4.setRange(t*tfactor-1.5,t*tfactor+1.5);

			xlim3.setRange(xmin,xmax);
			tlim3.setRange(t-1.5,t+1.5);
			
			spaceTimeInitialize st = new spaceTimeInitialize();
			datin = st.dataset1;
			rin = st.r1;

			spaceTimePInitialize stp = new spaceTimePInitialize();
			datin2 = stp.dataset2;
			rin2 = stp.r2;


			if(stgridon) {
					for (int i = 10; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = 10; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,true);
					}
			} else {
				for (int i = 10; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = 10; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,false);
					}
			}

			if(hypon) {
					for (int i = grid1index+1; i <= hyp1index;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = grid2index+1; i <= hyp2index;i++){
					rin2.setSeriesVisible(i-1,true);
					}
			} else {
				for (int i = grid1index+1; i <= hyp1index;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = grid2index+1; i <= hyp2index;i++){
					rin2.setSeriesVisible(i-1,false);
					}
			}

			XYSeries tax = new XYSeries("t axis");
			tax.add(0,t-1.5);
			tax.add(0,t+1.5);
			sub1 = new XYSeriesCollection(tax);
			XYSeries tpaxs = new XYSeries("t' axis in S");
			tpaxs.add(v*(t-1.5),t-1.5);
			tpaxs.add(v*(t+1.5),t+1.5);
			pulses p = new pulses();
			XYSeries events = new XYSeries("events");
			events.add(x1,t1);
			events.add(x2,t2);
			sub1.addSeries(tpaxs);
			sub1.addSeries(events);
			sub1.addSeries(p.data);	
			datin.addSeries(p.data);
			rin.setSeriesLinesVisible(datin.getSeriesCount()-1,false);
			rin.setSeriesShapesVisible(datin.getSeriesCount()-1,true);
			rin.setSeriesShape(datin.getSeriesCount()-1,r3.getSeriesShape(sub1.getSeriesCount()-1));
			rin.setSeriesPaint(datin.getSeriesCount()-1,r3.getSeriesPaint(sub1.getSeriesCount()-1));

			if ( Math.abs(t - t1) <= 0.1) { plot3.setBackgroundPaint(Color.CYAN);
			} else {plot3.setBackgroundPaint(Color.GRAY);
			}
			

			XYSeries tpax = new XYSeries("t' axis");
			tpax.add(0,t*tfactor-1.5);
			tpax.add(0,t*tfactor+1.5);
			sub2 = new XYSeriesCollection(tpax);
			XYSeries taxsp = new XYSeries("t axis in S'");
			taxsp.add(-v*(t*tfactor-1.5),t*tfactor-1.5);
			taxsp.add(-v*(t*tfactor+1.5),t*tfactor+1.5);
			pulsesp pp = new pulsesp();
			XYSeries eventsp = new XYSeries("events '");
			eventsp.add(xp1,tp1);
			eventsp.add(xp2,tp2);
			sub2.addSeries(taxsp);
			sub2.addSeries(eventsp);
			sub2.addSeries(pp.data);
			datin2.addSeries(pp.data);
			rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,false);
			rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,true);
			rin2.setSeriesShape(datin2.getSeriesCount()-1,r4.getSeriesShape(sub2.getSeriesCount()-1));
			rin2.setSeriesPaint(datin2.getSeriesCount()-1,r4.getSeriesPaint(sub2.getSeriesCount()-1));

			if ( Math.abs(t*tfactor - tp1) <= 0.1 || Math.abs(t*tfactor-tp2) <= 0.1) { plot4.setBackgroundPaint(Color.CYAN);
			} else { plot4.setBackgroundPaint(Color.GRAY);
			}
	

		


	
			plot1.setDataset(datin);
			plot1.setRenderer(rin);

			
		


			
			
			

			plot2.setDataset(datin2);
			plot2.setRenderer(rin2);	
			
			plot3.setDataset(sub1);
			plot4.setDataset(sub2);
			plot3.setRenderer(r3);
			plot4.setRenderer(r4);
				

		
		

	}

	public static class hypCheck implements ItemListener{
		public void hypCheck(){
		}

		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){
					hypon = true;
					for (int i = grid1index+1; i <= hyp1index;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = grid2index+1; i <= hyp2index;i++){
					rin2.setSeriesVisible(i-1,true);
					}
				} else {
					hypon = false;
					for (int i = grid1index+1; i <= hyp1index;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = grid2index+1; i <= hyp2index;i++){
					rin2.setSeriesVisible(i-1,false);
					}		
				}
		}
	}


	

}


