package com.hellostock.app.Indicator;

import java.util.List;

import com.hellostock.app.domain.StockHq;

public class EMA extends Indicator{

	private final int length;
	private final double multiplier;

	public EMA(final List<StockHq> qh, final int length) {
		super(qh);
		this.length = length;
		multiplier = 2. / (length + 1.);
	}

	@Override
	public double calculate() {
		List<StockHq> li = list;
		int size = li.size() - 1;
		int first = size - 2 * length + 1;
		double ema = li.get(first).getClosePrice();

		for (int i = first; i <= size; i++) {
			double barClose = li.get(i).getClosePrice();
			ema += (barClose - ema) * multiplier;
		}

		value = ema;

		return value;
	}
}
