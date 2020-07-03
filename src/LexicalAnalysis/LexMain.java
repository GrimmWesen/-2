package LexicalAnalysis;


import java.io.*;
import java.util.*;

public class LexMain {
	public static String outData = null;
	
	public static String[] readToString(String filePath)
    {
        File file = new File(filePath);
        Long filelength = file.length(); // 获取文件长度
        byte[] filecontent = new byte[filelength.intValue()];
        try
        {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        String[] fileContentArr = new String(filecontent).split("\r\n");
        
        return fileContentArr;// 返回文件内容,默认编码
    }
	public static void OutRes(List list){
		File file = new File("resource/LesOut.txt");
        FileOutputStream fileOut = null;
        BufferedOutputStream writer = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
		try{
			fileOut = new FileOutputStream(file);
            writer = new BufferedOutputStream(fileOut);
            outputStreamWriter = new OutputStreamWriter(writer, "utf-8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
			for(int i=0;i<list.size();i++){
				LexRes lr = (LexRes)list.get(i);
				bufferedWriter.write("<");
				bufferedWriter.write(lr.getKind()+"");
				bufferedWriter.write(", ");
				bufferedWriter.write(lr.getArr());
				bufferedWriter.write(">");
				bufferedWriter.newLine();
			}
			 bufferedWriter.close();
			
		}catch (IOException e)
        {
            e.printStackTrace();
        }
	}
	public static void OutRes(String str){
		File file = new File("resource/midd.txt");
        FileOutputStream fileOut = null;
        BufferedOutputStream writer = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
		try{
			fileOut = new FileOutputStream(file);
            writer = new BufferedOutputStream(fileOut);
            outputStreamWriter = new OutputStreamWriter(writer, "utf-8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
			for(int i=0;i<str.length();i++){
				bufferedWriter.write(str.charAt(i));
			}
			 bufferedWriter.close();
			
		}catch (IOException e)
        {
            e.printStackTrace();
        }
	}
	public static void main(String args[]){
//		String addr = "D:\\text.txt";
//		String[] res = readToString(addr);
//		NFA2DFA test = new NFA2DFA();
//		test.createNFA(res);
//		test.showNFA();
//		for(int i=0;i<test.NFA_move[(int)'A'][(int)'a'].len;i++){
//			System.out.println(test.NFA_move[(int)'A'][(int)'a'].set[i]);
//		}
//		String addr = "D:\\g.txt";
		String addr ="resource/Lexgrammar.txt";//相对路径下读文法文件
		String[] res = readToString(addr);
		
		NFA_Analysis test = new NFA_Analysis();
		test.createNFA(res);
//		test.showNFA();
		
//		String srcaddr = "D:\\src.txt";
		String srcaddr ="resource/program.txt";//相对路径下读程序代码文件
		ScanProgram sp = new ScanProgram(srcaddr);
		sp.scan();
		sp.flitter();
		char[] resProgram = sp.getdone();
		test.analysis(resProgram);
		test.forPhaser();
		outData = test.getResList();
		System.out.println("_______________________");
		OutRes(test.getList());
		OutRes(test.getResList());
		for(int i=0;i<test.resList.length();i++){
			System.out.print(test.resList.charAt(i));
		}
//		for(int i=0;i<resProgram.length;i++){
//			System.out.println(resProgram[i]);
//		}//看扫描后的字符
	}

}
