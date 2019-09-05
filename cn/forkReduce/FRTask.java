package cn.forkReduce;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.RecursiveAction;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.FRContainer.SeListSerial;

import cn.FRContainer.SeListSerial.SeList;
import cn.FRContainer.SeListSerial.SeList.FRText;

public class FRTask extends RecursiveAction {
	private Long numOfTasks;
	private int index=-1;;
	//TreeMap
	private TreeMap<String,Long> tm =null;
	public FRTask(Long numReduceTasks){
		numOfTasks=numReduceTasks;
	}
	public FRTask(int index){
		this.index=index;
		tm=new TreeMap<>();
	}
	@Override
	protected void compute() {
		//根据传入的numReduceTasks来判断需要拆分多少个任务
		if(index!=-1){
			//不等于0，说明已经给index值了，这里就执行任务
			InputStream is = FRTask.class.getClassLoader().getResourceAsStream("tmp"+index+".txt");
			BufferedInputStream br = new BufferedInputStream(is);
			//这个地方需要反序列化
			int tmp=0;
			byte[] bigbuffer=new byte[10485760];
			
			byte[] shortbuffer=null;
			try {
				while((tmp=br.read(bigbuffer))!=-1){
					shortbuffer=new byte[tmp];
					System.arraycopy(bigbuffer,0,shortbuffer,0,tmp);
		
				
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}finally{
				try {
					br.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			};
			try {
				
				SeList parseFrom = SeListSerial.SeList.parseFrom(shortbuffer);
				List<FRText> frtextsList = parseFrom.getFrtextsList();
				String tmpstr=null;
				for(FRText fr:frtextsList){
					tmpstr=fr.getKey();	
					if(tm.containsKey(tmpstr)){
						//后续这里要给Forkreduce的reduce开放一个方法
						
						tm.put(tmpstr, tm.get(tmpstr)+1);
					}else{
						tm.put(fr.getKey(),1L);
					}
				}
				System.out.println(tm);
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}
		}else{
			FRTask task1=new FRTask(0);
			FRTask task2=new FRTask(1);
			FRTask task3=new FRTask(2);
			task1.fork();
			task2.fork();
			task3.fork();
		}
	}

}
