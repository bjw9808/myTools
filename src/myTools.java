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
    private JButton jb_1, jb_2, jb_base_64_en, jb_base_64_de, jb_MD5, jb_url_encode, jb_url_decode;
    private JPanel jpanel;
    private JTextField jtext_1, jtext_2;
    private JLabel jlabel_input, jlabel_output;

    public myTools() {
        Font myFont=new Font("微软雅黑",Font.BOLD,14);
        //JPanel init
        jpanel = new JPanel();
        jpanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5));
        add(jpanel);

        jlabel_input = new JLabel("输入:");
        jlabel_output = new JLabel("输出:");

        jtext_1 = new JTextField("", 80);
        jtext_2 = new JTextField("", 80);

        jb_1 = new JButton("时间戳(ms)");
        jb_2 = new JButton("时间戳(s)");

        jb_base_64_en = new JButton("base64加密");
        jb_base_64_de = new JButton("base64解密");

        jb_MD5 = new JButton("MD5");

        jb_url_encode = new JButton("url编码");
        jb_url_decode = new JButton("url解码");

        jb_1.addActionListener(new actionTimeStampMs());
        jb_2.addActionListener(new actionTimeStampS());

        jb_base_64_en.addActionListener(new actionBase64Encode());
        jb_base_64_de.addActionListener(new actionBase64Decode());

        jb_url_encode.addActionListener(new actionURLEncode());
        jb_url_decode.addActionListener(new actionURLDecode());

        jb_MD5.addActionListener(new actionMD5encode());

        //统一设置字体
        jlabel_output.setFont(myFont);
        jlabel_input.setFont(myFont);
        jtext_1.setFont(myFont);
        jtext_2.setFont(myFont);
        jb_1.setFont(myFont);
        jb_2.setFont(myFont);
        jb_url_encode.setFont(myFont);
        jb_url_decode.setFont(myFont);
        jb_base_64_de.setFont(myFont);
        jb_base_64_en.setFont(myFont);
        jb_MD5.setFont(myFont);

        jpanel.add(jlabel_input);
        jpanel.add(jtext_1);

        jpanel.add(jlabel_output);
        jpanel.add(jtext_2);

        jpanel.add(jb_1);
        jpanel.add(jb_2);

        jpanel.add(jb_base_64_en);
        jpanel.add(jb_base_64_de);

        jpanel.add(jb_MD5);

        jpanel.add(jb_url_encode);
        jpanel.add(jb_url_decode);


        setTitle("小工具合集_by_imBobby");
        setSize(950, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    class actionTimeStampMs implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jtext_1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告",2);
            }
            else {
                String inputTimeStamp = jtext_1.getText();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    String sd = sdf.format(new Date(Long.parseLong(String.valueOf(inputTimeStamp))));
                    jtext_2.setText(sd);
                }
                catch (NumberFormatException error) {
                    JOptionPane.showMessageDialog(jpanel,"输入时间戳错误，请检查","警告",2);
                    jtext_1.setText("");
                }
            }
        }
    }

    class actionTimeStampS implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jtext_1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告",2);
            }
            else {
                try {
                    long inputTimeStamp = Long.parseLong(jtext_1.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String sd = sdf.format(new Date(Long.parseLong(String.valueOf(inputTimeStamp * 1000))));
                    jtext_2.setText(sd);
                }
                catch (Exception error) {
                    JOptionPane.showMessageDialog(jpanel,"输入时间戳错误，请检查","警告",2);
                    jtext_1.setText("");
                }
            }
        }
    }

    class actionBase64Encode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jtext_1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告",2);
            }
            else {
                byte[] inputString = jtext_1.getText().getBytes();
                String s = Base64.getEncoder().encodeToString(inputString);
                jtext_2.setText(s);
            }
        }
    }

    class actionBase64Decode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jtext_1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告",2);
            }
            else {
                byte[] base64decodedBytes = Base64.getDecoder().decode(jtext_1.getText());
                jtext_2.setText(new String(base64decodedBytes));
            }
        }
    }

    class actionMD5encode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String encodeText = jtext_1.getText();
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
                jtext_2.setText(buf.toString());
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(jpanel,"异常信息" + exception,"MD5异常",2);
            }
        }
    }

    class actionURLEncode implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (jtext_1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告",2);
            }
            else {
                String url = jtext_1.getText();
                try {
                    url = URLEncoder.encode(url, "utf-8");
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
                jtext_2.setText(url);
            }
        }
    }

    class actionURLDecode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jtext_1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告",2);
            }
            else {
                String url_encode = jtext_1.getText();
                try {
                    jtext_2.setText(URLDecoder.decode(url_encode, "UTF-8"));
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