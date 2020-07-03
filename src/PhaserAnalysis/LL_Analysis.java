package PhaserAnalysis;

import java.io.*;
import java.util.*;

import LexicalAnalysis.LexMain;
public class LL_Analysis {
	
	private char start = 'S';
	private char[][] grammar;
	public int lines;
	private boolean[] isV = new boolean[128];//初始化ascii，统计字符
	public int numsVt=0;
	public int numsVn=0;
	public char[] Vt = new char[128];
	public char[] Vn = new char[128];
	public First[] first = new First[128];//Z=90
	public Follow[] follow = new Follow[128];
	public Select[] select;
	public int [][] PredictTable;
	
	public String testInputs;
	public boolean[] isEmptyLines;//判断每一行是否推出空，以备select集用
	
	public LL_Analysis(int lines,String str){
		grammar = new char[lines][];
		this.lines = lines;
		this.testInputs = str;
		
		isEmptyLines = new boolean[lines];//初始化判断每一行能否推出空
		select = new Select[lines];
	
		for(int i =0;i<128;i++){
			first[i] = new First();//初始化first
			follow[i] = new Follow();//初始化follow
		}
		for(int i = 0;i<lines;i++){
			select[i] = new Select();
		}
	}
	public char[][] getGrammar(){
		return grammar;
	}
	public boolean isVt(char ch){
		boolean r1 = ch>='A'&&ch<='Z';
		boolean r2 = ch>='0'&&ch<='9';
		boolean r = r1||r2;
		if(!r && ch!='@'){
			return true;
		}
		else{
			return false;
		}
	}
//	public boolean isVn(char ch){
//		if(ch>='A'&&ch<='Z'){
//			return true;
//		}
//		else{
//			return true;
//		}
//	}
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
		
		for(int i=0;i<128;i++){
			if(i>=65 && i<=90 && isV[i]==true){
				Vn[numsVn++]=(char)i;
			}
			else if(i>=48 && i<=57 && isV[i]==true){
				Vn[numsVn++]=(char)i;
			}
			else if(i!=64){//@空
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
				break;
			}
		}
		return res;
	}
	//判断终结符能否推出空
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
				if(res==true){
					return res;
				}
			}
		}
		return res;
	}
	
	/**
	 * 设置每一行能否推出空 '@'的真假
	 */
	public void setIsEmptyLines(){
		for(int i=0;i<lines;i++){
			char ch1 = grammar[i][1];
			if(ch1=='@'){
				isEmptyLines[i]= true;
			}
			if(hasVtInLine(i)){
				isEmptyLines[i]= false;
			}
			if(!hasVtInLine(i)&&ch1!='@'){
				boolean res = true;
				for(int j=1;j<grammar[i].length;j++){
					res = res&&hasEmpty(grammar[i][j]);
				}
				isEmptyLines[i]=res;
			}
			
		}
	}
	public void getFirst(char v){
		for(int i=0;i<lines;i++){
			if(grammar[i][0]==v){
				char V1 = v;
				if(isVt(grammar[i][1])||grammar[i][1]=='@'){//产生式右部是终结符
					first[(int)V1].list.add(grammar[i][1]);
				}
				else{
					int point = 1;
					while(point<grammar[i].length){
						getFirst(grammar[i][point]);
						Set<Character> temp = new HashSet<Character>();//浅拷贝
						temp.addAll(first[(int)grammar[i][point]].list);
						if(temp.contains('@')){
							temp.remove('@');
						}
						first[(int)V1].list.addAll(temp);
						if(!hasEmpty(grammar[i][point])){
							break;
						}
						else{
							point++;
							if(point==grammar[i].length){
								first[(int)V1].list.add('@');
							}
						}
					}
				}
			}
			if(i==lines-1){
				return;
			}
		}
	}
	
	/**
	 * 求所有文法中的非终结符的first，因为getFirst对有些Vn无法递归到
	 * 以及终结符的first
	 */
	public void getAllFirst(){
		for(int i=0;i<numsVt;i++){
			first[(int)Vt[i]].list.add(Vt[i]);
		}//终结符是自身
		for(int i=0;i<numsVn;i++){
			getFirst(Vn[i]);
		}
	}
	
	/**
	 * @param ch
	 * @param i
	 * @return
	 * 寻找每行右部是否有所需的Vn，无则返回-1
	 */
	public int findVn(char ch,int line){
		int res =-1;
		for(int i=1;i<grammar[line].length;i++){
			if(grammar[line][i]==ch){
				res = i;
				break;
			}
		}
		return res;
	}
	
	
	/**
	 * @param line
	 * @param indexplus
	 * @return
	 * 判断S->aAβ，β能否推出空
	 */
	public boolean isToEmpty(int line,int indexplus){
		boolean res = true;
		for(int i = indexplus;i<grammar[line].length;i++){
			if(isVt(grammar[line][i])){//如果遇到终结符，直接返回false;
				res = false;
				break;
			}
			res = res && hasEmpty(grammar[line][i]);
		}
		return res;
	}
	
	/**
	 * @param line
	 * @param indexplus
	 * @return
	 * 求first(β) 混合型,无空,因为follow 中 first(β)-'@';
	 */
	public Set<Character> getMixFirst(int line,int indexplus){
		Set<Character> res = new HashSet<Character>();
		for(int i = indexplus;i<grammar[line].length;i++){
			char ch = grammar[line][i];
			if(isVt(ch)){
				res.add(ch);
				break;
			}
			if(!hasEmpty(ch)){
				Set<Character> temp = new HashSet<Character>();//浅拷贝
				temp.addAll(first[(int)ch].list);
				res.addAll(temp);
				break;
			}
			if(hasEmpty(ch)){
				Set<Character> temp = new HashSet<Character>();//浅拷贝
				temp.addAll(first[(int)ch].list);
				temp.remove('@');
				res.addAll(temp);
			}
			
		}
		return res;
	}
	

	public void getFollow(){
		follow[(int)start].follow.add('#');
		boolean changed = false;
		for(int i=0;i<numsVn;i++){
			int num1 = follow[(int)Vn[i]].follow.size();
			for(int j=0;j<lines;j++){
				int index = findVn(Vn[i],j);
				if(index!=-1){
					if(index == grammar[j].length-1){//Vn在最后一位
						follow[(int)Vn[i]].follow.addAll(follow[(int)grammar[j][0]].follow);//左部follw加入
					}
					else{
						Set<Character> temp = new HashSet<Character>();//浅拷贝
						temp.addAll(getMixFirst(j,index+1));
						follow[(int)Vn[i]].follow.addAll(temp);
						if(isToEmpty(j,index+1)){
							follow[(int)Vn[i]].follow.addAll(follow[(int)grammar[j][0]].follow);
						}
					}
				}
			}
			int num2 = follow[(int)Vn[i]].follow.size();
			if(num2!=num1){
				changed = changed || true;
			}
		}
		if(changed){
			 getFollow();
		}
		else{
			return;
		}
	}
	
	public void getSelect(){
		for(int i=0;i<lines;i++){
			if(!isEmptyLines[i]){//不能推空first(β)
				Set<Character> temp = new HashSet<Character>();//浅拷贝 first(β)
				temp.addAll(getMixFirst(i,1));
				select[i].select.addAll(temp);
			}
			else{//能推空
				//浅拷贝 first(β)-'@'
				Set<Character> temp1 = new HashSet<Character>();//浅拷贝
				temp1.addAll(getMixFirst(i,1));
				if(temp1.contains('@')){
					temp1.remove('@');
				}
				select[i].select.addAll(temp1);
				
				//浅拷贝follow(A)
				Set<Character> temp2 = new HashSet<Character>();//浅拷贝
				temp2.addAll(follow[(int)grammar[i][0]].follow);
				select[i].select.addAll(temp2);
			}
		}
	}
	
	public int VnIndex(char ch){
		int res = -1;
		for(int i=0;i<numsVn;i++){
			if(Vn[i]==ch){
				res = i;
				break;
			}
		}
		return res;
	}
	
	public int VtIndex(char ch){
		int res = -1;
		for(int i=0;i<numsVt;i++){
			if(ch=='#'){ //文法中无'#'
				res = numsVt;
				break;
			}
			if(Vt[i]==ch){
				res = i;
				break;
			}
		}
		return res;
	}
	
	public void setTable(){
		PredictTable = new int[numsVn][numsVt+1];//Vt个数不包含'#'
		for(int i=0;i<numsVn;i++){
			for(int j=0;j<numsVt+1;j++){
				PredictTable[i][j]=-1;
			}
		}
		for(int i=0;i<lines;i++){
			Set<Character> temp = select[i].select;
			for(char ch:temp){
				char Vn = grammar[i][0];
				int Vnindex = VnIndex(Vn);
				int Vtindex = VtIndex(ch);
				PredictTable[Vnindex][Vtindex] = i;
			}
		}
	}
	
	public void StackAnalysis() throws Exception{
		boolean res = true;
		Stack <Character> fenxi = new Stack <Character>();
		Stack <Character> shuru = new Stack <Character>();
		
		fenxi.push('#');
		fenxi.push(start);
		shuru.push('#');
		for(int i=testInputs.length()-1;i>=0;i--){
			shuru.push(testInputs.charAt(i));
		}
		
		int cnt=0;
		while(!fenxi.empty()){
			if(fenxi.peek()==shuru.peek()){//栈顶看而不取
				System.out.print(++cnt+"\t\t\t");
				for (Character x : fenxi){ 
	                System.out.print(x); 
	            }
				System.out.print("\t\t\t");
				for (Character x : shuru){ 
	                System.out.print(x); 
	            }
				System.out.print("\t\t\t");
				System.out.println("匹配");
				OutRes(cnt,fenxi,shuru,"匹配");//打印txt
				fenxi.pop();
				shuru.pop();
			}
			else{
				char Vn = fenxi.peek();//只看不移除 栈顶
				char Vt = shuru.peek();
				int Vnindex = VnIndex(Vn);
				int Vtindex = VtIndex(Vt);
				int line = -1;
				try{
					line = PredictTable[Vnindex][Vtindex];//如果输入的终结符非终结符不在文法中，标号为负抛异常
				}catch(Exception e){
					System.out.print(++cnt+"\t\t\t");
					for (Character x : fenxi){ 
		                System.out.print(x); 
		            }
					System.out.print("\t\t\t");
					for (Character x : shuru){ 
		                System.out.print(x); 
		            }
					System.out.print("\t\t\t");
					System.out.println("报错,无法找到该符号");
					
					OutRes(cnt,fenxi,shuru,"报错,无法找到该符号");//打印txt
					
					res = false;
					System.out.println("NO");
					return;
				}
				if(line!=-1){//未抛异常 有产生式
					System.out.print(++cnt+"\t\t\t");
					for (Character x : fenxi){ 
		                System.out.print(x); 
		            }
					System.out.print("\t\t\t");
					for (Character x : shuru){ 
		                System.out.print(x); 
		            }
					System.out.print("\t\t\t");
					System.out.println(line);
					
					OutRes(cnt,fenxi,shuru,line+"");
					fenxi.pop();
					for(int i=grammar[line].length-1;i>=1;i--){
						if(grammar[line][i]!='@'){
							fenxi.push(grammar[line][i]);
						}
					}
				}
				else{
					res = false;
					System.out.print(++cnt+"\t\t\t");
					for (Character x : fenxi){ 
		                System.out.print(x); 
		            }
					System.out.print("\t\t\t");
					for (Character x : shuru){ 
		                System.out.print(x); 
		            }
					System.out.print("\t\t\t");
					System.out.println("报错,无法找到该产生式");
					
					OutRes(cnt,fenxi,shuru,"报错,无法找到该产生式");
					res = false;
					System.out.println("NO");
					return;
				}
			}
		}
		if(res&&fenxi.isEmpty()){
			System.out.println("YES");
		}
		else{
			System.out.println("NO");
		}
	}
	public void OutRes(int cnt,Stack <Character> shuru,Stack <Character> fenxi,String str){
		FileWriter fw = null;
		try {
			//如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f=new File("resource/PhaserOut.txt");
			fw = new FileWriter(f, true);
			} catch (IOException e) {
			e.printStackTrace();
			}
			PrintWriter pw = new PrintWriter(fw);
			
			pw.print(cnt+"\t\t\t");
			for (Character x : fenxi){ 
				pw.print(x); 
            }
			pw.print("\t\t\t");
			for (Character x : shuru){ 
				pw.print(x); 
            }
			pw.print("\t\t\t");
			pw.println(str);
			
			pw.flush();
			try {
			fw.flush();
			pw.close();
			fw.close();
			} catch (IOException e) {
			e.printStackTrace();
			}

	}
}

