package com.hacker.neural.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ����һ�������֪�������κ������뷢���ʼ����н�����
 * @author Bin Xu
 * 			��xubin5@jd.com
 *
 */
public class Perceptron {
	private static final int T = 100; // ����������

	/**
	 * 
	 * @param dataSet
	 *            �����ݼ�
	 * @param dataWeight
	 *            ��ÿ�����ݵ�Ȩ��
	 * @param targetData
	 *            ��Ŀ��ֵ
	 * @return ѵ���õ�Ȩ��
	 */
	public ArrayList<Double> getWeightVector(
			ArrayList<ArrayList<Double>> dataSet, ArrayList<Double> dataWeight,
			ArrayList<Double> targetData) {
		// ���ݼ��ϵĳ���-�������ѵ������
		int dataLength = 1;
		// ����ά��-�����������ά
		int weightLength = 1;
		if (null == dataSet) {
			return null;
		} else {
			dataLength = dataSet.get(0).size();// +1 ����ƫִֵ�㷨
			weightLength = dataSet.size();
		}

		for (int j = 0; j < dataLength; j++) {
			Double WX = 1.0;
			Double b = 0d;
			// ����W*X
			for (int i = 0; i < weightLength + 1; i++) {
				if (i == weightLength) {
					b = dataWeight.get(i);
				} else {
					ArrayList<Double> dataItem = null;
					// x1...xn
					Double xj = 1.0;

					dataItem = dataSet.get(i);
					xj = dataItem.get(j);

					Double wj = dataWeight.get(i);
					WX = WX + (xj * wj);
				}

			}

			// ���ź��� handlimts
			Double oj = (WX + b) >= 0 ? 1.0 : -1.0;
			// �������
			Double dj = targetData.get(j);
			// ��ȷ���಻����Ȩֵ
			if (oj.doubleValue() == dj.doubleValue())
				continue;
			// ѧϰ��
			double n = 0.001;
			// ����Ȩֵ
			for (int i = 0; i < weightLength + 1; i++) {
				if (i != weightLength) {
					ArrayList<Double> dataItem = dataSet.get(i);
					Double xj = dataItem.get(j);
					Double Vw = n * (dj - oj) * xj;
					dataWeight.set(i, dataWeight.get(i) + Vw);
				} else {
					Double Vw = n * (dj - oj) * 1;
					dataWeight.set(i, dataWeight.get(i) + Vw);
				}

			}
		}
		return dataWeight;
	}

	public static void main(String[] args) {
		System.out.println("������"+ args[0]);
		File f = new File(args[0]);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(f));
			String str = null;
			try {
				ArrayList<ArrayList<Double>> dataSet = new ArrayList<ArrayList<Double>>();
				while ((str = reader.readLine()) != null) {
					ArrayList<Double> tmpList = new ArrayList<Double>();
					String[] s = str.split("\t");
					for (int i = 0; i < s.length; i++) {
						tmpList.add(Double.parseDouble(s[i]));
					}
					dataSet.add(tmpList);
				}
				ArrayList<Double> dataWeight = new ArrayList<Double>();
				dataWeight.add(1d);
				dataWeight.add(1d);
				dataWeight.add(1d);
				dataWeight.add(0d);

				ArrayList<Double> targetData = new ArrayList<Double>();
				targetData.add(-1.0);
				targetData.add(1.0);
				targetData.add(1.0);
				targetData.add(1.0);
				targetData.add(-1.0);
				targetData.add(-1.0);
				targetData.add(1.0);
				targetData.add(-1.0);

				Perceptron d = new Perceptron();

				ArrayList<Double> outputW = d.getWeightVector(dataSet,
						dataWeight, targetData);
				System.out.println("ѵ��Ȩֵ��");
				for (int i = 0; i < T; i++) {
					System.out.println(d.getWeightVector(dataSet, outputW,
							targetData));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
