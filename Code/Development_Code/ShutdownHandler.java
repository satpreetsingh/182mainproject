

/**
 * Handles shutting down any number of items that have the shutdown interface.
 * @author ben
 *
 */
public class  ShutdownHandler extends Thread 
{

        private ShutdownInterface app;

        /**
         * Create an object to watch.
         * @param app Object to watch.
         */
        public ShutdownHandler(ShutdownInterface app) 
        {
        	this.app = app;
        }

        /**
         * When called run shutdown on the object.
         */
        public void run() 
        {
        	app.shutDown();
        }
}
