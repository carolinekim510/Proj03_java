package com.Proj03_HW.Proj03;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;

public class Proj03Runner {

    public Proj03Runner() {
        System.out.println("I certify that this program is my own work");
        System.out.println("and is not the work of other. I agree not");
        System.out.println("to share my solution with others.");
        System.out.println("Caroline Kim\n");
    }

    public void run(String website) {
        try {
            new HtmlHandler(website);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class HtmlHandler extends JFrame implements ActionListener, HyperlinkListener {

    JEditorPane html;
    ArrayList<URL> bkHistory = new ArrayList<URL>(); //백 히스토
    Stack<URL> toForward = new Stack<URL>(); //넥스트 유알
    URL url;
    int curIndex = 0;
    int forwardCT = 0;

    public HtmlHandler(String website) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Copyright 2020, Caroline Kim");

        try {
            if (website != null) {

                url = new URL(website);

                JEditorPane html = new JEditorPane(website);
                html.setEditable(false);
                html.addHyperlinkListener(this);
                // making this html field to not null
                this.html = html;

                // adding back & forward button & address bar
                JPanel panel = new JPanel();
                JButton backBtn = new JButton("Back");
                JButton forwardBtn = new JButton("Forward");
                JTextField address = new JTextField("http://www.dickbaldwin.com/tocdsp.htm");

                backBtn.setActionCommand("Back");
                forwardBtn.setActionCommand("Forward");
                backBtn.addActionListener(this);
                forwardBtn.addActionListener(this);

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

    public void hyperlinkUpdate(HyperlinkEvent e) {

        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {   // When the link is cliked, this will send the back to the frame

            try {
                this.bkHistory.add(e.getURL());
                this.curIndex = this.curIndex + 1;
                this.html.setPage(e.getURL());

                System.out.println(bkHistory); // 클릭이 된후에 보여지는 리스트 ===================

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {    //클릭을 했을때의 엑션들이 먼저 이뤄진후, 화면이 보이도록 해야함.

        if (e.getActionCommand().equals("Back")) {
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
                System.out.println("no more old pages");
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
            }

        }


    }

}
