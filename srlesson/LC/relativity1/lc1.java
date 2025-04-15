package relativity1;

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
import org.jfree.chart.annotations.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.Range;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
* This source file, when compiled and run, creates an applet for visualizing the length contraction consequence of relative motion at an appreciable fraction of the speed of light. This is in effect, a visualization/animation of the derivation, using k-calculus, of length contraction in Hermann Bondi's "Relativity and Common Sense." The user can specify the relative velocity, ranging from 0 to 1 on a slider (speed of light defined as unity), the length of the "rod" being measured from 0 to 10 ls, the time at which the light pulses will simultaneously illuminate the two ends of the moving rod from 1 to 100 s, and the half-width of the S space-time diagram (S is defined as the frame in which the rod is moving) from 0.5 to 100 s / ls. The space-time diagrams for both the S and S' frames are then drawn as the user changes these parameters, with light rays corresponding to the illuminating light pulses drawn in each frame. This immediately makes apparent also the relativity of simultaneity -- the illumination of the two ends of the rod is simultaneous in S, but offset by vL in S', hence t = gamma*(t' + vx'). The space-time diagrams are initialized with light pulses, marked as red points, in positions corresponding to the first pulse being emitted at x = 0. The user can then stop, start, and slide through time at will and watch the light pulses propogate through both frames, with projections below the space-time diagrams which have a time range centered at t-1.5 and t+1.5 and t/gamma - 1.5 and t/gamma + 1.5 or t*gamma + 1.5 - t*gamma + 1.5, depending on which frame proper time is ascribed to by the user (see below), and the rod and t axes drawn for an intuitive visualization of "measuring rod" that has gridded time lines moving upward, helping intuit the passage of time as a dimension, with the length contraction and relativity of simultaneity demonstrations showing that it is a dimension with values dependent on spatial dimensional coordinates in the case of relative motion. The user can also specify which is the "proper frame" in which time passes at real (system) time. When the S frame is taken to be proper, time dilation gives consistent times for when light pulses cross the t' axis. When the S' frame is taken to be proper, time dilation gives consistent times for when light pulses cross the t axis. The user can also specify, with a check box, whether or dotted lines corresponding to the S' axis limits are drawn in S and the S axis limits are drawn in S' - showing what the regions of space-time displayed in the other frame occupy in each frame. 

Written 8/22/15 by Zachary Hall, Boston University

*/


public class lc1 {
	
	public static final int speed = 10; // update interval in ms for timer
	public static final double pOffset = 0.2; // fraction of L to extend x' beyond L
	public static double offset; // value to extend x' beyond L

	public static XYSeriesCollection datin; // public datasets to be modified for time or parameter changes
	public static XYSeriesCollection datin2;
	public static XYLineAndShapeRenderer rin; // public renderers for controlling visibility of spacetime borders
	public static XYLineAndShapeRenderer rin2;

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

	public static double k; //fundamental parameters
	public static double v;
	public static double L;
	public static double gamma;

	public static double tfactor; // 1/gamma or gamma depending on proper frame
	public static boolean tswitch = false; //boolean for changing time updates

	public static double t1; // parameters for times of light pulse,axis crossings
	public static double t2;
	public static double tr;
	public static double xr2;
	public static double xr1;
	public static double tp1;
	public static double tp2;
	public static double tp3;
	public static double tp4;
	public static double t4;
	public static double t3;
	public static double ts;
	public static double tminshow;
	public static double deltf;
	public static double tf;
	public static double t;
	public static double tref;
	public static double halfwidth;

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
	public static JSlider lslide;
	public static JSlider trslide;
	public static JSlider tslide;
	public static JSlider hwslide;

	public static JLabel kvgamLab; //public for updating
	public static JLabel lLab;
	public static JLabel trLab;
	public static JLabel hwLab;
	public static JLabel timeLab;

	public static boolean treflock = false; // boolean switches for controlling slider behaviors
	public static boolean slidelock = false;

	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				createAndShowGUI();
				timer = new Timer(speed,new movePulse());
				
		
				
			}
		});
	}

	private static void createAndShowGUI() {
	
	
		
			
		JFrame f = new JFrame("Length Contraction");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initializeConstants1(0.5); // initialize v = 0.5, L = 1.3, tr = 5, halfwidth as 1.5*tr-ts, time as emitting time, S as proper frame
		
		setL(1.3);
		settr(5);
		sethalfwidth(1.5*(tr-ts));
		initializeConstants2();	
		tfactor = 1/gamma;
		t = ts;
		tref = ts;
		

		pulsePlot p = new pulsePlot(ts); // make initial pulse datasets
		XYSeries addpulse = p.pulse;
		pulsePPlot pp = new pulsePPlot(ts);
		XYSeries addpulseP = pp.pulseP;	

		

		

		offset = pOffset*L; //initial offset
		
		spaceTimeInitialize st = new spaceTimeInitialize(); // initialize spacetime diagrams
		datin = st.dataset1;
		rin = st.r1;
		for (int i = 8; i <= datin.getSeriesCount()-1;i++){
		rin.setSeriesVisible(i-1,false);
		}
		datin.addSeries(addpulse);
		int nseries1 = datin.getSeriesCount();
		rin.setSeriesLinesVisible(nseries1-1,false);
		rin.setSeriesShapesVisible(nseries1-1,true);
		
		spaceTimePInitialize stp = new spaceTimePInitialize();
		datin2 = stp.dataset2;
		rin2 = stp.r2;
		for (int i = 8; i <= datin2.getSeriesCount()-1;i++){
		rin2.setSeriesVisible(i-1,false);
		}
		datin2.addSeries(addpulseP);	
		int nseries2 = datin2.getSeriesCount();
		rin2.setSeriesLinesVisible(nseries2-1,false);
		rin2.setSeriesShapesVisible(nseries2-1,true);
		
		

		

		JFreeChart chart1 =
 		ChartFactory.createXYLineChart("S Space-Time Diagram","x (ls)","t (s)",datin); // make charts, axes
		plot1 = chart1.getXYPlot();
		plot1.setRenderer(rin);
		plot1.setBackgroundPaint(null);

		xlim1 = (NumberAxis) plot1.getDomainAxis();
		tlim1 = (NumberAxis) plot1.getRangeAxis();
		xlim1.setRange(v*tf+L/gamma+offset/gamma-(tf-tminshow),v*tf+L/gamma+offset/gamma);
		tlim1.setRange(tminshow,tf);


		Marker origin = new ValueMarker(0,Color.BLACK,new BasicStroke(2.0f));
		plot1.addDomainMarker(origin);  //t, x axes
		plot1.addRangeMarker(origin);
	
	


		ChartPanel chartpanel1 = new ChartPanel(chart1);
		chartpanel1.setPreferredSize(new Dimension(300,300));	

		
		JFreeChart chart2 = ChartFactory.createXYLineChart("S' Space-Time Diagram","x' (ls)","t' (s)",datin2); // make charts, axes
		plot2 = chart2.getXYPlot();
		plot2.setRenderer(rin2);
		plot2.setBackgroundPaint(null);

		xlim2 = (NumberAxis) plot2.getDomainAxis();
		tlim2 = (NumberAxis) plot2.getRangeAxis();
		xlim2.setRange(L+offset-(tf-tminshow),L+offset);
		tlim2.setRange(tf/gamma-(tf-tminshow),tf/gamma);
		
		plot2.addDomainMarker(origin);
		plot2.addRangeMarker(origin);
	
	

		ChartPanel chartpanel2 = new ChartPanel(chart2);
		chartpanel2.setPreferredSize(new Dimension(300,300));
		

		AxisSpace rodax = new AxisSpace(); // for buffering rod visualization charts
		rodax.setLeft(70);
		rodax.setRight(1);
		rodax.setTop(1);
		rodax.setBottom(40);

		XYSeries rodS = new XYSeries("Rod in S"); //rod visualization is S initialization
		rodS.add(v*ts,ts);
		rodS.add(v*ts+L/gamma,ts);
		XYSeries tax = new XYSeries("Time axis");
		tax.add(0,ts-1.5);
		tax.add(0,ts+1.5);
		XYSeriesCollection cRodS = new XYSeriesCollection(rodS);	
		cRodS.addSeries(addpulse);
		cRodS.addSeries(tax);

		JFreeChart chart3 = ChartFactory.createXYLineChart(null,null,null,cRodS);
		plot3 = chart3.getXYPlot();
		XYLineAndShapeRenderer r3 = new XYLineAndShapeRenderer(true,false);
		r3.setBaseSeriesVisibleInLegend(false);
		r3.setSeriesStroke(0,new BasicStroke(10.0f));
		r3.setSeriesPaint(0,Color.BLACK);
		r3.setSeriesLinesVisible(1,false);
		r3.setSeriesShapesVisible(1,true);
		r3.setSeriesStroke(2,new BasicStroke(2.0f));
		r3.setSeriesPaint(2,Color.BLACK);
		
		plot3.setRenderer(r3);
		plot3.setFixedDomainAxisSpace(rodax);

		xlim3 = (NumberAxis) plot3.getDomainAxis();
		tlim3 = (NumberAxis) plot3.getRangeAxis();
		xlim3.setRange(v*tf+L/gamma-(tf-tminshow),v*tf+L/gamma+offset/gamma);	
		tlim3.setRange(ts-1.5,ts+1.5);
		tlim3.setTickLabelsVisible(false);


		ChartPanel chartpanel3 = new ChartPanel(chart3); // rod visualization in S' visualization
		chartpanel3.setPreferredSize(new Dimension(250,60));


		XYSeries rodSP = new XYSeries("Rod in S'");
		rodSP.add(0,ts*tfactor);
		rodSP.add(L,ts*tfactor);
		XYSeries taxp = new XYSeries("t axis in S'");
		taxp.add(-v*(ts*tfactor-1.5),ts*tfactor-1.5);
		taxp.add(-v*(ts*tfactor+1.5),ts*tfactor+1.5);
		XYSeriesCollection cRodSP = new XYSeriesCollection(rodSP);	
		cRodSP.addSeries(addpulseP);
		cRodSP.addSeries(taxp);
		

		JFreeChart chart4 = ChartFactory.createXYLineChart(null,null,null,cRodSP);
		plot4 = chart4.getXYPlot();
		XYLineAndShapeRenderer r4 = new XYLineAndShapeRenderer(true,false);
		r4.setBaseSeriesVisibleInLegend(false);
		r4.setSeriesStroke(0,new BasicStroke(10.0f));
		r4.setSeriesPaint(0,Color.BLACK);
		r4.setSeriesLinesVisible(1,false);
		r4.setSeriesShapesVisible(1,true);
		r4.setSeriesStroke(2,new BasicStroke(2.0f));
		r4.setSeriesPaint(2,Color.BLACK);
		plot4.setRenderer(r4);

		plot4.setFixedDomainAxisSpace(rodax);
		xlim4 = (NumberAxis) plot4.getDomainAxis();
		tlim4 = (NumberAxis) plot4.getRangeAxis();
		xlim4.setRange(L+offset-(tf-tminshow),L+offset);
		
		tlim4.setRange(ts*tfactor-1.5,ts*tfactor+1.5);
		tlim4.setTickLabelsVisible(false);
		ChartPanel chartpanel4 = new ChartPanel(chart4);
		chartpanel4.setPreferredSize(new Dimension(250,60));
		
	
	


	
		
	
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
		JPanel holdlLab = new JPanel();
		JPanel holdtrLab = new JPanel();	
		JPanel holdhwLab = new JPanel();

		GridBagConstraints sc = new GridBagConstraints();
		GridBagConstraints sc1 = new GridBagConstraints();
		GridBagConstraints sc2 = new GridBagConstraints();
		GridBagConstraints sc3 = new GridBagConstraints();
		sc.gridy = 0; //vertical position within slider holder
		sc1.gridy = 1;
		sc2.gridy = 2;
		sc3.gridy = 3;

		vslide = new JSlider(0,999,500); // sliders
		vslide.addChangeListener(new vChange());
		vslide.setMajorTickSpacing(100);
		vslide.setMinorTickSpacing(10);
		vslide.setPaintTicks(true);
		vslide.setName("v");
		
		vLabel();
		

		lslide = new JSlider(0,1000,130);
		lslide.addChangeListener(new lChange());
		lslide.setMajorTickSpacing(100);
		lslide.setMinorTickSpacing(10);
		lslide.setPaintTicks(true);
		lslide.setName("L");
		lLabel();

		trslide = new JSlider(10,1000,50);
		trslide.addChangeListener(new trChange());
		trslide.setMajorTickSpacing(100);
		trslide.setMinorTickSpacing(10);
		trslide.setPaintTicks(true);
		trslide.setName("tr");
		trLabel();

		int hw0 = (int) Math.round(100*halfwidth)/10;
		hwslide = new JSlider(5,1000,hw0);
		hwslide.addChangeListener(new hwChange());
		hwslide.setMajorTickSpacing(100);
		hwslide.setMinorTickSpacing(10);
		hwslide.setPaintTicks(true);
		hwslide.setName("Space-Time Half Width");
		hwLabel();


		int t0sl = (int) Math.round(1000*ts);
		int tfsl = (int) Math.round(1000*t4);
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
		lLab = new JLabel();
		trLab = new JLabel();
		hwLab = new JLabel();
		timeLab = new JLabel();
		setkvgamLab(); //external labelling methods so they can be updated
		setlLab();
		settrLab();
		sethwLab();
		settimeLab();

		Checkbox borders = new Checkbox("Display space-time borders?");
		borders.addItemListener(new stCheck());

		holdButtons.add(startButton);
		holdButtons.add(stopButton);
		holdButtons.add(resetButton);

		Dimension labsize = new Dimension(300,20);
		holdkvgamLab.add(kvgamLab);
		holdlLab.add(lLab);
		holdtrLab.add(trLab);
		holdhwLab.add(hwLab);
		holdkvgamLab.setMinimumSize(labsize);
		holdlLab.setMinimumSize(labsize);
		holdtrLab.setMinimumSize(labsize);
		holdhwLab.setMinimumSize(labsize);
		
		holdSliders.add(vslide,sc);
		holdSliders.add(holdkvgamLab,sc);
		holdSliders.add(lslide,sc1);
		holdSliders.add(holdlLab,sc1);
		holdSliders.add(trslide,sc2);
		holdSliders.add(holdtrLab,sc2);
		holdSliders.add(hwslide,sc3);
		holdSliders.add(holdhwLab,sc3);
		
		holdtslide.add(holdButtons,sc);
		holdtslide.add(tslide,sc1);
		holdtlab.add(timeLab);

		


		holdtlab.setPreferredSize(new Dimension(400,20));

		holdtslide.add(holdtlab,sc2);
		
		holdcheck.add(borders);
		
		
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
		c2.gridy = 1; // for relative positions of applet components
		c3.gridy = 2;
		c4.gridy = 3;
		c5.gridy = 4;
		c6.gridy = 5;
		c6.gridx = 1;

		JLabel author = new JLabel("Source code written 8/22/15 - Zachary Hall, Boston University");
		author.setFont( new Font("Ariel",Font.BOLD,10));
		container.add(spaceTime,c);
		container.add(spaceTimeP,c);

		

		container.add(rodSIll,c2);
		container.add(rodSPIll,c2);
		
		
		

		container.add(holdtslide,c4);
		
		container.add(holdSliders,c4);

	
		container.add(holdRadio,c5);
		container.add(holdcheck,c5);
		container.add(author,c6);

		f.add(container);
		f.pack();
		Dimension fd = f.getSize();
		f.setSize(new Dimension((int) Math.round(1.05*fd.width),(int) Math.round(1.05*fd.height))); // buffer for when velocity, time values take on more digits
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
			int tfsl = (int) Math.round(1000*t4*constant);
			
			tslide.setMinimum(t0sl);
			tslide.setMaximum(tfsl);
			tslide.setValue(t0sl);
			t = ts;
			tref = ts*constant;
		
			
			
			updatePlots();
		
		
		}
	}

	public static class lChange implements ChangeListener{
		public void lChange(){
		}
		
		public void stateChanged(ChangeEvent e){
			JSlider source = (JSlider)e.getSource();
			if(source.getValueIsAdjusting()){
			slidelock = true; //don't disable sliders due to initial time value change from initialization
			} else {
			slidelock = false; // turn switch off when done
			}
		      
			int hundL = (int)source.getValue();

			double ldum = (double)hundL/100;
			setL(ldum);
			setlLab();
			initializeConstants2(); //update

			double constant;// if S' proper, scale slider, reference time so they control t' in real time
			if(!tswitch) { constant = 1;
			} else { constant = tfactor;
			}
			int t0sl = (int) Math.round(1000*ts*constant);
			int tfsl = (int) Math.round(1000*tf*constant);
			
			tslide.setMinimum(t0sl); //update slider, make time start time
			tslide.setMaximum(tfsl);
			tslide.setValue(t0sl);
			t = ts;
			tref = ts*constant;
			
			
			updatePlots();
		
		}
	}

	public static class trChange implements ChangeListener{
		public void trChange(){
		}
		
		public void stateChanged(ChangeEvent e){
			JSlider source = (JSlider)e.getSource();

		     	 if(source.getValueIsAdjusting()){
			slidelock = true;
			} else {
			slidelock = false;
			}

			int tent = (int)source.getValue();

			double trdum = (double)tent/10;
			settr(trdum);
		
			initializeConstants2();
			settrLab();
			settimeLab();

			double constant;
			if(!tswitch) { constant = 1;
			} else { constant = tfactor;
			}
			int t0sl = (int) Math.round(1000*ts*constant);
			int tfsl = (int) Math.round(1000*t4*constant);
			
			tslide.setMinimum(t0sl);
			tslide.setMaximum(tfsl);
			tslide.setValue(t0sl); //update slider, make time start time
			t = ts;
			tref = ts*constant;
			
			
			updatePlots();
		
		
		}
	}

	public static class hwChange implements ChangeListener{
		public void hwChange(){
		}
		
		public void stateChanged(ChangeEvent e){
			JSlider source = (JSlider)e.getSource();

		     	
			
			//slidelock = false;
			

			int tenhw = (int)source.getValue();

			double hwdum = (double)tenhw/10;
			sethalfwidth(hwdum);
			initializeConstants2();
			sethwLab();
			settimeLab(); //update

			double tsave = t; //avoid precision loss
			double constant;
			if(!tswitch) { constant = 1; // scaling for proper frame switch
			} else { constant = tfactor;
			}
			int t0sl = (int) Math.round(1000*ts*constant); // update slider, keep time as current value
			int tfsl = (int) Math.round(1000*t4*constant); 
			int tsl = (int) Math.round(1000*t*constant);
			tslide.setMinimum(t0sl);
			tslide.setMaximum(tfsl);
			tslide.setValue(tsl);
			t = tsave;
			tref = t*constant;
			
			
			
			updatePlots();
		
		
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
			vslide.setEnabled(false);
			trslide.setEnabled(false);
			lslide.setEnabled(false);
		
			
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
					for (int i = 8; i <= datin.getSeriesCount()-2;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = 8; i <= datin2.getSeriesCount()-2;i++){
					rin2.setSeriesVisible(i-1,true);
					}
				} else {
					for (int i = 8; i <= datin.getSeriesCount()-2;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = 8; i <= datin2.getSeriesCount()-2;i++){
					rin2.setSeriesVisible(i-1,false);
					}		
				}
			}
				
	}

//Custom label tables for sliders

	public static void vLabel() {
		   Hashtable<Integer, JLabel> vlables = 
            new Hashtable<Integer, JLabel>();
		for (int i = 0; i <= 9; i++) {
		String s = Double.toString((double)i/10);
		JLabel lab = new JLabel(s);
		lab.setFont(new Font("Serif",Font.PLAIN,7));
		vlables.put(new Integer( (int)100*i ), lab);
		}
		
		vslide.setLabelTable(vlables);
		vslide.setPaintLabels(true);


	}

	public static void lLabel() {
		Hashtable<Integer, JLabel> llabels = 
            new Hashtable<Integer, JLabel>();
		for (int i = 0; i <= 10; i++) {
		String s = Double.toString((double)i);
		JLabel lab = new JLabel(s);
		lab.setFont(new Font("Serif",Font.PLAIN,7));
		llabels.put(new Integer( (int)100*i ), lab);
		}
		lslide.setLabelTable(llabels);
		lslide.setPaintLabels(true);
	}

	public static void trLabel() {
		Hashtable<Integer, JLabel> trlabels =
		new Hashtable<Integer, JLabel>();
		for (int i = 0; i <= 10; i++) {
		String s = Double.toString((double) 1 + i*9);
		JLabel lab = new JLabel(s);
		lab.setFont(new Font("Serif",Font.PLAIN,6));
		trlabels.put(new Integer( (int)100*i + 10), lab);
		}
		trslide.setLabelTable(trlabels);
		trslide.setPaintLabels(true);
	}

	public static void hwLabel() {
		Hashtable<Integer, JLabel> hwlabels =
		new Hashtable<Integer, JLabel>();
		for (int i = 0; i <= 10; i++) {
		String s = Double.toString((double) 0.5 + i*(1000-5)/100);
		JLabel lab = new JLabel(s);
		lab.setFont(new Font("Serif",Font.PLAIN,7));
		hwlabels.put(new Integer( (int) 5 + i*(1000-5)/10),lab);
		}
		hwslide.setLabelTable(hwlabels);
		hwslide.setPaintLabels(true);
	}


		




	public static void initializeConstants1(double vdum){ //initialize fundamental constants
		v = vdum;
		k = Math.sqrt((1+v)/(1-v));
	
		gamma = 1/(Math.sqrt(1-Math.pow(v,2)));
		if(tswitch) { tfactor = gamma;
		} else { tfactor = 1/gamma;
		}
	}

	public static void setL(double ldum){

		L = ldum;
	}

	public static void settr (double trdum) { //set reflection time (ts calculated for setting defualt half-width)
		tr = trdum;
		t2 = 2*tr/(Math.pow(k,2)+1);
		t1 = (double)Math.round((t2 - L/gamma)*1000)/1000;
		t2 = t1 + L/gamma;
		tr = t2*(Math.pow(k,2)+1)/2;
		ts = t1;
	}

	public static void sethalfwidth (double dummyw) {
		halfwidth = dummyw;
	}

	public static void initializeConstants2() { //derived values for making spacetime diagrams
		t2 = 2*tr/(Math.pow(k,2)+1); //given reflection time, time second pulse must be emitted in S
		t1 = (double)Math.round((t2 - L/gamma)*1000)/1000; // time first pulse must be emitted in S, rounded to ms precision
		t2 = t1 + L/gamma; 
		tr = t2*(Math.pow(k,2)+1)/2; // recalculated to be consistent with ms precision t1
		ts = t1; // start time for animation
		xr2 = v*tr; // position of second pulse, near end of rod at reflection time
		xr1 = xr2+L/gamma; // position of first pulse, far end of rod at reflection time
		tp1 = k*t1; // time in S' when first pulse crosses t' axis
		tp2 = tp1 + L; //time in S' when first pulse reaches x' = L, reflects
		tp3 = k*t2; //time in S' when second pulse reach t' axis, reflects
		tp4 = tp2 + L; // time when first pulse returns to t' axis
		t4 = k*tp4; // time when 1st pulse returns to t axis
		t3 = k*tp3; //time when 2nd pulse returns to t axis
		tf = tr + halfwidth; // maximum time shown
		tminshow = tr - halfwidth;
	
	}


		
	public static class startPressed implements ActionListener {
	
		public startPressed(){
		}

		public void actionPerformed(ActionEvent e) {
			if (startcount == 0){
			
			vslide.setEnabled(false);
			lslide.setEnabled(false);
			trslide.setEnabled(false);
			
			
			}
			
			
			startTime = System.currentTimeMillis();
			
			
			timer.start();
			stopButton.setEnabled(true);
			resetButton.setEnabled(false);
			startButton.setEnabled(false);
			tslide.setEnabled(false);
			hwslide.setEnabled(false);
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
			hwslide.setEnabled(true);
			tSelect.setEnabled(true);
			tpSelect.setEnabled(true);
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
			lslide.setEnabled(true);
			trslide.setEnabled(true);
			hwslide.setEnabled(true);

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

			if(t >= t4){
				timer.stop();
				//tref = t/constant;
				
				//startButton.setEnabled(true); // re-enable for greater functionality, but performance can be buggy (code needs to be more robust for this)
				stopButton.setEnabled(false);
				resetButton.setEnabled(true);
				hwslide.setEnabled(true);
				tslide.setEnabled(true);
			}
	
		}
	}
		

		

		
		//makes space-time diagram dataset and renderer
	public static class spaceTimeInitialize {

		
		
		private XYSeriesCollection dataset1;
		private XYLineAndShapeRenderer r1;
		
		public spaceTimeInitialize() {
		XYSeries tp = new XYSeries("t' axis");
		tp.add(tminshow*v,tminshow);
		tp.add(tf*v,tf);
		XYSeries xp = new XYSeries("x' axis");
		xp.add(tminshow/v,tminshow);
		xp.add(tf/v,tf);
		XYSeries xpL = new XYSeries("x' axis L-shifted");
		xpL.add(xr1+(tminshow-tr)*v,tminshow);
		xpL.add(xr1+(tf-tr)*v,tf);
		XYSeries p2s1 = new XYSeries("pulse 2 segment 1");
		XYSeries p2s2 = new XYSeries("pulse 2 segment 2");
		p2s1.add(tminshow+xr2-tr,tminshow);
		p2s1.add(xr2,tr);
		p2s2.add(xr2,tr);
		p2s2.add(-tf+tr+xr2,tf);
		XYSeries p1s1 = new XYSeries("pulse 1 segment 1");
		XYSeries p1s2 = new XYSeries("pulse 1 segment 2");
		p1s1.add(tminshow+xr1-tr,tminshow);
		p1s1.add(xr1,tr);
		p1s2.add(xr1,tr);
		p1s2.add(-tf+tr+xr1,tf);
		XYSeries lseg = new XYSeries("Proper Length Segment");
		lseg.add(xr1,tr);
		lseg.add(xr1-gamma*L,tr-gamma*v*L);
		//if (v != 0) {

		double minxp = L+offset-(tf-tminshow);
		double maxxp = L+offset;
		double mintp = tf/gamma-(tf-tminshow);
		double maxtp = tf/gamma;

		double ti1 = (minxp/gamma + maxtp/(gamma*v))/(1/v-v);
		double ti2 = (maxxp/gamma + maxtp/(gamma*v))/(1/v-v);
		double ti3 = (minxp/gamma + mintp/(gamma*v))/(1/v-v);
		double ti4 = (maxxp/gamma + mintp/(gamma*v))/(1/v-v);
		//}
			
		XYSeries tpmin = new XYSeries("min edge of t' frame");
		XYSeries tpmax = new XYSeries("max edge of t' frame");
		XYSeries xpmin = new XYSeries("min edge of x'frame");
		XYSeries xpmax = new XYSeries("max edge of x' frame");
		
		if (v != 0) {
		tpmin.add((ti3-mintp/gamma)/v,ti3);
		tpmin.add((ti4-mintp/gamma)/v,ti4);
		
		tpmax.add((ti1-maxtp/gamma)/v,ti1);
		tpmax.add((ti2-maxtp/gamma)/v,ti2);

		xpmin.add(v*ti3 + minxp/gamma,ti3);
		xpmin.add(v*ti1 + minxp/gamma,ti1);
	
		xpmax.add(v*ti4 + maxxp/gamma,ti4);
		xpmax.add(v*ti2 + maxxp/gamma,ti2);
		}

		dataset1 = new XYSeriesCollection(tp);
		dataset1.addSeries(xp);
		dataset1.addSeries(xpL);
		dataset1.addSeries(p1s1);
		dataset1.addSeries(p1s2);
		dataset1.addSeries(p2s1);
		dataset1.addSeries(p2s2);
		dataset1.addSeries(tpmax);
		dataset1.addSeries(tpmin);
		dataset1.addSeries(xpmax);
		dataset1.addSeries(xpmin);
		dataset1.addSeries(lseg);
		r1 = new XYLineAndShapeRenderer(true,false);
		
		float dasharr[] = {6.0f,6.0f};
		Stroke dashed = new BasicStroke(1.0f,BasicStroke.CAP_ROUND
		,BasicStroke.JOIN_ROUND,1.0f,dasharr,0.0f);
		float dotarr[] = {2.0f,6.0f};
		Stroke dot = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1.0f,dotarr,0.0f);
		int nseries = dataset1.getSeriesCount();
		for(int i=1;i<=nseries;i++){
		r1.setSeriesPaint(i-1,Color.BLACK); //black lines
			if(i < 4){ r1.setSeriesStroke(i-1,new BasicStroke(2.0f));
			} else if(i < 8) { r1.setSeriesStroke(i-1,dashed);
			} else {r1.setSeriesStroke(i-1,dot);
			}
		}	
                Stroke dashedthk = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,
		BasicStroke.JOIN_ROUND,1.0f,dasharr,0.0f);
		r1.setSeriesStroke(2,dashedthk);
		float sdasharr[] = {2.0f,2.0f};
		Stroke sdashed = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,
		BasicStroke.JOIN_ROUND,1.0f,sdasharr,0.0f);
		r1.setSeriesStroke(nseries-1,sdashed);
		r1.setBaseSeriesVisibleInLegend(false);
		
	
	
		
		
	
		}
		
	
	}

	// makes dataset for two pulse points in S at time t
	public static class pulsePlot {
		private XYSeries pulse;
		
		public pulsePlot(double tdum){
		pulse = new XYSeries("Pulse");
		if(tdum < tr){
		pulse.add(tdum-t1,tdum);
		pulse.add(tdum-t2,tdum);
		} else {
		pulse.add(t4-tdum,tdum);
		pulse.add(t3-tdum,tdum);
		}
		}	


	}

	//makes S' space-time diagram dataset and renderer
	public static class spaceTimePInitialize {
		private XYSeriesCollection dataset2;
		private XYLineAndShapeRenderer r2;

		public spaceTimePInitialize() {
		
	        XYSeries tt = new XYSeries("t axis");
                tt.add(-v*(tf/gamma-(tf-tminshow)),tf/gamma-(tf-tminshow));
                tt.add(-v*(tf/gamma),tf/gamma);
                XYSeries xx = new XYSeries("x axis");
                xx.add(-(tf/gamma-(tf-tminshow))/v,tf/gamma-(tf-tminshow));
                xx.add(-(tf/gamma)/v,tf/gamma);    
		XYSeries xL = new XYSeries("t axis, L shifted");
		xL.add(L,tf/gamma-(tf-tminshow));
		xL.add(L,tf/gamma);        
                XYSeries pp2s1 = new XYSeries("pulse' 2 segment 1");
                XYSeries pp2s2 = new XYSeries("pulse' 2 segment 2");
                pp2s1.add((tf/gamma-(tf-tminshow))-tp3,tf/gamma-(tf-tminshow));
                pp2s1.add(0,tp3);
                pp2s2.add(0,tp3);
                pp2s2.add(-tf/gamma+tp3,tf/gamma);
                XYSeries pp1s1 = new XYSeries("pulse' 1 segment 1");
                XYSeries pp1s2 = new XYSeries("pulse' 1 segment 2");
                pp1s1.add(tf/gamma-(tf-tminshow)+L-tp2,tf/gamma-(tf-tminshow));
                pp1s1.add(L,tp2);
                pp1s2.add(L,tp2);
                pp1s2.add(-tf/gamma+tp2+L,tf/gamma);
                XYSeries lsegp = new XYSeries("Proper Length segment (rest)");
		lsegp.add(0,tp2);
		lsegp.add(L,tp2);

		XYSeries xmax = new XYSeries("max xp");
		XYSeries xmin = new XYSeries("min xp");
		XYSeries tmax = new XYSeries("max tp");
		XYSeries tmin = new XYSeries("min tp");
		
		double minx = v*tf+L/gamma+offset/gamma-(tf-tminshow);
		double maxx = v*tf+L/gamma+offset/gamma;
		double mint = tminshow;
		double maxt = tf;
		double ti1 = (maxt/(gamma*v) - minx/gamma)/(1/v-v);
		double ti2 = (maxt/(gamma*v) - maxx/gamma)/(1/v-v);
		double ti3 = (mint/(gamma*v) - minx/gamma)/(1/v-v);
		double ti4 = (mint/(gamma*v) - maxx/gamma)/(1/v-v);
		if (v != 0){	
		tmin.add(-(ti4-mint/gamma)/v,ti4);
		tmin.add(-(ti3-mint/gamma)/v,ti3);
		tmax.add(-(ti2-maxt/gamma)/v,ti2);
		tmax.add(-(ti1-maxt/gamma)/v,ti1);
		xmin.add(-v*ti3+minx/gamma,ti3);
		xmin.add(-v*ti1+minx/gamma,ti1);
		xmax.add(-v*ti4+maxx/gamma,ti4);
		xmax.add(-v*ti2+maxx/gamma,ti2);
		}

		dataset2 = new XYSeriesCollection(tt);
                dataset2.addSeries(xx);
		dataset2.addSeries(xL);
                dataset2.addSeries(pp1s1);
                dataset2.addSeries(pp1s2);
                dataset2.addSeries(pp2s1);
                dataset2.addSeries(pp2s2);
		dataset2.addSeries(tmax);
		dataset2.addSeries(tmin);
		dataset2.addSeries(xmax);
		dataset2.addSeries(xmin);
		dataset2.addSeries(lsegp);
                r2 = new XYLineAndShapeRenderer(true,false);
	 	float dasharr[] = {6.0f,6.0f};
		Stroke dashed = new BasicStroke(1.0f,BasicStroke.CAP_ROUND
		,BasicStroke.JOIN_ROUND,1.0f,dasharr,0.0f);
		float dotarr[] = {2.0f,6.0f};
		Stroke dot = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1.0f,dotarr,0.0f);
                int nseries2 = dataset2.getSeriesCount();
		for(int i=1;i<=nseries2;i++){
		  r2.setSeriesPaint(i-1,Color.BLACK); //black lines
                        if(i < 4){ r2.setSeriesStroke(i-1,new BasicStroke(2.0f));
                        } else if (i < 8) { r2.setSeriesStroke(i-1,dashed);
			} else { r2.setSeriesStroke(i-1,dot);
                	} 
                }
		float sdasharr[] = {2.0f,2.0f};
		Stroke sdashed = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,
		BasicStroke.JOIN_ROUND,1.0f,sdasharr,0.0f);
		r2.setSeriesStroke(nseries2-1,sdashed);

		Stroke dashedthk = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,
		BasicStroke.JOIN_ROUND,1.0f,dasharr,0.0f);
		r2.setSeriesStroke(2,dashedthk);

		r2.setBaseSeriesVisibleInLegend(false);
             	}
	}	

		// makes dataset for two pulse locations in S' at time t' corresponding to time t
	public static class pulsePPlot {
	
		private XYSeries pulseP;
		
		public pulsePPlot(double tdum) {
			double tp = tdum*tfactor;
			pulseP = new XYSeries("Pulse'");
			if (tp <= tp2){
			pulseP.add(tp-tp1,tp);
			pulseP.add(tp-tp3,tp);
			} else if(tp <= tp3){
			pulseP.add(tp4-tp,tp);
			pulseP.add(tp-tp3,tp);
			} else {
			pulseP.add(tp4-tp,tp);
			pulseP.add(tp3-tp,tp);
			}
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
	
	public static void setlLab(){
		String s = "L = " + String.format("%1$3s" ,L) + " ls";
		lLab.setText(s);
	}

	public static void settrLab(){
		String s = "Rod illumination time = " + String.format("%1$3s", (double)Math.round(10*tr)/10) + " s";
		trLab.setText(s);
	}

	public static void sethwLab() {
		String s = "S half-width = " + String.format("%1$3s",(double)Math.round(10*halfwidth)/10) + " s (/ls)";
		hwLab.setText(s);
	}

	public static void settimeLab() {
		String s = "t = " + String.format("%.3f",(double)Math.round(1000*t)/1000) + " s (ts = " + String.format("%.3f",(double)Math.round(1000*ts)/1000) + " s , tf = " + String.format("%.3f",(double) Math.round(1000*t4)/1000) + " s ) t' = " + String.format("%.3f",(double)Math.round(1000*t*tfactor)/1000) + " s";
		timeLab.setText(s);
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
				int tfsl = (int) Math.round(1000*t4); 
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
				int tfsl = (int) Math.round(1000*t4*tfactor); 
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

		        XYSeries rodS = new XYSeries("Rod in S"); //redraws plots for time or any possible parameter change
			rodS.add(v*t,t);
			rodS.add(v*t+L/gamma,t);
			XYSeries rodSP = new XYSeries("Rod in S'");
			rodSP.add(0,t*tfactor);
			rodSP.add(L,t*tfactor);
			pulsePlot p = new pulsePlot(t);
			XYSeries addpulse = p.pulse;
			pulsePPlot pp = new pulsePPlot(t);
			XYSeries addpulseP = pp.pulseP;
		

			offset = pOffset*L;
			xlim2.setRange(L+offset-(tf-tminshow),L+offset);
			tlim2.setRange(tf/gamma-(tf-tminshow),tf/gamma);

			xlim1.setRange(v*tf+L/gamma+offset/gamma-(tf-tminshow),v*tf+L/gamma+offset/gamma);
			tlim1.setRange(tminshow,tf);
			xlim4.setRange(L+offset-(tf-tminshow),L+offset);
			tlim4.setRange(t*tfactor-1.5,t*tfactor+1.5);

			xlim3.setRange(v*tf+L/gamma-(tf-tminshow),v*tf+L/gamma+offset);
			tlim3.setRange(t-1.5,t+1.5);
			
		
			
	

			spaceTimeInitialize st = new spaceTimeInitialize();
			XYSeriesCollection datin = st.dataset1;
			datin.addSeries(addpulse);		
			plot1.setDataset(datin);
		


			spaceTimePInitialize stp = new spaceTimePInitialize();
			XYSeriesCollection datin2 = stp.dataset2;
			datin2.addSeries(addpulseP);	
			plot2.setDataset(datin2);		
				

			XYSeries tax = new XYSeries("t axis");
			tax.add(0,t-1.5);
			tax.add(0,t+1.5);
			XYSeriesCollection cRodS = new XYSeriesCollection(rodS);
			cRodS.addSeries(addpulse);
			cRodS.addSeries(tax);
			plot3.setDataset(cRodS);
			
			XYSeries taxp = new XYSeries("t axis in S'");
			taxp.add(-v*(t*tfactor-1.5),t*tfactor-1.5);
			taxp.add(-v*(t*tfactor+1.5),t*tfactor+1.5);
			XYSeriesCollection cRodSP = new XYSeriesCollection(rodSP);
			cRodSP.addSeries(addpulseP);
			cRodSP.addSeries(taxp);
			plot4.setDataset(cRodSP);
	}

}


