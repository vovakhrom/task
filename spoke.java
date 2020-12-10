import java.nio.charset.StandardCharsets;
import java.util.*;
import org.bouncycastle.util.encoders.Hex;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.security.*;

public class spoke {
    static ArrayList<String> words = new ArrayList<String>();

    static boolean replay(String[] f) {
        ArrayList<String> w = new ArrayList<String>();
        int n = 0;
        for (String str : f) {
            n++;
        }
        for (String str : f) {
            w.add(str);
        }
        for (int i = 0; i < w.size() - 1; i++) {
            for (int j = i + 1; j < w.size(); j++) {
                if (w.get(i).equals(w.get(j))) {
                    return false;
                }
            }
        }
        if (n >= 3 && n % 2 == 1)
            return true;
        else
            return false;
    }

    static void printing(ArrayList<String> word) {
        System.out.println("меню");
        for (int i = 0; i < word.size(); i++) {
            System.out.println(i + " - " + word.get(i));
        }
        System.out.println(word.size() + " - exit");
    }

    static int generator(long v, ArrayList<String> word) {
        int size = word.size();
        int f = (int) (v % size);
        return f;
    }
    public static String encode(String key, String data) throws Exception {
        Mac sha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256.init(secret_key);
        return Hex.toHexString(sha256.doFinal(data.getBytes("UTF-8")));
    }
    static void computerwins(ArrayList<String> word, int move, int computermove) {
        int user = move;
        int wons = word.size() / 2;
        int[] arraywins = new int[word.size()];
        if (computermove == user) {
            System.out.println("Ничья!");
            return;
        }
        if (computermove + wons <= word.size() - 1) {
            for (int i = computermove + 1; i <= computermove + wons; i++) {
                arraywins[i] = 1;
            }
        }
        if (computermove + wons > word.size() - 1) {
            int totail = word.size() - 1 - computermove;
            int fromhead = wons - totail;
            for (int i = computermove + 1; i <= computermove + totail; i++) {
                arraywins[i] = 1;
            }
            for (int j = 0; j < fromhead; j++) {
                arraywins[j] = 1;
            }
        }
        if (arraywins[user] == 1) {
            System.out.println("Вы проиграли!");
        }
        if (arraywins[user] == 0) {
            System.out.println("Вы победили!");
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        if (replay(args) == false) {
            System.out.println("Введите нечётное число >=3 неповторяющихся строк .Например: Камень Ножницы Бумага");
            return;
        } else {
            for (String str : args) {
                words.add(str);
            }
        }
        label: while (true) {
            SecureRandom rand = new SecureRandom();
            long v = Math.abs(rand.nextLong());
            String key = Long.toHexString(v) + Long.toHexString(rand.nextLong());
            System.out.println("HMAC:");
            try {
                System.out.println(encode("keys", key));
            } catch (Exception e) {
                e.printStackTrace();
            }
            printing(words);
            Scanner scn = new Scanner(System.in);
            System.out.print("Ваш выбор: ");
            int num = scn.nextInt();
            if (num == words.size()) {
                System.out.println("До свидания!");
                System.exit(0);
            }
            if (num<0 || num>words.size()){
                continue label;
            }
            System.out.println("Вы выбрали :" + words.get(num));
            int compmove = generator(v, words);
            System.out.println("Выбор компьютера: " + words.get(compmove));
            computerwins(words, num, compmove);
            System.out.println("Hmac key:"+key);
            continue label;
        }
    }

}
