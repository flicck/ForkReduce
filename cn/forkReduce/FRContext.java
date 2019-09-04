package cn.forkReduce;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



import cn.FRContainer.SeListSerial;
import cn.FRContainer.SeListSerial.SeList.Builder;

public class FRContext {

	//这个地方先让只有一个线程操作,后续增加多线程
	private static Long index=0L;
	private static int numReduceTasks=3;
	private static ArrayList<Builder> grandList=new ArrayList<Builder>(numReduceTasks);
	private int partitionFlag=0;
	private static File[] files=new File[numReduceTasks];
	private static int count=0;	
	private boolean flag=false;
	
	static{
		URL resource = FRContext.class.getClassLoader().getResource("");
		for(int i=0;i<numReduceTasks;i++){
			grandList.add(SeListSerial.SeList.newBuilder());		
			//创建一个长度为numReduceTasks的file数组
			files[i]=new File(resource.toString().substring(6,resource.toString().length())+"tmp"+i+".txt");		
			try {
				files[i].createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	public void write(String key,Long value) {	
		
		int partitionFlag=((key.hashCode() & Integer.MAX_VALUE )% numReduceTasks);
		
		grandList.get(partitionFlag).addInnerList(grandList.get(partitionFlag).addInnerListBuilder().setKey(key).setValue(value));	
		if(ReadLine.splitSignal && ForkReduce.flag){
			//恢复
			ReadLine.splitSignal=false;
			ForkReduce.flag=false;
			
			//要溢写了
			for(int j=0;j<numReduceTasks;j++){                                  
				BufferedOutputStream bw=null;
				try {
					bw=new BufferedOutputStream(new FileOutputStream(files[j],true));				
					bw.write(grandList.get(j).build().toByteArray());
					bw.flush();
					//溢写完毕，要做清理工作
					grandList.get(j).clear();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
				}
				
	}
	


