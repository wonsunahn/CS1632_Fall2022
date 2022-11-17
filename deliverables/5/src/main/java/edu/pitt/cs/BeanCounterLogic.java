package edu.pitt.cs;

public interface BeanCounterLogic {
	/**
	 * Returns the either BeanCounterLogicImpl or BeanCounterLogicBuggy instance
	 * depending on the Config.
	 * 
	 * @param slotCount the number of slots in the machine
	 * @return BeanCounterLogic object
	 */
	public static BeanCounterLogic createInstance(int slotCount) {
		switch (Config.getLogicType()) {
			case IMPL:
				return new BeanCounterLogicImpl(slotCount);
			case BUGGY:
				return new BeanCounterLogicBuggy(slotCount);
			case SOLUTION:
				return new BeanCounterLogicSolution(slotCount);
			default:
		}
		return null;
	}

	// Public interface of BeanCounterLogic

	// No bean in that particular Y coordinate
	public static final int NO_BEAN_IN_YPOS = -1;

	// Methods
	public int getSlotCount();
	
	public int getRemainingBeanCount();

	public int getInFlightBeanXPos(int yPos);

	public int getSlotBeanCount(int i);

	public double getAverageSlotBeanCount();

	public void upperHalf();

	public void lowerHalf();

	public void reset(Bean[] beans);

	public void repeat();

	public boolean advanceStep();
}