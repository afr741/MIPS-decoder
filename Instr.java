import java.util.*;

public class Instr
{

    private boolean isImmediateInstr;
    
	public int immediate;

    public String InstrString;  

	private boolean ok;

	public int opcode;	

	public int rs;

	public int rt;	

	public int rd;
	
    
	private String error = "The entered Instr is invalid.";
	
	
	Instr(String temp) {
		StringTokenizer tokens = new StringTokenizer(temp, " ");
		String op = "", t1 = "", t2 = "", t3 = "";
		
		int switcher;
	          
	    isImmediateInstr = false;  // if there is  no immediate operand	
	    ok = true;                // parser recognizes the instruction
        InstrString = temp; // full Istruction as a string


trying:		
		try {
			op = tokens.nextToken();

			t1 = tokens.nextToken();

			t2 = tokens.nextToken();

			
			
			if (!op.equals("lw") && !op.equals("sw")) {
				t3 = tokens.nextToken();
				if(t3.length() == 0) {
					ok = false;
					break trying;
				}
			}
			
			if(op.equals("ADD") || op.equals("add") || op.equals("SUB") || op.equals("sub")) {
				switcher = 0;  // Instructions of R-typee
				
			}
			
			else if(op.equals("LW") || op.equals("lw") || op.equals("SW") || op.equals("sw"))
				switcher = 1;  // store/load word Instructions
            else if(op.equals("BEQ") || op.equals("beq"))
                switcher = 2;  // branch Instructions
			else {
				ok = false;
				break trying;
			}
			
			switch (switcher) {
				case 0:    // R-type Instructions
					rd = Integer.parseInt(t1.substring(0));
					rs = Integer.parseInt(t2.substring(0));
					rt = Integer.parseInt(t3.substring(0));
				
					
					if (rd < 1 || rd > 30 || rs < 1 || rs > 30 || rt < 1 || rt > 30) {
						error = "Invalid Instr: register out of bounds";
						ok = false;
						break trying;
					}
					
					if (op.equals("ADD") || op.equals("add"))
						opcode = 32;
					else if (op.equals("SUB") || op.equals("sub"))
						opcode = 34;
					break;
					
				case 1:    // load/store word Instructions
					isImmediateInstr = true;
					rt = Integer.parseInt(t1.substring(0)); // destination register
					rs = Integer.parseInt(t2.substring(t2.indexOf("(") + 1, t2.indexOf(")"))); // source
					
					if (rt < 1 || rt > 30 || rs < 1 || rs > 30) {
						error = "Invalid Instr: register out of bounds";
						ok = false;
						break trying;
					}
					immediate = Integer.parseInt(t2.substring(0, t2.indexOf("(")));
					
					if (op.equals("LW") || op.equals("lw")) {
						opcode = 35;
                           rd = rs;
					} else
						opcode = 43; // SW is the only one left
					break;
					
                case 2:    // branch Instrs
                	isImmediateInstr = true;
					rs = Integer.parseInt(t1.substring(0));
					rt = Integer.parseInt(t2.substring(0));
					if (rs < 1 || rs > 30 || rt < 1 || rt > 30) {
						error = "Invalid Instr: register out of bounds";
						ok = false;
						break trying;
					}
					immediate = Integer.parseInt(t3);
					
					if(op.equals("BEQ") || op.equals("beq")) 
						opcode = 4;
					break;                                   
				default:
					ok = false;
					break trying;
			}
		}
		catch(NumberFormatException e) {
			ok = false;
		}
		catch(NoSuchElementException e2) {
			ok = false;
		}
		catch(StringIndexOutOfBoundsException e3) {
			ok = false;
		}
	}


	
    public boolean isImmediate() { 		// returns a boolean indicating whether the Instr is an I-type.
        return isImmediateInstr;
    }
   
    public String toString() {  	//returns a string representation of the Instr.
        return (InstrString+"\n");
    }

	public boolean valid() { 	 //returns a boolean indicating whether the Instr is valid.
		return ok;
	}
	
	public String theError() { 	//returns a string indicating the error generated from the entered Instr.
		return error;
	}
	
}