package com.company;

import java.math.BigInteger;
import java.util.*;

public class Main {

    public static void generate(List<Integer> lst) {
        for (int i = 2; i < 543; i++) {
            boolean bool = true;
            int x = (int) Math.sqrt(i);
            for (int j = 2; j <= x; j++) {
                if ((i % j) == 0) {
                    bool = false;
                    break;
                }
            }
            if (bool) lst.add(i);
        }
    }
    public static boolean isPrime(int num) {
        if (num <= 1) return false;
        for(int i = 2; i < num; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }
    public static boolean IsCoprime(int num1, int num2)
    {
        if (num1 == num2)
        {
            return num1 == 1;
        }
        else {
            if(num1>num2){
                return IsCoprime(num1 - num2, num2);
            }
            else return IsCoprime(num2-num1,num1);
        }
    }
    public static void main(String[] args) {
        List <Integer> lst= new ArrayList<>();
        Random random = new Random();
        Scanner in = new Scanner(System.in);
        String charRU = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";;
        System.out.print("Шифруемое сообщение: ");
        String text = in.nextLine(); //23 29
        StringBuilder digest= new StringBuilder();
        for(int i=0; i<text.length(); i++){
            if(i==0 && text.charAt(i)!=' ')
                digest.append((text.charAt(i)+"").toUpperCase(Locale.ROOT));
            else
            {
                if(text.charAt(i-1)==' '){
                    digest.append((text.charAt(i)+"").toUpperCase(Locale.ROOT));
                }
            }

        }
        generate(lst);
        int p=lst.get(random.nextInt(100)), q=lst.get(random.nextInt(100));
        int n = p*q;
        //формирование закрытого ключа
        int form = (p-1)*(q-1);
        //(h(i-1) + m(i)**2)%2
        int h0 =random.nextInt(200)+1;
        int hash = h0;
        for(int i=0; i<digest.length(); i++){
            hash = ((int) Math.pow((hash + charRU.indexOf(digest.charAt(i))),2))%n;
        }
        System.out.println("Хеш-образ шифруемого сообщения сообщения \""+digest+"\" : "+hash);
        int d =random.nextInt(100);
        while (!IsCoprime(form,d)){
            d =random.nextInt(200);
        }
        int e = (form+1)/d;
        int temp=form;
        while ((e*d)%(temp)!=1){
            temp+=form;
            e = (temp+1)/d;
        }
        System.out.println("------------------------------------");
        System.out.println("Полученный закрытый ключ: "+d + ", "+n);
        System.out.println("Полученный открытый ключ: "+e + ", "+n);
        System.out.println("------------------------------------");
        BigInteger s = new BigInteger(String.valueOf(hash));
        s = s.pow(d);
        s = s.remainder(BigInteger.valueOf(n));
        System.out.println("Электронная подпись: "+s);
        System.out.println("------------------------------------");

        System.out.print("Проверяемое сообщение: ");
        text = in.nextLine(); //23 29
        digest= new StringBuilder();
        for(int i=0; i<text.length(); i++){
            if(i==0 && text.charAt(i)!=' ')
                digest.append((text.charAt(i)+"").toUpperCase(Locale.ROOT));
            else
            {
                if(text.charAt(i-1)==' '){
                    digest.append((text.charAt(i)+"").toUpperCase(Locale.ROOT));
                }
            }
        }
        int hashNew = h0;
        for(int i=0; i<digest.length(); i++){
            hashNew = ((int) Math.pow((hashNew + charRU.indexOf(digest.charAt(i))),2))%n;
        }
        System.out.println("Хеш-образ сообщения \""+digest+"\": "+hashNew);
        BigInteger s1 = new BigInteger(String.valueOf(hashNew));
        s1 = s1.pow(d);
        s1 = s1.remainder(BigInteger.valueOf(n));

        BigInteger H = s1.pow(e);
        H = H.remainder(BigInteger.valueOf(n));
        System.out.println("H: "+H);
        if (H.intValue() ==hash){
            System.out.println("Электронная подпись совпадает");
        }
        else {
            System.out.println("Электронная подпись не совпадает");
        }

    }
}
