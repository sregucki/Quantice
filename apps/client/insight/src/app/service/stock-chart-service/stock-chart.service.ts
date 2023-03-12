import {Injectable} from '@angular/core';
import {StockChart} from "angular-highcharts";

@Injectable({
  providedIn: 'root'
})
export class StockChartService {

  constructor() {
  }

  getLineChart(ticker: string, data: unknown[][]): StockChart {
    return new StockChart({
      chart: {
        backgroundColor: 'white'
      },
      rangeSelector: {
        selected: 1
      },
      series: [{
        type: 'line',
        tooltip: {
          valueDecimals: 2
        },
        color: '#3677a8',
        name: ticker,
        data: data
      }]
    })
  }

}
