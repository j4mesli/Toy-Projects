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
class Converter {
	public static boolean isNumeric(String s) { 
		try {  
		    Integer.parseInt(s);  
		    return true; 
		} 
		catch(NumberFormatException e){  
		    return false;  
		}   
	}
	// takes place of parserhelper, tokenizes
	public static int order(char c) {
		switch(c) {
		case('+'):
			return 1;
		case('-'):
			return 1;
		case('*'):
			return 2;
		case('/'):
			return 2;
		case('^'):
			return 3;
		}
		return 0;
	}
	public String toPostFix(String s) {
		String output = "";
		LinkedStack<Character> stack = new LinkedStack<Character>();
		char[] chars = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (order(c) > 0) {
				while (!stack.isEmpty() && order(stack.top()) >= order(c)) {
					output += stack.pop() + " ";
				}
				stack.push(c);
			}
			else if(c==')'){
				char x = stack.pop();
                while(x!='('){
                    output += x + " ";
                    x = stack.pop();
                }
			}
			else if(c=='('){
                stack.push(c);
            }
			else{
                output += c + " ";
            }
		}
		for (int i = 0; i <= stack.size(); i++) {
			output += stack.pop();
	    }
		return output;
	}
}
