
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import static java.awt.Color.*;

public class cardgame extends Frame {

    Frame f = new Frame("CARD GAME");
    Label l1 = new Label("行:");
    TextField t1 = new TextField(20);
    Label l2 = new Label("列");
    TextField t2 = new TextField(20);
    JButton b1=new JButton("Confirm");
    Container  f2=new Container();
    public void go(){
        f.setBounds(50,50,700,400);
        f.setBackground(lightGray);
        f.setLayout(null);

        l1.setBounds(50, 50,50,20);
        t1.setBounds(120,50,150,20);
        l2.setBounds(300,50,50,20);
        t2.setBounds(370,50,150,20);
        b1.setBounds(550,50,100,20);

        f.add(l1);
        f.add(t1);
        f.add(l2);
        f.add(t2);
        f.add(b1);
        f.setVisible(true);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //给确认按钮添加事件
                try {
                    int row=0,col=0;
                    row= Integer.parseInt(t1.getText());//获取行列值
                    col= Integer.parseInt(t2.getText());
                    if(row<=0||col<=0){
                        gameEnd("错误的行列值");
                    }
                   else{
                        game(row,col);
                    }
                }
                catch (Exception a) {
                    a.printStackTrace();
                    gameEnd("错误的行列值");
                }

            }
        });
        f.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });//关闭窗口事件
    }

    public void game(int row,int col){
        f2.removeAll();
        JButton b[][]=new JButton[row][col];
        GridLayout layout=new GridLayout(row,col,0,0);//生成无间隙的网状布局
        f2.setLayout(layout);
        f2.setBounds(120,100,50*col,50*row);

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                int num=i*col+j+1;
                b[i][j]=new JButton(Integer.toString(num));//以按钮表示卡牌,按钮上数字代表所在的网格,随机初始化颜色
                Random rd = new Random(); //创建一个Random类对象实例
                int x = rd.nextInt(2);
              if(x==1){
                  b[i][j].setBackground(black);
              }
              else{
                  b[i][j].setBackground(white);
              }
                f2.add(b[i][j]);
            }
        }
        f.add(f2);
        f.setVisible(true);
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++) {
                b[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {//为每个按钮添加响应事件
                        JButton temp=(JButton)e.getSource();
                        int num= Integer.parseInt(temp.getText())-1;//获得响应按钮上的数字,找到按钮对应的行列值
                        int sRow=num/col;
                        int sCol=num%col;
                        //改变当前和四周按钮的颜色,注意边界情况
                        changeJButon(temp);
                        if(sRow-1>=0){
                            changeJButon(b[sRow-1][sCol]);
                        }
                        if(sCol-1>=0){
                            changeJButon(b[sRow][sCol-1]);
                        }
                        if(sRow+1<row){
                            changeJButon(b[sRow+1][sCol]);
                        }
                        if(sCol+1<col){
                            changeJButon(b[sRow][sCol+1]);
                        }
                        //每改变一次，进行一次检查游戏是否结束
                        boolean flag1=true,flag2=true;
                        for(int i=0;i<row;i++) {
                            for (int j = 0; j < col; j++) {
                                if(white == b[i][j].getBackground()) {
                                    flag1=false;//不可能是全黑结果
                                }
                                else{
                                    flag2=false;//不可能是全白结果
                                }
                            }
                            if(flag1==false&&flag2==false) break;//两种结果都不是，游戏没有结束
                        }
                        if(flag1==true||flag2==true) gameEnd("游戏胜利");
                    }
                });
            }
        }

    }
    public void changeJButon(JButton tmp){
        if(white == tmp.getBackground()){
            tmp.setBackground(black);
        }
        else{
            tmp.setBackground(white);
        }
    }//改变按钮颜色
    public void gameEnd(String text){
        Dialog dialog = new Dialog(f,"游戏结果",false);
        Label l = new Label(text);
        dialog.add(l);
        dialog.setLayout(null);
        dialog.setBounds(50,100,250,150);
        l.setBounds(90,70,150,20);
        dialog.setVisible(true);
        dialog.addWindowListener (new WindowAdapter()
        {
            @Override
            public void windowClosing ( WindowEvent e )
            {
                dialog.dispose ();
            }
        });
    }//弹出游戏结果对话框
    public static void main(String args[]){
        cardgame e = new cardgame();
        e.go();
    }
}
