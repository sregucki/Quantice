from fastapi import FastAPI
from starlette.middleware.cors import CORSMiddleware

from stock_data_service import get_historical_stock_data

app = FastAPI()

# TODO enable only fixed origins

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@app.get("/stock/data")
async def root(ticker: str, period: str, interval: str):
    e = get_historical_stock_data(ticker, period, interval)
    return {"data": e.to_dict()}
