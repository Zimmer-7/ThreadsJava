package filosofos;

public class Hashi {
	public boolean inUse = false;
	
	public synchronized void PegaHashi() throws InterruptedException {
		while (inUse) {
            wait();
        }
        
		inUse = true;
        
    }
	
	public synchronized void SoltaHashi() throws InterruptedException {
		inUse = false;
        notifyAll();
    }
	
}
