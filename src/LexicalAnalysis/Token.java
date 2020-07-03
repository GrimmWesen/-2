package LexicalAnalysis;

public class Token {
    final public String KeyWord[] = {"public","private","static","final","package",
    		                         "int","double","short","long","float","string","void",
    		                          "if","else","for","return","import",
    		                          "java","math","util","sql","exception"
    		                           };
    final private char danMu[]={'+','-','*','/','!','%','^','=','<','>'};//��Ŀ����
    final private String BioMu[]={"++","--","<=","!=","==",">=","+=","-=","*=","/="}; //˫Ŀ�����
    final private char delimiter[]={',','(',')','{','}',';','[',']','"'}; //���
    
    final private String Key_Des[] = {"public","private","static","final","package"};
    final private String Key_Type[] = {"int","double","short","long","float","string","void"};
    final private String Key_Lib[] = {"java","math","util","sql","exception"};
    
    public String[] getKeyWord(){
    	return KeyWord;
    }
    public char[] getdanMu(){
    	return danMu;
    }
    public char[] delimiter(){
    	return delimiter;
    }
  //�жϹؼ���
  	public boolean isKeyWord(String str){
  		for(int i=0;i<KeyWord.length;i++){
  			if(KeyWord[i].equals(str)){
  				return true;
  			}
  		}
  		return false;
  	}
  	//�ж���ĸ
  	public boolean isLetter(char ch){
  		if((ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z'))
              return true;
          else
              return false;
  	}
  	//�ж�����
  	public boolean isDig(char ch){
  		if(ch>='0'&&ch<='9'){
  			return true;
  		}
  		else{
  			return false;
  		}
  	}
  	public boolean isdanMU(char ch){
  		for(int i=0;i<danMu.length;i++){
  			if(danMu[i]==ch){
  				return true;
  			}
  		}
  		return false;
  	}
  	public boolean isBioMU(String arr){
  		for(int i=0;i<BioMu.length;i++){
  			if(BioMu[i].equals(arr)){
  				return true;
  			}
  		}
  		return false;
  	}
  	//�жϽ��
  	public boolean isdelimiter(char ch){
  		for(int i=0;i<delimiter.length;i++){
  			if(delimiter[i]==ch){
  				return true;
  			}
  		}
  		return false;
  	}
  	//�ж����η�
  	public boolean isKey_Des(String arr){
  		for(int i=0;i<Key_Des.length;i++){
  			if(Key_Des[i].equals(arr)){
  				return true;
  			}
  		}
  		return false;
  	}
  	
  	//�ж����ͱ�����
  	public boolean isKey_Type(String arr){
  		for(int i=0;i<Key_Type.length;i++){
  			if(Key_Type[i].equals(arr)){
  				return true;
  			}
  		}
  		return false;
  	}
  	
  //�жϿⱣ����
  	public boolean isKey_Lib(String arr){
  		for(int i=0;i<Key_Lib.length;i++){
  			if(Key_Lib[i].equals(arr)){
  				return true;
  			}
  		}
  		return false;
  	}
  	
  	
}
