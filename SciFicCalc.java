import javax.swing.*;
//import javax.swing.text.html.FormView;

import java.util.regex.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class SciFicCalc {
   static double A=0,B=0,C=0,D=0,E=0,F=0;
   public static double getVar(int i) {
    if(i==0)return A;
    if(i==1)return B;
    if(i==2)return C;
    if(i==3)return D;
    if(i==4)return E;
    if(i==5)return F;
    return 0.0;

   }
    public static double calculation(String a, String b) {
        if (b == "/") {

            return Double.parseDouble(a.substring(0, a.indexOf("/")))
                    / Double.parseDouble(a.substring(a.indexOf("/") + 1));
        } else if (b == "*") {

            return Double.parseDouble(a.substring(0, a.indexOf("*")))
                    * Double.parseDouble(a.substring(a.indexOf("*") + 1));
        } else if (b == "+") {

            return Double.parseDouble(a.substring(0, a.indexOf("+")))
                    + Double.parseDouble(a.substring(a.indexOf("+") + 1));
        } else if (b == "-") {

            return Double.parseDouble(a.substring(0, a.indexOf("-")))
                    - Double.parseDouble(a.substring(a.indexOf("-") + 1));
        }

        else if (b == "^") {

            return Math.pow(Double.parseDouble(a.substring(0, a.indexOf("^")))
                    , Double.parseDouble(a.substring(a.indexOf("^") + 1)));
        }
         else if (b == "sin") {

        return Math.sin(Math.toRadians(Double.parseDouble(a.substring(4,a.length()-1))));
               
        }
        else if (b == "cos") {

        return Math.cos(Math.toRadians(Double.parseDouble(a.substring(4,a.length()-1))));
               
        }
        else if (b == "tan") {

        return Math.tan(Math.toRadians(Double.parseDouble(a.substring(4,a.length()-1))));
               
        }
        else if (b == "sin\\^\\-1") {

        return Math.toDegrees(Math.asin(Double.parseDouble(a.substring(7,a.length()-1))));
               
        }
        

        else if (b == "cos\\^\\-1") {

        return Math.toDegrees(Math.acos(Double.parseDouble(a.substring(7,a.length()-1))));
               
        }

        else if (b == "tan\\^\\-1") {

        return Math.toDegrees(Math.atan(Double.parseDouble(a.substring(7,a.length()-1))));
               
        }

        else if (b == "log") {
           
        return Math.log10((Double.parseDouble(a.substring(4,a.length()-1))));
                   
        }

        else if (b == "ln") {

            return Math.log((Double.parseDouble(a.substring(3,a.length()-1))));
                       
            }
        else if (b == "sqrt") {

            return Math.sqrt((Double.parseDouble(a.substring(5,a.length()-1))));
                       
            }
    
            
    

        else
            return Double.parseDouble(a);
    }

    public static String replacer(String line, String what) {
        String temp = "";

        String pattern = "\\-?[0-9.]+\\" + what + "\\-?[0-9.]+";

        // \-?[0-9.]+\+\-?[0-9.]+

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(line);
        if (m.find()) {
            temp = line.substring(0, m.start()) + calculation(m.group(), what) + line.substring(m.end());
            return replacer(temp, what);
        } else
            return line;

    }

    public static String without_braces(String input) {

        return input.substring(1, input.length() - 1);
    }
  
    public static String mathFunction(String input) {
      String funs[]={"sin","cos","tan","sin\\^\\-1","cos\\^\\-1","tan\\^\\-1","log","ln","sqrt"};
      for (String fff : funs) {
        
      
        String temp = "";

        String pattern = fff+"\\[\\-?[0-9.\\+\\-\\*\\/]+\\]";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(input);
        if (m.find()) {
            temp = input.substring(0, m.start()) + calculation(solver(m.group()),fff) + input.substring(m.end());

            return mathFunction(temp);
        } 

    }
    return solver(input);
}
    public static String braces(String input) {
        String temp = "";

        String pattern = "\\(\\-?[\\w+\\-.*/]+\\)";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(input);
        if (m.find()) {
            temp = input.substring(0, m.start()) + solver(without_braces(m.group())) + input.substring(m.end());

            return braces(temp);
        } else
            return solver(input);

    }

    public static String solver(String input) {

        return replacer(replacer(replacer(replacer(replacer(input, "^"), "/"), "*"), "+"), "-");

    }

    public static String variableParser(String input) {
       
       while(input.indexOf("A")!=-1)input=input.substring(0, input.indexOf("A"))+A+input.substring(input.indexOf("A")+1);
       while(input.indexOf("B")!=-1)input=input.substring(0, input.indexOf("B"))+B+input.substring(input.indexOf("B")+1);
       while(input.indexOf("C")!=-1)input=input.substring(0, input.indexOf("C"))+C+input.substring(input.indexOf("C")+1);
       while(input.indexOf("D")!=-1)input=input.substring(0, input.indexOf("D"))+D+input.substring(input.indexOf("D")+1);
       while(input.indexOf("E")!=-1)input=input.substring(0, input.indexOf("E"))+E+input.substring(input.indexOf("E")+1);
       while(input.indexOf("F")!=-1)input=input.substring(0, input.indexOf("F"))+F+input.substring(input.indexOf("F")+1);
        return input;
    }
    public static String main_solver(String input) {

        return braces(mathFunction(braces(variableParser(input))));
    }

    public static ArrayList<String> history_fetch() {
        ArrayList<String> txt = new ArrayList<String>();

        try {
            File filee = new File("history.txt");
            FileReader fr = new FileReader(filee);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {

                txt.add(line);

            }
            fr.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        Collections.reverse(txt);
        return txt;
    }

    public static void history_add(String given) {

        Path file = Path.of("history.txt");
        try {
            File filee = new File("history.txt");
            FileReader fr = new FileReader(filee);
            BufferedReader br = new BufferedReader(fr);

            String line, total = "";
            int counter = 1;
            while ((line = br.readLine()) != null) {
                if (counter > 9)
                    break;
                total += line + "\n";
                counter++;
            }
            fr.close();
            Files.writeString(file, given + '\n' + total);

        } catch (IOException e) {
            System.out.println(e);
        }

    }


    public static void main(String[] qwertyuiop) {

        String[][] butn1 = {
                { "calc", "STO", "History", "", "x⁻¹", "x³" },
                { "x⁻¹", "sqrt", "x²", "x" + Character.toString(0x02b8), "log", "ln" },
                { "sin⁻¹", "cos⁻¹", "tan⁻¹", "sin", "cos", "tan" },
                { "A", "B", "C", "D", "E", "F"  },
                { "7", "8", "9", "DEL", "AC", "" },
                { "4", "5", "6", Character.toString(0x00d7), "÷", "" },
                { "1", "2", "3", "+", "-", "" },
                { "0", ".", Character.toString(0x00d7) + "10ˣ", "00", "=", "" }

        };

        JFrame f = new JFrame("SciFicCalc");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(400, 650);
        f.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField editTextArea = new JTextField("");
        JTextArea output = new JTextArea("");

        output.setBorder(BorderFactory.createDashedBorder(null, 2, 1));

        editTextArea.addActionListener(e -> {
            String input = editTextArea.getText();
            output.setText(main_solver(input));
            history_add(input);
         
         
        });
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        f.add(editTextArea, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        f.add(output, gbc);

        // gbc.ipady=0;

        gbc.weightx = 1;
        gbc.weighty = 1;
        JButton[][] btn = new JButton[4][6];
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (i == 2 && j == 3)
                    continue;
                gbc.gridx = j;
                gbc.gridy = i;
                if (i == 2 && j == 2) {
                    gbc.gridwidth = 2;
                } else
                    gbc.gridwidth = 1;

                btn[i - 2][j] = new JButton(butn1[i - 2][j]);
                btn[i - 2][j].setMargin(new Insets(0,0,0,0));
                f.add(btn[i - 2][j], gbc);
            }
        }
        JButton[][] btn2 = new JButton[4][6];
        for (int i = 6; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                gbc.gridx = j;
                gbc.gridy = i;
                if (j == 4)
                    gbc.gridwidth = 2;
                else
                    gbc.gridwidth = 1;
                btn2[i - 6][j] = new JButton(butn1[i - 2][j]);
                btn2[i - 6][j].setMargin(new Insets(0,0,0,0));
                f.add(btn2[i - 6][j], gbc);
            }
        }

        btn[0][2].addActionListener(e -> {

            
            ArrayList<String> hislist = history_fetch();
            JPopupMenu popup = new JPopupMenu("history");
            JMenuItem[] hry = new JMenuItem[hislist.size()];
            popup.setBackground(new Color(0, 0, 0));

            for (int i = hislist.size() - 1; i >= 0; i--) {
                hry[i] = new JMenuItem(hislist.get(i));
                hry[i].addActionListener(em -> {
                    String hhh = em.getSource().toString();
                    editTextArea.setText(hhh.substring(hhh.indexOf(",text=") + 6, hhh.length() - 1));

                });
                popup.add(hry[i]);

            }

            btn[0][2].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    popup.show(btn[0][2], e.getX(), e.getY());
                }
            });
            f.add(popup);

        });
        String[][] numpad = {
            { "7", "8", "9", "", "" },
            { "4", "5", "6", "*", "/" },
            { "1", "2", "3", "+", "-" },
            { "0", ".", "*10^", "00", "" }
        

    };
    for(int i=0;i<=3;i++){
        for(int j=0;j<=4;j++){
             String numtext=numpad[i][j];
             if(i==3&&j==4){
                //equal button
                btn2[i][j].addActionListener( e ->{
                    String input = editTextArea.getText();
                    output.setText(main_solver(input));
                    history_add(input);
                    editTextArea.requestFocus();
                });
                continue;
               }
               if(i==0&&j==4){
                //AC button
                btn2[i][j].addActionListener( e ->{
                    editTextArea.setText("");
                    editTextArea.requestFocus();
                });
                continue;
               }

               if(i==0&&j==3){
                //DEL button
                btn2[i][j].addActionListener( e ->{
                    String given=editTextArea.getText();
                    if(given.length()!=0)editTextArea.setText(given.substring(0, given.length()-1));
                    editTextArea.requestFocus();
                });
                continue;
               }


        btn2[i][j].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            int pos= editTextArea.getCaretPosition();
            String pre=editTextArea.getText();
            editTextArea.setText(pre.substring(0, pos)+numtext+pre.substring(pos));
            editTextArea.setCaretPosition(pos+numtext.length());
            editTextArea.requestFocus();
            }
        });
            


}}
String[][] funcPad= {
    { "^-1","sqrt[]","^2","^","log[]","ln[]" },
    { "sin^-1[]","cos^-1[]","tan^-1[]","sin[]","cos[]","tan[]" },
    { "A","B","C","D","E","F" }
    

};
    for(int i=0;i<=2;i++){
        for(int j=0;j<=5;j++){
             String functext=funcPad[i][j];
                int iii=i,jjj=j;
                

        btn[i+1][j].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            int pos= editTextArea.getCaretPosition();
            String pre=editTextArea.getText();
            editTextArea.setText(pre.substring(0, pos)+functext+pre.substring(pos));
            int cursor=(iii==2||(iii==0&&jjj==0)||(iii==0&&jjj==2)||(iii==0&&jjj==3))?functext.length():functext.length()-1;
            editTextArea.setCaretPosition(pos+cursor);

            
            
            editTextArea.requestFocus();
            }
        });


    }}
    for(int i=0;i<=5;i++){
        int iii=i;
        
    btn[3][i].addMouseListener(new MouseAdapter() {
        
            public void mouseClicked(MouseEvent e){
                if(e.getButton()==3&&!output.getText().isEmpty()){
                    if(iii==0){A=Double.parseDouble(output.getText());btn[3][iii].setToolTipText(getVar(iii)+"");}
                    if(iii==1){B=Double.parseDouble(output.getText());btn[3][iii].setToolTipText(getVar(iii)+"");}
                    if(iii==2){C=Double.parseDouble(output.getText());btn[3][iii].setToolTipText(getVar(iii)+"");}
                    if(iii==3){D=Double.parseDouble(output.getText());btn[3][iii].setToolTipText(getVar(iii)+"");}
                    if(iii==4){E=Double.parseDouble(output.getText());btn[3][iii].setToolTipText(getVar(iii)+"");}
                    if(iii==5){F=Double.parseDouble(output.getText());btn[3][iii].setToolTipText(getVar(iii)+"");}
                    
                }
                
            }

    });

    

    }

    
        

        f.setVisible(true);
        // f.pack();

    }

}