package 网吧;
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
import javax.swing.JOptionPane;
public class mainwindow {
	public static void main(String[] args) {
		Window win = new Window();
		win.setVisible(true);
	}
}
class Window extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private String kh="";
	private static final long serialVersionUID = 1L;
	Connection con=DBconnection.getconn();
	ResultSet rs=null;
	PreparedStatement statement;
	JButton b1=new JButton("��         ��");
	JButton b2=new JButton("��        ��");
	JButton b3=new JButton("�����");
	JButton b4=new JButton("��        ֵ");
	JButton b5=new JButton("������û");
	JButton b6=new JButton("��        ѯ");
	JButton b7=new JButton("��         ��");
	JButton b8=new JButton("��        ��");
	Box baseBox,box1,box2,box3,box4;
	public Window(){
		setBounds(500,300,500,400);
		setTitle("��ó����ڳ����");
		this.setLayout(new FlowLayout());
		init();	
	}
	public void init(){
		box1=Box.createVerticalBox();
		box1.add(b1);
		box1.add(Box.createVerticalStrut(10));
		box1.add(b5);
		box2=Box.createVerticalBox();
		box2.add(b2);
		box2.add(Box.createVerticalStrut(10));
		box2.add(b6);
		box3=Box.createVerticalBox();
		box3.add(b3);
		box3.add(Box.createVerticalStrut(10));
		box3.add(b7);
		box4=Box.createVerticalBox();
		box4.add(b4);
		box4.add(Box.createVerticalStrut(10));
		box4.add(b8);
		baseBox=Box.createHorizontalBox();
		baseBox.add(box1);
		baseBox.add(Box.createHorizontalStrut(10));
		baseBox.add(box2);
		baseBox.add(Box.createHorizontalStrut(10));
		baseBox.add(box3);
		baseBox.add(Box.createHorizontalStrut(10));
		baseBox.add(box4);
		add(baseBox);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b1){
			Play p= new Play();
			p.setVisible(true);
		}
		else if(e.getSource()==b2){
			Stop s = new Stop();
			s.setVisible(true);
		}
		else if(e.getSource()==b3){
			Newuser user = new Newuser();
			user.setVisible(true);
		}
		else if(e.getSource()==b4){
			recharge re = new recharge();
			re.setVisible(true);
		}
		else if(e.getSource()==b5){
			String sql="select ������ from ���� where ״̬='δʹ��'";
			try {
				statement=con.prepareStatement(sql);
				rs=statement.executeQuery();
			if(rs.next()){
					JOptionPane.showMessageDialog(this, "û��");
				}
			else	{
				JOptionPane.showMessageDialog(this, "����");
					
			}	
			}
			 catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==b6){
			select s= new select();
			s.setVisible(true);
		}
		else if(e.getSource()==b7){
			DBconnection.Close(con, statement, rs);
			setVisible(false);
		}
		else if(e.getSource()==b8){
			kh=JOptionPane.showInputDialog("���뿨��");
		  if(isVip()){
playMusic play = new playMusic();
			  play.setVisible(true);
		  }
		  else {JOptionPane.showMessageDialog(this, "��ͨ�û����ܵ��");} 
		}
	}
	private boolean isVip(){
		boolean vip=false;
		try {
			statement=con.prepareStatement("select ��Ա���� from �� where ����=?");
			statement.setString(1, kh);
			rs=statement.executeQuery();
			while(rs.next()){
				if(rs.getString("��Ա����").equals("�ѿ�ͨ")){
					vip=true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vip;
	}
}