package com.hellostock.app;

import java.util.Scanner;

public class Example7 {
	
	public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        System.out.println("请输入C6-C15");
        double [] c1=new double[10];
        for (int x = 0; x < 10; x++) {
            c1[x] = sc.nextDouble();
        }
        System.out.println("请输入C'6-C'15");
        double [] c2=new double[10];
        for (int x = 0; x < 10; x++) {
            c2[x] = sc.nextDouble();
        }

        count(c1,c2);
    }

    //  定义计算的方法
    public static void count(double[] arr1,double[] arr2) {

        //计算并输出D6-D15的值
        double [] d=new double[10];
        System.out.println("D6-D15的值分别是：");
        for(int x=0;x<10;x++){
            d[x]=arr1[x]-arr2[x];
            System.out.println(d[x]);
        }
        System.out.println();
        //计算并输出Dm²-Dn²的值
        double [] R=new double[5];
        System.out.println("Dm²-Dn²的值分别是：");
        for(int i=0;i<5;i++){
            double temp=d[i+5]*d[i+5]-d[i]*d[i];
            R[i]=temp/(4*5*589.3);

            System.out.println(temp);

            }
        System.out.println();
        //计算并输出R值
        System.out.println("R的值分别是：");
        for(int i=0;i<5;i++){
            double temp=d[i+5]*d[i+5]-d[i]*d[i];
            R[i]=temp/(4*5*589.3);

            System.out.println(R[i]);
        }
        System.out.println();
        //计算R的平均值
        double sum=0;
        for(int x=0;x<5;x++){   
            sum+=R[x];
        }
        System.out.println("R的平均值是  "+sum/5);
    }

}
