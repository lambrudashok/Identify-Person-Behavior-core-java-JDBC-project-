package org.behaviourprediction.model;

public class PredictionModel {

	private int cluster;
	private String[] docName;
	private int arr[];
	private double arrayVectors[];
	
	
	public int getCluster() {
		return cluster;
	}

	public void setCluster(int cluster) {
		this.cluster = cluster;
	}

	public String[] getDocName() {
		return docName;
	}

	public void setDocName(String[] docName) {
		this.docName = docName;
	}

	public int[] getArr() {
		return arr;
	}

	public double[] getArrayVectors() {
		return arrayVectors;
	}

	public void setArrayVectors(double[] arrayVectors) {
		this.arrayVectors = arrayVectors;
	}

	public void setArr(int[] arr) {
		this.arr = arr;
	}
	

}
