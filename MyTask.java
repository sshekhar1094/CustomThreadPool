
public class MyTask implements Runnable{
	
	private int num;
	
	public MyTask(int num) {
		this.num = num;
	}

	@Override
	public void run() {
		System.out.println("Task " + num + " is running");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Task " + num + " is finished");
	}

}
