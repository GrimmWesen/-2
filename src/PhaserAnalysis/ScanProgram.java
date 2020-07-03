package PhaserAnalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ScanProgram {
	/**
	 * @param filePath
	 * @return 
	 * 读文法转成字符串数组，以行分割
	 */
	public  String[] readToString(String filePath)
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
	
	
}
