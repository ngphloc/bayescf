package net.hudup.sparse;

import java.util.List;

import net.hudup.core.Util;
import net.hudup.core.data.RatingMatrix;
import net.hudup.core.data.RatingMatrixMetadata;
import net.hudup.core.data.RatingVector;


/**
 * 
 * @author Loc Nguyen
 * @version 10.0
 */
public class Reducer {
	
	
	/**
	 * 
	 */
	protected double reduceRatio = 0.5;
	
	
	/**
	 * 
	 */
	public Reducer(double reduceRatio) {
		this.reduceRatio = reduceRatio;
	}
	
	
	/**
	 * 
	 * @param matrix
	 * @param updateMetadata
	 * @return reduced matrix
	 */
	public RatingMatrix simplyReduce(RatingMatrix matrix, boolean updateMetadata) {
		
		List<double[]> newMatrix = Util.newList();
		List<Integer> newRowIdList = Util.newList(); 
		
		for (int i = 0; i < matrix.matrix.length; i++) {
			
			double[] row = matrix.matrix[i];
			
			int count = RatingVector.count(row, true);
			double cRatio = (double)count / (double)matrix.columnIdList.size();
			
			if (cRatio >= 1.0 - reduceRatio) {
				newMatrix.add(row);
				newRowIdList.add(matrix.rowIdList.get(i));
			}
		}
		
		List<Integer> newColIdList = Util.newList();
		newColIdList.addAll(matrix.columnIdList);
		
		RatingMatrix reducedMatrix = RatingMatrix.assign(
				newMatrix.toArray(new double[0][]), 
				newRowIdList, 
				newColIdList,
				(RatingMatrixMetadata) matrix.metadata.clone());
		
		if (updateMetadata)
			reducedMatrix.updateMetadata();
		return reducedMatrix;
	}
	
	
}
