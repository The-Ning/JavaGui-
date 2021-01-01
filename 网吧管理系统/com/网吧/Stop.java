package ����;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class Stop extends JFrame implements ActionListener{
	private static String vipMoney,Money;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection con=DBconnection.getconn();
	ResultSet rs=null;
	PreparedStatement statement;
	JLabel b1= new JLabel("ѡ�񿨺�");
	@SuppressWarnings("rawtypes")
	JComboBox kh = new JComboBox();	
	JButton b2= new JButton("ȷ��");
	JButton b3= new JButton("�˳�");
	JPanel p=new JPanel();  //������
	JPanel p1=new JPanel(); 
	public Stop(){
	 setSize(350,400);
		setTitle("��ӭ�»�");
		init();	
 }
	@SuppressWarnings("unchecked")
	void init(){
		kh.addItem("����");
		this.getKH();
		 this.getContentPane().add(p,BorderLayout.NORTH);
		 this.getContentPane().add(p1,BorderLayout.CENTER);
		p.add(b1);
		p1.add(kh);
		p1.add(b2);
		p1.add(b3);
		b2.addActionListener(this);
		b3.addActionListener(this);
	}
	@SuppressWarnings("unchecked")
	public void getKH(){
		try {
			String sql="select ���� from ���� where ��״̬='�ϻ�'";
			statement=con.prepareStatement(sql);
			rs=statement.executeQuery();
			while(rs.next()){
				kh.addItem(rs.getString("����"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isVip(){
		boolean vip=false;
		try {
			String sql="select ��Ա���� from ���� where ����=?";
			statement=con.prepareStatement(sql);
			statement.setString(1, kh.getSelectedItem().toString());
			rs=statement.executeQuery();
			while(rs.next()){
				if(rs.getString("��Ա����").equals("�ѿ�ͨ")){
					vip=true;
					
				}
				else {vip=false;}
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
				String sql="update �ϻ����  set �»�ʱ��=now() where ����=? and �ϻ�����=0";
				statement=con.prepareStatement(sql);
				statement.setString(1, kh.getSelectedItem().toString());
				statement.executeUpdate();
				String sql1="select ������,�ϻ�ʱ��,�»�ʱ�� from �ϻ���� where ����=? and �ϻ�����=0";
				statement=con.prepareStatement(sql1);
				statement.setString(1, kh.getSelectedItem().toString());
				rs=statement.executeQuery();
				while(rs.next()){
					JOptionPane.showMessageDialog(this, kh.getSelectedItem().toString()+"�»��ɹ�");
					      String sql3="update ���� set ״̬='δʹ��' where ������=?";
							statement=con.prepareStatement(sql3);
							statement.setString(1,rs.getString("������"));
							statement.executeUpdate();
							String sql4="update ���� set ��״̬='�»�' where ����=(select ���� from �ϻ���� where ������=? and �ϻ�����=0)";
							statement=con.prepareStatement(sql4);
							statement.setString(1,rs.getString("������"));
							statement.executeUpdate();
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  	
							Date sj = df.parse(rs.getString("�ϻ�ʱ��"));
							Date xj = df.parse(rs.getString("�»�ʱ��")); 
							long diff = xj.getTime() - sj.getTime();//�����õ��Ĳ�ֵ�Ǻ��뼶��  
						      long days = diff / (1000 * 60 * 60 * 24);  
						      long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60); 
						   if(isVip()){
							   if(hours>1){
								      String sql2="update �ϻ���� set �ϻ�����=?,Сʱ��=? where ����=? and �ϻ�����=0";
								      statement=con.prepareStatement(sql2);
								      statement.setDouble(1, hours*3);
								      statement.setDouble(2, hours);
								      statement.setString(3, kh.getSelectedItem().toString());
								      statement.executeUpdate();
								      vipMoney=Long.toString(hours*3);
								      }
								      else{
								    	  hours=1;
								    	  String sql2="update �ϻ���� set �ϻ�����=?,Сʱ��=? where ����=? and �ϻ�����=0";
									      statement=con.prepareStatement(sql2);
									      statement.setDouble(1, 3);
									      statement.setDouble(2, hours);
									      statement.setString(3, kh.getSelectedItem().toString());
									      statement.executeUpdate();
									       vipMoney="3";
								      }
					      
						   }   
						   else{
							   if(hours>1){
								      String sql2="update �ϻ���� set �ϻ�����=?,Сʱ��=? where ����=? and �ϻ�����=0";
								      statement=con.prepareStatement(sql2);
								      statement.setDouble(1, hours*5);
								      statement.setDouble(2, hours);
								      statement.setString(3, kh.getSelectedItem().toString());
								      statement.executeUpdate();
								 	  Money=Long.toString(hours*5);						     
								      }
								      else{
								    	  hours=1;
								    	  String sql2="update �ϻ���� set �ϻ�����=?,Сʱ��=? where ����=? and �ϻ�����=0";
									      statement=con.prepareStatement(sql2);
									      statement.setDouble(1, 5);
									      statement.setDouble(2, hours);
									      statement.setString(3, kh.getSelectedItem().toString());
									      statement.executeUpdate();
								          Money="5";									     
								      }
						   } 
				            
						     
						      String sql5="update ���� set ���=���-? where ����=?";
						    if(isVip()){
								    statement=con.prepareStatement(sql5);
								    statement.setDouble(1, Double.parseDouble(vipMoney));
								    statement.setString(2, kh.getSelectedItem().toString());
								    statement.executeUpdate();
						    JOptionPane.showMessageDialog(this, "VIP�û�"+kh.getSelectedItem().toString()+",�ϻ�"+hours+"Сʱ,��������"+vipMoney+"Ԫ");}
						    else {
						    	statement=con.prepareStatement(sql5);
							    statement.setDouble(1, Double.parseDouble(Money));
							    statement.setString(2, kh.getSelectedItem().toString());
							    statement.executeUpdate();
						    JOptionPane.showMessageDialog(this, "�û�"+kh.getSelectedItem().toString()+"�����ϻ�"+",�ϻ�"+hours+"Сʱ,��������"+Money+"Ԫ");}
						    break;
				}
				String sql6="select ��� from ���� where ����=?";	
				  statement=con.prepareStatement(sql6);
				  statement.setString(1, kh.getSelectedItem().toString());
				  rs=statement.executeQuery();
				  while(rs.next()){
					  if(rs.getInt("���")<0){
						  int n=JOptionPane.showConfirmDialog(this,"��ǰ�����Ƿ�ѣ��Ƿ��ֵ��","����", JOptionPane.YES_NO_OPTION);
						  if(n==JOptionPane.YES_OPTION){
							  recharge re =new recharge();
							  re.setVisible(true);
						  }
						  else {
							  JOptionPane.showMessageDialog(this, "����Ƿ�ѣ������ֵ");
						  }
					  }
				  }
			}
			
		 
			catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  	
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.getKH();
		}

	else  if(e.getSource()==b3){
			DBconnection.Close(con, statement, rs);
			this.setVisible(false);
		}
	}
}
