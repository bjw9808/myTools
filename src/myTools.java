import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Base64;

public class myTools extends JFrame {
    private JButton jb_1, jb_2, jb_base_64_en, jb_base_64_de, jb_MD5;
    private JPanel jpanel;
    private JTextField jtext_1, jtext_2;

    public myTools() {
        jpanel = new JPanel();
        jtext_1 = new JTextField("输入", 80);
        jtext_2 = new JTextField("输出", 80);
        jb_1 = new JButton("时间戳(ms)");
        jb_2 = new JButton("时间戳(s)");
        jb_base_64_en = new JButton("base64加密");
        jb_base_64_de = new JButton("base64解密");
        jb_MD5 = new JButton("MD5");
        jb_1.addActionListener(new actionTimeStampMs());
        jb_2.addActionListener(new actionTimeStampS());
        jb_base_64_en.addActionListener(new actionBase64Encode());
        jb_base_64_de.addActionListener(new actionBase64Decode());
        jb_MD5.addActionListener(new actionMD5encode());
//        jpanel.setLayout(new GridLayout(4,4,5,5));
        jpanel.add(jtext_1);
        jpanel.add(jtext_2);
        jpanel.add(jb_1);
        jpanel.add(jb_2);
        jpanel.add(jb_base_64_en);
        jpanel.add(jb_base_64_de);
        jpanel.add(jb_MD5);
        add(jpanel);
        setTitle("研发小工具_imBobby");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    class actionTimeStampMs implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputTimeStamp = jtext_1.getText();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sd = sdf.format(new Date(Long.parseLong(String.valueOf(inputTimeStamp))));
            jtext_2.setText(sd);
        }
    }

    class actionTimeStampS implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long inputTimeStamp = Long.valueOf(jtext_1.getText());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sd = sdf.format(new Date(Long.parseLong(String.valueOf(inputTimeStamp * 1000))));
            jtext_2.setText(sd);
        }
    }

    class actionBase64Encode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            byte[] inputString = jtext_1.getText().getBytes();
            String s = Base64.getEncoder().encodeToString(inputString);
            jtext_2.setText(s);
        }
    }

    class actionBase64Decode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            byte[] base64decodedBytes = Base64.getDecoder().decode(jtext_2.getText());
            jtext_1.setText(new String(base64decodedBytes));
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
                System.out.println(exception);
            }
        }
    }

    public static void main(String[] args) {
        new myTools();
    }
}