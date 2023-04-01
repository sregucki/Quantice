export class StockIndex {

  ticker: string;
  closePriceCurrent: number;
  closePriceBefore: number;

  constructor(ticker: string, closePriceCurrent: number, closePriceBefore: number) {
    this.ticker = ticker;
    this.closePriceCurrent = closePriceCurrent;
    this.closePriceBefore = closePriceBefore;
  }

}
