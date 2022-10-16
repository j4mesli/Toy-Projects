package calc;

import java.util.Scanner;

public class MainCalculator {
	public static int calculate(String s) {
        LinkedStack<Integer> stack = new LinkedStack<Integer>();
        for(int i = 0; i < s.length(); i++) {
        	char c = s.charAt(i);  
            if(Character.isDigit(c)) {
                stack.push(c - '0');	
            } 
            else {
                int num1 = stack.pop();
                int num2 = stack.pop();
                switch(c) {
                case '^':
                	stack.push((int) Math.pow(num2, num1));
                	break;
                case '*':
                	stack.push(num2 * num1);
                	break;
                case '-':
                    stack.push(num2 - num1);
                    break;
                case '/':
                	stack.push(num2 / num1);
                    break;
                case '+':
                   	stack.push(num2 + num1);
                   	break;
                }
            }
        }
        return stack.pop();   
    }
	public static void main(String[] args) {
		Converter calc = new Converter();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your infix expression here: \n");
		String infix = input.nextLine();
		String postfix = calc.toPostFix(infix);
		System.out.println("POSTFIX: "+postfix);
		System.out.println("ANSWER: " + calculate(postfix.replace(" ", "")));
	}
}
