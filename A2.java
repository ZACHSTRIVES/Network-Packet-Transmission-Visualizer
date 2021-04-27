//Name: Zach Wang UPI: zwan270 ID: 240914081 
import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class A2 extends JFrame {
	static MyData data=new MyData();
	static VisualizerPanel panel = new VisualizerPanel();
	public static void createAndShowGUI() {
		JFrame frame = new JFrame("Network Packet Transmission Visualizer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//Create menu bar 
		JMenuBar menubar = new JMenuBar();
		JMenu menulabel = new JMenu("File");
		menubar.add(menulabel);
		JMenuItem item1 = new JMenuItem("Open Trace File");
		JMenuItem item2 = new JMenuItem("Quit");
		menulabel.add(item1);
		menulabel.add(item2);
		
		//Create radio buttons
		JRadioButton radioButton1 = new JRadioButton("Source Hosts"); 
		JRadioButton radioButton2 = new JRadioButton("Destination Hosts"); 

	
		//add panels
		JPanel radioButtonPanel = new JPanel(); radioButtonPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
        
		c.anchor = GridBagConstraints.NORTHWEST; 
		c.insets = new Insets(5, 10, 5, 10);

	
		
		radioButtonPanel.add(radioButton1,c);
		c.gridy=1;
		c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(5, 10, 10, 10);
		radioButtonPanel.add(radioButton2,c);
		
		radioButtonPanel.setBounds(0, 0, 200, 100);
		
		
        //Create button group.
		ButtonGroup radioButtons = new ButtonGroup();
		
		radioButtons.add(radioButton1);
		radioButton1.setSelected(true);
		radioButtons.add(radioButton2);
		
		
		panel.setBounds(0, 100, 1000, 325);
        
        //Create combo box panel.
        JPanel comboboxpanel = new JPanel();
        comboboxpanel.setBounds(200, 25, 200, 100);
        JComboBox ipAdComboBox = new JComboBox();
        comboboxpanel.add(ipAdComboBox);
        ipAdComboBox.setVisible(false);
        
        
        
		frame.setJMenuBar(menubar);
		frame.getContentPane().add(radioButtonPanel);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(comboboxpanel);
		
		
		frame.setSize(1000, 500);
		frame.setVisible(true);
		
		//Action listener of menubar items.
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ArrayList<Packet> packets=data.getIpData();
				if(radioButton1.isSelected()==true){ 
					Object[] desthostlist=MyData.getUniqueSortedSourceHosts(data.packets);
					for(int i=0; i<desthostlist.length;i++){
						ipAdComboBox.addItem(desthostlist[i]);
					}
				}else if(radioButton2.isSelected()==true){
					Object[] desthostlist=MyData.getUniqueSortedDestHosts(data.packets);
					for(int i=0; i<desthostlist.length;i++){
						ipAdComboBox.addItem(desthostlist[i]);
					}
				
			}
				ipAdComboBox.setVisible(true);
		}});
		
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				frame.dispose();
			}
		});	
		
		//Action listener of radio buttons.
		radioButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ipAdComboBox.removeAllItems();
				Object[] desthostlist=MyData.getUniqueSortedSourceHosts(data.packets);
				for(int i=0; i<desthostlist.length;i++){
					ipAdComboBox.addItem(desthostlist[i]);
				}
				
			}
		});	
		radioButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ipAdComboBox.removeAllItems();
				Object[] desthostlist=MyData.getUniqueSortedDestHosts(data.packets);
				for(int i=0; i<desthostlist.length;i++){
					ipAdComboBox.addItem(desthostlist[i]);
				}
				
			}
		});	
		
		//Action listener of combo box.
		ipAdComboBox.addItemListener(new ItemListener() {
	        
            public void itemStateChanged(ItemEvent e) {
                
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	panel.getGraphData(data.packets,((Host)ipAdComboBox.getSelectedItem()).toString(),radioButton1.isSelected(),data.packets.get(data.packets.size()-1).getTimeStamp(),2);
                	panel.repaint();
                }
            }
        });
	}
	
	
   
	public static void main(String[] args) {
   	javax.swing.SwingUtilities.
         invokeLater(new Runnable() {
           public void run() {
             createAndShowGUI();
           }
       });
	}

    

		
	}
