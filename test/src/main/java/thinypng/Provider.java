package thinypng;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ͼƬԴ,������
 * @author ����
 *
 */
public class Provider {
	
	private static Logger log = LoggerFactory.getLogger(Provider.class);
	
	//��Ϣ����
	private BlockingQueue<File> messageQueue;
	
	//ͼƬԴ
	private static File src;
	
	public Provider(BlockingQueue<File> messageQueue ,File src) {
		this.setMessageQueue(messageQueue);
		Provider.src = src;
	}
	
	/**
	 * ����Ҫѹ�����ļ�
	 * @param src
	 */
	private void load(File src) {
		log.info("Provider load src:{}",src.getAbsolutePath());
		//��Ҫѹ�����ļ���Ŀ¼
		if(src.isDirectory()) {
			File[] files = src.listFiles();
			Arrays.stream(files).forEach(file -> load(file));
			
		//Ҫѹ�������ļ�	
		} else if(src.isFile()) {
			//У���ļ��Ƿ���jpg��pngͼƬ
			if(this.validateImg(src)) {
				try {
					this.messageQueue.put(src);
				} catch (InterruptedException e) {
					log.info("�������ʧ�� ");
					log.error("", e);
				}
			}
		}
	}
	
	/**
	 * У��ͼƬ�Ƿ�Ϊpng��jpg��ʽ
	 * @param file
	 * @return
	 */
	private boolean validateImg(File file) {
		int loc = file.getName().lastIndexOf(".");
		String suffix = file.getName().substring(++loc);
		return "png".equals(suffix) || "jpg".equals(suffix);
	}
	
	public static void main(String[] args) {
		
	}

	public BlockingQueue<File> getMessageQueue() {
		return messageQueue;
	}

	public void setMessageQueue(BlockingQueue<File> messageQueue) {
		this.messageQueue = messageQueue;
	}

	public static File getSrc() {
		return src;
	}

	public static void setSrc(File src) {
		Provider.src = src;
	}
}