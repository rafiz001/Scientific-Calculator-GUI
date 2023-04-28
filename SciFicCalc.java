import javax.swing.*;
import java.util.regex.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public static ArrayList<String> history_fetch(){
        ArrayList<String> txt = new ArrayList<String>();
        // Path file=Path.of("java.txt");
    try {
            File filee=new File("history.txt");    
            FileReader fr=new FileReader(filee);    
            BufferedReader br=new BufferedReader(fr);   
             
            String line;  
            while((line=br.readLine())!=null)  
            {  
            txt.add(line);  
            }  
            fr.close(); 
        
    
    
    } catch (Exception e) {
        System.out.println(e);
    } 
    return txt;
    }
    public static void history_add(String given){
        
         Path file=Path.of("history.txt");
    try {
            File filee=new File("history.txt");    
            FileReader fr=new FileReader(filee);    
            BufferedReader br=new BufferedReader(fr);   
             
            String line,total="";  
            while((line=br.readLine())!=null)  
            {  
            total+=line+"\n";
            }  
            fr.close(); 
        Files.writeString(file, total+given);
    
    
    } catch (IOException e) {
        System.out.println(e);
    } 
   
    }
    public static void main(String[] qwertyuiop) {
        String[][] butn1={
           { "calc","STO","History","","x⁻¹","x³"},
           { "a b/c","sqrt","x²","xʸ","log","ln"},
           { "sin⁻¹","cos⁻¹","tan⁻¹","sin","cos","tan"},
           { "RCL","ENG","(",")",",","M+"},
           { "7","8","9","DEL","AC",""},
           { "4","5","6","×","÷",""},
           { "1","2","3","+","-",""},
           { "0",".","×10ˣ","%","=",""}
           
        };


        JFrame f = new JFrame("SciFicCalc");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(500, 500);
        f.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

       
        
        JTextField editTextArea = new JTextField("");
        JTextArea output = new JTextArea("");
       
      
        output.setBorder(BorderFactory.createDashedBorder(null, 2, 1));
      
        
        
        editTextArea.addActionListener(e ->
        {
            String input=editTextArea.getText();
            output.setText(replacer(replacer(replacer(replacer(input,"/"),"*"),"+"),"-"));
            history_add(input);
        });
        gbc.ipady=20;
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth=6;gbc.fill = GridBagConstraints.HORIZONTAL;gbc.weighty=0;
        f.add(editTextArea,gbc);
        gbc.gridx = 0; gbc.gridy = 1; 
        f.add(output,gbc);
    
        // gbc.ipady=0;
         
         
         gbc.weightx=1;
         gbc.weighty=1;
        JButton[][] btn=new JButton[4][6];
        for(int i=2;i<6;i++){
            for(int j=0;j<6;j++){
                if(i==2&&j==3)continue;
                gbc.gridx = j; gbc.gridy = i;
                if(i==2&&j==2){
                    gbc.gridwidth=2;
                }else gbc.gridwidth=1;
                
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
    
        btn[0][2].addActionListener(e ->{
           
            //editTextArea.setText(history_fetch().get(history_fetch().size()-ith_history));
            ArrayList<String> hislist=history_fetch();
           JPopupMenu popup=new JPopupMenu("history");
            JMenuItem[] hry=new JMenuItem[hislist.size()];
            popup.setBackground(new Color(0, 0, 0));
           
            for( int i=hislist.size()-1;i>=0;i--){
                hry[i]=new JMenuItem(hislist.get(i));
                hry[i].addActionListener( em -> {
                    String hhh=em.getSource().toString();
                    editTextArea.setText(hhh.substring(hhh.indexOf(",text=")+6,hhh.length()-1));
                    
                });
                popup.add(hry[i]);

            }
           
            btn[0][2].addMouseListener(new MouseAdapter() {  
            public void mouseClicked(MouseEvent e) {              
                popup.show(btn[0][2] , e.getX(), e.getY());  
            }                 
         }); 
            f.add(popup);



        });
        
       


       


    
        f.setVisible(true);
        f.pack();

     
    }

}
