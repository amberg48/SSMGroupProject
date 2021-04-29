package model;

import java.io.Serializable;

public class GridCell implements Serializable{

	private static final long serialVersionUID = 1L;
	private int vegetationDensity;
	private boolean onFire;
	
	public GridCell(int vegetationDensity, boolean onFire)
	{
		this.vegetationDensity = vegetationDensity;
		this.onFire = onFire;
	}
	
	public int getVegetationDensity()
	{
		return vegetationDensity;
	}
	
	public boolean getOnFire()
	{
		return onFire;
	}
	
	public void setVegetationDensity(int vegetationDensity)
	{
		this.vegetationDensity = vegetationDensity;
	}
	
	public void setOnFire(boolean onFire) 
	{
		this.onFire = onFire;
	}
}