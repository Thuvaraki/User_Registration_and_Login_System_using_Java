/**User Registration and Login System
        --------------------------------------------------

        Create a simple UI Interface and get inputs for username, password and password confirmation.
        Add User inputs to database.

        Do following validation checks.

        UserName
        -----------------
        1. Cannot be a blank
        2. Length between 10 and 20 Characters
        3. Should not exist in database

        Pasword
        --------------
        1. Cannot be a blank
        2. Length between 10 and 20 Characters

        Password Confirmation
        -------------------------------------
        1. Cannot be a blank
        2. Length between 10 and 20 Characters
        3. should tally with password data

        Create following Database

        DataBase Name : UserList

        Table Name : Users

        Fields
        ---------

        UserName		Varchar(20)
        Password		Varchar(20)

        Primary Key Field : UserName
*/

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class UserAdd extends JFrame implements KeyListener{
    JLabel l1,l2,l3;
    JTextField t1;
    JPasswordField p1,p2;

    Connection con;
    PreparedStatement ps; //insert update delete

    Statement st;
    ResultSet rs;

    String uname,pword,conpword;

    UserAdd(){
        super("User Addiction");
        setSize(332,300);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.cyan);

        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize=getSize();

        this.setBounds((screensize.width-windowsize.width)/2,
                (screensize.height- windowsize.height)/2,windowsize.width,windowsize.height);

        l1=new JLabel("User Name");
        add(l1);
        l1.setBounds(60,80,100,25);

        l2=new JLabel("Password");
        add(l2);
        l2.setBounds(60,140,100,25);

        l3=new JLabel("Confirm Password");
        add(l3);
        l3.setBounds(60,200,100,25);


        t1=new JTextField();
        add(t1);
        t1.setBounds(180,80,100,25);
        t1.addKeyListener(this);


        p1=new JPasswordField();
        add(p1);
        p1.setEditable(false);
        p1.setBounds(180,140,100,25);
        p1.addKeyListener(this);

        p2=new JPasswordField();
        add(p2);
        p2.setEditable(false);
        p2.setBounds(180,200,100,25);
        p2.addKeyListener(this);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserList", "root", "");
            JOptionPane.showMessageDialog(null, "Database Connection Success", "Connection Messsage", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void keyPressed (KeyEvent e) {
        int code = e.getKeyCode();

        //code==10 is for Enter key
        if (code == 10) {
            if (e.getSource() == t1) {
                onUserNameVal();
            } else if (e.getSource() == p1) {
                onPasswordVal();
            } else if (e.getSource() == p2) {
                onConPwordVal();
            }
        }
    }
    public void keyTyped (KeyEvent e){
    }
    public void keyReleased (KeyEvent e){
    }


public void onUserNameVal(){
    uname=t1.getText();
    if(uname.equals(""))
        JOptionPane.showMessageDialog(null,"User Name is not Entered","Error",JOptionPane.ERROR_MESSAGE);
    else{
        uname=uname.trim();
        if((uname.length()<10) || (uname.length()>20)) {
            JOptionPane.showMessageDialog(null, "User Name should have a minimum of 10 Characters and maximum of 20 characters Length","Error",JOptionPane.ERROR_MESSAGE);
            t1.setText("");
        }
        else{
            try {
                st = con.createStatement();
                rs = st.executeQuery("select *from Users where username='" + uname + "'");

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "User Name already Exists", "Error", JOptionPane.ERROR_MESSAGE);
                    t1.setText("");
                } else {
                    p1.setEditable(true);
                    p1.requestFocus();
                    t1.setEditable(false);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,e,"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

public void onPasswordVal() {
    pword=p1.getText();
    if(pword.equals(""))
         JOptionPane.showMessageDialog(null,"Password is not entered","Error",JOptionPane.ERROR_MESSAGE);
    else {
        pword=pword.trim();
        if((pword.length()<10) || (pword.length()>20)) {
            JOptionPane.showMessageDialog(null, "Password should have a minimum of 10 Characters and maximum of 20 characters Length", "Error", JOptionPane.ERROR_MESSAGE);
            p1.setText("");
        }
        else{
            p1.setEditable(false);
            p2.setEditable(true);
            p2.requestFocus();
}
}
}

public void onConPwordVal(){
    conpword=p2.getText();
    if(conpword.equals(""))
        JOptionPane.showMessageDialog(null,"Please Confirm The Password","Error",JOptionPane.ERROR_MESSAGE);
    else{
        conpword=conpword.trim();
        if((conpword.length()<10)||(conpword.length()>20)){
            JOptionPane.showMessageDialog(null,"Password should have a minimum of 10 Characters and maximum of 20 characters length","Error",JOptionPane.ERROR_MESSAGE);
            p2.setText("");
        }
        else{
            if(!pword.equals(conpword))
            {
                JOptionPane.showMessageDialog(null,"Illegal password confirmation","Error",JOptionPane.ERROR_MESSAGE);
                p2.setText("");
            }
            else{
                onRecordAdd(); //calling the user defined method "onRecordAdd()"
            }
        }
    }
}

    public void onRecordAdd() {
        int confirm = JOptionPane.showConfirmDialog(null, "Do You Want to Add This Record", "Additon Message", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                ps = con.prepareStatement("insert into Users values(?,?)");
                ps.setString(1, uname);
                ps.setString(2, pword);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record Added", "Additon", JOptionPane.INFORMATION_MESSAGE);
                onUserConfirm(); //calling the user defined function "onUserConfirm()"
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            onUserConfirm();
        }
    }


    public void onUserConfirm(){
    int confirm=JOptionPane.showConfirmDialog(null,"Do you want to continue","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
    if(confirm==JOptionPane.YES_OPTION){
        onClearAndStart(); //calling the user defined function "onClearAndStart()"
        }
    else{
        System.exit(0);
        }
        }

        public void onClearAndStart(){
    t1.setText("");
    p1.setText("");
    p2.setText("");
    t1.setEditable(true);
    t1.requestFocus();
    p2.setEditable(false);
        }

        public static void main(String args[]){
        UserAdd uadd=new UserAdd();
        uadd.setVisible(true);
        }
        }
