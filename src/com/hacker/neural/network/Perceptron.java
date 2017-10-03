package com.hacker.neural.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这是一个单层感知器（有任何疑问请发送邮件进行交流）
 * @author Bin Xu
 * 			：xubin5@jd.com
 *
 */
public class Perceptron {
	private static final int T = 100; // 最大迭代次数

	/**
	 * 
	 * @param dataSet
	 *            ：数据集
	 * @param dataWeight
	 *            ：每条数据的权重
	 * @param targetData
	 *            ：目标值
	 * @return 训练好的权重
	 */
	public ArrayList<Double> getWeightVector(
			ArrayList<ArrayList<Double>> dataSet, ArrayList<Double> dataWeight,
			ArrayList<Double> targetData) {
		// 数据集合的长度-代表多少训练数据
		int dataLength = 1;
		// 数据维度-代表输入多少维
		int weightLength = 1;
		if (null == dataSet) {
			return null;
		} else {
			dataLength = dataSet.get(0).size();// +1 代表偏执值算法
			weightLength = dataSet.size();
		}

		for (int j = 0; j < dataLength; j++) {
			Double WX = 1.0;
			Double b = 0d;
			// 计算W*X
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

			// 符号函数 handlimts
			Double oj = (WX + b) >= 0 ? 1.0 : -1.0;
			// 期望输出
			Double dj = targetData.get(j);
			// 正确分类不调整权值
			if (oj.doubleValue() == dj.doubleValue())
				continue;
			// 学习率
			double n = 0.001;
			// 更改权值
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
		System.out.println("参数："+ args[0]);
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
				System.out.println("训练权值：");
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
