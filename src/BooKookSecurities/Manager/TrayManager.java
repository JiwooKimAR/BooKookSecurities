package BooKookSecurities.Manager;  

/*일단 JAVAFX에 대한 system tray 정보가 충분하지 않아 swing으로 하였고
 * 창을 최소화 하면 트레이에 들어가고 더블클릭하면 메인APP가 튀어나오도록 하는 기능입니다.
 * 이후 많은 수정을 거쳐야 함. 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
public class TrayManager {
  static class ShowMessageListener implements ActionListener {
    TrayIcon trayIcon;
    String title;
    String message;
    TrayIcon.MessageType messageType;
    ShowMessageListener(
        TrayIcon trayIcon,
        String title,
        String message,
        TrayIcon.MessageType messageType) {
      this.trayIcon = trayIcon;
      this.title = title;
      this.message = message;
      this.messageType = messageType;
    }
    public void actionPerformed(ActionEvent e) {
      trayIcon.displayMessage(title, message, messageType);
    }
  }
  JFrame frame=new JFrame("System Tray Demo"); 
  public void test()
  {
   frame.addWindowListener(new WindowListener()
   {
    public void windowOpened(WindowEvent e){}
       public void windowClosing(WindowEvent e){}
       public void windowClosed(WindowEvent e){}
       public void windowIconified(WindowEvent e){ 
        frame.setVisible(false);     
        displayTrayIcon();
       }
       public void windowDeiconified(WindowEvent e){}
       public void windowActivated(WindowEvent e){
         System.out.println("windowActivated");
       }
       public void windowDeactivated(WindowEvent e){}
   });
   frame.getContentPane().add(new JButton("Hello"));
   frame.setSize(400,500);
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.setVisible(true);
 }
  
  
   public void displayMainApp()
   {                                         
        frame.setVisible(true);
        frame.setExtendedState(Frame.NORMAL);
   }
  
 public void displayTrayIcon()
 {
  Runnable runner = new Runnable()
  {
   public void run()
   {
    if(SystemTray.isSupported())
    {
     MenuItem item;
          
     final SystemTray tray = SystemTray.getSystemTray();
     Image image = Toolkit.getDefaultToolkit().getImage("D:/myproject/JRun4/servers/manual/src/com/imnetpia/exbill/webmanual/app/gifIcon.gif");
    
     PopupMenu popup = new PopupMenu();
     final TrayIcon trayIcon = new TrayIcon(image, "The Tip Text", popup);
    
     ActionListener actionListener = new ActionListener()
     {
      public void actionPerformed(ActionEvent e)
      {
       trayIcon.displayMessage("Action Event", 
         "An Action Event Has Been Peformed!",
         TrayIcon.MessageType.INFO);
      }
     };
             
     MouseListener mouseListener = new MouseListener()
     {                
      public void mouseClicked(MouseEvent e)
      {
       System.out.println("Tray Icon - Mouse clicked!");                 
      }
      public void mouseEntered(MouseEvent e)
      {
       System.out.println("Tray Icon - Mouse entered!");                 
      }
      public void mouseExited(MouseEvent e)
      {
       System.out.println("Tray Icon - Mouse exited!");                 
      }
      public void mousePressed(MouseEvent e)
      {
       if(e.getClickCount() == 2)
       {
        tray.remove(tray.getTrayIcons()[0]);
        displayMainApp();
       }       
      }
      public void mouseReleased(MouseEvent e)
      {
       System.out.println("Tray Icon - Mouse released!");                 
      }
     };
    
     ActionListener viewApp = new ActionListener()
     {
      public void actionPerformed(ActionEvent e)
      {
       tray.remove(tray.getTrayIcons()[0]);                                           
       displayMainApp();
      }
     };
     item = new MenuItem("Main APP");
     item.addActionListener(viewApp);
     popup.add(item);
     item.addActionListener(new ShowMessageListener(trayIcon,"Error Title", "Error", TrayIcon.MessageType.ERROR));
     popup.add(item);
     item = new MenuItem("Warning");
     item.addActionListener(new ShowMessageListener(trayIcon,"Warning Title", "Warning", TrayIcon.MessageType.WARNING));
     popup.add(item);
     item = new MenuItem("Info");
     item.addActionListener(new ShowMessageListener(trayIcon,"Info Title", "Info", TrayIcon.MessageType.INFO));
     popup.add(item);
     item = new MenuItem("None");
     item.addActionListener(new ShowMessageListener(trayIcon,"None Title", "None", TrayIcon.MessageType.NONE));
     popup.add(item);
     item = new MenuItem("Close");
           
     item.addActionListener(new ActionListener()
     {
      public void actionPerformed(ActionEvent e)
      {      
       tray.remove(trayIcon);
      }
     });
     popup.add(item);
     try
     {
      tray.add(trayIcon);
               trayIcon.setImageAutoSize(true);
               trayIcon.addActionListener(actionListener);
               trayIcon.addMouseListener(mouseListener);
     }catch(AWTException e)
     {
      System.err.println("Can't add to tray");
     }
           }
           else
           {
             System.err.println("Tray unavailable");
           }
         }
      };
      EventQueue.invokeLater(runner);
    
 }
  
// public static void main(String args[])
// {
//  new FullTray().test();
// }
}

