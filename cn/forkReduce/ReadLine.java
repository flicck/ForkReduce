package cn.forkReduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ReadLine {
	//给FRContext发一个信号,要溢写了
	public static boolean splitSignal=false;
	
	public static void main(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		InputStream is = ReadLine.class.getClassLoader().getResourceAsStream("1.txt");
		String resource = ReadLine.class.getClassLoader().getResource("1.txt").getPath();
		File file=new File(resource);
		
		BufferedReader br =new BufferedReader(new InputStreamReader(is));
		String tmp=null;
		
		Long position=0L;
		ForkReduce fr=new ForkReduce();
		FRContext frc=new FRContext();
		Long tmpLength=0L;
		try {			
			RandomAccessFile raf=new RandomAccessFile(file,"r");
			Long remainlength = raf.length();
			while((tmp=br.readLine())!=null){
				position++;
				int length2 = tmp.getBytes().length;
				tmpLength=tmpLength+length2;
				remainlength=remainlength-length2;
				//100个字节了，改变信号,剩余只有不到100字节了,改变信号
				if(tmpLength>=100 ||remainlength<100){
					splitSignal=true;
					//恢复
					tmpLength=0L;
				}
				fr.fork(position,tmp,frc);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//调用reduce方法
		//1.需要读取文件中的数据
		ForkJoinPool pool = new ForkJoinPool();
		FRTask task=new FRTask(3L);
		pool.submit(task);
		try {
			pool.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool.shutdown();
		long currentTimeMillis1=System.currentTimeMillis();
		System.out.println(currentTimeMillis1-currentTimeMillis);
	}
	
}
