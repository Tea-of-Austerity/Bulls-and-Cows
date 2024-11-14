package bullscows;
import java.util.*;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        String secretCode =scanner.nextLine();
        if (!secretCode.chars().allMatch( Character::isDigit )) {
        System.out.printf("Error: \"%s\" isn't a valid number.%n", secretCode);
        System.exit(0);
        }
        int secretNum=Integer.valueOf(secretCode);

        System.out.println("Input the number of possible symbols in the code:");
        int secretCharNum = scanner.nextInt();
        if(secretCharNum >36){
            System.out.printf("Error: maximum number of possible symbols in the code is %d (0-9, a-z).%n",36);
            System.exit(0);
        }
        if(secretCharNum<secretNum){
            System.out.printf("Error: it's not possible to generate a code with a length of 6 with %d unique symbols.",secretCharNum);
            System.exit(0);
        }
        String secret = gnerateSecret(secretNum,secretCharNum);
        String stars ="";
        for(int i=0;i<secretNum;i++){
            stars+="*";
        }
        if(secretCharNum>10) {
            System.out.printf("The secret is prepared: %s (0-9, a-%s)%n", stars, (char) ('a' + secretCharNum - 11));
        }else{
            System.out.printf("The secret is prepared: %s (0-%d)%n", stars,secretCharNum-1);
        }
        //The secret is prepared: ****** (0-9, a-q).
        boolean match = false;
        System.out.println("Okay, let's start a game!");
        int count =1;
        while(true){
            System.out.printf("Turn %d:%n",count);
            match = guessSecret(secret);
            if(match){
                break;
            }
            count++;
        }
    }
    public static String gnerateSecret(int secretNum, int secretCharNum) {
        String secret="";
        List<String> choicePool = randomPool(secretCharNum);
        Collections.shuffle(choicePool);
        //System.out.println(String.valueOf(choicePool));
        //System.out.println(String.valueOf(choicePool));
        for(int i=0; i<secretNum;i++){
            secret+=choicePool.get(0);
            //System.out.println(secret);
            choicePool.remove(0);
            //System.out.println(String.valueOf(choicePool));
        }

        return secret;
    }

    public static List<String> randomPool(int secretCharNum){
        if(secretCharNum>10) {
            List<String> longPool = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"));
            for (int i = 0; i < secretCharNum - 10; i++) {
                longPool.add(String.valueOf((char)('a'+i)));
                //System.out.println(String.valueOf(longPool));
            }
            return longPool;
        }else{
            List<String> shortPool = new ArrayList<String>();
            for (int i = 0; i < secretCharNum; i++) {
                shortPool.add(String.valueOf(i));
                //System.out.println(String.valueOf(shortPool));
            }
            return shortPool;
        }
    }


    public static boolean guessSecret(String secret){
        Scanner scanner = new Scanner(System.in);
        String guess = scanner.next();
        String secretCode = secret;
        char[] testGuess = guess.toCharArray();
        char[] testSecretCode = secretCode.toCharArray();
        int[] result = arrayCompare(testSecretCode,testGuess);
        if(result[0]==testSecretCode.length){
            System.out.printf("Grade %d bull(s).%n",result[0]);
            System.out.println("Congratulations! You guessed the secret code.");
            return true;
        }
        else if(result[0]==0 && result[1]==0){
            System.out.printf("Grade None.%n");
        }else if(result[0]==0){
            System.out.printf("Grade %d cows(s).%n",result[1]);
        }else if(result[1]==0){
            System.out.printf("Grade %d bull(s).%n",result[0]);
        }else {
            System.out.printf("Grade %d bull(s) and %d cow(s).%n",result[0],result[1]);
        }
        return false;
    }
    public static int[] arrayCompare(char[] secret, char[] guess){
        int[] result = new int[2]; //int[0] = Bulls, int[1] = cows;
        //System.out.println(Arrays.toString(secret));
        //System.out.println(Arrays.toString(guess));
        for(int i = 0; i<secret.length;i++){
            if (secret[i]==guess[i]){
                result[0]+=1;
            }else {
                for (int j = 0; j < secret.length; j++) {
                    if(j==i){
                        continue;
                    }
                    else if (secret[j] == guess[i]) {
                        result[1] += 1;
                    }
                }
            }
        }
        result[1]=result[1]-result[0];
        return result;
    }
    public static int[] numberToArray(long number){
        int digits = String.valueOf(number).length();
        int[] returnArray = new int[digits];
        for (int i= digits-1;i>=0;i--){
            returnArray[i]=(int)number%10;
            number = number/10;
        }
        return returnArray;
    }
    public static int[] numberToArray(String number){
        int digits = String.valueOf(number).length();
        int[] returnArray = new int[digits];
        for (int i= 0;i<digits;i++){
            returnArray[i]=(int)number.charAt(i)-'0';
        }
        return returnArray;
    }

}
