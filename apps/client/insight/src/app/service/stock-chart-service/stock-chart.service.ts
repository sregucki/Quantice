import {Injectable} from '@angular/core';
import {StockChart} from "angular-highcharts";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class StockChartService {

  constructor(private http: HttpClient) {
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
        name: ticker,
        data: data
      }]
    })
  }

}
