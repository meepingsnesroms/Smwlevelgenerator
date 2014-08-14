package gen;

import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;

import com.mojang.mario.level.Level;
import com.mojang.mario.level.LevelGenerator;

public class Beta {
	static make intmake=new make();
	static String scripts[];
	static int imblvltp=0;
	
	private JFrame frmSmwlevelgen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Beta window = new Beta();
					window.frmSmwlevelgen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Beta() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	static final JTextPane txtpnErrorDialog = new JTextPane();
	
	private void initialize() {
		frmSmwlevelgen = new JFrame();
		frmSmwlevelgen.setResizable(false);
		frmSmwlevelgen.setTitle("SMWlevelgen");
		frmSmwlevelgen.setBounds(100, 100, 438, 436);
		frmSmwlevelgen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSmwlevelgen.getContentPane().setLayout(null);
		JButton btnGo = new JButton("Go");
		btnGo.setBounds(0, 384, 432, 23);
		frmSmwlevelgen.getContentPane().add(btnGo);
		txtpnErrorDialog.setToolTipText("Error Dialog");
		txtpnErrorDialog.setBounds(293, 141, 139, 232);
		frmSmwlevelgen.getContentPane().add(txtpnErrorDialog);
		
		final JTextPane txtpnScriptListAnd = new JTextPane();
		txtpnScriptListAnd.setToolTipText("Script List and Order");
		txtpnScriptListAnd.setBounds(0, 141, 283, 232);
		frmSmwlevelgen.getContentPane().add(txtpnScriptListAnd);
		
		final JCheckBox chckbxDisableInfinteMario = new JCheckBox("Disable infinte mario bros");
		chckbxDisableInfinteMario.setBounds(0, 7, 178, 23);
		frmSmwlevelgen.getContentPane().add(chckbxDisableInfinteMario);
		
		final JCheckBox chckbxVertical = new JCheckBox("Vertical");
		chckbxVertical.setBounds(196, 7, 74, 23);
		frmSmwlevelgen.getContentPane().add(chckbxVertical);
		
		final Choice choice = new Choice();
		choice.setBounds(0, 36, 212, 20);
		choice.add("Plains");
		choice.add("Cave");
		choice.add("Castle");
		frmSmwlevelgen.getContentPane().add(choice);
		
		JLabel lblScriptListran = new JLabel("Script list (ran in the order typed)");
		lblScriptListran.setBounds(0, 116, 291, 14);
		frmSmwlevelgen.getContentPane().add(lblScriptListran);
		
		JLabel lblGfxIndexNumber = new JLabel("Manual Gfx config");
		lblGfxIndexNumber.setBounds(218, 36, 112, 14);
		frmSmwlevelgen.getContentPane().add(lblGfxIndexNumber);
		
		final JSpinner spinner = new JSpinner();
		spinner.setToolTipText("-1=auto");
		spinner.setModel(new SpinnerNumberModel(-1, -1, 14, 1));
		spinner.setBounds(244, 55, 49, 20);
		frmSmwlevelgen.getContentPane().add(spinner);
		
		JLabel lblManualPalConfig = new JLabel("Manual Pal config");
		lblManualPalConfig.setBounds(73, 61, 104, 14);
		frmSmwlevelgen.getContentPane().add(lblManualPalConfig);
		
		final JSpinner spinner_1 = new JSpinner();
		spinner_1.setToolTipText("-1=auto");
		spinner_1.setModel(new SpinnerNumberModel(-1, -1, 7, 1));
		spinner_1.setBounds(87, 85, 49, 20);
		frmSmwlevelgen.getContentPane().add(spinner_1);
		
		JLabel lblDifficulty = new JLabel("Difficulty");
		lblDifficulty.setBounds(0, 61, 63, 14);
		frmSmwlevelgen.getContentPane().add(lblDifficulty);
		
		final JSpinner spinner_2 = new JSpinner();
		spinner_2.setToolTipText("-1=auto");
		spinner_2.setModel(new SpinnerNumberModel(-1, -1, 5, 1));
		spinner_2.setBounds(0, 85, 63, 20);
		frmSmwlevelgen.getContentPane().add(spinner_2);
		
		JLabel lblProgramOutput = new JLabel("Program output");
		lblProgramOutput.setBounds(293, 116, 139, 14);
		frmSmwlevelgen.getContentPane().add(lblProgramOutput);
		
		btnGo.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	int difficulty=start.mrandom(5);
            	header layer1header=new header();
            	layer1header.setheader(intmake.headerbase(imblvltp));
            	layer1header.setvalue("spriteset",0x00);
            	layer1header.setvalue("vscroll",0b1);
            	scripts=txtpnScriptListAnd.getText().split(":");
            	if((int)spinner.getValue()!=-1){
            		layer1header.setvalue("tileset", (int)spinner.getValue());
            	}
            	if((int)spinner_1.getValue()!=-1){
            		layer1header.setvalue("fgpalette", (int)spinner_1.getValue());
            	}
            	if((int)spinner_2.getValue()!=-1){
            		difficulty=(int)spinner_2.getValue();
            	}
            	intmake.customheader(layer1header.getheader());
            	boolean isver=false;
            	Level randomlevel;
            	if(chckbxVertical.isSelected()){
            			randomlevel=intmake.rotatevertical(new Level(440,32));
            			isver=true;
            	}
            	else randomlevel=new Level(500,26);
            	if(!chckbxDisableInfinteMario.isSelected())randomlevel=LevelGenerator.createLevel(300, 26, start.mrandom(1000), difficulty, imblvltp);
                if(scripts!=null){
                for(int f=0;f<scripts.length;f++){
                	if(!scripts[f].isEmpty())randomlevel=intmake.propertyscript(scripts[f], randomlevel);
                }
                }
                if(chckbxVertical.isSelected())randomlevel=intmake.rotatevertical(randomlevel);
                intmake.setspriteheader(0x00);
            	intmake.mwlmanager(randomlevel,isver);
            	intmake.clearall();
            	txtpnErrorDialog.setText(txtpnErrorDialog.getText()+"Level done"+"\n");
            }
        }); 
		
		chckbxVertical.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	if(chckbxVertical.isSelected()){
            		chckbxDisableInfinteMario.setEnabled(false);
            		chckbxDisableInfinteMario.setSelected(true);
            		choice.setEnabled(false);
            	}
            	else {
            		chckbxDisableInfinteMario.setEnabled(true);
            	}
            }
        }); 
		
		
		chckbxDisableInfinteMario.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	if(chckbxDisableInfinteMario.isSelected())choice.setEnabled(false);
            	else choice.setEnabled(true);
            }
        }); 
		
		choice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				switch((String)arg0.getItem()){
				case "Plains":
				imblvltp=0;
				break;
				case "Cave":
				imblvltp=1;
				break;
				case "Castle":
				imblvltp=2;
				break;
				}
				
			}
        }); 
		
		
		
	}
}
