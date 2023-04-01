import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class StockService {

  constructor(private http: HttpClient) { }

  getStockData(ticker: string, period: string, interval: string): Observable<object> {
    return this.http.get<object>(environment.stockApiUrl + `/stock/data?ticker=${ticker}&period=${period}&interval=${interval}`)
  }

  getStockDataFormatted(stockData: any, type: string): unknown[][] {
    const stockDataObj = stockData['data' as keyof typeof stockData][type as keyof typeof stockData];
    const stockDataFormatted = [];
    for (let [date, close] of Object.entries(stockDataObj)) {
      stockDataFormatted.push([Date.parse(date), close])
    }
    return stockDataFormatted;
  }

}
