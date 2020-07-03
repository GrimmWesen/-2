package LexicalAnalysis;

public class NFA {
	final private int ASSICnums = 128;
	public char[] set;
	public int len = 0;
	public NFA(){
		set = new char[ASSICnums];
		for(int i=0;i<ASSICnums;i++){
			set[i]='@';
		}
	}
}