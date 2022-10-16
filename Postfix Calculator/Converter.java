package calc;

public class Converter {
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