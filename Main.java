import java.util.*;
import static java.lang.System.out;

/**
 * MIPS machine
 * @author Anushervon Rakhmatov
 *
 */



public class Main {

	private static Scanner scan;


	public static void main(String[] args) {
		
	    Instr myInstr; // Instr object
		boolean flag = false;
		int[] registers = new int[30];
	    



		while(true) {
			
			out.println("Input an Instr with the following format(The registers should not contain symbol $):");  //asks user to input the instucitons
			out.println("opcode operand1 operand2 operand3");
			scan = new Scanner(System.in);
			String inst = scan.nextLine();
			out.println("inst= " + inst);
			myInstr = new Instr(inst);  // declare the Instructions
			


			if(flag == false) {  //Initializes registers just for the first time of use
				out.println("Input initial values of register set:"); 
				for(int i = 0; i < registers.length; i++) {
					out.printf("R%d = ", i+1);
					registers[i] = scan.nextInt();
				}
				flag = true;
			}
			

			/*
			 * Print out all the values in each stage.
			 */
			if(myInstr.isImmediate() == false) { // if R-type inst.
				// Semantics
				out.println("---Semantics---");
				if(myInstr.opcode == 32) // Add
					out.println("$" + myInstr.rd + " <- " + "$" + myInstr.rs + 
							" + " + "$" + myInstr.rt);
				else if(myInstr.opcode == 34) // Sub
					out.println("$" + myInstr.rd + " <- " + "$" + myInstr.rs + 
							" - " + "$" + myInstr.rt);
				out.println();
				
				// Fetch
				out.println("---IF Stage---");
				out.println("op=0 " + "rs=" + myInstr.rs + " rt=" + myInstr.rt +
						" rd=" + myInstr.rd + " shamt=0" + " funct=" + myInstr.opcode);
				out.println();
				
				// Decode
				out.println("---ID Stage---");
				out.println("Readregister1=" + myInstr.rs);
				out.println("Readregister2=" + myInstr.rt);
				out.println("Datapath=" + myInstr.rd);
				out.println("Read data1=" + registers[myInstr.rs - 1]);
				out.println("Read data2=" + registers[myInstr.rt - 1]);
				out.println();
				
				// Execute
				int zero = 0;
				if(myInstr.rs == myInstr.rt) zero = 1;
				int addResult = registers[myInstr.rs - 1] + registers[myInstr.rt - 1];
				int subResult = registers[myInstr.rs - 1] - registers[myInstr.rt - 1];
				int aluResult = 0;
				
				out.println("---EX Stage---");
				out.println("MUX at port1=" + myInstr.rd);
				out.println("RegDst=1");
				out.println("ALUop(ALU control)=" + myInstr.rs);
				out.println("funct(ALU control)=" + myInstr.opcode);
				out.println("ALU:Input1=" + myInstr.rs);
				out.println("ALU:Input2=" + myInstr.rt);
				
				if(myInstr.opcode == 32) {
					aluResult = addResult;
					out.println("ALU:ALUresult=" + addResult);
				}
				else if(myInstr.opcode == 34) {
					aluResult = subResult;
					out.println("ALU:ALUresult=" + subResult);
				}
				out.println("ALU:Zero=" + zero);
				out.println();
				
				// Memory
				out.println("---MEM Stage---");
				out.println("Read  address=" + aluResult);
				out.println("Write  address=" + aluResult);
				out.println("Write data=" + registers[myInstr.rt - 1]);
				out.println("MemWrite=0");
				out.println("MemRead=0");
				out.println();
				
				// Write Back
				out.println("---WB stage---");
				out.println("MUX at port0=" + aluResult);
				out.println("MemtoReg=0");	
			} else { // if I-type inst.
				// Semantics
				out.println("---Semantics---");
				if(myInstr.opcode == 35) // LW
					out.println("$" + myInstr.rs + " <- MEM[$" + myInstr.rt + "+" +
							myInstr.immediate + "]");
				else if(myInstr.opcode == 43) // SW
					out.println("MEM[$" + myInstr.rs + "+" + myInstr.immediate +
							"] <- $" + myInstr.rt);
				
				// Fetch
				out.println("---IF Buffer---");
				out.println("op=" + myInstr.opcode + " rs=" + myInstr.rs + 
						" rt=" + myInstr.rt + " offset=" + myInstr.immediate);
				out.println();
			}
			
		}

	}

}
