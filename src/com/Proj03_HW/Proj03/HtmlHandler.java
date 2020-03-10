package com.Proj03_HW.Proj03;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

public class HtmlHandler extends JFrame implements ActionListener, HyperlinkListener {

    JEditorPane html;
    String website = null;
    JTextField address;
    ArrayList<URL> bkHistory = new ArrayList<URL>(); //백 히스토
    int position = 0;
    boolean addHistory = true;

    public HtmlHandler(String website) {
        this.website = website;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Copyright 2020, Caroline Kim");
    }

    public void startBrowser() {

        try {
            if (website != null) {

                URL url = new URL(website);

                JEditorPane html = new JEditorPane(website);
                html.setEditable(false);
                html.addHyperlinkListener(this);
                this.html = html;

                // adding back & forward button & address bar
                JPanel panel = new JPanel();
                JButton backBtn = new JButton("Back");
                JButton forwardBtn = new JButton("Forward");
                address = new JTextField(website);

                backBtn.setActionCommand("Back");
                forwardBtn.setActionCommand("Forward");
                backBtn.addActionListener(this);
                forwardBtn.addActionListener(this);
                address.addActionListener(this);

                panel.add(backBtn);
                panel.add(address);
                panel.add(forwardBtn);

                // adding scroll pane
                JScrollPane scroller = new JScrollPane();
                JViewport vp = scroller.getViewport();
                vp.add(html);

                this.getContentPane().add(panel, BorderLayout.NORTH);
                this.getContentPane().add(scroller, BorderLayout.CENTER);
                this.bkHistory.add(url);    //default starting page is added
                this.setSize(669, 669);
                this.setVisible(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {

        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {   // When the link is clicked, this will send the back to the frame

            try {
                if (addHistory = true) {
                    this.bkHistory.add(e.getURL());     //history only adds when new link is clicked.
                }

                address.setText(e.getURL().toString());
                this.html.setPage(e.getURL());
                this.position = this.position + 1;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {    //클릭을 했을때의 엑션들이 먼저 이뤄진후, 화면이 보이도록 해야함.
        try {
            URL url = null;
            if (e.getActionCommand().equals("Back")) {
                addHistory = false;

                if ((bkHistory.size()-1) > 0 ) {
                    url = this.bkHistory.get(this.position - 1);
                    address.setText(url.toString());
                    this.position = this.position - 1;

                } else {
                    throw new Exception("Previous Page does not exist");
                }
            } else if (e.getActionCommand().equals("Forward")){
                addHistory = false;

                if (this.position < (this.bkHistory.size() - 1)) {
                    url = this.bkHistory.get(this.position + 1);
                    address.setText(url.toString());
                    this.position = this.position + 1;

                } else {
                    throw new Exception("Next page does not exist.");
                }
            } else {

                System.out.println("old: " + position);
                url = new URL(address.getText());

                this.position = this.position + 1;
                this.bkHistory.add(this.position, url);
                this.bkHistory.subList(this.position+1, this.bkHistory.size()).clear();

                System.out.println("new: " + position);                       //ERASE LATER

            }

            this.html.setPage(new URL(address.getText()));          //bring html for any page

            System.out.println("history button: " + bkHistory);     //ERASE later

        } catch (Exception i) {
            i.printStackTrace();
        }



    }

}
