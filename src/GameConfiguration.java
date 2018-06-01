
/**
 * @author Xiang Li
 *
 */
public class GameConfiguration implements GameConfig{
	
	int v,u,M,K;
	
	public GameConfiguration(int v, int u, int M, int K) {
		this.v = v;
		this.u = u;
		this.M = M;
		this.K = K;
	}

	
	@Override
	public int s() {
		return 1;
	}

	@Override
	public int v() {
		return v;
	}

	@Override
	public int u() {
		return u;
	}

	@Override
	public int M() {
		return M;
	}

	@Override
	public int K() {
		return K;
	}

 

}
