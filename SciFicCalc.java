import javax.swing.*;
import java.util.regex.*;
import java.awt.*;

public class SciFicCalc {
    public static double calculation(String a,String b){
        if(b=="/") {

            return Double.parseDouble(a.substring(0,a.indexOf("/")))/Double.parseDouble(a.substring(a.indexOf("/")+1));
        }
        else if(b=="*") {

            return Double.parseDouble(a.substring(0,a.indexOf("*")))*Double.parseDouble(a.substring(a.indexOf("*")+1));
        }
        else if(b=="+") {

            return Double.parseDouble(a.substring(0,a.indexOf("+")))+Double.parseDouble(a.substring(a.indexOf("+")+1));
        }
        else if(b=="-") {

            return Double.parseDouble(a.substring(0,a.indexOf("-")))-Double.parseDouble(a.substring(a.indexOf("-")+1));
        }
        
        
        
        
        else return Double.parseDouble(a);
    }
  
     public static String replacer(String line,String what){
        String temp="";
        
        String pattern = "\\-?[0-9.]+\\"+what+"\\-?[0-9.]+";
    
        
        Pattern r = Pattern.compile(pattern);
    
       
        Matcher m = r.matcher(line);
        if (m.find()) {
        temp=line.substring(0,m.start())+calculation(m.group(), what)+line.substring(m.end());
        return replacer(temp, what);
        }else return line;
        
    }
    public static void main(String[] qwertyuiop) {
        String[][] butn1={
           { "calc","STO","/\\","\\/","x^-1","x^3"},
           { "a b/c","sqrt","x^2","^","log","ln"},
           { "sin^-1","cos^-1","tan^-1","sin","cos","tan"},
           { "RCL","ENG","(",")",",","M+"},
           { "7","8","9","DEL","AC",""},
           { "4","5","6","*","/",""},
           { "1","2","3","+","-",""},
           { "0",".","*10^x","%","=",""}
           
        };


        JFrame f = new JFrame("SciFicCalc");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(500, 500);
        f.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

       
        {
        JTextField editTextArea = new JTextField("");
        JTextArea output = new JTextArea("");
       
      
        output.setBorder(BorderFactory.createDashedBorder(null, 2, 1));
      
        
        
        editTextArea.addActionListener(e ->
        {
            
            output.setText(replacer(replacer(replacer(replacer(editTextArea.getText(),"/"),"*"),"+"),"-"));
        });
        gbc.ipady=20;
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth=6;gbc.fill = GridBagConstraints.HORIZONTAL;gbc.weighty=0;
        f.add(editTextArea,gbc);
        gbc.gridx = 0; gbc.gridy = 1; 
        f.add(output,gbc);
    }
        // gbc.ipady=0;
         gbc.gridwidth=1;
         
         gbc.weightx=1;
         gbc.weighty=1;
        JButton[][] btn=new JButton[4][6];
        for(int i=2;i<6;i++){
            for(int j=0;j<6;j++){
                gbc.gridx = j; gbc.gridy = i;
                btn[i-2][j]=new JButton(butn1[i-2][j]);
                f.add(btn[i-2][j],gbc);
            }
        }
        JButton[][] btn2=new JButton[4][6];
        for(int i=6;i<10;i++){
            for(int j=0;j<5;j++){
                gbc.gridx = j; gbc.gridy = i;
                if(j==4)gbc.gridwidth=2;
                else gbc.gridwidth=1;
                btn2[i-6][j]=new JButton(butn1[i-2][j]);
                f.add(btn2[i-6][j],gbc);
            }
        }
        
        
        
       
    
        f.setVisible(true);
        f.pack();

     
    }

}