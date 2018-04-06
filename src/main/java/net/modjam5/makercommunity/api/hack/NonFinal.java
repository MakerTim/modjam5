package net.modjam5.makercommunity.api.hack;

/**
 * @author Tim Biesenbeek
 */
public class NonFinal<T> {

	private T var;

	public NonFinal() {
	}

	public NonFinal(T var) {
		this.var = var;
	}

	public T get() {
		return var;
	}

	public void set(T var) {
		this.var = var;
	}
}
