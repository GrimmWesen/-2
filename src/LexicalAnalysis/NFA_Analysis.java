package LexicalAnalysis;

import java.util.*;

public class NFA_Analysis {
	final private int ASSICnums = 128;
	private char start;
	private char [] NFA_state;
	private int  NFA_state_len=0;
	private char  [] NFA_terminal;
	private int  NFA_terminal_len=0;
	private NFA[][] NFA_move;
	
	private List<LexRes> list = new ArrayList<LexRes>();
	public StringBuffer resList = new StringBuffer();
	
	
	public String getResList(){
		String res = resList.toString();
		return res;
	}
	public NFA_Analysis(){//���캯����ʼ��
		NFA_move = new NFA[ASSICnums][ASSICnums];
		NFA_state = new char[ASSICnums];
		NFA_terminal = new char[ASSICnums];
		for(int i = 0;i<ASSICnums;i++){
			for(int j=0;j<ASSICnums;j++){
				NFA_move[i][j]=new NFA();
			}
		}
	}
	public boolean isState(char ch){
		for(int i=0;i<NFA_state.length;i++){
			if(ch==NFA_state[i]){
				return true;
			}
		}
		return false;
	}
	public boolean isTerminal(char ch){
		for(int i=0;i<NFA_terminal.length;i++){
			if(ch==NFA_terminal[i]){
				return true;
			}
		}
		return false;
	}
	//�ж϶���������ս�����Ƿ�����Ӧ�ط��ս��
	public boolean hasState(int swag,int in,char ch){
		boolean res = false;
		for(int i =0;i<NFA_move[swag][in].len;i++){
			if(NFA_move[swag][in].set[i]==ch){
				res = true;
			}
		}
		return res;
	}
	//�ж�һ�������ܷ�NFAʶ��ĵڶ����ж�������Ӧ���ڵݹ�
	public boolean is2ndCondition(int swag,int in,char ch,String arr,int i){
		boolean res= false;
		if(hasState(swag,in,ch)){
			if(i==arr.length()-1){
				res = true;
			}
			else{
				res = false;
			}
		}
		else{
			res = false;
		}
		return res;
	}
	//Ϊ������list�������󣬷���LexRes��Ķ���
	public LexRes addLexRes(int kind,String arr){
		LexRes lr = new LexRes(kind,arr);
		return lr;
	}
	
	public List getList(){
		return list;
	}
//	public void createNFA(String[] grammar){
//		int lines = grammar.length;
//		boolean isStart = true;
//		for(int i=0;i<lines;i++){
//			int nums = grammar[i].length();
//			char char0 = grammar[i].charAt(0);
//			char char3 = grammar[i].charAt(3);
//			char charTail =  grammar[i].charAt(nums-1);
//			if(isStart){
//				start = char0;
//				isStart = false;
//			}
//			if(isState(char0)==false){
//				NFA_state[NFA_state_len++]=char0;
//			}
//			if(isTerminal(char3)==false){//�������
//				NFA_terminal[NFA_terminal_len++]=char3;
//			}
//			if(nums==5){//A->aA
//				
//				NFA_move[char0][char3].set[NFA_move[char0][char3].len++]=charTail;//f(A,a)=B,char3��¼B
//			}
//			if(nums==4){
//				NFA_move[char0][char3].set[NFA_move[char0][char3].len++]='Z';//A->a
//			}
//		}
//	}
	public void createNFA(String[] grammar){//�Ľ���ʹ�������ķ����Ӽ�
		int lines = grammar.length;
		boolean isStart = true;
		for(int i=0;i<lines;i++){
			int nums = grammar[i].length();
			char char0 = grammar[i].charAt(0);
			char char3 = grammar[i].charAt(3);
			char charTail =  grammar[i].charAt(nums-1);
			if(isStart){
				start = char0;
				isStart = false;
			}
			if(isState(char0)==false){
				NFA_state[NFA_state_len++]=char0;
			}
			if(isTerminal(char3)==false){//�������
				NFA_terminal[NFA_terminal_len++]=char3;
			}
			if(nums==5){//A->aA
				setInput(char0,char3, charTail);
//				NFA_move[char0][char3].set[NFA_move[char0][char3].len++]=charTail;//f(A,a)=B,char3��¼B
			}
			if(nums==4){//A->a
				setInput(char0,char3, 'Z');
//				NFA_move[char0][char3].set[NFA_move[char0][char3].len++]='Z';//A->a
			}
		}
	}
	
	//�����ķ���Լ����ĸ�������ַ�
	public void setInput(char char0,char char3,char charTail){
		if(char3=='l'){//Լ��l�ַ�����Сд��ĸ
			for(int i=(int)'a';i<=(int)'z';i++){
				NFA_move[char0][i].set[NFA_move[char0][i].len++]=charTail;
			}
		}
		if(char3=='d'){//Լ��d�ַ���������
			for(int i=(int)'0';i<=(int)'9';i++){
				NFA_move[char0][i].set[NFA_move[char0][i].len++]=charTail;
			}
		}
		if(char3=='f'){//ָ������
			char[] fuhao = {'+','-'};
			for(int i=0;i<fuhao.length;i++){
				NFA_move[char0][fuhao[i]].set[NFA_move[char0][fuhao[i]].len++]=charTail;
			}
		}
		if(char3=='e'||char3=='.'||char3=='"'){
			NFA_move[char0][char3].set[NFA_move[char0][char3].len++]=charTail;
		}
//		if(char3=='e'){
//			NFA_move[char0][char3].set[NFA_move[char0][char3].len++]=charTail;
//		}
//		if(char3=='.'){
//			NFA_move[char0][char3].set[NFA_move[char0][char3].len++]=charTail;
//		}
//		if(char3=='"'){
//			NFA_move[char0][char3].set[NFA_move[char0][char3].len++]=charTail;
//		}
		
	}
	
	public  void showNFA(){
		for(int i = 0;i<ASSICnums;i++){
			for(int j=0;j<ASSICnums;j++){
				for(int k=0;k<ASSICnums;k++){
					if(NFA_move[i][j].set[k]!='@'){
						System.out.println((char)i+" "+(char)j+" "+NFA_move[i][j].set[k]);
					}
				}
			}
		}
	}
	
	//��NFA�����ַ����ܷ�ʶ��
	public boolean NFAgo(char start,String arr,int i){
		int swag=(int)start;
		if(i>=arr.length()){
			return false;
		}
		char in = arr.charAt(i);
		int cnt=NFA_move[swag][in].len;

		if(hasState(swag,in,'Z')&&i==arr.length()-1){
			return true;
		}
		else if(!is2ndCondition(swag,in,'Z', arr, i)&&cnt!=0){
			
			int j=0;
			boolean res = false;
			while(j<cnt){
				//boolean temp = NFAgo(NFA_move[swag][in].set[i],arr);
				res= NFAgo(NFA_move[swag][in].set[j++],arr,i+1)||res;
			}
			return res;
		}
		else {
			return false;
		}

	}
	
	public boolean isNFA(String arr){
		return NFAgo('S',arr,0);
	}
	
	//��������
	public void analysis(char[] txtDone){
		Token token = new Token();
		char ch;
		int kind = -1;
		String word=null;
		for(int i=0;i<txtDone.length;i++){
			String arr = "";
			ch = txtDone[i];
			if(ch == ' '||ch == '\t'||ch == '\n'||ch == '\r'){}
			else if(token.isLetter(ch)){
				while(token.isLetter(ch)||token.isDig(ch)){
					arr+=ch;
					ch=txtDone[++i];
				}
				i--;
				if(token.isKeyWord(arr)){
					if(token.isKey_Des(arr)){
						kind = 11;
					}
					else if(token.isKey_Type(arr)){
						kind = 12;
					}
					else if(token.isKey_Lib(arr)){
						kind = 13;
					}
					else{
						kind = 14;
					}
					word=arr;
					list.add(addLexRes(kind,word));
					System.out.println(arr+" "+"�ؼ���"+kind);
				}
				else{
					if(isNFA(arr)){
						kind = 2;
						word=arr;
						list.add(addLexRes(kind,word));
						System.out.println(arr+" "+"��ʶ��"+kind);
					}
					else{
						kind=-1;
						word=arr;
						list.add(addLexRes(kind,word));
						System.out.println(arr+" "+"�޷�ʶ����ĸ����"+kind);
					}
				}
			}
			else if(token.isDig(ch)){
				while(token.isDig(ch)||ch=='e'||ch=='+'||ch=='-'||ch=='.'){
					arr+=ch;
					ch=txtDone[++i];
				}
				i--;
				if(isNFA(arr)){
					kind = 31;
					word=arr;
					list.add(addLexRes(kind,word));
					System.out.println(arr+" "+"��������"+kind);
				}
				else{
					kind=-1;
					word=arr;
					list.add(addLexRes(kind,word));
					System.out.println(arr+" "+"�޷�ʶ�����ֿ�ͷ"+kind);
				}
			}
			else if(token.isdanMU(ch)){
				arr+=ch;
				arr+=txtDone[++i];
				if(token.isBioMU(arr)){
					if(arr.equals("++")||arr.equals("--")){
						kind = 43;
					}
					else if(arr.equals("<=")||arr.equals(">=")||arr.equals("!=")||arr.equals("==")){//����˫Ŀ
						kind = 42;
					}
					else {//�߼�˫Ŀ<=
						kind = 44;
					}
					word=arr;
					list.add(addLexRes(kind,word));
					System.out.println(arr+" "+"˫Ŀ�����"+kind);
				}
				else{
					i--;
					word=ch+"";
					if(ch!='='&&ch!='<'&&ch!='>'){
						kind = 41;
					}
					else if(ch=='='){
						//��ֵ���ţ�ͬ<=һ��
						kind = 44;
					}
					else{
						kind = 42;
					}
					list.add(addLexRes(kind,word));
					System.out.println(ch+" "+"��Ŀ�����"+kind);
				}
			}
			else if(token.isdelimiter(ch)){
				if(ch=='"'){//�ж��Ƿ����ַ�������
					int cnt=0;//��¼�ַ������ַ�����
					arr+=ch;
					ch=txtDone[++i];
					while(ch!='"'){
						arr+=ch;
						if(i==txtDone.length-1){
							break;
						}
						ch=txtDone[++i];
						cnt++;
					}
					if(i!=txtDone.length-1){
						arr+=ch;
					}
					if(isNFA(arr)){
						kind = 32;
//						i--;
						word=arr;
						list.add(addLexRes(kind,word));
						System.out.println(arr+" "+"����"+kind);
					}
					else{
						kind = 5;
						i=i-cnt-1;
						list.add(addLexRes(kind,word));
						System.out.println(txtDone[i]+" "+"�ֽ��"+kind);
					}
				}
				else{
					kind = 5;
					word=ch+"";
					list.add(addLexRes(kind,word));
					System.out.println(ch+" "+"�ֽ��"+kind);
				}
			}
		}
	}
	
	/**
	 * token tablesת��Ϊ�﷨�����Ĵ�
	 */
	public void forPhaser(){
		Token t = new Token();
		LexRes lr = null;
		for(int i=0;i<list.size();i++){
			lr = list.get(i);
			String temp = lr.getArr();
			if(lr.getKind()==11){//���η�
				resList.append('d');
			}
			else if(lr.getKind()==12){//����֮��
				resList.append('c');
			}
			else if(lr.getKind()==13){//����
				resList.append('b');
			}
			else if(lr.getKind()==14){//����ָ��
				if(temp.equals("if")){
					resList.append('k');
				}
				if(temp.equals("else")){
					resList.append('m');
				}
				if(temp.equals("return")){
					resList.append('n');
				}
				if(temp.equals("for")){
					resList.append('g');
				}
				if(temp.equals("import")){
					resList.append('a');
				}
			}
			else if(lr.getKind()==2){//��ʶ��
				resList.append('e');
			}
			else if(lr.getKind()==31){
				resList.append('f');
			}
			else if(lr.getKind()==32){
				resList.append('s');
			}
			else if(lr.getKind()==41){
				resList.append('i');
			}
			else if(lr.getKind()==42){
				resList.append('h');
			}
			else if(lr.getKind()==43){
				resList.append('j');
			}
			else if(lr.getKind()==44){
				resList.append('p');
			}
			else if(lr.getKind()==5){//���������
				resList.append(lr.getArr());
			}
		}
	}
	
	
}
