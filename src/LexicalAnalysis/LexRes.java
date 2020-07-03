package LexicalAnalysis;

public class LexRes {
	private int kind;
	private String arr;
	public int len =0;
	
	public LexRes(int kind,String arr){
		this.kind = kind;
		this.arr = arr;
	}
	public LexRes(){
		
	}
	public int getKind(){
		return kind;
	}
	public String getArr(){
		return arr;
	}
}
