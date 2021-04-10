package model;

import java.io.Serializable;

public class GridCell implements Serializable{

	private static final long serialVersionUID = 1L;
	private double vegetationDensity;
	private boolean onFire;
	
	public GridCell(double vegetationDensity, boolean onFire)
	{
		this.vegetationDensity = vegetationDensity;
		this.onFire = onFire;
	}
	
	public double getVegetationDensity() 
	{
		return vegetationDensity;
	}
	
	public boolean getOnFire()
	{
		return onFire;
	}
	
	public void setVegetationDensity(double vegetationDensity)
	{
		this.vegetationDensity = vegetationDensity;
	}
	
	public void setOnFire(boolean onFire) 
	{
		this.onFire = onFire;
	}
}