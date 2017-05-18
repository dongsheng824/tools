package com.guods.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 执行windows的cmd命令
 * @author guods
 *
 */
public class ProcessCmd {

	public String execCmd(String[] args) throws IOException{
		//执行命令
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		Process process = processBuilder.start();
		//输出结果
		InputStream inputStream = process.getInputStream();
		//获取输出结果
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferReader=new BufferedReader(new InputStreamReader(inputStream,"gbk"));
        String str;
		while ((str = bufferReader.readLine()) != null) {
			stringBuffer.append(str).append(System.lineSeparator());
		}
		return stringBuffer.toString();
	}
}
