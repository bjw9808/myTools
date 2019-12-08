import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Base64;

public class myTools extends JFrame {
    private JButton jButton1, jButton2, jButtonBase64En, jButtonBase64De, jButtonMD5, jButtonUrlEn, jButtonUrlDe;
    private JPanel jpanel;
    private JTextField jText1, jText2;
    private JLabel jLabelIn, jLabelOut;

    public myTools() {
        Font myFont=new Font("微软雅黑",Font.BOLD,14);
        //JPanel init
        jpanel = new JPanel();
        jpanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5));
        add(jpanel);

        jLabelIn = new JLabel("输入:");
        jLabelOut = new JLabel("输出:");

        jText1 = new JTextField("", 80);
        jText2 = new JTextField("", 80);

        jButton1 = new JButton("时间戳(ms)");
        jButton2 = new JButton("时间戳(s)");

        jButtonBase64En = new JButton("base64加密");
        jButtonBase64De = new JButton("base64解密");

        jButtonMD5 = new JButton("MD5");

        jButtonUrlEn = new JButton("url编码");
        jButtonUrlDe = new JButton("url解码");

        jButton1.addActionListener(new actionTimeStampMs());
        jButton2.addActionListener(new actionTimeStampS());

        jButtonBase64En.addActionListener(new actionBase64Encode());
        jButtonBase64De.addActionListener(new actionBase64Decode());

        jButtonUrlEn.addActionListener(new actionURLEncode());
        jButtonUrlDe.addActionListener(new actionURLDecode());

        jButtonMD5.addActionListener(new actionMD5encode());

        //统一设置字体
        jLabelOut.setFont(myFont);
        jLabelIn.setFont(myFont);
        jText1.setFont(myFont);
        jText2.setFont(myFont);
        jButton1.setFont(myFont);
        jButton2.setFont(myFont);
        jButtonUrlEn.setFont(myFont);
        jButtonUrlDe.setFont(myFont);
        jButtonBase64De.setFont(myFont);
        jButtonBase64En.setFont(myFont);
        jButtonMD5.setFont(myFont);

        jpanel.add(jLabelIn);
        jpanel.add(jText1);

        jpanel.add(jLabelOut);
        jpanel.add(jText2);

        jpanel.add(jButton1);
        jpanel.add(jButton2);

        jpanel.add(jButtonBase64En);
        jpanel.add(jButtonBase64De);

        jpanel.add(jButtonMD5);

        jpanel.add(jButtonUrlEn);
        jpanel.add(jButtonUrlDe);


        setTitle("小工具合集_by_imBobby");
        setSize(950, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    class actionTimeStampMs implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jText1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告",2);
            }
            else {
                String inputTimeStamp = jText1.getText();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    String sd = sdf.format(new Date(Long.parseLong(String.valueOf(inputTimeStamp))));
                    jText2.setText(sd);
                }
                catch (NumberFormatException error) {
                    JOptionPane.showMessageDialog(jpanel,"输入时间戳错误，请检查","警告",2);
                    jText1.setText("");
                }
            }
        }
    }

    class actionTimeStampS implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jText1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告",2);
            }
            else {
                try {
                    long inputTimeStamp = Long.parseLong(jText1.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String sd = sdf.format(new Date(Long.parseLong(String.valueOf(inputTimeStamp * 1000))));
                    jText2.setText(sd);
                }
                catch (Exception error) {
                    JOptionPane.showMessageDialog(jpanel,"输入时间戳错误，请检查","警告",2);
                    jText1.setText("");
                }
            }
        }
    }

    class actionBase64Encode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jText1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告",2);
            }
            else {
                byte[] inputString = jText1.getText().getBytes();
                String s = Base64.getEncoder().encodeToString(inputString);
                jText2.setText(s);
            }
        }
    }

    class actionBase64Decode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jText1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告",2);
            }
            else {
                byte[] base64decodedBytes = Base64.getDecoder().decode(jText1.getText());
                jText2.setText(new String(base64decodedBytes));
            }
        }
    }

    class actionMD5encode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String encodeText = jText1.getText();
            try{
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(encodeText.getBytes());
                byte b[] = md.digest();
                int i;
                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0) {
                        i += 256;
                    }
                    if (i < 16) {
                        buf.append("0");
                    }
                    buf.append(Integer.toHexString(i));
                }
                jText2.setText(buf.toString());
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(jpanel,"异常信息" + exception,"MD5异常",2);
            }
        }
    }

    class actionURLEncode implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (jText1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告",2);
            }
            else {
                String url = jText1.getText();
                try {
                    url = URLEncoder.encode(url, "utf-8");
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
                jText2.setText(url);
            }
        }
    }

    class actionURLDecode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jText1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告",2);
            }
            else {
                String url_encode = jText1.getText();
                try {
                    jText2.setText(URLDecoder.decode(url_encode, "UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new myTools();
    }
}