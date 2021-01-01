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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Play extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection con=DBconnection.getconn();
	ResultSet rs=null;
	PreparedStatement statement=null;
	JLabel b1= new JLabel("������");
	JLabel b2= new JLabel("����");
	JLabel b3= new JLabel("����");
	JButton b4=new JButton("�ϻ�");
	JButton b5=new JButton("�˳�");
	JPanel p=new JPanel();  //������
	JPanel p1=new JPanel();  //������
	@SuppressWarnings("rawtypes")
	JComboBox hm= new JComboBox();
	JTextField kh=new JTextField(10);
	JPasswordField mm=new JPasswordField(10);
  public Play(){
	  setSize(350,350);
	  setTitle("��ӭ�ϻ�");
	p.add(b2);
	p.add(kh);
	p.add(b3);
	p.add(mm);
	p1.add(b1);
	p1.add(hm);
	p1.add(b4);
	p1.add(b5);
	this.getContentPane().add(p,BorderLayout.NORTH);
	this.getContentPane().add(p1,BorderLayout.CENTER);
	  init();
  }
@SuppressWarnings("unchecked")
public void init(){
	   b4.addActionListener(this);
	   b5.addActionListener(this);
	   String sql="select ������ from ����.���� where ״̬='δʹ��'";
		  try {
			statement = con.prepareStatement(sql);
			rs=statement.executeQuery(); //��ѯ����
			while(rs.next()){
				 hm.addItem(rs.getString("������"));
			}
			 
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
}
@SuppressWarnings("deprecation")
public void actionPerformed(ActionEvent e) {
	if(e.getSource()==b5){
		DBconnection.Close(con, statement, rs);
		this.setVisible(false);
	}
	if(e.getSource()==b4) {
		if(kh.getText().equals("")||mm.getText().equals("")){
			JOptionPane.showMessageDialog(this, "���� ���벻��Ϊ��");
		}
		else {
			con=DBconnection.getconn();
		try {	
			statement = con.prepareStatement("select * from �û�  where ����=?");
			statement.setString(1,kh.getText());
			rs=statement.executeQuery(); //��ѯ����
		if(rs.next()){
			String sql="select ��״̬ from ���� where ����=?";
			statement = con.prepareStatement(sql);
			statement.setString(1, kh.getText());
			rs=statement.executeQuery();
			while(rs.next()){
				if(rs.getString("��״̬").equals("�ϻ�")){
					JOptionPane.showMessageDialog(this, "���û����ϻ�����");
				}
				else{ 
					String sql2="select ���� from �û� where ����=?";
					statement = con.prepareStatement(sql2);
					statement.setString(1,kh.getText());
					rs=statement.executeQuery(); //��ѯ����
					while(rs.next()){
					if(rs.getString("����").equals(mm.getText())){
						String sql6="select ��� from ���� where ����=?";
						statement = con.prepareStatement(sql6);
						statement.setString(1,kh.getText());
						rs=statement.executeQuery(); //��ѯ����
						while(rs.next()){
							if(rs.getInt("���")>=5){
						JOptionPane.showMessageDialog(this, "�ϻ��ɹ�");
						String sql3="insert into �ϻ���� (����,������,�ϻ�ʱ��,�ϻ�����) values(?,?,now(),0)";
						statement=con.prepareStatement(sql3);
						statement.setString(1,kh.getText());
						statement.setString(2,hm.getSelectedItem().toString());
						statement.executeUpdate();
						String sql4="update ���� set ��״̬='�ϻ�',����=? where ����=?";
						statement=con.prepareStatement(sql4);
						statement.setString(1,mm.getText());
						statement.setString(2,kh.getText());
						statement.executeUpdate();
						String sql5="update ���� set ״̬='ʹ��' where ������=?";
						statement=con.prepareStatement(sql5);
						statement.setString(1,hm.getSelectedItem().toString());
						statement.executeUpdate();
							}
							else {
								JOptionPane.showMessageDialog(this, "����5Ԫ�����ֵ����");
							}
					}
					}
					else {JOptionPane.showMessageDialog(this, "�������");}
					
					
					}
					}
			}
				
		}
		else {
			JOptionPane.showMessageDialog(this, "���Ŵ���");
			
		}	
		}
		
		 catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		}
	}
	
}
}
