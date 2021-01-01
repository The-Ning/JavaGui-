package ����;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class Newuser extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection con=DBconnection.getconn();
	ResultSet rs=null;
	PreparedStatement statement=null;
	JLabel b1=new JLabel("����");
	JLabel b2=new JLabel("�û���");
	JLabel b3=new JLabel("����");
	JLabel b4=new JLabel("ȷ������");
	JTextField kh=new JTextField(5);
	JTextField name=new JTextField(5);
	JPasswordField password=new JPasswordField(5);
	JPasswordField password1=new JPasswordField(5);
	JButton b5=new JButton("ע��");
	JButton b6=new JButton("ע��");
	JButton b7=new JButton("�޸�����");
	Box basebox,box1,box2;
	JPanel p = new JPanel();
	JPanel p1= new JPanel();
	public Newuser(){
    	setBounds(500,300,400,400);
    	setTitle("���û�ע��");
    	setLayout(new FlowLayout());
    	init();
   
    }
 void init(){
	 box1=Box.createVerticalBox();
	 box1.add(b1);
	 box1.add(Box.createVerticalStrut(8));
	 box1.add(b2);
	 box1.add(Box.createVerticalStrut(8));
	 box1.add(b3);
	 box1.add(Box.createVerticalStrut(8));
	 box1.add(b4);
	 box2=Box.createVerticalBox();
	 box2.add(Box.createVerticalStrut(8));
	 box2.add(kh);
	 box2.add(Box.createVerticalStrut(8));
	 box2.add(name);
	 box2.add(Box.createVerticalStrut(8));
	 box2.add(password);
	 box2.add(Box.createVerticalStrut(8));
	 box2.add(password1);
	 basebox=Box.createHorizontalBox();
	 basebox.add(box1);
	 basebox.add(Box.createHorizontalStrut(20));
	 basebox.add(box2);
	 p.add(basebox);
	 p1.add(b5);
	 p1.add(b6);
	 p1.add(b7);
	 this.getContentPane().add(p,BorderLayout.NORTH);
	 this.getContentPane().add(p1,BorderLayout.CENTER);
	 b5.addActionListener(this);
	 b6.addActionListener(this);
	 b7.addActionListener(this);
	 if(this.isVisible()){
		 DBconnection.Close(con, statement, rs);
	 }
    }
 @SuppressWarnings("deprecation")
private void setPassword(){
	 try {
		statement=con.prepareStatement("select ����  from �û�  where ����=?");
	    statement.setString(1, kh.getText());
	    rs=statement.executeQuery();
	    while(rs.next()){
	    	if(rs.getString("����").equals(password.getText())){
	    	String pass=JOptionPane.showInputDialog(this, "��֤�ɹ���������������");
	    	String pass1=JOptionPane.showInputDialog(this, "��ȷ������");
	    	if(pass.equals("")||pass1.equals("")){
	    		JOptionPane.showMessageDialog(this, "��������Ϊ�գ����޸�����");
	    	}
	    	else if(pass.equals(pass1)){
	    		statement=con.prepareStatement("update �û�  set ����=? where ����=?");
	    		statement.setString(1,pass1);
	    		statement.setString(2,kh.getText());
	    		statement.executeUpdate();
	    		statement=con.prepareStatement("update ����  set ����=? where ����=?");
	    		statement.setString(1,pass1);
	    		statement.setString(2,kh.getText());
	    		statement.executeUpdate();  		
	    		JOptionPane.showMessageDialog(this, "�����޸ĳɹ�");	    			    			      		
	    	}
	    
	    	else{
	    		JOptionPane.showMessageDialog(this, "������ȷ�����벻һ��");
	    	}
	    }
	    
	    
	    else{
	    	JOptionPane.showMessageDialog(this,"�������");
	    }
	 }
	 } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
private boolean isPlay(){
	boolean play=false;
	try {
		statement=con.prepareStatement("select ��״̬ from ����  where ����=?");
		statement.setString(1, kh.getText());
		rs=statement.executeQuery();
		while(rs.next()){
			if((rs.getString("��״̬").equals("�ϻ�"))){
				play=true;
			}
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return play;
}
private void delete(){
	try {
		statement=con.prepareStatement("delete  from �û� where ����=?");
		statement.setString(1, kh.getText());
		statement.executeUpdate();
		statement=con.prepareStatement("delete from ���� where ����=?");
		statement.setString(1, kh.getText());
		statement.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
@SuppressWarnings("deprecation")
private void insert(){
	  try {
		statement=con.prepareStatement("insert into �û� (����,����,�û���) values(?,?,?)");
	    statement.setString(1, kh.getText());
	    statement.setString(2, password.getText());
	    statement.setString(3, name.getText());
	    statement.executeUpdate();  
	    statement=con.prepareStatement("insert into ���� (����,�û���,����,��״̬,���,��Ա����) values(?,?,?,'�»�',0,'δ��ͨ')");
	    statement.setString(1, kh.getText());
	    statement.setString(2, name.getText());
	    statement.setString(3, password.getText());
	    statement.executeUpdate();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
@SuppressWarnings("deprecation")
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(e.getSource()==b6){
		if(kh.getText().equals("")||password.getText().equals("")|password1.getText().equals("")||name.getText().equals(""))
		{
		   JOptionPane.showMessageDialog(this, "����Ϊ��!!");
		}
		else{
			try {
				statement=con.prepareStatement("select * from �û�  where ����=?");
				statement.setString(1, kh.getText());
				rs=statement.executeQuery();
					if(!rs.next()){
					JOptionPane.showMessageDialog(this, "���˻�������");
					}
					else {
						if(isPlay()){
							JOptionPane.showMessageDialog(this, "�����»�����");
						}
						else{
						if(password.getText().equals(password1.getText())){
							String sql="select ��� from ���� where ����=?";
							statement=con.prepareStatement(sql);
							statement.setString(1, kh.getText());
							rs=statement.executeQuery();
							while(rs.next()){
								if(rs.getInt("���")>=0){
									delete();
									JOptionPane.showMessageDialog(this, "ע���ɹ�");
								}
							
								else{		
								int n=JOptionPane.showConfirmDialog(this, "�����Ƿ��","",JOptionPane.YES_NO_OPTION);
						        if(n==JOptionPane.YES_OPTION){
							    recharge re = new recharge();
							    re.setVisible(true);
						         }
						        }
								
						         }
							
							
						}
						  else {
							JOptionPane.showMessageDialog(this, "ȷ�����������벻һ��");
						    }
					} 	
					}} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	else if(e.getSource()==b5){
		if(kh.getText().equals("")||password.getText().equals("")|password1.getText().equals("")||name.getText().equals(""))
		{
		   JOptionPane.showMessageDialog(this, "����Ϊ��!!");
		}
		else{
			try {
			statement=con.prepareStatement("select * from �û�  where ����=?");
			statement.setString(1, kh.getText());
			rs=statement.executeQuery();
			
				if(rs.next()){
				JOptionPane.showMessageDialog(this, "���˻���ע��");
				}
				else {
					if(password.getText().equals(password1.getText())){
						insert();
						JOptionPane.showMessageDialog(this, "ע��ɹ�");
						int n=JOptionPane.showConfirmDialog(this, "�Ƿ����ڳ�ֵ��","",JOptionPane.YES_NO_OPTION);
					if(n==JOptionPane.YES_OPTION){
						recharge re = new recharge();
						re.setVisible(true);
					}
					}
					else {
						JOptionPane.showMessageDialog(this, "ȷ�����������벻һ��");
					}
				} 	
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	}
	else if(e.getSource()==b7){
		if(kh.getText().equals("")||password.getText().equals("")|password1.getText().equals("")||name.getText().equals(""))
		{
		   JOptionPane.showMessageDialog(this, "����Ϊ��!!");
		}
		else {
			try {
				statement=con.prepareStatement("select * from �û� where ����=?");
			    statement.setString(1, kh.getText());
			    rs=statement.executeQuery();
			    if(rs.next()){
			    	if(password.getText().equals(password1.getText())){
			    		setPassword();
			    	}
			    	else{
			    		JOptionPane.showMessageDialog(this, "ȷ�����������벻һ��");
			    	}
			    }
			    else{
			    	JOptionPane.showMessageDialog(this, "���Ŵ���");
			    }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
}
