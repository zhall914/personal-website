package relativity3;

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
import javax.swing.BoxLayout;
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
* This source file, when compiled and run, creates an applet for visualizing the length contraction consequence of relative motion at an appreciable fraction of the speed of light. This is in effect, a visualization/animation of the derivation, using k-calculus, of length contraction in Hermann Bondi's "Relativity and Common Sense." The user can specify the relative velocity, ranging from 0 to 1 on a slider (speed of light defined as unity), the length of the "rod" being measured from 0 to 10 ls, the time at which the light pulses will simultaneously illuminate the two ends of the moving rod from 1 to 100 s, and the half-width of the S space-time diagram (S is defined as the frame in which the rod is moving) from 0.5 to 100 s / ls. The space-time diagrams for both the S and S' frames are then drawn as the user changes these parameters, with light rays corresponding to the illuminating light pulses drawn in each frame. This immediately makes apparent also the relativity of simultaneity -- the illumination of the two ends of the rod is simultaneous in S, but offset by vL in S', hence t = gamma*(t' + vx'). The space-time diagrams are initialized with light pulses, marked as red points, in positions corresponding to the first pulse being emitted at x = 0. The user can then stop, start, and slide through time at will and watch the light pulses propogate through both frames, with projections below the space-time diagrams which have a time range centered at t-1.5 and t+1.5 and t/gamma - 1.5 and t/gamma + 1.5 or t*gamma + 1.5 - t*gamma + 1.5, depending on which frame proper time is ascribed to by the user (see below), and the rod and t axes drawn for an intuitive visualization of "measuring rod" that has gridded time lines moving upward, helping intuit the passage of time as a dimension, with the length contraction and relativity of simultaneity demonstrations showing that it is a dimension with values dependent on spatial dimensional coordinates in the case of relative motion. The user can also specify which is the "proper frame" in which time passes at real (system) time. When the S frame is taken to be proper, time dilation gives consistent times for when light pulses cross the t' axis. When the S' frame is taken to be proper, time dilation gives consistent times for when light pulses cross the t axis. The user can also specify, with a check box, whether or dotted lines corresponding to the S' axis limits are drawn in S and the S axis limits are drawn in S' - showing what the regions of space-time displayed in the other frame occupy in each frame. 

Written 8/22/15 by Zachary Hall, Boston University

*/


/*update "update plots," add clear, delete buttons for hlines*/

public class sandbox {
	

	public static XYSeriesCollection datin; // public datasets to be modified for time or parameter changes
	public static XYSeriesCollection datin2;


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

	public static ChartPanel chartpanel1;
	public static ChartPanel chartpanel2;

	public static XYSeries xvals;
	public static XYSeries xpvals;
	public static XYSeries tvals;
	public static XYSeries tpvals;

	public static XYSeriesCollection stEventsS;
	public static XYSeriesCollection stpEventsS;
	public static XYSeriesCollection stEventsSP;
	public static XYSeriesCollection stpEventsSP;

	public static ChartMouseListener listen11;
	public static ChartMouseListener listen12;
	public static ChartMouseListener listen13;
	public static ChartMouseListener listen21;
	public static ChartMouseListener listen22;
	public static ChartMouseListener listen23;

	public static double k; //fundamental parameters
	public static double v;
	public static double gamma;

	public static double tfactor; // 1/gamma or gamma depending on proper frame
	public static boolean tswitch = false; //boolean for changing time updates

	public static double t1; // parameters for times of light pulse,axis crossings
	public static double t2;
	public static double xr2;
	public static double xr1;
	public static double tp1;
	public static double tp2;
	public static double tp3;
	public static double tp4;
	public static double t4;
	public static double t3;
	public static double ts;
	public static double tmin;
	public static double tmax;
	public static double xmin;
	public static double xmax;
	public static double tpmin;
	public static double tpmax;
	public static double xpmin;
	public static double xpmax;

	public static int grid1index;
	public static int grid2index;

	public static int hyp1index;
	public static int hyp2index;

	public static int iS = 0;
	public static int ilS = 0;
	public static int ihlS = 0;
	public static int iSP = 0;
	public static int ilSP = 0;
	public static int ihlSP = 0;
	public static int moveCountS = 0;
	public static int moveCountlS = 0;
	public static int moveCounthlS = 0;
	public static int moveCountSP = 0;
	public static int moveCountlSP = 0;
	public static int moveCounthlSP = 0;
	
	
	public static int n = 0;
	public static int nl = 0;
	public static int nhl = 0;
	
	
	


	public static double Sphalfwidth;
	public static double Shalfwidth;

	

	public static JButton clearButton;
	public static JButton deleteButton;
	public static JButton deletelButton;
	public static JButton deletehlButton;
	public static JButton clearlButton;
	public static JButton clearhlButton;

	public static JRadioButton tSelect;
	public static JRadioButton tpSelect;

	public static JRadioButton vlineSelect;
	public static JRadioButton hlineSelect;
	public static JRadioButton eventSelect;

	public static JSlider vslide;

	public static JSlider tslide;
	public static JSlider hwSslide;
	public static JSlider hwSpslide;

	public static JLabel kvgamLab; //public for updating
	public static JLabel ShwLab;
	public static JLabel SphwLab;
	public static JLabel timeLab;

	public static boolean treflock = false; // boolean switches for controlling slider behaviors
	public static boolean slidelock = false;
	public static boolean stgridon = true; //for space-time grids
	public static boolean hypon = true;
	public static boolean delswitch = false;
	public static boolean delswictchl = false;
	public static boolean pulseswitch = false;
	public static boolean pulseswitchlag = false;
	public static boolean pulseswitchlagp = false;
	public static boolean twopulse;
	public static boolean twopulsep;
	public static boolean prime;
	public static boolean primel;
	public static boolean primehl;

	public static java.util.List<Boolean> primearr;	
	public static java.util.List<Boolean> primearrl;
	public static java.util.List<Boolean> primearrhl;
	public static java.util.List<Boolean> pulsearr;
	public static java.util.List<Boolean> pulsearrs;
	public static java.util.List<Boolean> pulsearrp;
	public static Stroke dashed;

	public static Color primeAxCol = Color.BLUE;
	public static Color hypCol = Color.RED;

	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				createAndShowGUI();
				
				
		
				
			}
		});
	}

	private static void createAndShowGUI() {
	
		float dasharr[] = {6.0f,6.0f};
		dashed = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,
		BasicStroke.JOIN_ROUND,1.0f,dasharr,0.0f);

		xvals = new XYSeries("xvals");
		xpvals = new XYSeries("xpvals");
		tvals = new XYSeries("tvals");
		tpvals = new XYSeries("tpvals");
			
		JFrame f = new JFrame("Relativistic Sandbox");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initializeConstants1(0); // initialize v = 0.5, L = 1.3, tr = 5, halfwidth as 1.5*tr-ts, time as emitting time, S as proper frame
		
		
	
		setSphalfwidth(5);
		setShalfwidth(5);
		
		tfactor = 1/gamma;
		
		
		primearr = new ArrayList<Boolean>();
		primearrl = new ArrayList<Boolean>();
		primearrhl = new ArrayList<Boolean>();
	
		pulsearrs = new ArrayList<Boolean>();
		pulsearrp = new ArrayList<Boolean>();


	

		

		

		
		
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
		Marker originp = new ValueMarker(0,primeAxCol,new BasicStroke(2.0f));
		plot1.addDomainMarker(origin);  //t, x axes
		plot1.addRangeMarker(origin);
	
	


		chartpanel1 = new ChartPanel(chart1);
		chartpanel1.setPreferredSize(new Dimension(300,300));
		
		
		listen11 = new eventSlisten();
		listen12 = new lineSlisten();
		listen13 = new hlineSlisten();

		chartpanel1.addChartMouseListener(listen11);	
		chartpanel1.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e1) {
			delswitch = false;
			}
			
			
			public void mouseExited(MouseEvent e2) {

			updatePlots();
			}
			
		
		});


		

		
	
		JFreeChart chart2 = ChartFactory.createXYLineChart("S' Space-Time Diagram","x' (ls)","t' (s)",datin2); // make charts, axes
		plot2 = chart2.getXYPlot();
		plot2.setRenderer(rin2);
		plot2.setBackgroundPaint(null);

		xlim2 = (NumberAxis) plot2.getDomainAxis();
		tlim2 = (NumberAxis) plot2.getRangeAxis();
		xlim2.setRange(xpmin,xpmax);
		tlim2.setRange(tpmin,tpmax);
		
		plot2.addDomainMarker(originp);
		plot2.addRangeMarker(originp);
	
	

		chartpanel2 = new ChartPanel(chart2);
		chartpanel2.setPreferredSize(new Dimension(300,300));
		
		
		listen21 = new eventSPlisten();
		listen22 = new lineSPlisten();
		listen23 = new hlineSPlisten();

		chartpanel2.addChartMouseListener(listen21);
		chartpanel2.addMouseListener(new MouseAdapter() {

			
			public void mouseEntered(MouseEvent e1) {
			delswitch = false;
			}
			
			
			public void mouseExited(MouseEvent e2) {

			updatePlots();
			}
			
		
		});

	
		
		
	
		
	
		JPanel container = new JPanel(new GridBagLayout()); // container to add to frame

		JPanel spaceTime = new JPanel();
		JPanel spaceTimeP = new JPanel();
		JPanel rodSIll = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel rodSPIll = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel holdButtons = new JPanel();
		JPanel holdstButtons = new JPanel(new GridBagLayout());
	
		
		spaceTime.add(chartpanel1);
		spaceTimeP.add(chartpanel2);
	
		

		JPanel holdSliders = new JPanel(new GridBagLayout()); // holds v, L, tr sliders
		JPanel holdtslide = new JPanel(new GridBagLayout()); // holds time slider, time buttons, time label
		JPanel holdhwslide = new JPanel(new GridBagLayout());
		JPanel holdcheck = new JPanel(new GridBagLayout());
	
		JPanel holdkvgamLab = new JPanel();	
		

		JPanel holdhws = new JPanel(new GridBagLayout());
		JPanel holdhwsp = new JPanel(new GridBagLayout());

		GridBagConstraints sc = new GridBagConstraints();
		GridBagConstraints sc1 = new GridBagConstraints();
		GridBagConstraints sc2 = new GridBagConstraints();
		GridBagConstraints sc3 = new GridBagConstraints();
		sc.gridy = 0; //vertical position within slider holder
		sc1.gridy = 1;
		sc2.gridy = 2;
		sc3.gridy = 3;

		vslide = new JSlider(-999,999,0); // sliders
		vslide.addChangeListener(new vChange());
		vslide.setMajorTickSpacing(500);
		//vslide.setMinorTickSpacing(10);
		vslide.setPaintTicks(true);
		vslide.setName("v");
		
		vLabel();
		


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


	
	
		

		

		ActionListener cp = new clearPressed();
		clearButton = new JButton("Clear space-time events");
		clearButton.addActionListener(cp);
		clearButton.setEnabled(false);

		ActionListener dp = new deletePressed();
		deleteButton = new JButton("Delete space-time event");
		deleteButton.addActionListener(dp);
		deleteButton.setEnabled(false);

		ActionListener cpl = new clearlPressed();
		clearlButton = new JButton("Clear rest lines");
		clearlButton.addActionListener(cpl);
		clearlButton.setEnabled(false);

		ActionListener dpl = new deletelPressed();
		deletelButton = new JButton("Delete rest line");
		deletelButton.addActionListener(dpl);
		deletelButton.setEnabled(false);

		ActionListener chpl = new clearhlPressed();
		clearhlButton = new JButton("Clear global time lines");
		clearhlButton.addActionListener(chpl);
		clearhlButton.setEnabled(false);
		
		ActionListener dhpl = new deletehlPressed();
		deletehlButton = new JButton("Delete global time line");
		deletehlButton.addActionListener(dhpl);
		deletehlButton.setEnabled(false);

		
		vlineSelect = new JRadioButton("Add rest line");
		vlineSelect.addItemListener(new vlineSelected());

		hlineSelect = new JRadioButton("Add time line");
		hlineSelect.addItemListener(new hlineSelected());


		eventSelect = new JRadioButton("Add event");
		eventSelect.setSelected(true);
		eventSelect.addItemListener(new eventSelected());
	

	
		ButtonGroup group2 = new ButtonGroup();
	
		JPanel holdRadio2 = new JPanel();		

		group2.add(eventSelect);
		group2.add(vlineSelect);
		group2.add(hlineSelect);
	
		holdRadio2.add(eventSelect);
		holdRadio2.add(vlineSelect);
		holdRadio2.add(hlineSelect);
		
		kvgamLab = new JLabel();
		ShwLab = new JLabel();
		SphwLab = new JLabel();

		holdhws.add(hwSslide,sc1);
		holdhws.add(ShwLab,sc2);
		holdhwsp.add(hwSpslide,sc1);
		holdhwsp.add(SphwLab,sc2);
		
		setkvgamLab(); //external labelling methods so they can be updated
		
		setShwLab();
		setSphwLab();
	

		Checkbox stgrid = new Checkbox("Display space-time grids?");
		stgrid.addItemListener(new stCheck());
		stgrid.setState(true);

		Checkbox pulses = new Checkbox("Add light pulse to this event?");
		pulses.addItemListener(new pulseCheck());
		pulses.setState(false);

		Checkbox hyp = new Checkbox("Display hyperbolas?");
		hyp.addItemListener(new hypCheck());
		hyp.setState(true);

	

		JLabel author = new JLabel("Source code written 9/10/15 - Zachary Hall, Boston University",SwingConstants.CENTER);
		author.setFont( new Font("Ariel",Font.BOLD,10));

		JPanel holdauth = new JPanel();
		holdauth.add(author);

		holdstButtons.add(clearButton,sc);
		holdstButtons.add(deleteButton,sc);
		holdstButtons.add(clearlButton,sc1);
		holdstButtons.add(deletelButton,sc1);
		holdstButtons.add(clearhlButton,sc2);
		holdstButtons.add(deletehlButton,sc2);
		holdstButtons.add(author,sc3);
		
		

		
		holdkvgamLab.add(kvgamLab);
		Dimension kvd = holdkvgamLab.getSize();
		Dimension labsize = new Dimension((int) Math.round(1.0*kvd.width),(int) Math.round(kvd.height));
		
	
		holdkvgamLab.setMinimumSize(labsize);
	
		
		holdSliders.add(vslide,sc);
		holdSliders.add(holdkvgamLab,sc);
	


		


	
		
		holdcheck.add(stgrid,sc);
		holdcheck.add(hyp,sc1);
		holdcheck.add(pulses,sc2);
		
		
		
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
		c7.gridy = 6;
		c7.gridx = 1;
	

	
		container.add(spaceTime,c);
		container.add(spaceTimeP,c);

		

		container.add(holdhws,c2);
		container.add(holdhwsp,c2);

		JPanel container2 = new JPanel();
		container2.setLayout(new BoxLayout(container2,BoxLayout.PAGE_AXIS));
		
		
		

		container2.add(container);
		
		container2.add(vslide);
		container2.add(kvgamLab);
	

		container2.add(holdcheck);
		container2.add(holdRadio2);
		container2.add(holdstButtons);
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
		      
			
			delswitch = false;

			int thouV = (int)source.getValue();
             
			double vdum = (double)thouV/1000;
			initializeConstants1(vdum);
			setkvgamLab();
			 //update

		
	
			
			
			updatePlots();
		
		
		
		}
	}


	

	public static class ShwChange implements ChangeListener{
		public void ShwChange(){
		}
		
		public void stateChanged(ChangeEvent e){
			JSlider source = (JSlider)e.getSource();

		     	
			delswitch = false;
			
			

			int tenhw = (int)source.getValue();

			double hwdum = (double)tenhw/10;
			setShalfwidth(hwdum);
		
			setShwLab();
			

			
			
			
			
			updatePlots();
			
		
		
		}
	}
	
		public static class SphwChange implements ChangeListener{
		public void SphwChange(){
		}
		
		public void stateChanged(ChangeEvent e){
			JSlider source = (JSlider)e.getSource();

		     	
			delswitch = false;
			
			

			int tenhw = (int)source.getValue();

			double hwdum = (double)tenhw/10;
			setSphalfwidth(hwdum);
		
			setSphwLab();
			

			
			
			
			
			updatePlots();
			
		
		
		}
	}


	

	public static class stCheck implements ItemListener{ //controls visibility of space-time border datasets through public renderers
			public void stCheck(){
			}
			
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					stgridon = true;
					for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,true);
					}
				} else {
					stgridon = false;
					for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,false);
					}		
				}
			}
				
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




	public static class pulseCheck implements ItemListener{
			public void pulseCheck(){
			}

			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {pulseswitch = true;
				} else { pulseswitch = false;
				}
			}
	}

//Custom label tables for sliders

	public static void vLabel() {
		   Hashtable<Integer, JLabel> vlabels = 
            new Hashtable<Integer, JLabel>();

		
		vlabels.put(new Integer( (int) -500), new JLabel("v = -0.5"));
		vlabels.put(new Integer( (int) 0), new JLabel("v = 0"));
		vlabels.put(new Integer( (int) 500), new JLabel("v = 0.5"));
		
		vslide.setLabelTable(vlabels);
		vslide.setPaintLabels(true);


	}



	public static void ShwLabel() {
		Hashtable<Integer, JLabel> hwlabels =
		new Hashtable<Integer, JLabel>();
		

		hwlabels.put(new Integer( (int) 5),new JLabel("0.5"));
		hwlabels.put(new Integer( (int) 1000), new JLabel("100"));
		hwSslide.setLabelTable(hwlabels);
		hwSslide.setPaintLabels(true);
		hwSpslide.setLabelTable(hwlabels);
		hwSpslide.setPaintLabels(true);
		
	}


		




	public static void initializeConstants1(double vdum){ //initialize fundamental constants
		v = vdum;
		k = Math.sqrt((1+Math.abs(v))/(1-Math.abs(v)));
	
		gamma = 1/(Math.sqrt(1-Math.pow(v,2)));
		
	}

	



	public static void setShalfwidth (double dummyw) {
		Shalfwidth = dummyw;
		tmin = -Shalfwidth;
		xmin = -Shalfwidth;
		tmax = Shalfwidth;
		xmax = Shalfwidth;
		ts = tmin;
		t4 = tmax;
	}

	public static void setSphalfwidth (double dummyw) {
		Sphalfwidth = dummyw;
		tpmin = -Sphalfwidth;
		xpmin = -Sphalfwidth;
		tpmax = Sphalfwidth;
		xpmax = Sphalfwidth;

		
	}




	
	public static class clearlPressed implements ActionListener {
		public clearlPressed() {
		}
	
		public void actionPerformed(ActionEvent e) {

		xvals = new XYSeries("xvals");
		xpvals = new XYSeries("xpvals");

		updatePlots();

			moveCountlS = 0;
			ilS = 0;
			moveCountlSP = 0;
			ilSP = 0;
			nl = 0;
			delswitch = false;
			clearlButton.setEnabled(false);
			deletelButton.setEnabled(false);
			primearrl = new ArrayList<Boolean>();

		


		}

	}

	public static class clearhlPressed implements ActionListener {
		public clearhlPressed() {
		}
	
		public void actionPerformed(ActionEvent e) {

		tvals = new XYSeries("tvals");
		tpvals = new XYSeries("tpvals");

		updatePlots();

			moveCounthlS = 0;
			ihlS = 0;
			moveCounthlSP = 0;
			ihlSP = 0;
			nhl = 0;
			delswitch = false;
			clearhlButton.setEnabled(false);
			deletehlButton.setEnabled(false);
			primearrhl = new ArrayList<Boolean>();

		


		}

	}
	public static class clearPressed implements ActionListener {
		public clearPressed() {
		}

		public void actionPerformed(ActionEvent e) {
			

			stEventsS = null;
			stpEventsS = null;
			stEventsSP = null;
			stpEventsSP = null;
			updatePlots();

			moveCountS = 0;
			iS = 0;
			moveCountSP = 0;
			iSP = 0;
			n = 0;
			delswitch = false;
			clearButton.setEnabled(false);
			deleteButton.setEnabled(false);
			primearr = new ArrayList<Boolean>();
	
			pulsearrs = new ArrayList<Boolean>();
			pulsearrp = new ArrayList<Boolean>();


		}

	}

	public static class deletelPressed implements ActionListener {
		public deletelPressed() {
		}

		public void actionPerformed(ActionEvent e) {

		if(primearrl.get(nl-1)) {
			if(xpvals.getItemCount() != 0) {
				xpvals.remove(xpvals.getItemCount()-1);
				moveCountlSP = 0;
			}
		} else {
			if(xvals.getItemCount() != 0) {
				xvals.remove(xvals.getItemCount()-1);
				moveCountlS = 0;
			}
		}

		primearrl.remove(nl-1);
		updatePlots();

		nl--;

		if(nl == 0) {
			moveCountlS = 0;
			ilS = 0;
			moveCountlSP = 0;
			ilSP = 0;
			nl = 0;
			delswitch = false;
			clearlButton.setEnabled(false);
			deletelButton.setEnabled(false);
			primearrl = new ArrayList<Boolean>();

		}
			


		}

	}

	public static class deletehlPressed implements ActionListener {
		public deletehlPressed() {
		}

		public void actionPerformed(ActionEvent e) {

		if(primearrhl.get(nhl-1)) {
			if(tpvals.getItemCount() != 0) {
				tpvals.remove(tpvals.getItemCount()-1);
				moveCountlSP = 0;
			}
		} else {
			if(tvals.getItemCount() != 0) {
				tvals.remove(tvals.getItemCount()-1);
				moveCountlS = 0;
			}
		}

		primearrhl.remove(nhl-1);
		updatePlots();

		nhl--;

		if(nhl == 0) {
			moveCounthlS = 0;
			ihlS = 0;
			moveCounthlSP = 0;
			ihlSP = 0;
			nhl = 0;
			delswitch = false;
			clearhlButton.setEnabled(false);
			deletehlButton.setEnabled(false);
			primearrhl = new ArrayList<Boolean>();

		}
			


		}

	}

	public static class deletePressed implements ActionListener {
		public deletePressed(){
		}

		public void actionPerformed(ActionEvent e) {
			
		
		
		
			if(primearr.get(n-1)){
			if (stEventsSP.getSeriesCount() != 0){
			stEventsSP.removeSeries(stEventsSP.getSeriesCount()-1);
			stpEventsSP.removeSeries(stpEventsSP.getSeriesCount()-1);
			moveCountSP = 0;
			}
							
			} else {
			if (stEventsS.getSeriesCount() != 0) {
			stEventsS.removeSeries(stEventsS.getSeriesCount()-1);
			stpEventsS.removeSeries(stpEventsS.getSeriesCount()-1);
			moveCountS = 0;
			}
			}
			primearr.remove(n-1);
			
		updatePlots();
		
		
		//System.out.println("n = " + n);
		n--;
		//System.out.println("n = " + n);
		delswitch = false;
		
		if (n == 0) {
		stEventsS = null;
		stpEventsS = null;
		stEventsSP = null;
		stpEventsSP = null;
		updatePlots();
		deleteButton.setEnabled(false);
		clearButton.setEnabled(false);
	
		iS = 0;
		moveCountS = 0;
		iSP = 0;
		moveCountSP = 0;

		primearr = new ArrayList<Boolean>();
	
		pulsearrs = new ArrayList<Boolean>();
		pulsearrp = new ArrayList<Boolean>();

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

			
		r1 = new XYLineAndShapeRenderer(true,false);
		
		
		float dasharr[] = {6.0f,6.0f};
		float dotarr[] = {2.0f,6.0f};
		float linearr[] = {1.0f};
		Stroke grid = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1.0f,linearr,0.0f);
		Stroke bdot = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1.0f,dotarr,0.0f);
		Stroke dashedthk = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,
		BasicStroke.JOIN_ROUND,1.0f,dasharr,0.0f);
		
		
		for(int i=1;i<=hyp1index;i++){
		
			if(i < 3){ r1.setSeriesStroke(i-1,new BasicStroke(2.0f));
			r1.setSeriesPaint(i-1,primeAxCol); //black lines
			} else if(i < 5) { r1.setSeriesStroke(i-1,dashedthk);
			r1.setSeriesPaint(i-1,Color.BLACK);
			}
			else if(i < 9) { r1.setSeriesStroke(i-1,bdot);
			r1.setSeriesPaint(i-1,primeAxCol); //black lines
			} else if (i <= grid1index) {r1.setSeriesStroke(i-1,grid);
			r1.setSeriesPaint(i-1,primeAxCol);
			} else {r1.setSeriesStroke(i-1,new BasicStroke(1.0f));
			r1.setSeriesPaint(i-1,hypCol);
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

                r2 = new XYLineAndShapeRenderer(true,false);

		float dasharr[] = {6.0f,6.0f};
	 	Stroke dashedthk = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,
		BasicStroke.JOIN_ROUND,1.0f,dasharr,0.0f);
		r2.setSeriesStroke(2,dashedthk);
		float dotarr[] = {2.0f,6.0f};
		float linearr[] = {1.0f};
		Stroke grid = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1.0f,linearr,0.0f);
		Stroke bdot = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1.0f,dotarr,0.0f);
            
		for(int i=1;i<=hyp2index;i++){
		
                        if(i < 3){ r2.setSeriesStroke(i-1,new BasicStroke(2.0f));
			  r2.setSeriesPaint(i-1,Color.BLACK); //black lines
                        } else if (i < 5) { r2.setSeriesStroke(i-1,dashedthk);
			r2.setSeriesPaint(i-1,Color.BLACK);
			} else if (i < 9) { r2.setSeriesStroke(i-1,bdot);
			 r2.setSeriesPaint(i-1,Color.BLACK); //black lines
			} else if (i <= grid2index) { r2.setSeriesStroke(i-1,grid);
			r2.setSeriesPaint(i-1,Color.DARK_GRAY);
                	} else {r2.setSeriesStroke(i-1,new BasicStroke(1.0f));
			r2.setSeriesPaint(i-1,hypCol);
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
	/*	String s = "v = " + String.format("%5s",String.format("%.3f",v)) + ", k = " + String.format("%4s",String.format("%.2f",(double)Math.round(100*k)/100)) +  ", " + "\u03B3" + " = " + String.format("%5s",String.format("%.3f",(double) Math.round(1000*gamma)/1000));*/
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





	public static class eventSelected implements ItemListener {
		public static void eventSelected() {
		}
		
		public void itemStateChanged(ItemEvent e) {
			if(eventSelect.isSelected()) {
				chartpanel1.removeChartMouseListener(listen12);
				chartpanel1.removeChartMouseListener(listen13);
				chartpanel2.removeChartMouseListener(listen22);
				chartpanel2.removeChartMouseListener(listen23);

				chartpanel1.addChartMouseListener(listen11);
				chartpanel2.addChartMouseListener(listen21);
				delswitch = false;


			}
		}
	}

	public static class hlineSelected implements ItemListener {
		public static void hlineSelected() {
		}
		
		public void itemStateChanged(ItemEvent e) {
			if(hlineSelect.isSelected()) {
				chartpanel1.removeChartMouseListener(listen11);
				chartpanel1.removeChartMouseListener(listen12);
				chartpanel2.removeChartMouseListener(listen21);
				chartpanel2.removeChartMouseListener(listen22);

				chartpanel1.addChartMouseListener(listen13);
				chartpanel2.addChartMouseListener(listen23);
				delswitch = false;


			}
		}
	}

	public static class vlineSelected implements ItemListener {
		public static void vlineSelected() {
		}

		public void itemStateChanged(ItemEvent e) {
		if(vlineSelect.isSelected()) {
				chartpanel1.addChartMouseListener(listen12);
				chartpanel2.addChartMouseListener(listen22);
			
				chartpanel1.removeChartMouseListener(listen11);
				chartpanel1.removeChartMouseListener(listen13);
				chartpanel2.removeChartMouseListener(listen21);
				chartpanel2.removeChartMouseListener(listen23);
				delswitch = false;
		}
		}

	}

	public static void updatePlots() {

			String s1 = "";
			String s2 = "";
			String sP1 = "";
			String sP2 = "";

		
			xlim2.setRange(xpmin,xpmax);
			tlim2.setRange(tpmin,tpmax);

			xlim1.setRange(xmin,xmax);
			tlim1.setRange(tmin,tmax);

		
			
			spaceTimeInitialize st = new spaceTimeInitialize();
			datin = st.dataset1;
			rin = st.r1;

			spaceTimePInitialize stp = new spaceTimePInitialize();
			datin2 = stp.dataset2;
			rin2 = stp.r2;


			if(stgridon) {
					for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,true);
					}
			} else {
				for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = 5; i <= grid2index;i++){
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

			
	

		
	
			if(stEventsS != null) {
				for(int i=1;i<=stEventsS.getSeriesCount();i++){
				

				if(pulsearrs.get(i-1)){
				double pointx = (double) stEventsS.getSeries(i-1).getX(0);
				double pointt = (double) stEventsS.getSeries(i-1).getY(0);
			
				s1 = "1seg1 " + (i-1);
				s2 = "1seg2 " + (i-1);
				pulseseg p = new pulseseg(pointx,pointt,s1,s2);

				XYSeries seg1 = p.seg1;
				XYSeries seg2 = p.seg2;

				datin.addSeries(seg1);
				rin.setSeriesStroke(datin.getSeriesCount()-1,dashed);
				rin.setSeriesPaint(datin.getSeriesCount()-1,Color.BLACK);
				rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
				rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);
				datin.addSeries(seg2);
				rin.setSeriesStroke(datin.getSeriesCount()-1,dashed);
				rin.setSeriesPaint(datin.getSeriesCount()-1,Color.BLACK);
				rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
				rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);

				
				

				}

				datin.addSeries(stEventsS.getSeries(i-1));
				

				Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
				rin.setSeriesLinesVisible(datin.getSeriesCount()-1,false);
				rin.setSeriesShapesVisible(datin.getSeriesCount()-1,true);
				rin.setSeriesShape(datin.getSeriesCount()-1,cross);	
				rin.setSeriesPaint(datin.getSeriesCount()-1,Color.CYAN);

			

				}
			}

			if(stEventsSP != null) {
				for(int i=1; i<=stEventsSP.getSeriesCount();i++){
				double xpSP = (double) stpEventsSP.getSeries(i-1).getX(0);
				double tpSP = (double) stpEventsSP.getSeries(i-1).getY(0);
				double xSP = gamma*(xpSP + v*tpSP);
				double tSP = gamma*(tpSP + v*xpSP);
				String s = "New event2 " + i;
				XYSeries newpt = new XYSeries(s);
				newpt.add(xSP,tSP);

				if (pulsearrp.get(i-1)) {

				s1 = "2seg1 " + (i-1);
				s2 = "2seg2 " + (i-1);

				pulseseg p = new pulseseg(xSP,tSP,s1,s2);
				XYSeries seg1 = p.seg1;
				XYSeries seg2 = p.seg2;

				datin.addSeries(seg1);
				rin.setSeriesStroke(datin.getSeriesCount()-1,dashed);
				rin.setSeriesPaint(datin.getSeriesCount()-1,Color.BLACK);
				rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
				rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);
				datin.addSeries(seg2);
				rin.setSeriesStroke(datin.getSeriesCount()-1,dashed);
				rin.setSeriesPaint(datin.getSeriesCount()-1,Color.BLACK);
				rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
				rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);



				}

				datin.addSeries(newpt);
			

				Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
				rin.setSeriesLinesVisible(datin.getSeriesCount()-1,false);
				rin.setSeriesShapesVisible(datin.getSeriesCount()-1,true);
				rin.setSeriesShape(datin.getSeriesCount()-1,cross);	
				rin.setSeriesPaint(datin.getSeriesCount()-1,Color.MAGENTA);

				;

				}
			}
		


	
		
			
		


			
			
			if(stpEventsSP != null) {
				for(int i=1;i<=stpEventsSP.getSeriesCount();i++){
				

				double pointxp = (double) stpEventsSP.getSeries(i-1).getX(0);
				double pointtp = (double) stpEventsSP.getSeries(i-1).getY(0);

				if(pulsearrp.get(i-1)) {
				sP1 = "2seg1 ' " + (i-1);
				sP2 = "2seg2 ' " + (i-1);

				pulsesegp pp = new pulsesegp(pointxp,pointtp,sP1,sP2);
				XYSeries segp1 = pp.segp1;
				XYSeries segp2 = pp.segp2;

				datin2.addSeries(segp1);
				rin2.setSeriesStroke(datin2.getSeriesCount()-1,dashed);
				rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.BLACK);
				rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
				rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
				datin2.addSeries(segp2);
				rin2.setSeriesStroke(datin2.getSeriesCount()-1,dashed);
				rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.BLACK);
				rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
				rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);

				}

				datin2.addSeries(stpEventsSP.getSeries(i-1));
				
				
				Shape cross = ShapeUtilities.createDiagonalCross(3, 1);

				rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,false);
				rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,true);
				rin2.setSeriesShape(datin2.getSeriesCount()-1,cross);	
				rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.MAGENTA);

				

				}
			}

			if(stpEventsS != null) {
				for(int i=1; i<=stEventsS.getSeriesCount();i++){
				double xS = (double) stEventsS.getSeries(i-1).getX(0);
				double tS = (double) stEventsS.getSeries(i-1).getY(0);
				double xpS = gamma*(xS - v*tS);
				double tpS = gamma*(tS - v*xS);
				String s = "New event2' " + i;
				XYSeries newpt = new XYSeries(s);
				newpt.add(xpS,tpS);

				if(pulsearrs.get(i-1)){

				sP1 = "1seg1 ' " + (i-1);
				sP2 = "1seg2 ' " + (i-1);
				pulsesegp pp = new pulsesegp(xpS,tpS,sP1,sP2);

				XYSeries segp1 = pp.segp1;
				XYSeries segp2 = pp.segp2;

				datin2.addSeries(segp1);
				rin2.setSeriesStroke(datin2.getSeriesCount()-1,dashed);
				rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.BLACK);
				rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
				rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
				datin2.addSeries(segp2);
				rin2.setSeriesStroke(datin2.getSeriesCount()-1,dashed);
				rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.BLACK);
				rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
				rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
				
				}
				
				datin2.addSeries(newpt);
			

				Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
				rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,false);
				rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,true);
				rin2.setSeriesShape(datin2.getSeriesCount()-1,cross);	
				rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.CYAN);

		
				}
			}

			if (tvals.getItemCount() != 0) {
				for(int i = 1; i <= tvals.getItemCount(); i++){

				double tval = (double) tvals.getDataItem(i-1).getXValue();

				String shl = "hlineS " + (i-1);
				String shlP = "hlineS ' " + (i-1);
				XYSeries lineS = new XYSeries(shl);
				lineS.add(xmin,tval);
				lineS.add(xmax,tval);
				XYSeries lineSP = new XYSeries(shlP);
				lineSP.add(xpmin,-v*xpmin + tval/gamma);
				lineSP.add(xpmax,-v*xpmax + tval/gamma);

				datin.addSeries(lineS);
				datin2.addSeries(lineSP);
			
		
		

				rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
				rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);
				rin.setSeriesStroke(datin.getSeriesCount()-1,new BasicStroke(3.0f));
				rin.setSeriesPaint(datin.getSeriesCount()-1,Color.GREEN);	

				rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);	
				rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
				rin2.setSeriesStroke(datin2.getSeriesCount()-1,new BasicStroke(3.0f));
				rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.GREEN);

	

				


				}

			}

			if (xvals.getItemCount() != 0) {
				for(int i = 1; i <= xvals.getItemCount(); i++){

				double xval = (double) xvals.getDataItem(i-1).getXValue();

				String sl = "lineS " + (i-1);
				String slP = "lineS ' " + (i-1);
				XYSeries lineS = new XYSeries(sl);
				lineS.add(xval,tmin);
				lineS.add(xval,tmax);
				XYSeries lineSP = new XYSeries(slP);
				lineSP.add(-v*tpmin + xval/gamma,tpmin);
				lineSP.add(-v*tpmax + xval/gamma,tpmax);

				datin.addSeries(lineS);
				datin2.addSeries(lineSP);
				
		
		

				rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
				rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);
				rin.setSeriesStroke(datin.getSeriesCount()-1,new BasicStroke(3.0f));
				rin.setSeriesPaint(datin.getSeriesCount()-1,Color.GREEN);	

				rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);	
				rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
				rin2.setSeriesStroke(datin2.getSeriesCount()-1,new BasicStroke(3.0f));
				rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.GREEN);

				

				


				}

			}

			if (tpvals.getItemCount() != 0) {
				for(int i = 1; i <= tpvals.getItemCount(); i++){

				double tpval = (double) tpvals.getDataItem(i-1).getXValue();

				String spl = "hlineSP " + (i-1);
				String splP = "hlineSP ' " + (i-1);
				XYSeries linepS = new XYSeries(spl);
				linepS.add(xmin,v*xmin+tpval/gamma);
				linepS.add(xmax,v*xmax+tpval/gamma);
				XYSeries linepSP = new XYSeries(splP);
				linepSP.add(xpmin,tpval);
				linepSP.add(xpmax,tpval);

				datin.addSeries(linepS);
				datin2.addSeries(linepSP);
			
		
		

				rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
				rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);
				rin.setSeriesStroke(datin.getSeriesCount()-1,new BasicStroke(3.0f));
				rin.setSeriesPaint(datin.getSeriesCount()-1,Color.ORANGE);	

				rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);	
				rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
				rin2.setSeriesStroke(datin2.getSeriesCount()-1,new BasicStroke(3.0f));
				rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.ORANGE);

				


				}

			}

			if (xpvals.getItemCount() != 0) {
				for(int i = 1; i <= xpvals.getItemCount(); i++){

				double xpval = (double) xpvals.getDataItem(i-1).getXValue();

				String spl = "lineSP " + (i-1);
				String splP = "lineSP ' " + (i-1);
				XYSeries linepS = new XYSeries(spl);
				linepS.add(v*tmin+xpval/gamma,tmin);
				linepS.add(v*tmax+xpval/gamma,tmax);
				XYSeries linepSP = new XYSeries(splP);
				linepSP.add(xpval,tpmin);
				linepSP.add(xpval,tpmax);

				datin.addSeries(linepS);
				datin2.addSeries(linepSP);
			
		
		

				rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
				rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);
				rin.setSeriesStroke(datin.getSeriesCount()-1,new BasicStroke(3.0f));
				rin.setSeriesPaint(datin.getSeriesCount()-1,Color.ORANGE);	

				rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);	
				rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
				rin2.setSeriesStroke(datin2.getSeriesCount()-1,new BasicStroke(3.0f));
				rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.ORANGE);

			

				


				}

			}

			

			plot1.setDataset(datin);
			plot1.setRenderer(rin);



			plot2.setDataset(datin2);
			plot2.setRenderer(rin2);	
			
		
				

		
		

	}

	public static void addhlineS(ChartMouseEvent event, boolean save) {
		Point2D pt = chartpanel1.translateScreenToJava2D(event.getTrigger().getPoint());
		Rectangle2D plotArea = chartpanel1.getScreenDataArea();
		double tval = tlim1.java2DToValue(pt.getY(),plotArea,plot1.getRangeAxisEdge());

		String s;
		String sP;

		primehl = false;

		if(delswitch) {

		datin.removeSeries(datin.getSeriesCount()-1);
	
		
		datin2.removeSeries(datin2.getSeriesCount()-1);
	
		}
		
		if(!save){delswitch = true;
		}

		if(save){

		s = "hlineS " + ihlS;
		sP = "hlineS ' " + ihlS;

		primearrhl.add(primehl);

		} else {
		
		s = "movinghlS " + moveCountlS;
		sP = "movinghlS ' " + moveCountlS;

		}

		XYSeries lineS = new XYSeries(s);
		XYSeries lineSP = new XYSeries(sP);

		lineS.add(xmin,tval);
		lineS.add(xmax,tval);

		lineSP.add(xpmin,-v*xpmin+tval/gamma);
		lineSP.add(xpmax,-v*xpmax+tval/gamma);

		datin.addSeries(lineS);
		datin2.addSeries(lineSP);
	
		
		

		rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
		rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);
		rin.setSeriesStroke(datin.getSeriesCount()-1,new BasicStroke(3.0f));
		rin.setSeriesPaint(datin.getSeriesCount()-1,Color.GREEN);

		rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
		rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
		rin2.setSeriesStroke(datin2.getSeriesCount()-1,new BasicStroke(3.0f));
		rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.GREEN);

		

			if(stgridon) {
					for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,true);
					}
			} else {
				for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,false);
					}
			}

		plot1.setDataset(datin);
		plot1.setRenderer(rin);
		
		plot2.setDataset(datin2);
		plot2.setRenderer(rin2);

		


		
		
		

		if (save && ihlS == 0) { tvals.add(tval,0);
		delswitch = false;
		deletehlButton.setEnabled(true);
		clearhlButton.setEnabled(true);
		
		} else if (save) {
		 tvals.add(tval,0);
		delswitch = false;
		}
	}
		
	public static void addlineS(ChartMouseEvent event, boolean save) {

		Point2D pt = chartpanel1.translateScreenToJava2D(event.getTrigger().getPoint());
		Rectangle2D plotArea = chartpanel1.getScreenDataArea();
		double xval = xlim1.java2DToValue(pt.getX(),plotArea,plot1.getDomainAxisEdge());


		String s;
		String sP;

		primel = false;

		if(delswitch) {

		datin.removeSeries(datin.getSeriesCount()-1);
	
		
		datin2.removeSeries(datin2.getSeriesCount()-1);
		
		}
		
		if(!save){delswitch = true;
		}

		if(save){

		s = "lineS " + ilS;
		sP = "lineS ' " + ilS;

		primearrl.add(primel);

		} else {
		
		s = "movinglS " + moveCountlS;
		sP = "moninglS ' " + moveCountlS;

		}

		XYSeries lineS = new XYSeries(s);
		XYSeries lineSP = new XYSeries(sP);

		lineS.add(xval,tmin);
		lineS.add(xval,tmax);

		lineSP.add(-v*tpmin+xval/gamma,tpmin);
		lineSP.add(-v*tpmax+xval/gamma,tpmax);

		datin.addSeries(lineS);
		datin2.addSeries(lineSP);
		
		
		

		rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
		rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);
		rin.setSeriesStroke(datin.getSeriesCount()-1,new BasicStroke(3.0f));
		rin.setSeriesPaint(datin.getSeriesCount()-1,Color.GREEN);

		rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
		rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
		rin2.setSeriesStroke(datin2.getSeriesCount()-1,new BasicStroke(3.0f));
		rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.GREEN);


			if(stgridon) {
					for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,true);
					}
			} else {
				for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,false);
					}
			}

		plot1.setDataset(datin);
		plot1.setRenderer(rin);
		
		plot2.setDataset(datin2);
		plot2.setRenderer(rin2);




		
		
		

		if (save && ilS == 0) { xvals.add(xval,0);
		delswitch = false;
		deletelButton.setEnabled(true);
		clearlButton.setEnabled(true);
		
		} else if (save) {
		 xvals.add(xval,0);
		delswitch = false;
		}

	}

	public static void addhlineSP(ChartMouseEvent event, boolean save) {
		Point2D pt = chartpanel2.translateScreenToJava2D(event.getTrigger().getPoint());
		Rectangle2D plotArea = chartpanel2.getScreenDataArea();
		double tpval = tlim2.java2DToValue(pt.getY(),plotArea,plot2.getRangeAxisEdge());

		String s;
		String sP;

		primehl = true;

		if(delswitch) {

		datin.removeSeries(datin.getSeriesCount()-1);
		
		
		datin2.removeSeries(datin2.getSeriesCount()-1);
		
		}
		
		if(!save){delswitch = true;
		}

		if(save){

		s = "hlineSP " + ihlSP;
		sP = "hlineSP ' " + ihlSP;

		primearrhl.add(primehl);

		} else {
		
		s = "movinghlSP " + moveCountlSP;
		sP = "movinghlSP ' " + moveCountlSP;

		}

		XYSeries lineS = new XYSeries(s);
		XYSeries lineSP = new XYSeries(sP);

		lineS.add(xmin,v*xmin+tpval/gamma);
		lineS.add(xmax,v*xmax+tpval/gamma);

		
		lineSP.add(xpmin,tpval);
		lineSP.add(xpmax,tpval);

		datin.addSeries(lineS);
		datin2.addSeries(lineSP);
	
		
		

		rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
		rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);
		rin.setSeriesStroke(datin.getSeriesCount()-1,new BasicStroke(3.0f));
		rin.setSeriesPaint(datin.getSeriesCount()-1,Color.ORANGE);

		rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
		rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
		rin2.setSeriesStroke(datin2.getSeriesCount()-1,new BasicStroke(3.0f));
		rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.ORANGE);

		

			if(stgridon) {
					for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,true);
					}
			} else {
				for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,false);
					}
			}

		plot1.setDataset(datin);
		plot1.setRenderer(rin);
		
		plot2.setDataset(datin2);
		plot2.setRenderer(rin2);

	




		
		
		

		if (save && ihlSP == 0) { tpvals.add(tpval,0);
		delswitch = false;
		deletehlButton.setEnabled(true);
		clearhlButton.setEnabled(true);
		
		} else if (save) {
		 tpvals.add(tpval,0);
		delswitch = false;
		}
	}

	public static void addlineSP(ChartMouseEvent event, boolean save) {

		Point2D pt = chartpanel2.translateScreenToJava2D(event.getTrigger().getPoint());
		Rectangle2D plotArea = chartpanel2.getScreenDataArea();
		double xpval = xlim2.java2DToValue(pt.getX(),plotArea,plot2.getDomainAxisEdge());


		String s;
		String sP;

		primel = true;

		if(delswitch) {

		datin.removeSeries(datin.getSeriesCount()-1);
		
		
		datin2.removeSeries(datin2.getSeriesCount()-1);
		
		}
		
		if(!save){delswitch = true;
		}

		if(save){

		s = "lineSP " + ilSP;
		sP = "lineSP ' " + ilSP;

		primearrl.add(primel);

		} else {
		
		s = "movinglSP " + moveCountlSP;
		sP = "moninglSP ' " + moveCountlSP;

		}

		XYSeries lineS = new XYSeries(s);
		XYSeries lineSP = new XYSeries(sP);

		lineS.add(v*tmin + xpval/gamma,tmin);
		lineS.add(v*tmax + xpval/gamma,tmax);

		lineSP.add(xpval,tpmin);
		lineSP.add(xpval,tpmax);

		datin.addSeries(lineS);
		datin2.addSeries(lineSP);
	
		
		

		rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
		rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);
		rin.setSeriesStroke(datin.getSeriesCount()-1,new BasicStroke(3.0f));
		rin.setSeriesPaint(datin.getSeriesCount()-1,Color.ORANGE);

		rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
		rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
		rin2.setSeriesStroke(datin2.getSeriesCount()-1,new BasicStroke(3.0f));
		rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.ORANGE);

		

			if(stgridon) {
					for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,true);
					}
			} else {
				for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,false);
					}
			}

		plot1.setDataset(datin);
		plot1.setRenderer(rin);
		
		plot2.setDataset(datin2);
		plot2.setRenderer(rin2);

	
		
		
		

		if (save && ilSP == 0) { 
		xpvals.add(xpval,0);
		delswitch = false;
		clearlButton.setEnabled(true);
		deletelButton.setEnabled(true);
		} else if (save) {
		xpvals.add(xpval,0);
		delswitch = false;
		}

	}


		


	public static void addEventS(ChartMouseEvent event, boolean save) {

		Point2D pt = chartpanel1.translateScreenToJava2D	(event.getTrigger().getPoint());
		Rectangle2D plotArea = chartpanel1.getScreenDataArea();
		double pointx = xlim1.java2DToValue(pt.getX(),plotArea,plot1.getDomainAxisEdge());
		double pointt = tlim1.java2DToValue(pt.getY(),plotArea,plot1.getRangeAxisEdge());

		

	
		String s;
		String sP;
		String s1 = "";
		String sP1 = "";
		String s2 = "";
		String sP2  = "";
	
		prime = false;
		
	
		if(delswitch){

		if(pulseswitchlag) {
		datin.removeSeries(datin.getSeriesCount()-1);
		datin.removeSeries(datin.getSeriesCount()-1);
		datin.removeSeries(datin.getSeriesCount()-1);
		
		
		datin2.removeSeries(datin2.getSeriesCount()-1);
		datin2.removeSeries(datin2.getSeriesCount()-1);
		datin2.removeSeries(datin2.getSeriesCount()-1);
	

		} else {
		datin.removeSeries(datin.getSeriesCount()-1);
	
		
		datin2.removeSeries(datin2.getSeriesCount()-1);
	
		}
		}

		if(!save) {delswitch = true;
		}


		if(save) {
		s = "New event1 " + iS;
		sP = "New event1 ' " + iS;

		s1 = "1seg1 " + iS;
		sP1 = "1seg1 ' " + iS;

		s2 = "1seg2 " + iS;
		sP2 = "1seg2 ' " + iS;

		primearr.add(prime);
		
		pulsearrs.add(pulseswitch);
		} else {
		s = "Moving event1 " + moveCountS;
		sP = "Moving event1' " + moveCountS;

		
		s1 = "1seg1m " + moveCountS;
		sP1 = "1seg1m ' " + moveCountS;

		s2 = "1seg2m " + moveCountS;
		sP2 = "1seg2m ' " + moveCountS;
		
		}

		XYSeries stEvent = new XYSeries(s);
		stEvent.add(pointx,pointt);

		double pointxp = gamma*(pointx - v*pointt);
		double pointtp = gamma*(pointt - v*pointx);
		XYSeries stpEvent = new XYSeries(sP);
		stpEvent.add(pointxp,pointtp);

		if(pulseswitch) {
		pulseswitchlag = true;
		
		pulseseg p = new pulseseg(pointx,pointt,s1,s2);
		XYSeries seg1 = p.seg1;
		XYSeries seg2 = p.seg2;

		pulsesegp pp = new pulsesegp(pointxp,pointtp,sP1,sP2);
		XYSeries segp1 = pp.segp1;
		XYSeries segp2 = pp.segp2;
		
		
		datin.addSeries(seg1);
		rin.setSeriesStroke(datin.getSeriesCount()-1,dashed);
		rin.setSeriesPaint(datin.getSeriesCount()-1,Color.BLACK);
		rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
		rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);
		datin.addSeries(seg2);
		rin.setSeriesStroke(datin.getSeriesCount()-1,dashed);
		rin.setSeriesPaint(datin.getSeriesCount()-1,Color.BLACK);
		rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
		rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);

		datin2.addSeries(segp1);
		rin2.setSeriesStroke(datin2.getSeriesCount()-1,dashed);
		rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.BLACK);
		rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
		rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
		datin2.addSeries(segp2);
		rin2.setSeriesStroke(datin2.getSeriesCount()-1,dashed);
		rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.BLACK);
		rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
		rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
		
		





		} else { pulseswitchlag = false;
		}
		
		
		if(save && iS == 0) {
		stEventsS = new XYSeriesCollection(stEvent);
		clearButton.setEnabled(true);
		deleteButton.setEnabled(true);
		} else if(save) {
		stEventsS.addSeries(stEvent);
		}
	
		if(save && iS == 0) {
		stpEventsS = new XYSeriesCollection(stpEvent);
		} else if(save) {
		stpEventsS.addSeries(stpEvent);
		}

		if(save) {delswitch = false;
		}

		datin.addSeries(stEvent);
		Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
		rin.setSeriesLinesVisible(datin.getSeriesCount()-1,false);
		rin.setSeriesShapesVisible(datin.getSeriesCount()-1,true);
		rin.setSeriesShape(datin.getSeriesCount()-1,cross);	
		rin.setSeriesPaint(datin.getSeriesCount()-1,Color.CYAN);

		plot1.setDataset(datin);
		plot1.setRenderer(rin);

		
		

		datin2.addSeries(stpEvent);
		rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,false);
		rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,true);
		rin2.setSeriesShape(datin2.getSeriesCount()-1,cross);
		rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.CYAN);

		plot2.setDataset(datin2);
		plot2.setRenderer(rin2);
			if(stgridon) {
					for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,true);
					}
			} else {
				for (int i = 5; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = 5; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,false);
					}
			}

		

		
	}

	public static void addEventSP(ChartMouseEvent event, boolean save) {

			Point2D pt = chartpanel2.translateScreenToJava2D	(event.getTrigger().getPoint());
			Rectangle2D plotArea = chartpanel2.getScreenDataArea();
		double pointxp = xlim2.java2DToValue(pt.getX(),plotArea,plot2.getDomainAxisEdge());
		double pointtp = tlim2.java2DToValue(pt.getY(),plotArea,plot2.getRangeAxisEdge());

	
		String s;
		String sP;
		String s1 = "";
		String sP1 = "";
		String s2 = "";
		String sP2 = "";
	
		prime = true;
		
	
		if(delswitch){

		if(pulseswitchlagp) {
		datin.removeSeries(datin.getSeriesCount()-1);
		datin.removeSeries(datin.getSeriesCount()-1);
		datin.removeSeries(datin.getSeriesCount()-1);
		
		
		datin2.removeSeries(datin2.getSeriesCount()-1);
		datin2.removeSeries(datin2.getSeriesCount()-1);
		datin2.removeSeries(datin2.getSeriesCount()-1);
		

		} else {
		datin.removeSeries(datin.getSeriesCount()-1);
		
		
		datin2.removeSeries(datin2.getSeriesCount()-1);
		
		}
		}

		if(!save) {delswitch = true;
		}


		if(save) {
		s = "New event2 " + iSP;
		sP = "New event2 ' " + iSP;

		s1 = "2seg1 " + iSP;
		sP1 = "2seg1 ' " + iSP;

		s2 = "2seg2 " + iSP;
		sP2 = "2seg2 ' " + iSP;

		primearr.add(prime);
		
		pulsearrp.add(pulseswitch);
		} else {
		s = "Moving event2 " + moveCountSP;
		sP = "Moving event2' " + moveCountSP;

		
		s1 = "2seg1m " + moveCountSP;
		sP1 = "2seg1m ' " + moveCountSP;

		s2 = "2seg2m " + moveCountSP;
		sP2 = "2seg2m ' " + moveCountSP;
		
		}

		XYSeries stpEvent = new XYSeries(s);
		stpEvent.add(pointxp,pointtp);

		double pointx = gamma*(pointxp + v*pointtp);
		double pointt = gamma*(pointtp + v*pointxp);
		XYSeries stEvent = new XYSeries(sP);
		stEvent.add(pointx,pointt);


		if(pulseswitch) {
		pulseswitchlagp = true;

		pulseseg p = new pulseseg(pointx,pointt,s1,s2);
		XYSeries seg1 = p.seg1;
		XYSeries seg2 = p.seg2;

		pulsesegp pp = new pulsesegp(pointxp,pointtp,sP1,sP2);
		XYSeries segp1 = pp.segp1;
		XYSeries segp2 = pp.segp2;
		
		datin.addSeries(seg1);
		rin.setSeriesStroke(datin.getSeriesCount()-1,dashed);
		rin.setSeriesPaint(datin.getSeriesCount()-1,Color.BLACK);
		rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
		rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);
		datin.addSeries(seg2);
		rin.setSeriesStroke(datin.getSeriesCount()-1,dashed);
		rin.setSeriesPaint(datin.getSeriesCount()-1,Color.BLACK);
		rin.setSeriesShapesVisible(datin.getSeriesCount()-1,false);
		rin.setSeriesLinesVisible(datin.getSeriesCount()-1,true);

		datin2.addSeries(segp1);
		rin2.setSeriesStroke(datin2.getSeriesCount()-1,dashed);
		rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.BLACK);
		rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
		rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
		datin2.addSeries(segp2);
		rin2.setSeriesStroke(datin2.getSeriesCount()-1,dashed);
		rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.BLACK);
		rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,false);
		rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,true);
		
		





		} else { pulseswitchlagp = false;
		}

		if(save && iSP == 0) {
		stEventsSP = new XYSeriesCollection(stEvent);
		clearButton.setEnabled(true);
		deleteButton.setEnabled(true);
		} else if(save) {
		stEventsSP.addSeries(stEvent);
		}
	
		if(save && iSP == 0) {
		stpEventsSP = new XYSeriesCollection(stpEvent);
		} else if(save) {
		stpEventsSP.addSeries(stpEvent);
		}

		if(save) {delswitch = false;
		}
		
		
		
		

		datin.addSeries(stEvent);
		Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
		rin.setSeriesLinesVisible(datin.getSeriesCount()-1,false);
		rin.setSeriesShapesVisible(datin.getSeriesCount()-1,true);
		rin.setSeriesShape(datin.getSeriesCount()-1,cross);	
		rin.setSeriesPaint(datin.getSeriesCount()-1,Color.MAGENTA);

		plot1.setDataset(datin);
		plot1.setRenderer(rin);

		
		

		datin2.addSeries(stpEvent);
		rin2.setSeriesLinesVisible(datin2.getSeriesCount()-1,false);
		rin2.setSeriesShapesVisible(datin2.getSeriesCount()-1,true);
		rin2.setSeriesShape(datin2.getSeriesCount()-1,cross);
		rin2.setSeriesPaint(datin2.getSeriesCount()-1,Color.MAGENTA);

		plot2.setDataset(datin2);
		plot2.setRenderer(rin2);
			if(stgridon) {
					for (int i = 9; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,true);
					}
					for (int i = 9; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,true);
					}
			} else {
					for (int i = 9; i <= grid1index;i++){
					rin.setSeriesVisible(i-1,false);
					}
					for (int i = 9; i <= grid2index;i++){
					rin2.setSeriesVisible(i-1,false);
					}
			}
		
	

		
		

		
	}

	public static class pulseseg {

		private XYSeries seg1;
		private XYSeries seg2;
		double ts1 = 0;
		double tf1 = 0;
		double ts2 = 0;
		double tf2 = 0;
		double ts = 0;
		double tf = 0;
		double tsp1 = 0;
		double tfp1 = 0;
		double tsp2 = 0;
		double tfp2 = 0;
		double tsp = 0;
		double tfp = 0;

		public pulseseg(double pointx,double pointt,String s1, String s2) {
		if ((0 < pointx && pointx < v*pointt) || (v*pointt < pointx && pointx < 0)) {

		ts1 = pointt - pointx;
		tf1 = (pointt-pointx)/(1-v);

		ts2 = pointt + pointx;
		tf2 = (pointt + pointx)/(1+v);
		
		twopulse = true;

		} else if (pointx < 0) {

		ts = pointt + Math.abs(pointx)*Math.max(Math.abs(pointx),Math.abs((pointt+pointx)/(1+v)-pointt))/(pointx);
		tf = pointt + Math.abs(-pointx)*Math.max(Math.abs(-pointx),Math.abs((pointt-pointx)/(1-v)-pointt))/(-pointx);
		
		twopulse = false;
		
		} else {
		ts = pointt + Math.abs(-pointx)*Math.max(Math.abs(-pointx),Math.abs((pointt-pointx)/(1-v)-pointt))/(-pointx);
		tf = pointt + Math.abs(pointx)*Math.max(Math.abs(pointx),Math.abs((pointt+pointx)/(1+v)-pointt))/(pointx);
		twopulse = false;
		}
		seg1 = new XYSeries(s1);
		seg2 = new XYSeries(s2);

		if(twopulse) {

		seg1.add(ts1-pointt + pointx,ts1);
		seg1.add(tf1-pointt + pointx,tf1);

		seg2.add(-ts2+pointt+pointx,ts2);
		seg2.add(-tf2+pointt+pointx,tf2);
		
		} else if (pointx < 0) {
		seg1.add(-ts+pointt+pointx,ts);
		seg1.add(pointx,pointt);
		
		seg2.add(pointx,pointt);
		seg2.add(tf-pointt+pointx,tf);
		


		} else if (pointx > 0) {
		seg1.add(ts-pointt+pointx,ts);
		seg1.add(pointx,pointt);

		seg2.add(pointx,pointt);
		seg2.add(-tf+pointt+pointx,tf);
		}
		
		}

	}

	public static class pulsesegp {
		private XYSeries segp1;
		private XYSeries segp2;

		double ts1 = 0;
		double tf1 = 0;
		double ts2 = 0;
		double tf2 = 0;
		double ts = 0;
		double tf = 0;
		double tsp1 = 0;
		double tfp1 = 0;
		double tsp2 = 0;
		double tfp2 = 0;
		double tsp = 0;
		double tfp = 0;

		public pulsesegp(double pointxp,double pointtp, String sP1, String sP2) {
		if ((0 < pointxp && pointxp < -v*pointtp) || (-v*pointtp < pointxp && pointxp < 0)) {

		tsp1 = pointtp - pointxp;
		tfp1 = (pointtp-pointxp)/(1+v);

		tsp2 = pointtp + pointxp;
		tfp2 = (pointtp + pointxp)/(1-v);
		
		twopulsep = true;

		} else if (pointxp < 0) {

		tsp = pointtp + Math.abs(pointxp)*Math.max(Math.abs(pointxp),Math.abs((pointtp+pointxp)/(1-v)-pointtp))/(pointxp);
		tfp = pointtp + Math.abs(-pointxp)*Math.max(Math.abs(-pointxp),Math.abs((pointtp-pointxp)/(1+v)-pointtp))/(-pointxp);
		
		twopulsep = false;
		
		} else {
		tsp = pointtp + Math.abs(-pointxp)*Math.max(Math.abs(-pointxp),Math.abs((pointtp-pointxp)/(1+v)-pointtp))/(-pointxp);
		tfp = pointtp + Math.abs(pointxp)*Math.max(Math.abs(pointxp),Math.abs((pointtp+pointxp)/(1-v)-pointtp))/(pointxp);
		twopulsep = false;
		}
		segp1 = new XYSeries(sP1);
		segp2 = new XYSeries(sP2);

		if(twopulsep) {

		segp1.add(tsp1-pointtp + pointxp,tsp1);
		segp1.add(tfp1-pointtp + pointxp,tfp1);

		segp2.add(-tsp2+pointtp+pointxp,tsp2);
		segp2.add(-tfp2+pointtp+pointxp,tfp2);
		
		} else if (pointxp < 0) {
		segp1.add(-tsp+pointtp+pointxp,tsp);
		segp1.add(pointxp,pointtp);
		
		segp2.add(pointxp,pointtp);
		segp2.add(tfp-pointtp+pointxp,tfp);
		


		} else if (pointxp > 0) {
		segp1.add(tsp-pointtp+pointxp,tsp);
		segp1.add(pointxp,pointtp);

		segp2.add(pointxp,pointtp);
		segp2.add(-tfp+pointtp+pointxp,tfp);
		}

		}
	}

	public static class eventSlisten implements ChartMouseListener {

		public static void eventSlisten() {
		}
		public void chartMouseMoved(ChartMouseEvent eventM) {
		
		
		addEventS(eventM,false);
		moveCountS++;
		}
		public void chartMouseClicked(ChartMouseEvent eventC) {
		
		addEventS(eventC,true);
		moveCountS = 0;
		iS++;
		n++;
	
		}

	}

	public static class eventSPlisten implements ChartMouseListener {
		public static void eventSPlisten() {
		}
		
		public void chartMouseMoved(ChartMouseEvent eventM) {
		
		
		addEventSP(eventM,false);
		moveCountSP++;

		
		}

		public void chartMouseClicked(ChartMouseEvent eventC) {
		
		addEventSP(eventC,true);
		moveCountSP = 0;
		iSP++;
		n++;

	
		}
		

	}

	public static class lineSlisten implements ChartMouseListener {
		public static void lineSlisten() {
		}

		public void chartMouseMoved(ChartMouseEvent eventM) {
		
		addlineS(eventM,false);
		moveCountlS++;
		}
		public void chartMouseClicked(ChartMouseEvent eventC) {
		addlineS(eventC,true);
		moveCountlS = 0;
		ilS++;
		nl++;
		}

	}

	public static class hlineSlisten implements ChartMouseListener {
		public static void hlineSlisten() {
		}

		public void chartMouseMoved(ChartMouseEvent eventM) {
	

		addhlineS(eventM,false);
		moveCounthlS++;
		}

		public void chartMouseClicked(ChartMouseEvent eventC) {

		addhlineS(eventC, true);
		moveCounthlS = 0;
		ihlS++;
		nhl++;
		}
	}

	public static class hlineSPlisten implements ChartMouseListener {
		public static void hlineSPlisten() {
		}

		public void chartMouseMoved(ChartMouseEvent eventM) {

		addhlineSP(eventM, false);
		moveCounthlSP++;
			
		}

		public void chartMouseClicked(ChartMouseEvent eventC) {

		addhlineSP(eventC,true);
		moveCounthlSP = 0;
		ihlSP++;
		nhl++;

		}
		
	}




	public static class lineSPlisten implements ChartMouseListener {
		public static void lineSPlisten() {
		}

		public void chartMouseMoved(ChartMouseEvent eventM) {

		addlineSP(eventM, false);
		moveCountlSP++;
			
		}

		public void chartMouseClicked(ChartMouseEvent eventC) {

		addlineSP(eventC,true);
		moveCountlSP = 0;
		ilSP++;
		nl++;

		}
		
	}



	

}


