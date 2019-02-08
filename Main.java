
public class Main {
	public static void main(String args[]) {
		// create a threadpool of size 2
		MyThreadPool pool = new MyThreadPool(2);
		
		// total 5 tasks, since thread pool is 2, they will execute 2 at a time
		for(int i=0; i<5; i++) {
			MyTask task = new MyTask(i);
			Thread t = new Thread(task);
			pool.submit(t);
		}
		
		
	}
}
