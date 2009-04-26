/** Multi-Draw
 ** April/May 2009
 ** Iteration 3, 4
 ** GUI must have the following sections:
 ** Screen 1:
 ** Username, owner?, IP/POrt & Connection
 **
 ** Screen 2:
 ** Toolbar with File, Tool, Control, Session
 ** Tools vertical along western side
 ** Chat vertically in its own window cell on the eastern side
 ** IP info on the southern side
 ** Font etc on southern side
 **
 **/

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.lang.*;
import java.util.*;
import java.awt.event.*;

import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


 public class GUI
 {
     public static JMenuBar MenuBar()
     {
          // First: MenuBar

         //Menu aka Toolbar (in Swing it's a "Menubar")
         // [  File ][  Tool ][  Control ][  Session ][  Help ]
         //  - New    - Add    - Req ctrl  - Save      - About
         //  - Quit   - Share  - Relinqsh  - Load      - Index

         JMenuBar menubar = new JMenuBar();
         JMenu fileMenu = new JMenu("File");
         fileMenu.add(new JSeparator());
         JMenu toolMenu = new JMenu("Tool");
         toolMenu.add(new JSeparator());
         JMenu ctrlMenu = new JMenu("Control");
         ctrlMenu.add(new JSeparator());
         JMenu sessionMenu = new JMenu ("Session");
         sessionMenu.add(new JSeparator());
         JMenu helpMenu = new JMenu ("Help");
         helpMenu.add(new JSeparator());

         // Here are the options in each drop-down category.

         // File
         JMenuItem fileItem1 = new JMenuItem("New");
         JMenuItem fileItem2 = new JMenuItem("Quit");
         fileItem2.add(new JSeparator());

         // Tool
         JMenuItem toolItem1 = new JMenuItem("Add Tool");
         JMenuItem toolItem2 = new JMenuItem("Share Tool");

         // Control
         JMenuItem ctrlItem1 = new JMenuItem("Request");
         JMenuItem ctrlItem2 = new JMenuItem("Relinquish");

         // Session
         JMenuItem sessionItem1 = new JMenuItem("Save");
         JMenuItem sessionItem2 = new JMenuItem("Load");

         // Help
         JMenuItem helpItem1 = new JMenuItem("About");
         JMenuItem helpItem2 = new JMenuItem("Index");
         helpItem2.add(new JSeparator());

         //now let's actually add them to our canvas toolbar

         // File
         fileMenu.add(fileItem1);
         fileMenu.add(fileItem2);

         // Tool
         toolMenu.add(toolItem1);
         toolMenu.add(toolItem2);

         //Control
         ctrlMenu.add(ctrlItem1);
         ctrlMenu.add(ctrlItem2);

         //Session
         sessionMenu.add(sessionItem1);
         sessionMenu.add(sessionItem2);

         //Help
         helpMenu.add(helpItem1);
         helpMenu.add(helpItem2);

         //now place each menu....
         menubar.add(fileMenu);
         menubar.add(toolMenu);
         menubar.add(ctrlMenu);
         menubar.add(sessionMenu);
         menubar.add(helpMenu);

         return menubar;
     }

     public static JToolBar ToolBar()
     {
         // Vertical Toolbar
         // How to add a new Tool icon:
         // very similar to professor Kuhl's example.
         // Where anything preceeded with * should be changed.
         // JButton *buttonName = new JButton(new ImageIcon("*filename.jpg"));
         // toolbar.add(*filename.jpg);

         //Toolbar
         JToolBar toolbar = new JToolBar("Toolbar", JToolBar.VERTICAL);

         //Select Tool
         JButton selectBTN = new JButton(new ImageIcon("select.JPG"));
         toolbar.add(selectBTN);

         //Freehand Tool
         JButton freehandBTN = new JButton(new ImageIcon("freehand.jpg"));
         toolbar.add(freehandBTN);

         //Line Tool
         JButton lineBTN = new JButton(new ImageIcon("line.jpg"));
         toolbar.add(lineBTN);

         //Rectangle Tool
         JButton rectangleBTN = new JButton(new ImageIcon("rectangle.jpg"));
         toolbar.add(rectangleBTN);

         //Oval Tool
         JButton ovalBTN = new JButton(new ImageIcon("oval.jpg"));
         toolbar.add(ovalBTN);

         //Text Tool
         JButton textBTN = new JButton(new ImageIcon("text.jpg"));
         toolbar.add(textBTN);

         //Eraser Tool
         JButton eraserBTN = new JButton(new ImageIcon("eraser.jpg"));
         toolbar.add(eraserBTN);

         return toolbar;
     }

     public static JTabbedPane TabbedPane()
     {
         // Tab Implementation (to be commented out)
         // We could try this for sessions later, if we wanted, but simply
         //replace our static number 1 and 2 dynamically when a new
         //session is created. We can also disable tabs when they are not
         //connected.

         JTabbedPane tab = new JTabbedPane();

         //"Session Canvas 1" would display session 1
         JButton tabBTN = new JButton("Canvas for session 1");
         tab.add("Session 1", tabBTN);
         //"2" would display session 2
         tabBTN  = new JButton("Canvas for session 2");
         tab.add("Session 2", tabBTN);

         return tab;
     }
     public static String[] getUsers(int amt)
     {
     	// This is eventually going to get a list of users from the chat section
     	// Possibly in array form??
     	// Right now there are dummy values for this in an array, please adjust as needed
		int amtUsers = amt; // constant here, this would be retrieved
     	String[] users = new String[amtUsers];
        users[0] = "Amanda";
        users[1] = "Ben";
        users[2] = "Joe";
        users[3] = "Mark";
        users[4] = "Satpreet";

     	return users;
     }
     public static JToolBar ChatPane()
     {
         JToolBar chat = new JToolBar("Chat", JToolBar.VERTICAL);

         //Users
         //JButton userBTN = new JButton(getUsers());
         //chat.add("User List", userBTN);
         //JTextArea users = new JTextArea(" List here" + );
         //int pos = 5;
         //users.insert("anything", pos);
         //Display Chat
         int amtUsers = 5;
         String[] users = new String[amtUsers];

         users = getUsers(5);

         JLabel list0 = new JLabel(users[0]);
         JLabel list1 = new JLabel(users[1]);
         JLabel list2 = new JLabel(users[2]);
         JLabel list3 = new JLabel(users[3]);
         JLabel list4 = new JLabel(users[4]);
         
         chat.add(list0);
         chat.add(list1);
         chat.add(list2);
         chat.add(list3);
         chat.add(list4);
         chat.addSeparator();

         JTextArea chatbox = new JTextArea( "This is where chats should be sent");

         JScrollPane scrollPane = new JScrollPane(chatbox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    chatbox.setEditable(false);
        chatbox.setFont(new Font("Serif", Font.BOLD, 16));
        chatbox.setLineWrap(true);
        chatbox.setWrapStyleWord(true);

        JScrollPane areaScrollPane = new JScrollPane(chatbox);
        areaScrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            areaScrollPane.setPreferredSize(new Dimension(250, 250));

         chat.add(chatbox);

         chat.addSeparator();
         JTextArea mychat = new JTextArea(1,20);
         JScrollPane scrollPane2 = new JScrollPane(mychat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    mychat.setEditable(true);

         chat.add(mychat);

         JButton chatBTN = new JButton("Send");
         chat.add(chatBTN);

         return chat;
     }

     public static String getClientIP()
     {
        String clientip = "12.345.67.890";
        return clientip;
     }

     public static String getHostIP()
     {
        String hostip = "09.876.54.321";
        return hostip;
     }

     public static int getHostPort()
     {
        int port = 1234;
        return port;
     }
     public static Panel panel()
     {
         //Chat window that can be removed like the western toolbar
         // IP, Port and Font, etc.
         // This will take up the southern part of the screen
         // [------------][Font][Color][-------------] | [Chat input]
         // My IP :  [IP: ###...###]
         // Host IP: [IP: ###...###] Port: [####]


         //First let's make the southern box frame
         Panel panel = new Panel();

         // IP Section:
         // This accepts a String for the Client's IP
         String clientip = getClientIP();
         String hostip = getHostIP();
         int port = getHostPort();

         JLabel myIP = new JLabel("My IP: " + clientip);
         JLabel theirIP = new JLabel("Host IP: " + hostip + " : " + port);

         panel.add(myIP);
         
         panel.add(theirIP);

         return panel;
     }
     public static void main(String[] args)
     {
         JFrame frame = new JFrame("Multi-Draw");

         JMenuBar menubar = MenuBar();

         //Now let's actually place the menubar at the top of the page.
         frame.add(menubar,BorderLayout.NORTH);

         JToolBar toolbar = ToolBar();
         frame.getContentPane().add(toolbar,BorderLayout.WEST);

         JTabbedPane tab = TabbedPane();
         frame.add(tab,BorderLayout.CENTER);

         JToolBar chat = ChatPane();
         frame.add(chat,BorderLayout.EAST);

         Panel panel = panel();
         frame.add(panel,BorderLayout.SOUTH);


         // Frames
         // This section will layout all of the panels for the rest of
         // the GUI.

         //frame.setJMenuBar(menubar);
         frame.setUndecorated(true);
         frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(800,600);
         frame.setVisible(true);
    }

 }
