import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.hibernate.*;
import org.hibernate.cfg.*;

class DeleteFrame extends JFrame {
Container c;
JLabel lblHid;
JTextField txtHid;
JButton btnSave, btnBack;
int hid;

DeleteFrame() {
c = getContentPane();
c.setLayout(new GridBagLayout());

JLabel lbltitle;
 
lbltitle= new JLabel("Hotel Management System -> DELETE details of Customer ");
lbltitle.setFont(new Font("Forte", Font.BOLD, 40));
lbltitle.setOpaque(true);
lbltitle.setBackground(Color.CYAN);

JLabel background;
ImageIcon img=new ImageIcon("hotel6.jpg");
background = new JLabel("",img,JLabel.CENTER);
 background.setPreferredSize(new Dimension(700, 700));


lblHid = new JLabel("Enter Customer ID : ");
txtHid = new JTextField(20);

btnSave = new JButton("Delete Details");
btnBack = new JButton("Back");

GridBagConstraints m = new GridBagConstraints();
m.fill=GridBagConstraints.HORIZONTAL;
 m.gridx = 0;//set the x location of the grid for the next component
        m.gridy = 0;//set the y location of the grid for the next component
        
c.add(lbltitle,m);
m.gridy = 1;
c.add(background,m);
m.gridx = 0;
        m.gridy = 2;//change the y location
 m.insets = new Insets(5, 0, 5, 0);
        m.anchor=GridBagConstraints.CENTER;//left align components after this point
        c.add(lblHid,m);
 m.gridy = 3;
c.add(txtHid,m);

       
        m.gridy = 8;//change the y location
        c.add(btnSave,m);

        m.gridy = 9;//change the y location
        c.add(btnBack,m);


btnBack.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent ae) {
MainFrame a = new MainFrame();
dispose();
} } );

btnSave.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent ae) {

Configuration cfg = new Configuration();
cfg.configure("hibernate.cfg.xml");

SessionFactory sfact = cfg.buildSessionFactory();
Session session = sfact.openSession();

Transaction t = null;

try {
t = session.beginTransaction();

try {
hid = Integer.parseInt(txtHid.getText());
}
catch(NumberFormatException nfe) {
JOptionPane.showMessageDialog(new JDialog(),"Please Enter Valid customer Id");
txtHid.setText("");
txtHid.requestFocus();
return;
}

Hotel e = (Hotel)session.get(Hotel.class, hid);
if(e != null) {
session.delete(e);
t.commit();
JOptionPane.showMessageDialog(new JDialog(), "Record Deleted");
txtHid.setText("");
txtHid.requestFocus();
}
else {
JOptionPane.showMessageDialog(new JDialog(), "Record Not Found");
txtHid.setText("");
txtHid.requestFocus();
}
}
catch(Exception e) {
t.rollback();
JOptionPane.showMessageDialog(new JDialog(), "Error : " + e);
}
finally {
session.close();
}
}
});

setTitle("Delete ");
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
pack();
setSize(screenSize.width,screenSize.height);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);
}
}