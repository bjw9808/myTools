import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Random;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class myTools extends JFrame {
    private JPanel jpanel;
    private JTextField jText1, jText2;
    String iconPath = "src/res/Tools.png";

    public myTools() {
        Font myFont=new Font("微软雅黑",Font.BOLD,13);
        //JPanel init
        jpanel = new JPanel();
        jpanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5));
        add(jpanel);

        ImageIcon softwareIcon = new ImageIcon(iconPath);

        JLabel jLabelIn = new JLabel("输入:");
        JLabel jLabelOut = new JLabel("输出:");

        jText1 = new JTextField("", 65);
        jText2 = new JTextField("", 65);

        JButton jButtonRandomKeyNum = new JButton("随机密钥(数字)");
        JButton jButtonRandomKeyWord = new JButton("随机密钥(混合)");

        JButton jButton1 = new JButton("时间戳(ms)");
        JButton jButton2 = new JButton("时间戳(s)");

        JButton jButtonBase64En = new JButton("base64加密");
        JButton jButtonBase64De = new JButton("base64解密");

        JButton jButtonMD5 = new JButton("MD5");

        JButton jButtonUrlEn = new JButton("url编码");
        JButton jButtonUrlDe = new JButton("url解码");

        JButton jButtonFileMD5 = new JButton("文件MD5计算");

        JButton jButtonFileCRC32 = new JButton("文件CRC32计算");

        JButton jButtonTenToBin = new JButton("10进制转2进制");
        JButton jButtonTenToHex = new JButton("10进制转16进制");

        JButton jButtonBinToTen = new JButton("2进制转10进制");

        jButtonFileCRC32.addActionListener(new actionFileCRC32());

        jButton1.addActionListener(new actionTimeStampMs());
        jButton2.addActionListener(new actionTimeStampS());

        jButtonBase64En.addActionListener(new actionBase64Encode());
        jButtonBase64De.addActionListener(new actionBase64Decode());

        jButtonUrlEn.addActionListener(new actionURLEncode());
        jButtonUrlDe.addActionListener(new actionURLDecode());

        jButtonMD5.addActionListener(new actionMD5encode());

        jButtonFileMD5.addActionListener(new actionFileMD5());

        jButtonRandomKeyNum.addActionListener(new actionRandomKeyNum());
        jButtonRandomKeyWord.addActionListener(new actionRandomKeyWord());

        jButtonTenToBin.addActionListener(new actionTenToBin());
        jButtonTenToHex.addActionListener(new actionTenToHex());
        jButtonBinToTen.addActionListener(new actionBinToTen());

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
        jButtonFileMD5.setFont(myFont);
        jButtonRandomKeyNum.setFont(myFont);
        jButtonRandomKeyWord.setFont(myFont);
        jButtonFileCRC32.setFont(myFont);
        jButtonTenToBin.setFont(myFont);
        jButtonTenToHex.setFont(myFont);
        jButtonBinToTen.setFont(myFont);

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

        jpanel.add(jButtonFileMD5);

        jpanel.add(jButtonRandomKeyNum);
        jpanel.add(jButtonRandomKeyWord);

        jpanel.add(jButtonFileCRC32);

        jpanel.add(jButtonTenToBin);
        jpanel.add(jButtonTenToHex);
        jpanel.add(jButtonBinToTen);

        setTitle("小工具合集_by_imBobby");
        setSize(950, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setIconImage(softwareIcon.getImage());
    }

    class actionTimeStampMs implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jText1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告", JOptionPane.WARNING_MESSAGE);
            }
            else {
                String inputTimeStamp = jText1.getText();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    String sd = sdf.format(new Date(Long.parseLong(String.valueOf(inputTimeStamp))));
                    jText2.setText(sd);
                }
                catch (NumberFormatException error) {
                    JOptionPane.showMessageDialog(jpanel,"输入时间戳错误，请检查","警告", JOptionPane.WARNING_MESSAGE);
                    jText1.setText("");
                }
            }
        }
    }

    class actionTimeStampS implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jText1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告", JOptionPane.WARNING_MESSAGE);
            }
            else {
                try {
                    long inputTimeStamp = Long.parseLong(jText1.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String sd = sdf.format(new Date(Long.parseLong(String.valueOf(inputTimeStamp * 1000))));
                    jText2.setText(sd);
                }
                catch (Exception error) {
                    JOptionPane.showMessageDialog(jpanel,"输入时间戳错误，请检查","警告", JOptionPane.WARNING_MESSAGE);
                    jText1.setText("");
                }
            }
        }
    }

    class actionBase64Encode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jText1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告", JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告", JOptionPane.WARNING_MESSAGE);
            }
            else {
                try {
                    byte[] base64decodedBytes = Base64.getDecoder().decode(jText1.getText());
                    jText2.setText(new String(base64decodedBytes));
                }
                catch (IllegalArgumentException error) {
                    JOptionPane.showMessageDialog(jpanel,"输入了不合法的字符串","解码失败", JOptionPane.WARNING_MESSAGE);
                    jText1.setText("");
                }

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
                JOptionPane.showMessageDialog(jpanel,"异常信息" + exception,"MD5异常", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    class actionURLEncode implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (jText1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告", JOptionPane.WARNING_MESSAGE);
            }
            else {
                String url = jText1.getText();
                try {
                    url = URLEncoder.encode(url, "utf-8");
                } catch (UnsupportedEncodingException ex) {
                    JOptionPane.showMessageDialog(jpanel,"编码失败","警告", JOptionPane.WARNING_MESSAGE);
                }
                jText2.setText(url);
            }
        }
    }

    class actionURLDecode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jText1.getText().equals("")) {
                JOptionPane.showMessageDialog(jpanel,"输入禁止为空","警告", JOptionPane.WARNING_MESSAGE);
            }
            else {
                String url_encode = jText1.getText();
                try {
                    jText2.setText(URLDecoder.decode(url_encode, "UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    JOptionPane.showMessageDialog(jpanel,"解码失败","警告", JOptionPane.WARNING_MESSAGE);
                    jText2.setText("");
                } catch (IllegalArgumentException error) {
                    JOptionPane.showMessageDialog(jpanel,"解码失败(非法输入)","警告", JOptionPane.WARNING_MESSAGE);
                    jText2.setText("");
                }
            }
        }
    }

    class actionFileMD5 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                CalculateFile calculateFile = new CalculateFile();
                Thread thread = new Thread(calculateFile);
                thread.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        class CalculateFile implements Runnable {
            @Override
            public void run() {
                JFileChooser jFileChoose = new JFileChooser("C:\\");
                int fileChooseResult = jFileChoose.showOpenDialog(null);
                if(fileChooseResult == JFileChooser.APPROVE_OPTION)
                {
                    String fileName = jFileChoose.getSelectedFile().toString();
                    MessageDigest digest = null;
                    FileInputStream in = null;
                    byte buffer[] = new byte[1024];
                    int len;
                    try
                    {
                        digest = MessageDigest.getInstance("MD5");
                        in = new FileInputStream(fileName);
                        while ((len = in.read(buffer, 0, 1024)) != -1)
                        {
                            digest.update(buffer, 0, len);
                        }
                        in.close();
                    }
                    catch (Exception error)
                    {
                        JOptionPane.showMessageDialog(jpanel,"文件MD5计算出错","警告", JOptionPane.WARNING_MESSAGE);
                    }
                    BigInteger bigInt = new BigInteger(1, digest.digest());
                    String finalMD5 = bigInt.toString(16);
                    jText2.setText(finalMD5);
                }
                else if (fileChooseResult == JFileChooser.ERROR_OPTION) {
                    JOptionPane.showMessageDialog(jpanel,"文件读取错误","警告", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    class actionTenToBin implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BigInteger bigInteger = new BigInteger(jText1.getText().trim());
            jText2.setText(bigInteger.toString(2));
        }
    }

    class actionRandomKeyNum implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String inputLength = jText1.getText();
                Random random = new Random();
                if (inputLength.equals("")) {
                    StringBuilder newPsdNum = new StringBuilder(10);
                    for (int i = 0; i < 10; i ++) {
                        newPsdNum.append(random.nextInt(10));
                    }
                    jText2.setText(newPsdNum.toString());
                }
                else {
                    int psdByte = Integer.parseInt(jText1.getText());
                    if (psdByte > 10000000) {
                        JOptionPane.showMessageDialog(jpanel,"长度过长，可能生成速度较慢","提示", JOptionPane.WARNING_MESSAGE);
                    }
                    StringBuilder newPsdNum = new StringBuilder(psdByte);
                    for (int i = 0; i < psdByte; i ++) {
                        newPsdNum.append(random.nextInt(10));
                    }
                    jText2.setText(newPsdNum.toString());
                }
            }
            catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(jpanel,"密钥长度输入错误","警告", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    class actionRandomKeyWord implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String keyAllWord = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789!@#$%^&*()_=+";
                String inputLength = jText1.getText();
                Random random = new Random();
                if (inputLength.equals("")) {
                    StringBuilder newPsdNum = new StringBuilder(32);
                    for (int i = 0; i < 32; i ++) {
                        newPsdNum.append(keyAllWord.charAt(random.nextInt(75)));
                    }
                    jText2.setText(newPsdNum.toString());
                }
                else {
                    int psdByte = Integer.parseInt(jText1.getText());
                    if (psdByte > 10000000) {
                        JOptionPane.showMessageDialog(jpanel,"长度过长，可能生成速度较慢","提示", JOptionPane.WARNING_MESSAGE);
                    }
                    StringBuilder newPsdNum = new StringBuilder(psdByte);
                    for (int i = 0; i < psdByte; i ++) {
                        newPsdNum.append(keyAllWord.charAt(random.nextInt(75)));
                    }
                    jText2.setText(newPsdNum.toString());
                }
            }
            catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(jpanel,"密钥长度输入错误","警告", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    class actionFileCRC32 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Crc32Cal crc32Cal = new Crc32Cal();
                Thread thread = new Thread(crc32Cal);
                thread.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        class Crc32Cal implements Runnable {
            @Override
            public void run() {
                JFileChooser jFileChoose = new JFileChooser("C:\\");
                int fileChooseResult = jFileChoose.showOpenDialog(null);
                if(fileChooseResult == JFileChooser.APPROVE_OPTION) {
                    String fileName = jFileChoose.getSelectedFile().toString();
                    CRC32 crc32 = new CRC32();
                    FileInputStream fileinputstream = null;
                    try {
                        fileinputstream = new FileInputStream(new File(fileName));
                        CheckedInputStream checkedinputstream = new CheckedInputStream(fileinputstream, crc32);
                        while (checkedinputstream.read() != -1) {
                        }
                        checkedinputstream.close();
                        String hexString = Long.toHexString(crc32.getValue());
                        jText2.setText(hexString);
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(jpanel,"CRC32","警告", JOptionPane.WARNING_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(jpanel,"文件CRC32计算出错","警告", JOptionPane.WARNING_MESSAGE);
                    }
                }
                else if (fileChooseResult == JFileChooser.ERROR_OPTION) {
                    JOptionPane.showMessageDialog(jpanel,"文件读取错误","警告", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    class actionTenToHex implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BigInteger bigInteger = new BigInteger(jText1.getText().trim());
            jText2.setText(bigInteger.toString(16));
        }
    }

    class actionBinToTen implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BigInteger bigInteger = new BigInteger(jText1.getText().trim(), 2);
            jText2.setText(bigInteger.toString(10));
        }
    }

    public static void main(String[] args) {
        new myTools();
    }
}