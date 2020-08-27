import java.io.IOException;
import java.io.BufferedWriter;
import java.util.ArrayList;

public class bnet {
	static ArrayList<String> array1 = new ArrayList<String>();
	static ArrayList<String> array2 = new ArrayList<String>();

	static double burglary=0.001;
	static double earthquake=0.002;
	
	static double alarm[]= {0.95,0.94,0.29,0.001};
	static double JohnCalls[]= {0.90,0.05};
	static double MaryCalls[]= {0.70,0.01};

	public static void main(String[] args) {				
		int countB=0, countE=0, countA=0, countJC=0, countMC=0;
		if (args.length <1 || args.length > 6) { 
			System.exit(0);
		}

		int index =-1;
		for(int i=0;i<args.length;i++) {
			if(args[i].equals("given")){
				index=0;
				continue;
			}
			if(index==-1) {
				array1.add(args[i]);
			} else{
				array2.add(args[i]);
			}
		}	
		if (array1.size() <1 || array1.size() > 6) { 
			System.exit(0);
		}
		if(index==0) {
			if (array2.size() <1 || array2.size() > 4) {
				System.exit(0);
			}		
		}

		System.out.println(array1 + "given" + array2);
		array1.addAll(array2); 
		for(int i=0;i< array1.size();i++) {  			
			if (!array1.contains("Et")&&!array1.contains("Ef")) {
				array1.add("Et");
				array1.add("Ef");
				countE=1;
			}
			if (!array1.contains("Bt")&&!array1.contains("Bf")) {
				array1.add("Bt");
				array1.add("Bf");
				countB=1;
			}
			if (!array1.contains("At")&&!array1.contains("Af")) {
				array1.add("At");
				array1.add("Af");
				countA=1;
			}
			if (!array1.contains("Mt")&&!array1.contains("Mf")) {
				array1.add("Mt");
				array1.add("Mf");
				countMC=1;
			}
			if (!(array1.contains("Jt"))&&!array1.contains("Jf")) {
				array1.add("Jt");
				array1.add("Jf");
				countJC=1;
			}
		}

		double numeratorVal = compute(countB, countE, countA, countJC, countMC, array1);

		if(array2.size()==0) {
			System.out.println("Probability: "+ numeratorVal);
		}

		countB = countE = countA = countJC = countMC=0;

		for(int j=0;j< array2.size();j++) {		
			if (!array2.contains("Et") && !array2.contains("Ef")) {
				array2.add("Et");
				array2.add("Ef");
				countE=1;
			}
			if (!array2.contains("Bt") && !array2.contains("Bf")) {
				array2.add("Bt");
				array2.add("Bf");
				countB=1;
			}
			if (!array2.contains("Jt") && !array2.contains("Jf")) {
				array2.add("Jt");
				array2.add("Jf");
				countJC=1;
			}
			if (!array2.contains("Mt") && !array2.contains("Mf")) {
				array2.add("Mt");
				array2.add("Mf");
				countMC=1;
			}
			if (!array2.contains("At") && !array2.contains("Af")) {
				array2.add("At");
				array2.add("Af");
				countA=1;
			}
		}

		double denominatorVal = compute(countB, countE, countA, countJC, countMC, array2);

		if(array2.size()>0) {
			System.out.println("Probability: " + numeratorVal / denominatorVal);
		}		
	}
	
	public static double computeProbability(boolean burglaryBool, boolean earthquakeBool, boolean alarmBool, boolean JohnCallsBool, boolean MaryCallsBool) {
		double valB = 0.0;

		if(burglaryBool) {
			valB = burglary;
		} else {
			valB = 1 - burglary;
		}

		double valE;
		if(earthquakeBool) {
			valE=  earthquake;
		}
		else {
			valE = 1-earthquake;
		}

		double valA = 0.0;

		if(alarmBool) {
			if(burglaryBool==true && earthquakeBool==true)
				valA = alarm[0];
			else if(burglaryBool==true && earthquakeBool==false)
				valA = alarm[1];
			else if(burglaryBool==false && earthquakeBool==true)
				valA = alarm[2];
			else if(burglaryBool==false && earthquakeBool==false)
				valA = alarm[3];
		} else {
			if(burglaryBool==true && earthquakeBool==true )
				valA = 1-alarm[0];
			else if(burglaryBool==true && earthquakeBool==false )
				valA = 1-alarm[1];
			else if(burglaryBool==false && earthquakeBool==true )
				valA = 1-alarm[2];
			else if(burglaryBool==false && earthquakeBool==false )
				valA = 1-alarm[3];
		}

		double valMC = 0.0;
		if(MaryCallsBool) {
			if(alarmBool == true)
				valMC = MaryCalls[0];
			else if(alarmBool==false )
				valMC = MaryCalls[1];
		} else {
			if(alarmBool == true)
				valMC =1-MaryCalls[0];
			else if(alarmBool==false)
				valMC =1-MaryCalls[1];
		}
		double valJC = 0.0;
		if(JohnCallsBool) {
			if(alarmBool == true)
				valJC = JohnCalls[0];
			else if(alarmBool==false )
				valJC = JohnCalls[1];
		} else {
			if(alarmBool == true)
				valJC =1-JohnCalls[0];
			else if(alarmBool==false )
				valJC =1-JohnCalls[1];
		}
		return (valB) * (valE) * (valA) * (valJC) * (valMC); 
	}

	public static double compute(int bc, int ec, int ac, int jc, int mc, ArrayList<String> arrayString) {				
		Boolean burglary_bool=false, earthquake_bool=false, alarm_bool=false, JohnCalls_bool=false, MaryCalls_bool=false;

		if(ac==0) {
			if(arrayString.contains("At")) {
				alarm_bool=true;
			}
			else alarm_bool=false;
		}
		if(ec==0) {
			if(arrayString.contains("Et")) {
				earthquake_bool=true;
			}
			else earthquake_bool=false;
		}
		if(mc==0) {
			if(arrayString.contains("Mt")) {
				MaryCalls_bool=true;
			}
			else MaryCalls_bool=false;
		}
		if(bc==0) {
			if(arrayString.contains("Bt")) {
				burglary_bool=true;
			}
			else burglary_bool=false;
		}
		if(jc==0) {
			if(arrayString.contains("Jt")) {
				JohnCalls_bool=true;
			}
			else JohnCalls_bool=false;
		}

		double probValue = 0.0;

		for(int iter1=0; iter1<=bc; iter1++) {

			for(int iter2=0; iter2<=ec; iter2++) {

				for(int iter3=0; iter3<=ac; iter3++) {

					for(int iter4=0; iter4<=jc; iter4++) {

						for(int iter5=0; iter5<=mc; iter5++) {
							probValue += computeProbability(burglary_bool, earthquake_bool, alarm_bool, JohnCalls_bool, MaryCalls_bool);

							if(mc==1 && MaryCalls_bool==false) {
								MaryCalls_bool=true;
							} else if(mc==1 && MaryCalls_bool==true) {
								MaryCalls_bool=false;
							}
						}

						if(jc==1 && JohnCalls_bool==false) {
							JohnCalls_bool=true;
						} else if(jc==1 && JohnCalls_bool==true) {
							JohnCalls_bool=false;
						}
					} // in the iteration 4

					if(ac==1 && alarm_bool==false){
						alarm_bool=true;	
					} else if(ac==1 && alarm_bool==true) {
						alarm_bool=false;
					}
				} // in the iteration 3
				if(ec==1 && earthquake_bool==false){ 
					earthquake_bool=true;
				} else if(ec==1 && earthquake_bool==true){ 
					earthquake_bool=false;
				}
			}// in the iteration 2
			if(bc==1 && burglary_bool==false){
				 burglary_bool=true;
			} else if(bc==1 && burglary_bool==true) {
				burglary_bool=false;
			}
		}// in the iteration 1

		return probValue;
	}
}