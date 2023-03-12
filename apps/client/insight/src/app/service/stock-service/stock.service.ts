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

}
