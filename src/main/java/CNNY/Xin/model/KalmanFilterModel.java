package CNNY.Xin.model;

import Jama.Matrix;

public class KalmanFilterModel {

	// system matrix
	public Matrix A;
	public Matrix G;
	public Matrix H;
	public Matrix I;

	// noise matrix
	public Matrix Q;
	public Matrix R;
	
	// filter parameter
	public Matrix X_predict;
	public Matrix P_predict;
	public Matrix X_filtered;
	public Matrix P;
	public Matrix K;
	public Matrix e;
}
