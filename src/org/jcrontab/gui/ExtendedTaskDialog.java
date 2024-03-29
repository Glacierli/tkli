/**
 *  This file is part of the jcrontab package
 *  Copyright (C) 2001-2004 Israel Olalla
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free
 *  Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *  MA 02111-1307, USA
 *
 *  For questions, suggestions:
 *
 *  iolalla@yahoo.com
 *
 */
package org.jcrontab.gui;

import org.jcrontab.data.CrontabEntryBean;
import org.jcrontab.data.CrontabEntryDAO;
import org.jcrontab.data.CrontabParser;
import javax.swing.JTextField;
import org.jcrontab.log.Log;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;

/**
 * This class  
 * @author $Author: iolalla $
 * @version $Revision: 1.2 $
 */
class ExtendedTaskDialog extends JDialog implements TaskDialog{
    
	private JTextField seconds;
    private JTextField minute;
	private JTextField hour;
	private JTextField dayOfMonth;
	private JTextField month;
	private JTextField dayOfWeek;
	private JTextField task;
    private JTextField method;
	private JTextField parameters;
    private JTextField years;
    private DateButton startDateButton;
    private DateButton endDateButton;
    private Date startDate;
    private Date endDate;
    private JCheckBox runInBusinessDays;
    
    private JPanel panel;
    private JLabel label;
	private JButton ok;
	private JButton cancel;
    private boolean isUpdate = false;
    private boolean isExtended = true;
    private CrontabEntryBean ceb;
    private int id;
    private int position;
    
    /**
	 *	Default constructor of the TaskDialog class
	 * @param CrontabEntryBean
	 * @param boolean to know if its update or not
	 */
	public ExtendedTaskDialog(CrontabEntryBean ceb2, boolean update, int position){
       
            
        if (update) this.id = ceb2.getId();
        
        this.ceb = ceb2;
        this.isUpdate = update;
          
		panel = new JPanel(new GridLayout(13,2,0,6));
		panel.setBorder(new EmptyBorder(12,12,6,12));
        
        addFields();

		getContentPane().add(BorderLayout.CENTER,panel);
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		panel.setBorder(new EmptyBorder(6,12,12,12));
		panel.add(Box.createGlue());
		ok = new JButton("Ok");
		ok.addActionListener(new ActionHandler());
		getRootPane().setDefaultButton(ok);
		panel.add(ok);
		panel.add(Box.createHorizontalStrut(6));
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionHandler());
		panel.add(cancel);
		panel.add(Box.createGlue());
		getContentPane().add(BorderLayout.SOUTH,panel);
        
		Dimension screen = getToolkit().getScreenSize();
		pack();
		setLocation((screen.width - getSize().width) / 2,
			(screen.height - getSize().height) / 2);
		setVisible(true);
        toFront();
	}
    public void addFields() {
        label = new JLabel("Seconds",JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
        panel.add(seconds = new JTextField(ceb.getSeconds()));
        //
		label = new JLabel("Minutes",JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
		panel.add(minute = new JTextField(ceb.getMinutes()));
        //
		label = new JLabel("Hours", JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
		panel.add(hour = new JTextField(ceb.getHours()));
        //
		label = new JLabel("Days Of Month",JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
		panel.add(dayOfMonth = new JTextField(ceb.getDaysOfMonth()));
        //
		label = new JLabel("Months",JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
		panel.add(month = new JTextField(ceb.getMonths()));
        //
		label = new JLabel("Days Of Week",JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
		panel.add(dayOfWeek = new JTextField(ceb.getDaysOfWeek()));
        //
		label = new JLabel("Class Name",JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
        panel.add(task = new JTextField(ceb.getClassName()));
		//
        label = new JLabel("Method name",JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
		panel.add(method = new JTextField(ceb.getMethodName()));
        //
		label = new JLabel("Parameters",JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
        	String params = new String();
		if ( ceb.getExtraInfo() != null) {
			for (int i = 0; i< ceb.getExtraInfo().length ;i++) {
				params += ceb.getExtraInfo()[i] + " ";
			}
		}
		panel.add(parameters = new JTextField(params));
        //
        label = new JLabel("Years",JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
        	panel.add(years = new JTextField(ceb.getYears()));
        //
        	label = new JLabel("Start Date",JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
        	panel.add(startDateButton = new DateButton());
        //
        	label = new JLabel("End Date",JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
        	panel.add(endDateButton = new DateButton());
        //
        	label = new JLabel("Run in Business Days",JLabel.RIGHT);
		label.setBorder(new EmptyBorder(0,0,0,12));
		panel.add(label);
        	panel.add(runInBusinessDays = new JCheckBox());
        //

    }

	public void ok() {
        CrontabEntryBean ceb3 = new CrontabEntryBean();
        try {
        ceb3.setSeconds(seconds.getText());
        ceb3.setMinutes(minute.getText());
        ceb3.setHours(hour.getText());
        ceb3.setDaysOfMonth(dayOfMonth.getText());
        ceb3.setMonths(month.getText());
        ceb3.setDaysOfWeek(dayOfWeek.getText());
        ceb3.setYears(years.getText());
        
        if (task.getText().indexOf("#") > 0) {
            StringTokenizer tokenize = new StringTokenizer(task.getText(), "#");
            String className = tokenize.nextToken();
            String methodName = tokenize.nextToken();
            ceb3.setClassName(className);
            ceb3.setMethodName(methodName);
        } else {
            ceb3.setClassName(task.getText());
            ceb3.setMethodName(method.getText());
        }
        if (parameters.getText() != null) {
           StringTokenizer tokenizer = new StringTokenizer(parameters.getText());
           int numTokens = tokenizer.countTokens();

           boolean bextraInfo = true;
           int i = 0;
            String[] extraInfo = new String[numTokens];
            while (tokenizer.hasMoreElements()) {
                   extraInfo[i] = tokenizer.nextToken();
                i++;
            }
            ceb.setBExtraInfo(bextraInfo);
            ceb.setExtraInfo(extraInfo);
        }
        
        CrontabParser parser = new CrontabParser();
        ceb3 = parser.completeTheMarshalling(ceb3);
        if (isUpdate) {
                CrontabEntryBean[] cebList = new CrontabEntryBean[1];
                ceb3.setId(this.id);
                cebList[0] = ceb3;
                CrontabEntryDAO.getInstance().remove(cebList);
        }
                CrontabEntryDAO.getInstance().store(ceb3);
                this.ceb = ceb3;
        org.jcrontab.gui.Event event = new DataModifiedEvent(DataModifiedEvent.DATA, this);
        JcrontabGUI.getInstance().notify(event);
        } catch (Exception ex) {
            BottomController.getInstance().setError(ex.toString());
            Log.error("Error", ex);
        }
        dispose();
	}
   
    public void cancel() {
        dispose();
    }

    public CrontabEntryBean getCrontabEntryBean() {
        return ceb;
    }
    
	class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource() == ok)
				ok();
			else
				cancel();
		}
	}
}
