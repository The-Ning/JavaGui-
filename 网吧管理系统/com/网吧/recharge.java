package ����;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class recharge extends JFrame implements ActionListener{
	Connection con = DBconnection.getconn();
	ResultSet rs=null;
	PreparedStatement statement=null;
	int arr[]={5,10,15,20,30,50,100};
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel b1 = new JLabel("��ֵһ��Ԫ���ɳ�ΪVIP");
	JButton b2 = new JButton("��ֵ");
	JButton b3 = new JButton("��ѯ���");
	JButton b4 = new JButton("�˳�");
	JPanel p = new JPanel();
	JPanel p1 = new JPanel();
	@SuppressWarnings("rawtypes")
	JComboBox hm= new JComboBox();
	@SuppressWarnings("rawtypes")
	JComboBox m= new JComboBox();
  public recharge(){
	  setTitle("��ӭ��ֵ");
	  setSize(400,500);
	  init();
  }
  @SuppressWarnings("unchecked")
public void init(){
	  add(b1);
	  this.getContentPane().add(p,BorderLayout.NORTH);	  
	  this.getContentPane().add(p1,BorderLayout.CENTER);
	  p.add(b1);
	  hm.addItem("����");
	  for(int i=0;i<arr.length;i++)
	  {
	  m.addItem(arr[i]);
	  }
	  p1.add(hm);
	  p1.add(m);
	  p1.add(b2);
	  p1.add(b3);
	  p1.add(b4);
	  getkh();
	  b2.addActionListener(this);
	  b3.addActionListener(this);
	  b4.addActionListener(this);
  }
   @SuppressWarnings("unchecked")
public void getkh(){
	   try {
		statement=con.prepareStatement("select ���� from �û�");
		rs=statement.executeQuery();
		while(rs.next()){
			hm.addItem(rs.getString("����"));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
   }
   private boolean isVip(){
	   boolean vip=false;
	   try {
		statement=con.prepareStatement("select ��Ա���� from ���� where ����=?");
		statement.setString(1, hm.getSelectedItem().toString());
		rs=statement.executeQuery();
		while(rs.next()){
			if(rs.getString("��Ա����").equals("�ѿ�ͨ")){
				vip=true;
			}
			else {
				vip=false;
			}
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return vip;  
   }
@Override
public void actionPerformed(ActionEvent e) {
	if(e.getSource()==b2){
		try {
			statement=con.prepareStatement("update ���� set ���=���+? where ����=?");
			statement.setInt(1, Integer.parseInt((m.getSelectedItem().toString())));
			statement.setString(2, hm.getSelectedItem().toString());
			statement.executeUpdate();
			int money=Integer.parseInt(m.getSelectedItem().toString());
			if(money==100){
				if(isVip()){
			JOptionPane.showMessageDialog(this, "�ɹ���ֵ"+money+"Ԫ����֮ǰ����VIP");}
				else {
					statement=con.prepareStatement("update ���� set ��Ա����='�ѿ�ͨ' where ����=?");
					statement.setString(1, hm.getSelectedItem().toString());
					statement.executeUpdate();
					JOptionPane.showMessageDialog(this, "�ɹ���ֵ"+money+"Ԫ������ΪVIP");}
				}
			else {
				JOptionPane.showMessageDialog(this, "�ɹ���ֵ"+money+"Ԫ");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	else if(e.getSource()==b3){
		try {
			statement=con.prepareStatement("select ��� from ���� where ����=?");
			statement.setString(1, hm.getSelectedItem().toString());
			rs=statement.executeQuery();
			while(rs.next()){
				JOptionPane.showMessageDialog(this, "���ĵ�ǰ��"+rs.getString("���"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	else if(e.getSource()==b4){
		DBconnection.Close(con, statement, rs);
		this.setVisible(false);
	}
}
}
