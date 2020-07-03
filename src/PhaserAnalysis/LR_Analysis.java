package PhaserAnalysis;

import java.util.*;

public class LR_Analysis {
	
	private char start = 'S';
	private char[][] grammar;
	private int lines;
	private boolean[] isV = new boolean[128];//初始化ascii，统计字符
	private int numsVt=0;
	private int numsVn=0;
	private char[] Vt = new char[128];
	private char[] Vn = new char[128];
	
	private List[] firstVn = new ArrayList[128];
	private boolean[][] firstV = new boolean[128][128];
	private boolean[] isEmpty = new boolean[128];
	
	public LR_Analysis(int lines){
		grammar = new char[lines+1][];
		this.lines = lines;
	}
	public char[][] getGrammar(){
		return grammar;
	}
	public boolean isVt(char ch){
		if(ch>='a'&&ch<='z'){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean isVn(char ch){
		if(ch>='A'&&ch<='Z'){
			return true;
		}
		else{
			return true;
		}
	}
	//判断是否在文法
	public boolean inVn(char ch){
		boolean res = false;
		for(int i=0;i<Vn.length;i++){
			if(Vn[i]==ch){
				res=true;
			}
		}
		return res;
	}
	
	public void setGrammar(String[] gr){
		int lines = gr.length;
		for(int i=0;i<lines;i++){
			int nums= gr[i].length();
			grammar[i] = new char[nums-2];
			grammar[i][0]=gr[i].charAt(0);
			isV[(int)grammar[i][0]]=true;
			for(int j=3;j<nums;j++){//从->后面开始
				char ch = gr[i].charAt(j);
				grammar[i][j-2]=ch;
				isV[(int)ch]=true;
			}
		}
		//扩展文法，未将扩展的S'置true
		grammar[lines] = new char[2];
		grammar[lines][0]=start;
		grammar[lines][1]=grammar[0][0];
		
		for(int i=0;i<128;i++){
			if(i>=65 && i<=90 && isV[i]==true){
				Vn[numsVn++]=(char)i;
			}
			else{
				if(isV[i]==true){
					Vt[numsVt++]=(char)i;
				}
			}
		}
		
//		System.out.println(numsVn);
	}
	
	/**
	 * @return
	 * 判断一行产生式中是否有终结符，以便判断空
	 */
	public boolean hasVtInLine(int line){
		boolean res = false;
		for(int i=0;i<grammar[line].length;i++){
			char ch = grammar[line][i];
			if(isVt(ch)){
				res = true;
			}
		}
		return res;
	}
	//判断能否推出空
	public boolean hasEmpty(char ch){
		boolean res = false;
		for(int i=0;i<lines;i++){
			boolean res1 = true;
			if(grammar[i][0]==ch){
				char ch1 = grammar[i][1];
				if(hasVtInLine(i)){
					res1= false;
				}
				else if(ch1=='@'){
					res1= true;
				}
				else if(!hasVtInLine(i)){
					int k=1;
					while(k<grammar[i].length){
						res1= hasEmpty(grammar[i][k++])&&res1;
					}
					
				}
				res = res||res1;
			}
		}
		return res;
	}
	public void getFirst(){
		char[] resFirst=null;
		for(int i=0;i<numsVn;i++){
			
		}
	}

}
