import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPool {
	private LinkedBlockingQueue<Thread> queue;	// to maintain a list of all threads that need to be executed
	private int nThreads;					  	// size of thread pool
	private List<Thread> workers;				// list of actove threads of the pool

	public MyThreadPool(int nThreads) {
		this.nThreads = nThreads;
		queue = new LinkedBlockingQueue<>();
		
		// now create nThreads and start them, the active threads will now look for any incoming task
		workers = new ArrayList<>();
		for(int i=0; i<nThreads; i++) {
			workers.add(new Thread(new MyPoolWorker()));
			workers.get(i).start(); 
		}
	}
	
	public void submit(Thread r) {
		// the run inside MyPoolWorker waits if queue is empty, so notify whenever data is inserted if any thread waiting
		synchronized(queue) {
			queue.add(r);
			queue.notify();
		}
		
	}
	
	
	private class MyPoolWorker implements Runnable{

		@Override
		public void run() {
			while(true) { 						// keep looking for new tasks to come
				Thread task = null;
				
				synchronized (queue) {			// bcz queue is a shared resource between various threads
					while(queue.isEmpty()) {		// wait for new task to come
						try { queue.wait();} 
						catch (InterruptedException e) { e.printStackTrace(); }
					}
					task = queue.poll();			// get the task
				}
				
				// here calling run instead of start since start will create a new thread which we dont want
				// we want the task to be executed inide this thread, 
				// also calling run directly makes run act as a nomal method and will thus wait to finish, so no need to join
				task.run();					// start
//				try { task.join();} 				// wait for this to finish before starting next task
//				catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
	}
	
}
