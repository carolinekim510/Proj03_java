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
        int curIndex = 0;

        public HtmlHandler(String website) {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Copyright 2020, Caroline Kim");

            try {
                if (website != null) {

                    URL url = new URL(website);

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
                    this.bkHistory.add(url); // adding default starting page.  The stack of toBack will always have the default page as starter
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

                    System.out.println(bkHistory); // 클릭이 된후에 보여지는 리스트

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void actionPerformed(ActionEvent e) {    //클릭을 했을때의 엑션들이 먼저 이뤄진후, 화면이 보이도록 해야함.

            if (e.getActionCommand().equals("Back")) {
                if (this.bkHistory.size() > 1) {

                    //this.toForward.add(this.bkHistory.get(this.curIndex));
                    try {
                        this.html.setPage(this.bkHistory.get(this.curIndex - 1));
                        this.bkHistory.remove(this.bkHistory.size()-1);

                        System.out.println(bkHistory);
                    } catch (Exception io) {
                        System.out.println("No more page.");
                    }

                }
            } else if (e.getActionCommand().equals("Forward")) {

            } else {
                System.out.println("ERROR");
            }


    }

            /*
            if (e.getActionCommand().equals("Back")) {  // 뒤로가기 버튼을 누르

                if (this.bkHistory.size() > 1) {    // 히스토리 리스트의 숫자가 1보다 크다면 (인덱스 [0]에는 시작 페이지가 함유 되어있기 때문에, 숫자가 1보다 커야 한다.)

                    //URL temp = this.bkHistory.get(this.curIndex - 1); //리스트에서중 그 전에 인덱스 주소에 가도록 해줌
                    this.bkHistory.remove(this.bkHistory.size() - 1); //히스토리 리스트중 마지막을 삭제

                    try {
                        this.html.setPage(this.bkHistory.get(this.curIndex - 1));
                        System.out.println(bkHistory); // 히스토리안에 남아있는 리스트 주소들 값을 보여줌
                    } catch (Exception io){
                        System.out.println("no more page.");
                    }

                }

            ========
            try{
                if (e.getActionCommand().equals("Back")) {
                    // 뒤로가기 버튼을 누르
                    if (this.bkHistory.size() > 1) {
                        // 히스토리 리스트의 숫자가 1보다 크다면 (인덱스 [0]에는 시작 페이지가 함유 되어있기 때문에, 숫자가 1보다 커야 한다.)

                        URL temp = this.bkHistory.get(this.curIndex - 1); //리스트에서중 그 전에 인덱스 주소에 가도록 해줌
                        this.html.setPage(temp); //창에 인덱스안에있는 주소로
                        this.bkHistory.remove(this.bkHistory.size() -1 ); //히스토리 리스트중 마지막을 삭제

                        System.out.println(bkHistory); // 히스토리안에 남아있는 리스트 주소들 값을 보여줌
                    } else {
                        System.out.println("no more page.");
                    }
                } else if (e.getActionCommand().equals("Forward")) {
                    System.out.println("forward clicked");
                }
            } catch (Exception io) {
                System.out.println("error");
            }

             */


    }
