package us.jblog.aggregator.exception;

/**
 * This class was added as part of step 45, in order to parse the feeds
 * and convert to Items list.
 * @author lokesh
 *
 */
public class RssException extends RuntimeException{

	public RssException(Throwable cause) {
		super(cause);
	}
	
}
