from fastapi import FastAPI
from stock_data_service import get_historical_stock_data

app = FastAPI()


@app.get("/stock/data")
async def root(ticker: str, period: str, interval: str):
    e = get_historical_stock_data(ticker, period, interval)
    return {"data": e.to_dict()}

