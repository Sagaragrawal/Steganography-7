import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


@SuppressWarnings("serial")
public class DecryptEnd extends JFrame implements WindowListener,ActionListener{

	int flag1=0;
	Font font;
	String txtdir,imagedir;
	String txtfile,imagefile;
	String s,s1,sb;
	JTextField l1,l2;
	JButton b1,b2;
	JPanel mp,p1,p2,p3,p4;
	
	public DecryptEnd() throws Exception
	{
		
		
		
		font = Font.createFont(Font.TRUETYPE_FONT, new File("zipper.ttF"));
		font = font.deriveFont(Font.TRUETYPE_FONT, 32f);
		
		
		ImageIcon v = new ImageIcon("decrypt.png");
		ImageIcon u = new ImageIcon("IMAGE.png");
		
		b1=new JButton(u);
		b2=new JButton(v);
		b1.setPreferredSize(new Dimension(550,50));
	    b2.setPreferredSize(new Dimension(550,50));
		
		
		l1=new JTextField(21);
		l2=new JTextField(21);
		
		l1.setEditable(false);
		l2.setEditable(false);
		
		l1.setFont(font);
		l2.setFont(font);
		
		l1.setText("ENCRYPTED IMAGE FILE NAME");
		l2.setText(".........................");
		mp=new JPanel(new FlowLayout());
		
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		p4=new JPanel();
		
		p1.add(b1);
		p2.add(l1);
		p3.add(b2);
		p4.add(l2);
		mp.add(p1);
	    mp.add(p2);
	    mp.add(p3);
	    mp.add(p4);
		add(mp);
		
		
		

		ImageIcon img111 = new ImageIcon("logo.png");
		this.setIconImage(img111.getImage());
		setSize(600,300);
		
		setVisible(true);
		setTitle("DECRYPT");
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		addWindowListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	
	
	public static void main(String agr[])
	{
		try {
			new DecryptEnd();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void windowOpened(WindowEvent e) {}
	public void windowClosing(WindowEvent e) { System.exit(0);}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==b1)
		{
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & PNG Images", "jpg", "png");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		     
		            imagefile=chooser.getSelectedFile().getName();
		            s=chooser.getSelectedFile().getAbsolutePath();
		            flag1=1;
		    }
            
		}//IF ENDS HERE OF B1
		
		if(e.getSource()==b2)
		{
			if(flag1==1)
			{l2.setText("SAVE AS TXT FILE");
			
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & PNG Images", "jpg", "png");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		     
		            txtfile=chooser.getSelectedFile().getName();
		            s1=chooser.getSelectedFile().getAbsolutePath();
		            
		            try {l2.setText("DENCRYPTING................");
					decrypt(s1);
		            } catch (Exception e1) {e1.printStackTrace();}
		            
		            l2.setText("SAVED");
		            
		    }
    		
    	
			}else
    			l2.setText("SELECT IMAGE PROPERLY");
		}//IF ENDS HERE OF B2
		
		
		
		
	}//END OF ACTION
	
/////////////user function//////////////////////////////	
	
	public void decrypt(String s1) throws Exception
	{
	File img = new File(s);
	BufferedImage bimg = ImageIO.read(img);
	File f = new File(s1);
	FileOutputStream fos = new FileOutputStream(f);
	DataOutputStream dos = new DataOutputStream(fos);
	String mystring=new String("");
	
	int pixel[][] = new int[bimg.getWidth()][bimg.getHeight()];
	
	for(int i=0;i<bimg.getWidth();i++)
		for(int j=0;j<bimg.getHeight();j++)
		{ pixel[i][j]=bimg.getRGB(i,j);}
	
	int count=0;
	int flag=0;
	char character;
	String finalstr = new String();
	for(int i=0;i<bimg.getWidth();i++)
	{
		for(int j=0;j<bimg.getHeight();j++)
		{ 
		
		Color c1= new Color(pixel[i][j],true);
		int red1 = c1.getRed();
        int green1 = c1.getGreen();
        int blue1 = c1.getBlue();
        int alpha1 = c1.getAlpha();
		
              	    
	    int[] c=new int[4];
	    c[0]=alpha1 & 0x01;
	    c[1]=red1 & 0x01;
	    c[2]=green1 & 0x01;
	    c[3]=blue1 & 0x01;
	   
	    
	    String s = new String(""+c[0]+c[1]+c[2]+c[3]);
	    mystring=mystring.concat(s);
	    count++;
	    
	    if(count==2)
	    { count=0;
	    	//System.out.println(mystring);
	    	character = decryptedAlphabet(mystring);
	    	finalstr=finalstr.concat(""+character);
	    	if(mystring.equals("00000000")) flag=1;	
	    	mystring="";
	    }
	    
	     if(flag==1)break;
	    }   
	   if(flag==1)break;
	    }
	
	dos.writeBytes(finalstr);
	dos.close();
	fos.close();
	}
	
	public static char decryptedAlphabet(String str)
	{
		int a = Integer.parseInt(str,2);
		char c = (char)a;
		return c;
	}
	
///////////////////////////user function ends////////////////////

}

