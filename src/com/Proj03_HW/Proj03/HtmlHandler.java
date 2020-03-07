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
    HtmlHandler y;


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

        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {   // When the link is cliked, this will send the back to the frame

            try {
                // Comparing current position and history list
                // if it's same history sends to the last array
                // if it isn't add into the [index] where back was entered
                // if it's last page then add to the last history
                // if it isn't remove all history after it was entered
                if (this.position != (this.bkHistory.size() - 1)) {
                    for(int i=this.position + 1; i<this.bkHistory.size(); i++) {
                        this.bkHistory.remove(i);
                    }
                }
                this.bkHistory.add(e.getURL());
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

                // "Back" isn't activate when it isn't the first page
                // Others will throw exception
                if ((bkHistory.size()-1) >0 ) {
                    url = this.bkHistory.get(this.position - 1);
                    address.setText(url.toString());
                    this.position = this.position - 1;
                } else {
                    throw new Exception("Previous Page does not exist");
                }
            } else if (e.getActionCommand().equals("Forward")){

                // It navigates to next page where there is next page
                // Others will throw exception
                if (this.position < (this.bkHistory.size() - 1)) {
                    url = this.bkHistory.get(this.position + 1);
                    address.setText(url.toString());
                    this.position = this.position + 1;
                } else {
                    throw new Exception("Next page does not exist.");
                }
            } else {
                this.html.setPage(address.getText());
            }
            this.html.setPage(url.toString());
        } catch (Exception i) {
            i.printStackTrace();
        }


    }
/*if (e.getActionCommand().equals("Back")) {
            if (this.bkHistory.size() > 1) {
                this.toForward.push(this.bkHistory.get(this.bkHistory.size() -1));
                this.bkHistory.remove(this.bkHistory.size() - 1);   //현재 리스트에서 마지막 1개를 지움

                try {
                    this.html.setPage(this.bkHistory.get(this.bkHistory.size() - 1));   //지워진 리스트중 마지막 인덱스에 있는 페이지로 이동함
                    System.out.println("this is " + bkHistory);  //리스트안에 남겨진 히스토리 프린
                    System.out.println("this is " + toForward + "| Count: " + forwardCT);
                    //System.out.println("Forward" + toForward);
                } catch (Exception io) {
                    io.printStackTrace();
                    // System.out.println("--");
                }
            } else {
                System.out.println("There is no more page to go back.");
            }
        } else if (e.getActionCommand().equals("Forward")) {
            if (!this.toForward.isEmpty()) {
                try {
                    this.bkHistory.add(this.toForward.get(this.toForward.size()-1));
                    this.html.setPage(this.toForward.pop());
                    forwardCT = forwardCT + 1;
                    System.out.println("this is " + bkHistory);
                    System.out.println("this is " + toForward + "| Count: " + forwardCT);  //리스트안에 남겨진 히스토리 프린
                } catch (Exception io) {
                    io.printStackTrace();
                }
            } else {
                System.out.println("There is no more page to go forward.");
            }

        } else {
            myURL = new String (this.address.getText());
            System.out.println(myURL);

            this. y = new HtmlHandler(myURL);

        }
*/
}
