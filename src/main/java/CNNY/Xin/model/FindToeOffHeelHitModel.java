package CNNY.Xin.model;

public class FindToeOffHeelHitModel {

	public Integer windowWidth;
	public Integer gradientLimitation;
	
	public Integer	distanceToLastTOHHCount;
	public Integer	distanceToLastTOHHCountLimitation;
	public Double 	pPeak;
	public Double 	pGradient;
	public Double 	pPeriod;
	public Double 	pTotal;
	
	public FindToeOffHeelHitModel() {
		
		windowWidth 		= 0;
		gradientLimitation 	= 0;
		
		distanceToLastTOHHCount = 0;
		distanceToLastTOHHCountLimitation = 300;
		pPeak 		= 0.0;
		pGradient 	= 0.0;
		pPeriod 	= 0.0;
		pTotal		= 0.0;
	}
}
