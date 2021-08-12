import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.hibernate.*;
import org.hibernate.cfg.*;

class AddFrame extends JFrame {
Container c;
JLabel lblHid, lblHname, lblHprice;
JTextField txtHid, txtHname, txtHprice;
JButton btnSave, btnBack;
int hid;
String hname;
double hprice;

AddFrame() {
c = getContentPane();
c.setLayout(new GridBagLayout());

JLabel lbltitle;
 
lbltitle= new JLabel("Hotel Management System -> ADD Customer ");
lbltitle.setFont(new Font("Forte", Font.BOLD, 40));
lbltitle.setOpaque(true);
lbltitle.setBackground(Color.ORANGE);

JLabel background;
ImageIcon img=new ImageIcon("hotel2.png");
background = new JLabel("",img,JLabel.CENTER);
 background.setPreferredSize(new Dimension(700, 500));


lblHid = new JLabel("Enter Customer ID : ");
txtHid = new JTextField(40);
lblHname = new JLabel("Enter Customer Name :");
txtHname = new JTextField(20);
lblHprice = new JLabel("Enter Room Price : ");
txtHprice = new JTextField(20);
btnSave = new JButton("Add Details");
btnBack = new JButton("Back");

GridBagConstraints m = new GridBagConstraints();

m.fill=GridBagConstraints.HORIZONTAL;

 m.gridx = 0;//set the x location of the grid for the next component
        m.gridy = 0;//set the y location of the grid for the next component
        
c.add(lbltitle,m);
m.gridy = 1;
c.add(background,m);
m.gridx =0 ;
        m.gridy = 2;//change the y location
 m.insets = new Insets(5, 0, 5, 0);
        m.anchor=GridBagConstraints.CENTER;//left align components after this point
        c.add(lblHid,m);
 m.gridy = 3;

c.add(txtHid,m);

        m.gridy = 4;//change the y location
        c.add(lblHname,m);
 m.gridy = 5;
c.add(txtHname,m);

 m.gridy = 6;//change the y location
        c.add(lblHprice,m);
 m.gridy = 7;
c.add(txtHprice,m);

        m.gridy = 8;//change the y location
        c.add(btnSave,m);

        m.gridy = 9;//change the y location
        c.add(btnBack,m);



//c.add(lblHid);	c.add(txtHid);
//c.add(lblHname);	c.add(txtHname);
//c.add(lblHprice);	c.add(txtHprice);
//c.add(btnSave);	c.add(btnBack);

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

try{
t = session.beginTransaction();
Hotel e = new Hotel();


try{
hid = Integer.parseInt(txtHid.getText());
}
catch(NumberFormatException nfe) {
JOptionPane.showMessageDialog(new JDialog(),"Please Enter Valid Customer Hid");
txtHid.setText("");
txtHid.requestFocus();
return;
}

try{
hname = txtHname.getText();
if(hname.matches(".*\\d+.*"))
	throw new NullPointerException();
//if(!hname.matches("[a-zA-Z]+"))
if(!hname.matches("[a-zA-Z][a-zA-Z]+[a-zA-Z]$"))
	throw new NullPointerException();
if(hname.length() < 2)
	throw new IllegalAccessException();
}
catch(NullPointerException npe){
JOptionPane.showMessageDialog(new JDialog(),"Name Should only include Alphabets");
txtHname.setText("");
txtHname.requestFocus();
return;
}
catch(IllegalAccessException iae) {
JOptionPane.showMessageDialog(new JDialog(),"Name Should contain 2 or more than 2 Alphabets \n Please enter valid name");
txtHname.setText("");
txtHname.requestFocus();
return;
}

try{
hprice = Double.parseDouble(txtHprice.getText());
if(hprice < 1000.0)
	throw new NullPointerException();
}
catch(NullPointerException npe) {
JOptionPane.showMessageDialog(new JDialog(),"Minimum price Should Be 1000");
txtHprice.setText("");
txtHprice.requestFocus();
return;
}
catch(NumberFormatException nfe) {
JOptionPane.showMessageDialog(new JDialog(),"Please Enter Valid price");
txtHprice.setText("");
txtHprice.requestFocus();
return;
}

e.setHid(hid);
e.setHname(hname);
e.setHprice(hprice);
session.save(e);
t.commit();
JOptionPane.showMessageDialog(new JDialog(),"Record Saved");
txtHid.setText("");
txtHname.setText("");
txtHprice.setText("");
txtHid.requestFocus();

}
catch(org.hibernate.exception.ConstraintViolationException cve) {
JOptionPane.showMessageDialog(new JDialog(),"Record Already Exists,Please Use Different Customer ID");
txtHid.setText("");
txtHid.requestFocus();
}
catch(Exception e) {
t.rollback();
JOptionPane.showMessageDialog(new JDialog(),"Error : " + e);
}
finally {
session.close();
} } } );

Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
pack();
setSize(screenSize.width,screenSize.height);
setTitle("Add ");

setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);
}
}



