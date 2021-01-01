package 网吧;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
@SuppressWarnings("serial")
public class select extends JFrame implements ActionListener{
	JButton b1= new JButton("用户信息");
	JButton b2= new JButton("电脑信息");
	JButton b3= new JButton("网吧曲库");
	JButton b4= new JButton("上机情况");
	JButton b5= new JButton("网卡情况");
 public select(){
	 setBounds(500,300,500,400);
 	setTitle("查询");
 	setLayout(new FlowLayout());
 	init();
 }
 private void init(){
	 add(b1);
	 add(b2);
	 add(b3);
	 add(b4);
	 add(b5);
	 b1.addActionListener(this);
	 b2.addActionListener(this);
	 b3.addActionListener(this);
	 b4.addActionListener(this);
	 b5.addActionListener(this);
 }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b1){
			user sel = new user();
			sel.setVisible(true);
		}
		if(e.getSource()==b2){
			machine mac = new machine();
			mac.setVisible(true);
		}
		if(e.getSource()==b3){
			music mus = new music();
			mus.setVisible(true);
		}
		if(e.getSource()==b4){
			playOption option= new playOption();
			option.setVisible(true);
		}
		if(e.getSource()==b5){
			internetCard card = new internetCard();
			card.setVisible(true);
		}
	}
	
}